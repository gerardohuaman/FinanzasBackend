package com.example.finanzasbackend.util;
import com.example.finanzasbackend.CalculadoraFinanciera;
import com.example.finanzasbackend.dtos.SimulacionInputDTO;
import com.example.finanzasbackend.dtos.SimulacionResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraFinancieraTest {
    @Test
    void testConvertirATEM_DesdeTEA(){
        //Escenario: El banco ofrece una TEA del 12.5%
        //Formula esperada: (1 + 0.125)^(30/360) - 1
        double temObtenida = CalculadoraFinanciera.convertirATEM("TEA", 12.5, null);

        assertEquals(0.0098635, temObtenida, 0.0001, "La conversion de TEA a TEM falló");
    }

    @Test
    void testConvertirATEM_DesdeTNA(){
        //Escenario: El banco ofrece una TNA del 12% con capitalizacion mensual
        //TNA 12% capitalizable mensualmente equivale exactamente a una TEM de 1% (0.01)
        double temObtenida = CalculadoraFinanciera.convertirATEM("TNA", 12.0, "MENSUAL");

        assertEquals(0.01, temObtenida, 0.0001, "La conversion de TNA a TEM falló");
    }

    @Test
    void testGenerarCronogramaCompraInteligente_SinGracia(){
        //1. Preparamos el DTO simulando lo que enviaria el frontend
        SimulacionInputDTO input = new SimulacionInputDTO();
        input.setPorcentaje_inicial(20.0);
        input.setPorcentaje_balon(40.0);
        input.setPlazo_meses(24);
        input.setTipo_tasa("TEA");
        input.setValor_tasa(15.0);
        input.setMeses_gracia(0);
        input.setTipo_gracia("SIN_GRACIA");
        input.setTasa_desgravamen(0.05);
        input.setTasa_vehicular(3.0);
        input.setCok(10.0);

        double precioVehiculo = 20000.0;

        //2. Ejecutamos el motor del calculo
        SimulacionResponseDTO response = CalculadoraFinanciera.generarCronogramaCompraInteligente(precioVehiculo, input);

        //3. Verifacion de los resultados macroeconomicos
        assertNotNull(response, "La respuesta no deberia ser nula");
        assertEquals(16000.0, response.getMonto_financiado(), "El monto a financiar debe ser Precio - Cuota Inicial");
        assertEquals(8000.0, response.getCuota_balon(), "La cuota balon debe ser el 40% de 20,000");

        //4. Verificamos el tamaño del cronograma
        assertEquals(24, response.getCronograma().size(), "El cronograma debe tener exactamente 24 cuotas generadas");

        //5. Verificamos la extincion de la deuda (Regla de oro contable)
        double saldoFinalUltimaCuota = response.getCronograma().get(23).getSaldo_final();
        assertEquals(0.0, saldoFinalUltimaCuota, 0.05, "El saldo final en el mes 24 debe ser cero (la deuda se extinguio)");

        //6. Verificamos que los indicadores de transparencia SBS se hayan procesado
        assertTrue(response.getTcea() > 0, "La TCEA debio calcularse y ser mayor a 0");
        assertTrue(response.getTir() > 0, "La TIR debio calcularse exitosamente");
        assertNotEquals(0.0, response.getVan(), "El VAN debe tener un valor calculado");
    }

    @Test
    void testGenerarCronogramaCompraInteligente_conGraciatotal(){
        //Configuramos la misma simulacion, pero con 2 mese de gracia total
        SimulacionInputDTO input = new SimulacionInputDTO();
        input.setPorcentaje_inicial(20.0);
        input.setPorcentaje_balon(40.0);
        input.setPlazo_meses(24);
        input.setTipo_tasa("TEA");
        input.setValor_tasa(15.0);
        input.setMeses_gracia(2);
        input.setTipo_gracia("TOTAL");
        input.setTasa_desgravamen(0.0);
        input.setTasa_vehicular(0.0);
        input.setCok(10.0);

        double precioVehiculo = 20000.0;
        SimulacionResponseDTO response = CalculadoraFinanciera.generarCronogramaCompraInteligente(precioVehiculo, input);

        //En gracia total, la cuota tolta de los primeros meses debe ser 0.0
        double cuotaMes1 = response.getCronograma().get(0).getCuota_total();
        double cuotaMes2 = response.getCronograma().get(1).getCuota_total();

        assertEquals(0.0, cuotaMes1, "En Gracia Total, el cliente no desembolsa dinero en el mes 1");
        assertEquals(0.0, cuotaMes2, "En Gracia Total, el cliente no desembolsa dinera en el mes 2");

        //El saldo final del mes 1 debe ser MAYOR al saldo inicial (16000), porque el interes se capitalizo
        double saldoFinalMes1 = response.getCronograma().get(0).getSaldo_final();
        assertTrue(saldoFinalMes1 > 16000.0, "El saldo debio incrementarse por la capitalizacion de intereses en el mes de gracia");
    }

    //No tiene capitalizacion
    @Test
    void testCompraInteligente_ConGraciaParcialAislada(){
        //Este test no contiene la capitalizacion
        SimulacionInputDTO input = new SimulacionInputDTO();
        input.setPorcentaje_inicial(20.0);
        input.setPorcentaje_balon(40.0);
        input.setPlazo_meses(24);
        input.setTipo_tasa("TEA");
        input.setValor_tasa(12.0);
        input.setMeses_gracia(2);
        input.setTipo_gracia("PARCIAL");
        input.setTasa_desgravamen(0.0);
        input.setTasa_vehicular(0.0);
        input.setCok(10.0);

        SimulacionResponseDTO response = CalculadoraFinanciera.generarCronogramaCompraInteligente(20000.0, input);

        //En gracia parcial la amortizacion es 0 pero la cuota total es igual al interes del mes
        double montoFinanciado = response.getMonto_financiado();
        double tem = CalculadoraFinanciera.convertirATEM("TEA", 12.0, null);
        double interesEsperadoMes1 = montoFinanciado * tem;

        assertEquals(0.0, response.getCronograma().get(0).getAmortizacion(), 0.01, "En Gracia Parcial la amortizacion debe ser cero");
        assertEquals(interesEsperadoMes1, response.getCronograma().get(0).getCuota_total(),0.05, "En Gracia Parcial el saldo deudor no debe variar");
        assertEquals(montoFinanciado, response.getCronograma().get(0).getSaldo_final(), 0.01, "El calculo de la cuota total en gracia parcial difiere del estandar contable");
        assertEquals(0.0, response.getCronograma().get(23).getSaldo_final(), 0.05, "El cronograma de Compra Inteligente con Gracia Parcial no extinguio la deuda a cero");
    }

    @Test
    void testCompraInteligente_DesdeTNA_CapitalizacionDiaria() {
        SimulacionInputDTO input = new SimulacionInputDTO();
        input.setPorcentaje_inicial(10.0);
        input.setPorcentaje_balon(30.0);   // <-- BALÓN OBLIGATORIO ACTIVO
        input.setPlazo_meses(12);
        input.setTipo_tasa("TNA");
        input.setValor_tasa(14.0);
        input.setCapitalizacion("DIARIA"); // Conversión bajo año comercial de 360 días
        input.setMeses_gracia(0);
        input.setTipo_gracia("SIN_GRACIA");
        input.setTasa_desgravamen(0.05);
        input.setTasa_vehicular(3.0);
        input.setCok(12.0);

        SimulacionResponseDTO response = CalculadoraFinanciera.generarCronogramaCompraInteligente(15000.0, input);

        assertNotNull(response);
        // Verifica la extinción exacta procesando la tasa nominal compleja
        assertEquals(0.0, response.getCronograma().get(11).getSaldo_final(), 0.05);
    }
}
