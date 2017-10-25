package com.exe.jpg.nowuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exe.jpg.nowuff.API.APIService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity
{
    private CompositeDisposable disposable;
    private APIService service = APIController.GetUserService();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registrar");

        disposable = new CompositeDisposable();

        disposable.add(service.getUserData(SessionController.getInstance().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                    if(r.getData().getCampus() != -1)
                        selectCampus(r.getData().getCampus());
                }));
    }

    public void ChoosePressed(View v){
        int selected = -1;
        switch(v.getId())
        {
            case R.id.choose_gragoata:
                selected = 0;
                break;
            case R.id.choose_praia:
                selected = 1;
                break;
            case R.id.choose_valonguinho:
                selected = 2;
                break;
        }
        selectCampus(selected);
    }

    private void selectCampus(int selected)
    {
        disposable.add(service.updateUserCampus(SessionController.getInstance().getId(), selected)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(r -> {
            startActivity(new Intent(this, MainActivity.class).putExtra("currentPage", selected));
            finish();
        }));

    }

    @Override
    public void onDestroy(){
        disposable.clear();
        super.onDestroy();
    }
}
