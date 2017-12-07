package com.gmonetix.bookspivot.activity;

import android.content.SharedPreferences;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.gmonetix.bookspivot.R;

public class SplashActivity extends AppCompatActivity {
    public static String str_login_test;
    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sh = getSharedPreferences("myprefe", 0);
        editor = sh.edit();
        str_login_test = sh.getString("loginTest", null);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                /*
                 * if user login test is true on oncreate then redirect the user
                 * to result page
                 */

                if (str_login_test != null
                        && !str_login_test.toString().trim().equals("")) {
                    Intent send = new Intent(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(send);
                }
                /*
                 * if user login test is false on oncreate then redirect the
                 * user to login & registration page
                 */
                else {

                    Intent send = new Intent(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(send);

                }
                finish();
            }

        }, 3000);
    }

    public boolean containsPass(String str){

        return  sh.contains(str)?true:false;

    }
}