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

public class MainActivity extends AppCompatActivity
{
    private int currentPage = 0;
    private ImageView backgroundView;
    private TextView titleView;

    private RecyclerView recycler;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Recentes");

        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(new String[]{"Teste 123 123 123"});
       recycler.setAdapter(adapter);

        Intent extra = getIntent();
        currentPage = extra.getIntExtra("currentPage", 0);

        backgroundView = (ImageView) findViewById(R.id.background_view);
        titleView = (TextView) findViewById(R.id.title_view);
        Invalidate();
    }

    public void NewAlertPressed(View v){
        startActivity(new Intent(this, NewAlertActivity.class));
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

    }

    public void NextPressed(View v){
        currentPage = currentPage < 3 - 1 ? currentPage + 1 : 0;
        Invalidate();
    }

    public void BackPressed(View v)
    {
        currentPage = currentPage > 0 ? currentPage - 1 : 3 - 1;
        Invalidate();
    }


    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
        private String[] data;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public ImageView[] hearts;
            public ViewHolder(View v) {
                super(v);
                textView = (TextView) v.findViewById(R.id.text_view);
                hearts = new ImageView[]{
                    (ImageView) v.findViewById(R.id.heart_1),
                    (ImageView) v.findViewById(R.id.heart_2),
                    (ImageView) v.findViewById(R.id.heart_2)
                };
            }
        }

        public MainAdapter(String[] data) {
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
            holder.textView.setText(data[position]);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }
    }
}
