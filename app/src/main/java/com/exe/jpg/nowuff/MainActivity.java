package com.exe.jpg.nowuff;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exe.jpg.nowuff.API.APIService;
import com.exe.jpg.nowuff.API.Model.AlertModel;
import com.tapadoo.alerter.Alerter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements INotificationListener
{
    private int currentPage = 0;
    private ImageView backgroundView;
    private TextView titleView;

    private RecyclerView recycler;
    private MainAdapter adapter;

    private CompositeDisposable disposable;
    private APIService service;

    private AlertModel[][] alerts;
    private boolean firstFetch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Recentes");

        alerts = new AlertModel[3][];

        disposable = new CompositeDisposable();
        service = APIController.GetUserService();

        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        final Intent extra = getIntent();
        currentPage = extra.getIntExtra("currentPage", 0);

        backgroundView = (ImageView) findViewById(R.id.background_view);
        titleView = (TextView) findViewById(R.id.title_view);

        final SessionController session = SessionController.getInstance();
        if(session.isPendingFcmTokenUpdate())
        {
            disposable.add(service.updateUserFcmToken(session.getId(), session.getFcmToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(r -> {
                if(r.getStatus().equals("200"))
                    session.removePendingFcmTokenUpdate();
            }));
        }
    }

    public void NewAlertPressed(View v){
        startActivity(new Intent(this, NewAlertActivity.class).putExtra("campus", currentPage));
    }

    private void Invalidate(){
        switch(currentPage)
        {
            case 0:
                backgroundView.setImageResource(R.drawable.gragoata);
                titleView.setText("Gragoatá");
                break;
            case 1:
                backgroundView.setImageResource(R.drawable.praia);
                titleView.setText("Praia Vermelha");
                break;
            case 2:
                backgroundView.setImageResource(R.drawable.valonguinho);
                titleView.setText("Valonguinho");
                break;
        }
        if(alerts[currentPage] != null)
        {
            adapter = new MainAdapter(alerts[currentPage]);
            recycler.setAdapter(adapter);
        }
    }

    private void Refresh(){
        for(int i = 0; i<3; i++)
        {
            final int _i = i;
            disposable.add(service.getAlertsData(_i)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(r -> {
                        alerts[_i] = r.getData().toArray(new AlertModel[r.getData().size()]);

                        if(_i == currentPage)
                            Invalidate();
                    }, err -> {
                        if(firstFetch)
                        {
                            internetError();
                            firstFetch = false;
                        }
                    })
            );
        }
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

    public void NextPressed(View v){
        currentPage = currentPage < 3 - 1 ? currentPage + 1 : 0;
        Invalidate();
        Refresh();
    }

    public void BackPressed(View v)
    {
        currentPage = currentPage > 0 ? currentPage - 1 : 3 - 1;
        Invalidate();
        Refresh();
    }


    @Override
    public void onDestroy(){
        disposable.clear();
        super.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();
        NotificationController.getInstance().addListener(this);
        Refresh();
    }

    @Override
    public  void onPause(){
        super.onPause();
        NotificationController.getInstance().removeListener(this);
    }

    @Override
    public void onReceiveNotification(int id, String title, String body, ArrayMap<String, String> data) {
        Alerter.create(this)
                .setTitle(title)
                .setText(body)
                .setDuration(5000)
                .enableIconPulse(true)
                .setBackgroundColorRes(R.color.colorAccent)
                .show().findViewById(R.id.pbProgress).setVisibility(View.GONE);

        runOnUiThread(this::onResume);
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
        private AlertModel[] data;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public ImageView[] hearts;
            public ViewHolder(View v) {
                super(v);
                textView = (TextView) v.findViewById(R.id.text_view);
                hearts = new ImageView[]{
                    (ImageView) v.findViewById(R.id.heart_1),
                    (ImageView) v.findViewById(R.id.heart_2),
                    (ImageView) v.findViewById(R.id.heart_3)
                };
            }
        }

        public MainAdapter(AlertModel[] data) {
            this.data = data;
        }

        @Override
        public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
            final ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(data[position].getText());
            holder.hearts[1].setImageResource(data[position].getRating() >= 1.5f ? R.drawable.ic_heart_on : R.drawable.ic_heart_off);
            holder.hearts[2].setImageResource(data[position].getRating() >= 2.5f ? R.drawable.ic_heart_on : R.drawable.ic_heart_off);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }
    }
}
