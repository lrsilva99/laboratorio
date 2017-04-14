package com.labotech.lims.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

/**
 * Created by Leonardo on 24/03/2017.
 */
public class Utility_Date {
    /*
    Retornar o ano atual
     */

    public static int getAnoCorrente (){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static ZonedDateTime getDataHoraAtual(){
        ZoneId fusoHorarioDeSaoPaulo = ZoneId.of("America/Sao_Paulo");
        return  ZonedDateTime.now(fusoHorarioDeSaoPaulo);
    }

}
