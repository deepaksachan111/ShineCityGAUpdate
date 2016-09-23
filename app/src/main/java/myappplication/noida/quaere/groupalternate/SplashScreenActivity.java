package myappplication.noida.quaere.groupalternate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MotionEvent;

import myappplication.noida.quaere.groupalternate.swipetab.MainTabActivity;

public class SplashScreenActivity extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 2000;
    NetworkInfo netInfo;
    AlertDialog.Builder builder;
    ConnectivityManager conMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


         conMgr = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
       netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null) {

            // Animation.Description.setVisibility(View.INVISIBLE);
            builder = new AlertDialog.Builder(SplashScreenActivity.this)
                    .setTitle("Connection Failed !")
                    .setMessage("Please enable Internet Connection!! ")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            builder.show();
        }
        else{

           Thread splashTread = new Thread() {
                @Override
                public void run() {
                    try {
                        int waited = 0;
                        while (_active && (waited < _splashTime)) {
                            sleep(1000);
                            if (_active) {
                                waited += 1000;
                            }
                        }
                    } catch (InterruptedException e) {
                        // do nothing
                    } finally {

                            finish();
                            startActivity(new Intent(SplashScreenActivity.this, MainTabActivity.class));
                                            }
                }
            };
            splashTread.start();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}
