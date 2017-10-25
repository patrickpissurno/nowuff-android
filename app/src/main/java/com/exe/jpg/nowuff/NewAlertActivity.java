package com.exe.jpg.nowuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.exe.jpg.nowuff.API.APIService;
import com.tapadoo.alerter.Alerter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewAlertActivity extends AppCompatActivity
{
    private EditText editText;
    private CompositeDisposable disposable;
    private int campus;
    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alert);
        setTitle("Novo Aviso");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        editText = (EditText) findViewById(R.id.edit_text);
        disposable = new CompositeDisposable();

        campus = getIntent().getIntExtra("campus", -1);

        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setRawInputType(editText.getInputType() & ~InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(loadingView == null)
            super.onBackPressed();
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

    private void error(String description){
        Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
    }

    public void PostPressed(View v){
        final String txt = editText.getText().toString().trim();
        if(txt.length() < 15)
            error("Seu alerta tem poucas informações. Detalhe mais");
        else if(txt.length() > 120)
            error("Seu alerta é grande demais. Seja mais específico");
        else
        {
            loadingView = LoadingView.Show(this);
            final APIService service = APIController.GetUserService();
            disposable.add(service.postAlert(txt, campus)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(r -> finish(), err -> internetError())
            );
        }
    }

    @Override
    public void onDestroy(){
        disposable.clear();
        super.onDestroy();
    }
}
