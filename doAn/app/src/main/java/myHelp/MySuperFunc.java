package myHelp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MySuperFunc {
    public static int[] getDateInInteger(){
        //cài đặt ngày lập phiếu
        Date date = new Date();
        LocalDate localDate = null;
        int year=0,month=0,day=0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            year  = localDate.getYear();
            month = localDate.getMonthValue();
            day   = localDate.getDayOfMonth();

        }
        return (new int[]{day,month,year});
    }
}
