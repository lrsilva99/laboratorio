package com.labotech.lims.utility;

import java.util.Calendar;

/**
 * Created by Leonardo on 24/03/2017.
 */
public class Data {
    /*
    Retornar o ano atual
     */

    public static int getAnoCorrente (){
        return Calendar.getInstance().get(Calendar.YEAR);

    }
}
