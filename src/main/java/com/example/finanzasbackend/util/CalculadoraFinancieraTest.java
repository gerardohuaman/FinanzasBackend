package com.example.finanzasbackend.util;
import com.example.finanzasbackend.CalculadoraFinanciera;
import com.example.finanzasbackend.dtos.CronogramaPagosDTO;
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
        input.setPlazo_meses(48);
        input.setTipo_tasa("TEA");
        input.setValor_tasa(12.5);
        input.setMeses_gracia(0);
        input.setTipo_gracia("SIN_GRACIA");
        input.setTasa_desgravamen(0.03);
        input.setTasa_vehicular(0.03);
        input.setCok(1);

        double precioVehiculo = 200000.0;

        //2. Ejecutamos el motor del calculo
        SimulacionResponseDTO response = CalculadoraFinanciera.generarCronogramaCompraInteligente(precioVehiculo, input);

        //PARA VISUALIZAR
        visualizarCronogramaEnConsola("Prueba sin Gracia", response);

        //3. Verifacion de los resultados macroeconomicos
        assertNotNull(response, "La respuesta no deberia ser nula");
        assertEquals(160000.0, response.getMonto_financiado(), "El monto a financiar debe ser Precio - Cuota Inicial");
        assertEquals(80000.0, response.getCuota_balon(), "La cuota balon debe ser el 40% de 20,000");

        //4. Verificamos el tamaño del cronograma
        assertEquals(48, response.getCronograma().size(), "El cronograma debe tener exactamente 48 cuotas generadas");

        //5. Verificamos la extincion de la deuda (Regla de oro contable)
        double saldoFinalUltimaCuota = response.getCronograma().get(47).getSaldo_final();
        assertEquals(0.0, saldoFinalUltimaCuota, 0.05, "El saldo final en el mes 48 debe ser cero (la deuda se extinguio)");

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
        input.setTasa_desgravamen(0.03);
        input.setTasa_vehicular(0.03);
        input.setCok(1);

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
        input.setTasa_desgravamen(0.03);
        input.setTasa_vehicular(0.03);
        input.setCok(12.0);

        SimulacionResponseDTO response = CalculadoraFinanciera.generarCronogramaCompraInteligente(15000.0, input);

        assertNotNull(response);
        // Verifica la extinción exacta procesando la tasa nominal compleja
        assertEquals(0.0, response.getCronograma().get(11).getSaldo_final(), 0.05);
    }

    private void visualizarCronogramaEnConsola(String nombreTest, SimulacionResponseDTO response){
        System.out.println("\n=====================================================================================================================================================");
        System.out.println("REPORTE DE SIMULACION: " + nombreTest);
        System.out.println("Monto Financiado: " + response.getMonto_financiado() + " | Cuota Balon: " + response.getCuota_balon());
        System.out.println(String.format(
                "TCEA: %.7f%% | TIR: %.7f%% | VAN: %.2f",
                response.getTcea() * 100,
                response.getTir() * 100,
                response.getVan()
        ));
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-12s | %-12s | %-12s | %-12s | %-14s | %-14s | %-12s | %-12s\n",
                "Mes", "Fecha", "S. Inicial", "Interes", "Amortizacion", "S. Desgravamen", "S. Vehicular", "Cuota Total", "S. Final");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");

        for (CronogramaPagosDTO cuota : response.getCronograma()){
            System.out.printf("%-5d | %-12s | %-12.2f | %-12.2f | %-12.2f | %-14.2f | %-14.2f | %-12.2f | %-12.2f\n",
                    cuota.getNumero_mes(),
                    cuota.getFecha_pago().toString(),
                    cuota.getSaldo_inicial(),
                    cuota.getInteres(),
                    cuota.getAmortizacion(),
                    cuota.getSeguro_desgravamen(),
                    cuota.getSeguro_vehicular(),
                    cuota.getCuota_total(),
                    cuota.getSaldo_final());
        }
        System.out.println("=====================================================================================================================================================\n");
    }


    @Test
    void testGenerarCronogramaCompraInteligente_Sheet2_GraciaParcial(){
        // 1. Configuración de entrada basada en "EjemplosTF_Finanzas.xlsx - Sheet2"
        SimulacionInputDTO input = new SimulacionInputDTO();
        input.setPorcentaje_inicial(20.0);
        input.setPorcentaje_balon(30.0);
        input.setPlazo_meses(36);

        // Uso de TNA del 10.5% con capitalización mensual
        input.setTipo_tasa("TNA");
        input.setValor_tasa(10.5);
        input.setCapitalizacion("MENSUAL");

        // 2 meses de Gracia Parcial
        input.setMeses_gracia(2);
        input.setTipo_gracia("PARCIAL");

        // Seguros
        input.setTasa_desgravamen(0.03); // 0.03% mensual
        input.setTasa_vehicular(0.03);   // 0.03% mensual

        // El COK en el Excel es 1% mensual (0.01).
        input.setCok(1);

        // 2. Ejecución.
        // El precio original es $25,000. El motor recibe el precio ya convertido a la moneda de evaluación (Soles).
        // $25,000 * 3.8 = S/ 95,000
        double precioVehiculoConvertido = 95000.0;

        SimulacionResponseDTO response = CalculadoraFinanciera.generarCronogramaCompraInteligente(precioVehiculoConvertido, input);

        // Visualización opcional en consola
        visualizarCronogramaEnConsola("Prueba Sheet 2 (TNA y Gracia Parcial)", response);

        // 3. Verificaciones de Datos Iniciales
        assertNotNull(response, "La respuesta no deberia ser nula");
        assertEquals(76000.0, response.getMonto_financiado(), 0.01, "El monto financiado debe ser 76,000");
        assertEquals(28500.0, response.getCuota_balon(), 0.01, "La cuota balón debe ser 28,500");
        assertEquals(36, response.getCronograma().size(), "El cronograma debe tener 36 cuotas");

        // 4. Verificación de Gracia Parcial (Mes 1 y 2)
        // En gracia parcial solo se paga Interés (665) + S.Desgravamen (22.8) + S.Vehicular (28.5) = 716.3
        assertEquals(716.30, response.getCronograma().get(0).getCuota_total(), 0.05, "La cuota del mes 1 (gracia) debe ser 716.30");
        assertEquals(0.0, response.getCronograma().get(0).getAmortizacion(), 0.01, "En gracia parcial la amortización es 0");
        assertEquals(716.30, response.getCronograma().get(1).getCuota_total(), 0.05, "La cuota del mes 2 (gracia) debe ser 716.30");

        // 5. Verificación de Cuota Regular (Inicia en Mes 3)
        assertEquals(1915.69, response.getCronograma().get(2).getCuota_total(), 0.05, "La cuota regular debe ser 1915.69");

        // 6. Verificación Cuota Final y Extinción de Deuda (Mes 36)
        assertEquals(30415.69, response.getCronograma().get(35).getCuota_total(), 0.05, "La cuota final incluye la cuota regular + balón");
        assertEquals(0.0, response.getCronograma().get(35).getSaldo_final(), 0.05, "La deuda debe extinguirse en la última cuota");

        // 7. Verificación de Indicadores Financieros (Resultados)
        assertEquals(-767.28, response.getVan(), 1.0, "El VAN debe coincidir");
        assertEquals(0.009552, response.getTir(), 0.0001, "La TIR mensual debe ser 0.9552%");
        assertEquals(0.12084, response.getTcea(), 0.0001, "La TCEA debe ser 12.084%");
    }
}
