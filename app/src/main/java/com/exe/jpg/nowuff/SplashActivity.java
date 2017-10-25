package com.exe.jpg.nowuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tapadoo.alerter.Alerter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity
{
    private CompositeDisposable disposable;
    private View button;
    private View loading;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new PreferencesController(this);
        new SessionController();
        new APIController();
        new NotificationController();

        disposable = new CompositeDisposable();
        loading = findViewById(R.id.loading);
        button = findViewById(R.id.retry_button);

        if(!SessionController.getInstance().isAuthenticated())
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        else
            CheckUserStatus();
    }

    public void RetryPressed(View v)
    {
        button.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        CheckUserStatus();
    }

    private void CheckUserStatus(){
        disposable.add(APIController.GetUserService().getUserData(SessionController.getInstance().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                    int campus = r.getData().getCampus();
                    if(campus != -1)
                        startActivity(new Intent(this, MainActivity.class).putExtra("currentPage", campus));
                    else
                        startActivity(new Intent(this, RegisterActivity.class));
                    finish();
                }, err -> internetError()));
    }

    private void internetError(){
        Alerter.create(this)
                .setTitle("Você está offline")
                .setText("Verifique sua conexão com a internet")
                .setDuration(3000)
                .enableIconPulse(true)
                .setBackgroundColorRes(R.color.colorDarkGrey)
                .show().findViewById(R.id.pbProgress).setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy(){
        disposable.clear();
        super.onDestroy();
    }
}
