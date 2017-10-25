package com.exe.jpg.nowuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.exe.jpg.nowuff.API.AuthService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.tapadoo.alerter.Alerter;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity
{
    private CallbackManager callbackManager;
    private CompositeDisposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        disposable = new CompositeDisposable();

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        final AccessToken accessToken = loginResult.getAccessToken();

                        final AuthService service = APIController.GetAuthService();
                        disposable.add(service.user(accessToken.getUserId(), accessToken.getToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(r -> {
                                    SessionController.getInstance().setAuth(r.getAuthToken(), r.getId());

                                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                                    finish();
                                }, err -> internetError()));
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        internetError();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        internetError();
                    }
                });
    }

    private void internetError(){
        Alerter.create(this)
                .setTitle("Você está offline")
                .setText("Verifique sua conexão com a internet")
                .setDuration(3000)
                .enableIconPulse(true)
                .setBackgroundColorRes(R.color.colorDarkGrey)
                .show().findViewById(R.id.pbProgress).setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void LoginPressed(View v)
    {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    @Override
    public void onDestroy(){
        disposable.clear();
        super.onDestroy();
    }
}
