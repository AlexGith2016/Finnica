package com.example.jaime.finnica;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfiguracionActivity extends AppCompatActivity {


    private Button btnGuardar;
    public Conf c;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        btnGuardar = (Button) findViewById(R.id.btnEstablecer);
        comprobarContra(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        comprobarContra(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    protected void onResume() {
        super.onResume();
        comprobarContra(this);
    }

    public void comprobarContra(final Context context) {
        if (Conf.isSugarEntity(Conf.class) && Conf.count(Conf.class) > 0) {
            c = Conf.findById(Conf.class, 1);

            btnGuardar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String contraantigua = ((EditText) findViewById(R.id.contraantigua)).getText().toString();
                    String contranueva1 = ((EditText) findViewById(R.id.contranueva1)).getText().toString();
                    String contranueva2 = ((EditText) findViewById(R.id.contranueva2)).getText().toString();
                    Toast.makeText(getApplicationContext(), "contraseña antigua: " + contraantigua, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "contraseña 1: " + contranueva1, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "contraseña 2: " + contranueva2, Toast.LENGTH_SHORT).show();
                    if (contraantigua.equals(c.getContra()) && contranueva1.equals(contranueva2)) {
                        c.setContra(contranueva2);
                        c.save();
                        Toast.makeText(getApplicationContext(), "CONTRASEÑA GUARDADA", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainMenu.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Asegurese de escribir bien la contraseña", Toast.LENGTH_LONG).show();
                    }

                }
            });

        } else {
            btnGuardar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String contranueva1 = ((EditText) findViewById(R.id.contranueva1)).getText().toString();
                    String contranueva2 = ((EditText) findViewById(R.id.contranueva2)).getText().toString();

                    Toast.makeText(getApplicationContext(), "contraseña 1: " + contranueva1, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "contraseña 2: " + contranueva2, Toast.LENGTH_SHORT).show();

                    if (contranueva1.equals(contranueva2)) {
                        c = new Conf(contranueva2);
                        c.save();
                        Toast.makeText(getApplicationContext(), "CONTRASEÑA GUARDADA", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainMenu.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Asegurese de escribir bien la contraseña", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Configuracion Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}