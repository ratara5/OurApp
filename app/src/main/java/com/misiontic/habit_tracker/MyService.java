/*
package com.misiontic.habit_tracker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.misiontic.habit_tracker.db.MySQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyService extends Service {

    private MySQLiteHelper connectionBD;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        if(time=="00:00"){
            String insertQuery = "INSERT INTO habits(checked)" +
                    "VALUES('" + today + "','"+time+ "','" +checkedHabitName+"','"+ checkedHabitId +"')";

            boolean suc = connectionBD.insertData(insertQuery);

        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
*/