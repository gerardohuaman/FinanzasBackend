package com.example.finanzasbackend;

import com.example.finanzasbackend.dtos.CronogramaPagosDTO;
import com.example.finanzasbackend.dtos.SimulacionInputDTO;
import com.example.finanzasbackend.dtos.SimulacionResponseDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraFinanciera {
    public static SimulacionResponseDTO generarCronogramaCompraInteligente(double precioVehiculoConvertido, SimulacionInputDTO input){
        double cuotaInicial = precioVehiculoConvertido * (input.getPorcentaje_inicial() / 100.0);
        double cuotaBalon = precioVehiculoConvertido * (input.getPorcentaje_balon() / 100.0);
        double montoFinanciado = precioVehiculoConvertido - cuotaInicial;

        double tem = convertirATEM(input.getTipo_tasa(), input.getValor_tasa(), input.getCapitalizacion());
        double cokMensual = input.getCok()/100;

        double tasaDesgravamenMensual = input.getTasa_desgravamen() / 100.0;
        double temAjustada = tem + tasaDesgravamenMensual; // Factor ajustado para el Método Francés
        double seguroVehicularMensual = precioVehiculoConvertido * (input.getTasa_vehicular() / 100.0);

        List<CronogramaPagosDTO> cronograma = new ArrayList<>();
        double[] flujoCaja = new double[input.getPlazo_meses() + 1];
        flujoCaja[0] = montoFinanciado;

        double saldoDeudor = montoFinanciado;
        LocalDate fechaVencimiento = LocalDate.now();
        double sumaIntereses = 0;
        double sumaSeguros = 0;

        int mesesOrdinarios = input.getPlazo_meses() - input.getMeses_gracia();
        boolean cuotaCalculada = false;
        double cuotaFijaSinSV = 0;

        for(int i = 1; i <= input.getPlazo_meses(); i++){
            fechaVencimiento = fechaVencimiento.plusMonths(1);

            double saldoInicialMes = saldoDeudor;
            double interesMes = saldoInicialMes * tem;
            double sDesgravamenMes = saldoInicialMes * tasaDesgravamenMensual;

            double amortizacionMes = 0;
            double cuotaTotalMes = 0;

            if(i <= input.getMeses_gracia()){
                if("TOTAL".equalsIgnoreCase(input.getTipo_gracia())){
                    amortizacionMes = 0;
                    cuotaTotalMes = 0;
                    saldoDeudor = saldoInicialMes + interesMes;
                } else if("PARCIAL".equalsIgnoreCase(input.getTipo_gracia())){
                    amortizacionMes = 0;
                    cuotaTotalMes = interesMes + sDesgravamenMes + seguroVehicularMensual;
                    saldoDeudor = saldoInicialMes;
                }
            } else {
                if(!cuotaCalculada) {
                    double vpBalon = cuotaBalon * Math.pow(1 + temAjustada, -mesesOrdinarios);
                    double capitalRemanente = saldoDeudor - vpBalon;
                    // Cálculo de Anualidad con TEM Ajustada
                    cuotaFijaSinSV = capitalRemanente * (temAjustada / (1 - Math.pow(1 + temAjustada, -mesesOrdinarios)));
                    cuotaCalculada = true;
                }

                amortizacionMes = cuotaFijaSinSV - interesMes - sDesgravamenMes;
                cuotaTotalMes = cuotaFijaSinSV + seguroVehicularMensual;
                saldoDeudor = saldoInicialMes - amortizacionMes;
            }

            if(i == input.getPlazo_meses()){
                cuotaTotalMes += cuotaBalon;
                amortizacionMes += cuotaBalon;
                saldoDeudor -= cuotaBalon;
                if(Math.abs(saldoDeudor) < 0.1) saldoDeudor = 0.0;
            }

            // Los desembolsos se guardan en positivo para estructurar la ecuación estándar del VAN
            flujoCaja[i] = cuotaTotalMes;

            sumaIntereses += interesMes;
            sumaSeguros += (sDesgravamenMes + seguroVehicularMensual);
            CronogramaPagosDTO cuotaDTO = new CronogramaPagosDTO();
            cuotaDTO.setNumero_mes(i);
            cuotaDTO.setFecha_pago(fechaVencimiento);
            cuotaDTO.setSaldo_inicial(Math.round(saldoInicialMes * 100.0) / 100.0);
            cuotaDTO.setInteres(Math.round(interesMes * 100.0) / 100.0);
            cuotaDTO.setAmortizacion(Math.round(amortizacionMes * 100.0) / 100.0);
            cuotaDTO.setSeguro_desgravamen(Math.round(sDesgravamenMes * 100.0) / 100.0);
            cuotaDTO.setSeguro_vehicular(Math.round(seguroVehicularMensual * 100.0) / 100.0);
            cuotaDTO.setCuota_total(Math.round(cuotaTotalMes * 100.0) / 100.0);
            cuotaDTO.setSaldo_final(Math.abs(Math.round(saldoDeudor * 100.0) / 100.0));

            cronograma.add(cuotaDTO);
        }

        double van = calcularVAN(flujoCaja, cokMensual);
        double tirMensual = calcularTIR(flujoCaja);
        double tcea = Math.pow(1 + tirMensual, 12) - 1;

        SimulacionResponseDTO response = new SimulacionResponseDTO();
        response.setPrecio_vehiculo_calculado(precioVehiculoConvertido);
        response.setCuota_inicial(cuotaInicial);
        response.setMonto_financiado(montoFinanciado);
        response.setCuota_balon(cuotaBalon);

        response.setVan(Math.round(van * 100.0) / 100.0);
        response.setTir(Math.round(tirMensual * 10000000.0) / 10000000.0);
        response.setTcea(Math.round(tcea * 10000000.0) / 10000000.0);

        response.setTotal_intereses(Math.round(sumaIntereses * 100.0) / 100.0);
        response.setTotal_seguros(Math.round(sumaSeguros * 100.0) / 100.0);
        response.setCronograma(cronograma);

        return response;
    }

    public static double convertirATEM(String tipoTasa, double valorTasa, String capitalizacion){
        double tasaDecimal = valorTasa / 100.0;

        if(tipoTasa.toUpperCase().contains("NOMINAL") || tipoTasa.equalsIgnoreCase("TNA")){
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
            double tea = Math.pow(1 + (tasaDecimal / m), m) - 1;
            return Math.pow(1 + tea, 30.0 / 360.0) - 1;
        } else {
            return Math.pow(1 + tasaDecimal, 30.0 / 360.0) - 1;
        }
    }

    private static double calcularVAN(double[] flujos, double cokMensual){
        double van = 0;
        // La sumatoria de las cuotas traidas a valor presente menos el capital prestado
        for (int t = 1; t < flujos.length; t++){
            van += flujos[t] / Math.pow(1 + cokMensual, t);
        }
        return van - flujos[0];
    }

    private static double calcularTIR(double[] flujos) {
        double limiteInferior = 0.0;
        double limiteSuperior = 1.0;
        double tirEstimado = 0.0;
        double tolerancia = 0.00000001;
        int maxIteraciones = 10000;

        for (int i = 0; i < maxIteraciones; i++){
            tirEstimado = (limiteInferior + limiteSuperior) / 2.0;
            double vanEvaluado = calcularVAN(flujos, tirEstimado);

            if(Math.abs(vanEvaluado) < tolerancia){
                break;
            } else if(vanEvaluado > 0){
                limiteInferior = tirEstimado;
            } else {
                limiteSuperior = tirEstimado;
            }
        }
        return tirEstimado;
    }

}
