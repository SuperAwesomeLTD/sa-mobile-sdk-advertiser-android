package tv.superawesomeadvertiser.test.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import tv.superawesomeadvertiser.sdk.SuperAwesomeAdvertiser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SuperAwesomeAdvertiser.getInstance().handleInstall(this, new SuperAwesomeAdvertiser.Interface() {
            @Override
            public void saDidCountAnInstall(boolean success) {
                Log.d("SuperAwesomeAdvertiser", "Handle install result: " + success);
            }
        });

    }
}
