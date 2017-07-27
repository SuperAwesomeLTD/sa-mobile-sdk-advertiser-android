package tv.superawesomeadvertiser.test.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import tv.superawesome.sdk.advertiser.SAVerifyInstall;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SAVerifyInstall.getInstance().handleInstall(this, new SAVerifyInstall.Interface() {
            @Override
            public void saDidCountAnInstall(boolean success) {
                Log.d("SAVerifyInstall", "Handle install result: " + success);
            }
        });

    }
}
