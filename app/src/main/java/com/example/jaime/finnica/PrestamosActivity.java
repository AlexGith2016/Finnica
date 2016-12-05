package com.example.jaime.finnica;

import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.jaime.finnica.clases.Prestamo;
import com.example.jaime.finnica.fragmentClasses.FragmentPrestamo;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PrestamosActivity extends AppCompatActivity implements View.OnClickListener, FragmentPrestamo.InterfacePrestamo{
    Spinner spinner;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    EditText agenteP;
    EditText montoP;
    DatePicker fechaP;
    EditText descP;
    EditText cuotaP;
    Button btnIngresarP;
    FragmentPrestamo fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos);

        Resources res = getResources();
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Ingresar");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Informe");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        agenteP = (EditText)findViewById(R.id.txtAgentePres);
        montoP = (EditText)findViewById(R.id.txtMontoPres);
        fechaP = (DatePicker) findViewById(R.id.dtFechaPres);
        descP = (EditText)findViewById(R.id.txtDescPres);
        cuotaP = (EditText)findViewById(R.id.txtCuotaPres);
        btnIngresarP = (Button) findViewById(R.id.btnIngPres);

        btnIngresarP.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Prestamos Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jaime.finnica/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Prestamos Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jaime.finnica/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onClick(View v) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha = String.valueOf(fechaP.getDayOfMonth()) + "/" + String.valueOf(fechaP.getMonth()+1) + "/" + String.valueOf(fechaP.getYear());

        try {
            Prestamo prestamo =  new Prestamo(agenteP.getText().toString(), Float.parseFloat(montoP.getText().toString()), format.parse(strFecha),
                    descP.getText().toString(), Integer.parseInt(cuotaP.getText().toString()));

            prestamo.save();

            fp.agregar(prestamo);
            Toast.makeText(this, "AGREGADO",
                    Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFragmentInteractionListener() {
        fp = (FragmentPrestamo)getSupportFragmentManager().findFragmentById(R.id.fragmentP);
    }
}
