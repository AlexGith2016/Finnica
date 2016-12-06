package com.example.jaime.finnica;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaime.finnica.clases.Conf;
import com.example.jaime.finnica.clases.Gasto;
import com.example.jaime.finnica.clases.Ingresos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfiguracionActivity extends AppCompatActivity {


    private Button btnGuardar;
    public  Conf c ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);


        if(Conf.isSugarEntity(Conf.class) && Conf.count(Conf.class) > 0) {
            c = Conf.findById(Conf.class, 1);
           // EditText aux = (EditText) findViewById(R.id.editText_pass);
          //  String auxi = aux.getText().toString();

            btnGuardar = (Button)findViewById(R.id.btnEstablecer);
            btnGuardar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String contraantigua = ((EditText)findViewById(R.id.contraantigua)).getText().toString();
                    String contranueva1 = ((EditText)findViewById(R.id.contranueva1)).getText().toString();
                    String contranueva2 = ((EditText)findViewById(R.id.contranueva2)).getText().toString();
                    Toast.makeText(getApplicationContext(), "contraseña: "+contraantigua, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "contraseña 1: "+contranueva1, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "contraseña 2: "+contranueva2, Toast.LENGTH_LONG).show();
                    if (contraantigua.equals(c.getContra()) && contranueva1.equals(contranueva2)){
                        c.setContra(contranueva2);
                        c.save();
                    } else {
                        Toast.makeText(getApplicationContext(), "Asegurese de escribir bien la contraseña", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
        else{
            btnGuardar = (Button)findViewById(R.id.btnEstablecer);
            btnGuardar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String contranueva1 = ((EditText)findViewById(R.id.contranueva1)).getText().toString();
                    String contranueva2 = ((EditText)findViewById(R.id.contranueva2)).getText().toString();

                    Toast.makeText(getApplicationContext(), "contraseña 1: "+contranueva1, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "contraseña 2: "+contranueva2, Toast.LENGTH_LONG).show();

                    if (contranueva1.equals(contranueva2)){
                        c= new Conf(contranueva2);
                        c.save();
                    } else {
                        Toast.makeText(getApplicationContext(), "Asegurese de escribir bien la contraseña", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }







    }


}