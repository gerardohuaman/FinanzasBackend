package com.example.finanzasbackend;

import com.example.finanzasbackend.dtos.CronogramaPagosDTO;
import com.example.finanzasbackend.dtos.SimulacionInputDTO;
import com.example.finanzasbackend.dtos.SimulacionResponseDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraFinanciera {
    public static SimulacionResponseDTO generarCronogramaCompraInteligente(double precioVehiculoConvertido, SimulacionInputDTO input){
        // Calculos iniciales
        double cuotaInicial = precioVehiculoConvertido * (input.getPorcentaje_inicial() / 100.0);
        double cuotaBalon = precioVehiculoConvertido * (input.getPorcentaje_balon() / 100.0);
        double montoFinanciado = precioVehiculoConvertido - cuotaInicial;

        //Conversion de tasas
        double tem = convertirATEM(input.getTipo_tasa(), input.getValor_tasa(), input.getCapitalizacion());
        double cokMensual = Math.pow(1 + (input.getCok() / 100.0), 30.0 / 360.0) - 1;
        double tasaDesgravamenMensual = input.getTasa_desgravamen() / 100.0;

        //Seguro vehicular comercial
        double seguroVehicularMensual = precioVehiculoConvertido * ((input.getTasa_vehicular() / 100.0) / 12.0);

        //Preparacion del Cronograma
        List<CronogramaPagosDTO> cronograma = new ArrayList<>();
        double[] flujoCaja = new double[input.getPlazo_meses() + 1];

        //Momento 0 (Desembolso)
        flujoCaja[0] = montoFinanciado;
        double saldoDeudor = montoFinanciado;
        LocalDate fechaVencimiento = LocalDate.now();
        double sumaIntereses = 0;
        double sumaSeguros = 0;

        //Calcular los meses restantes despues de los periodos de gracia
        int mesesOrdinarios = input.getPlazo_meses() - input.getMeses_gracia();

        for(int i = 1; i <= input.getPlazo_meses(); i++){
            fechaVencimiento = fechaVencimiento.plusMonths(1);

            double saldoInicialMes = saldoDeudor;
            double interesMes = saldoInicialMes * tem;
            double sDesgravamenMes = saldoInicialMes * tasaDesgravamenMensual;

            double amortizacionMes = 0;
            double cuotaTotalMes = 0;

            //Evaluamos si estamos en periodo de gracia
            if(i <= input.getMeses_gracia()){
                if("TOTAL".equalsIgnoreCase(input.getTipo_gracia())){
                    //Gracia total seria que no paga nada, el interes se capitaliza (se suma a la deuda)
                    amortizacionMes = 0;
                    cuotaTotalMes = 0;
                    saldoDeudor = saldoInicialMes + interesMes; //El saldo empieza a crecer aqui
                } else if("PARCIAL".equalsIgnoreCase(input.getTipo_gracia())){
                    amortizacionMes = 0;
                    cuotaTotalMes = interesMes + sDesgravamenMes + seguroVehicularMensual;
                    saldoDeudor = saldoInicialMes; //El saldo se mantiene igual
                }
            } else {
                //Meses Ordinarios: Se calcula la cuota francesa
                //Formula de la cuota base incluyendo la cuota balon al final del plazo
                double cuotaBase = calcularCuotaFrancesaConBalon(saldoDeudor, tem, mesesOrdinarios, cuotaBalon);

                amortizacionMes = cuotaBase - interesMes;
                cuotaTotalMes = cuotaBase + sDesgravamenMes + seguroVehicularMensual;
                saldoDeudor = saldoInicialMes - amortizacionMes;

                mesesOrdinarios--;
            }

            if(i == input.getPlazo_meses()){
                cuotaTotalMes += cuotaBalon;
                amortizacionMes += cuotaBalon;
                saldoDeudor = 0;
            }

            //Guardar flujo para el VAN y TIR (en negativo porque sale del bolsillo del deudor)
            flujoCaja[i] = -cuotaTotalMes;

            sumaIntereses += interesMes;
            sumaSeguros += (sDesgravamenMes + seguroVehicularMensual);

            //Armar la fila del DTO
            CronogramaPagosDTO cuotaDTO = new CronogramaPagosDTO();
            cuotaDTO.setNumero_mes(i);
            cuotaDTO.setFecha_pago(fechaVencimiento);
            cuotaDTO.setSaldo_inicial(Math.round(saldoInicialMes * 100.0) / 100.0);
            cuotaDTO.setInteres(Math.round(amortizacionMes * 100.0) / 100.0);
            cuotaDTO.setAmortizacion(Math.round(sDesgravamenMes * 100.0) / 100.0);
            cuotaDTO.setSeguro_desgravamen(Math.round((sDesgravamenMes * 100.0) / 100.0));
            cuotaDTO.setSeguro_vehicular(Math.round(seguroVehicularMensual * 100.0) / 100.0);
            cuotaDTO.setCuota_total(Math.round(cuotaTotalMes * 100.0) / 100.0);
            cuotaDTO.setSaldo_final(Math.abs(Math.round(saldoDeudor * 100.0) / 100.0));

            cronograma.add(cuotaDTO);
        }

        // Calculo de indicadores
        double van = calcularVAN(flujoCaja, cokMensual);
        double tirMensual = calcularTIR(flujoCaja);
        // TCEA: La TIR mensual anualizada (Factor de 360/30 = 12 meses)
        double tcea = Math.pow(1 + tirMensual, 12) - 1;

        //Construir Respuesta
        SimulacionResponseDTO response = new SimulacionResponseDTO();
        response.setPrecio_vehiculo_calculado(precioVehiculoConvertido);
        response.setCuota_inicial(cuotaInicial);
        response.setMonto_financiado(montoFinanciado);
        response.setCuota_balon(cuotaBalon);

        response.setVan(Math.round(van * 100.0) / 100.0);
        response.setTir(Math.round(tirMensual * 10000.0) / 10000.0);
        response.setTcea(Math.round(tcea * 10000.0) / 10000.0);

        response.setTotal_intereses(Math.round(sumaIntereses * 100.0) / 100.0);
        response.setTotal_seguros(Math.round(sumaSeguros * 100.0) / 100.0);
        response.setCronograma(cronograma);

        return response;
    }

    public static double convertirATEM(String tipoTasa, double valorTasa, String capitalizacion){
        double tasaDecimal = valorTasa / 100.0;

        if(tipoTasa.toUpperCase().contains("NOMINAL") || tipoTasa.equalsIgnoreCase("TNA")){
            //Hallar cuantas capitalizaciones hay en un año (m)
            double m = 1;
            if(capitalizacion != null){
                switch (capitalizacion.toUpperCase()){
                    case "DIARIA": m = 360; break;
                    case "QUINCENAL": m = 24; break;
                    case "MENSUAL": m = 12; break;
                    case "BIMESTRAL": m = 6; break;
                    case "TRIMESTRAL": m = 4; break;
                    case "SEMESTRAL": m = 2; break;
                    default: m = 12;
                }
            }

            //Convertir Nominal a Efectiva Anual (TEA)
            double tea = Math.pow(1 + (tasaDecimal / m), m) - 1;

            //Convertir TEA a TEM
            return Math.pow(1 + tea, 30.0 / 360.0) - 1;
        } else {
            return Math.pow(1 + tasaDecimal, 30.0 / 360.0) - 1;
        }
    }

    private static double calcularCuotaFrancesaConBalon(double saldo, double tem, int mesesRestantes, double cuotaBalon){
        if(mesesRestantes <= 0) return 0;

        //La cuota balon se le resta al valor presenta descontada
        double numerador = (saldo - (cuotaBalon / Math.pow(1 + tem, mesesRestantes))) * tem;
        double denominador = 1 - Math.pow(1 + tem, -mesesRestantes);
        return numerador / denominador;
    }

    //Calcular del valor actual neto (VAN)
    private static double calcularVAN(double[] flujos, double cokMensual){
        double van = 0;
        for (int t = 0; t < flujos.length; t++){
            van += flujos[t] / Math.pow(1 + cokMensual, t);
        }
        return van;
    }

    //Calculo de la tasa interna de retorno (TIR)
    private static double calcularTIR(double[] flujos) {
        double limiteInferior = 0.0;
        double limiteSuperior = 1.0;
        double tirEstimado = 0.0;
        double tolerancia = 0.0000001;
        int maxIteraciones = 1000;

        for (int i = 0; i < maxIteraciones; i++){
            tirEstimado = (limiteInferior + limiteSuperior) / 2.0;
            double vanEvaluado = calcularVAN(flujos, tirEstimado);

            //Si el VAN es positivo, la tasa de descuento fue muy baja (la TIR real es mayor)
            //Si el VAN es negativo, la tasa de descuento fue muy alta (la TIR real es menor)
            if(Math.abs(vanEvaluado) < tolerancia){
                break; //Encontramos la raiz exacta
            } else if(vanEvaluado > 0){
                limiteInferior = tirEstimado;
            } else {
                limiteSuperior = tirEstimado;
            }
        }
        return tirEstimado;
    }

}
