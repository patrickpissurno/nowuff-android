package com.exe.jpg.nowuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        new PreferencesController(this);
        new SessionController();
        new APIController();
        new NotificationController();

        if(!SessionController.getInstance().isAuthenticated())
            startActivity(new Intent(this, LoginActivity.class));
        else
            startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }
}
