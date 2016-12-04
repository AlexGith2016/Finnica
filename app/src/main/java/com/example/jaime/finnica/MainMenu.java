package com.example.jaime.finnica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ImageView imageView= (ImageView) findViewById(R.id.imageView_menu);
        imageView.setImageResource(R.drawable.img);

    }


    public void startIngresos(View view){
        Intent intent = new Intent(this, IngresosActivity.class);
        startActivity(intent);

    }

    public void startPrestamos(View view){
        Intent intent = new Intent(this, PrestamosActivity.class);
        startActivity(intent);

    }

    public void startGastos(View view){
        Intent intent = new Intent(this, GastosActivity.class);
        startActivity(intent);

    }

    public void startDeudas(View view){
        Intent intent = new Intent(this, DeudasActivity.class);
        startActivity(intent);

    }
}
