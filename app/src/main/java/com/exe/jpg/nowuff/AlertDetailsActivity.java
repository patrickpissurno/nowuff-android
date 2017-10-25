package com.exe.jpg.nowuff;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exe.jpg.nowuff.API.APIService;
import com.tapadoo.alerter.Alerter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AlertDetailsActivity extends AppCompatActivity
{
    private ImageView[] hearts;
    private CompositeDisposable disposable;
    private APIService service;
    private int id;
    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);
        setTitle("Avalie este aviso");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        disposable = new CompositeDisposable();
        service = APIController.GetUserService();

        Intent i = getIntent();
        id = i.getIntExtra("id", -1);

        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(i.getStringExtra("text"));

        hearts = new ImageView[]{(ImageView) findViewById(R.id.heart_1), (ImageView) findViewById(R.id.heart_2), (ImageView) findViewById(R.id.heart_3)};
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ReportPressed(View v)
    {
        new AlertDialog.Builder(this)
                .setTitle("Reportar aviso")
                .setMessage("Reporte este aviso caso tenha conteúdo errado.")
                .setCancelable(false)
                .setNegativeButton("Cancelar", (d, w) -> {})
                .setPositiveButton("OK", (dialog, which) -> {
                    loadingView = LoadingView.Show(this);
                    disposable.add(service.reportAlert(id).subscribeOn(Schedulers.io())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe(r -> finish(), err -> internetError()));
                }).show();
    }

    private void internetError(){
        Alerter.create(this)
                .setTitle("Você está offline")
                .setText("Verifique sua conexão com a internet")
                .setDuration(3000)
                .enableIconPulse(true)
                .setBackgroundColorRes(R.color.colorDarkGrey)
                .show().findViewById(R.id.pbProgress).setVisibility(View.GONE);
        if(loadingView != null)
            loadingView.Hide(() -> loadingView = null);
    }

    public void HeartPressed(View v)
    {
        int i = 0;
        switch(v.getId())
        {
            case R.id.heart_1:
                i = 1;
                break;
            case R.id.heart_2:
                i = 2;
                break;
            case R.id.heart_3:
                i = 3;
                break;
        }
        hearts[1].setImageResource(i >= 2 ? R.drawable.ic_heart_on : R.drawable.ic_heart_off);
        hearts[2].setImageResource(i == 3 ? R.drawable.ic_heart_on : R.drawable.ic_heart_off);
    }

    @Override
    public void onBackPressed(){
        if(loadingView == null)
            super.onBackPressed();
    }

    @Override
    public void onDestroy(){
        disposable.clear();
        super.onDestroy();
    }
}
