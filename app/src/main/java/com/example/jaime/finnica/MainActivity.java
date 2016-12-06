package com.example.jaime.finnica;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.jaime.finnica.clases.Conf;

public class MainActivity extends AppCompatActivity {
    public Conf c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void ingresar(View view){
        Intent intent = new Intent(this, MainMenu.class);

        if(Conf.isSugarEntity(Conf.class) && Conf.count(Conf.class) > 0){
            c = Conf.findById(Conf.class, 1);
           /* if(c.getContra().isEmpty()){
                startActivity(intent);
            }
            else {*/
                // c = Conf.findById(Conf.class, 1);
                EditText aux = (EditText) findViewById(R.id.editText_pass);
                String auxi = aux.getText().toString();

                if (auxi.equals(c.getContra())) {
                    Toast.makeText(this, "la contra ingresada  es " + auxi.toString(), Toast.LENGTH_LONG).show();
                   // Toast.makeText(this, "la contra es " + c.getContra(), Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "la contra ingresada es " + auxi.toString(), Toast.LENGTH_LONG).show();
                   // Toast.makeText(this, "la contra es " + c.getContra(), Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "No puedes ingresar", Toast.LENGTH_LONG).show();
              //  }
            }
        }
        else{
            startActivity(intent);
        }

    }
}
