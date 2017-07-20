package ru.idaspin.helperforbulgakov.activities_old;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.SimpleDateFormat;

/**
 * Created by User on 17.07.2017.
 */

public class DateUtil {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getCurrentData(){
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyy h:mm a");
        return sdf.format(date);
    }
}
