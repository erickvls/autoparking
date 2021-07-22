package br.com.autoparking.service.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utils {

    public static LocalDateTime converterDataString(String dataPrevista){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dataPrevista.replace("T"," ").substring(0,16));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static boolean dataSaidaEMaiorQueEntrada(String dataPrevistaEntrada,String dataPrevistaSaida){
        LocalDateTime dataHoraSelecionadaentrada = converterDataString(dataPrevistaEntrada);
        LocalDateTime dataHoraSelecionadaSaida = converterDataString(dataPrevistaSaida);
        return dataHoraSelecionadaSaida.isBefore(dataHoraSelecionadaentrada);
    }


}
