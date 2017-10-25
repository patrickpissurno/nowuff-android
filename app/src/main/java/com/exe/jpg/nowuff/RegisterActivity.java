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

public class RegisterActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registrar");
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

        startActivity(new Intent(this, MainActivity.class).putExtra("currentPage", selected));
        finish();
    }
}
