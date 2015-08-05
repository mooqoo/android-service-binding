package practice.example.com.service_practice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * This is Sample Service to test the basic lifecycle/structure of service
 */

public class MyService extends Service {
    public static final String TAG = "MyService";

    //global variable
    private int count;
    private boolean isEven;

    //interface
    private CustomLog customLog;

    //binder
    private final IBinder mBinder = new MyBinder();
    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }


    //Constructor
    public MyService() {
        Log.i(TAG,"Constructor: ");
        count = 0;
        isEven = false;
    }

    //set the CustomLog interface
    public void setLog(CustomLog customLog) {
        this.customLog = customLog;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy: ");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG,"onBind: ");
        return mBinder;
    }

    //---------------- service public method --------------------
    public boolean isEven() {
        customLog.printLog("isEven: " + isEven);
        return isEven;
    }

    public void incrementCount() {
        count++;
        checkEven();
        customLog.printLog("incrementCount: count=" + count);
    }

    public void decrementCount() {
        count--;
        checkEven();
        customLog.printLog("decrementCount: count=" + count);
    }

    public void addCount(int num) {
        count+=num;
        checkEven();
    }

    public void minusCount(int num) {
        count-=num;
        checkEven();
    }


    //------------------------ helper method ---------------------
    private void checkEven() {
        if(count%2==0)
            isEven = true;
        else
            isEven = false;
    }

    public interface CustomLog {
        public void printLog(String msg);
    }
}
