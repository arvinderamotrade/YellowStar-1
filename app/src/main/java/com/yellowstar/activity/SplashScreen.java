package com.yellowstar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.yellowstar.database.Shaved_shared_preferences;

import com.yellowstar.R;

public class SplashScreen extends BaseActivity
{
    Shaved_shared_preferences shaved_shared_preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

     //   Utils utils = new Utils();
     //   utils.setStatusBarGradiant(SplashScreen.this);

        shaved_shared_preferences = new Shaved_shared_preferences(SplashScreen.this);

        Log.e("Enter_",""+shaved_shared_preferences.get_Login());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(shaved_shared_preferences.get_Login()==1) {
                    Intent dash_intent = new Intent(getApplicationContext(), SetLocationActivity.class);
                    startActivity(dash_intent);
                    overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
                    finish();
                }
                else
                {
                    Intent dash_intent = new Intent(getApplicationContext(), LoginPage.class);
                    startActivity(dash_intent);
                    overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
                    finish();
                }
            }
        },3000);
    }
}