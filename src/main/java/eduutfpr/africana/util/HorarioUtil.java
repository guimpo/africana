/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eduutfpr.africana.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author josevictor
 */
public class HorarioUtil {
    
    public static String getCurrentDateString(String dateFormat)
    {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        return reportDate;
    }
}
