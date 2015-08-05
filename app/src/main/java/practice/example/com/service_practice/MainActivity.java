package practice.example.com.service_practice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity implements MyService.CustomLog{
    public static final String TAG = "Main";

    public boolean mBound = false;
    public MyService mService;

    @Bind(R.id.tv_log) TextView tv_log;
    @OnClick({R.id.btn_increment, R.id.btn_decrement, R.id.btn_iseven})
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_increment:
                if(mService!=null)
                    mService.incrementCount();
                break;
            case R.id.btn_decrement:
                if(mService!=null)
                    mService.decrementCount();
                break;
            case R.id.btn_iseven:
                if(mService!=null)
                    mService.isEven();
                break;
        }
    }

    @Override
    public void printLog(String msg) {
        if(tv_log!=null)
            tv_log.setText(tv_log.getText() + msg + "\n");
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyService.MyBinder binder = (MyService.MyBinder) service;
            mService = binder.getService();
            mService.setLog(MainActivity.this);
            mBound = true;
            Log.i(TAG,"onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Log.i(TAG,"onServiceDisconnected: ");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreate: ");

        //bind view using butterknife
        ButterKnife.bind(this);
        tv_log.setMovementMethod(new ScrollingMovementMethod());


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart: ");

        // Bind to LocalService
        Intent intent = new Intent(this, MyService.class);
        mBound = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop: ");

        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
