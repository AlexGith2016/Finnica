package com.example.jaime.finnica;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaime.finnica.clases.Prestamo;
import com.example.jaime.finnica.fragmentClasses.FragmentPrestamo;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PrestamosActivity extends AppCompatActivity implements View.OnClickListener, FragmentPrestamo.InterfacePrestamo{
    Spinner spinner;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private int dia, mes, anio;
    EditText agenteP;
    EditText montoP;
    DatePicker fechaP;
    EditText descP;
    EditText cuotaP;
    Button btnIngresarP;
    FragmentPrestamo fp;
    private DatePickerDialog.OnDateSetListener dialogoFecha;
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    private TextView tvFechaConsultaPrestamo;
    private Button btnConsultarPrestamos;
    private Button btnEstablecerFechaPrestamo;
    private Button btnResetPrestamo;

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

        tvFechaConsultaPrestamo = (TextView)findViewById(R.id.tvFechaPrestamo);
        btnConsultarPrestamos = (Button)findViewById(R.id.btnConsultarPrestamo);
        btnEstablecerFechaPrestamo =(Button)findViewById(R.id.btnFechaPrestamo);
        btnResetPrestamo = (Button)findViewById(R.id.btnResetPrestamo);

        final Calendar c = Calendar.getInstance();
        anio = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);
        dialogoFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                anio = year;
                mes= month + 1;
                dia = dayOfMonth;
                tvFechaConsultaPrestamo.setText(dia+"/"+mes+"/"+anio);
            }
        };

        btnIngresarP.setOnClickListener(this);
        btnConsultarPrestamos.setOnClickListener(this);
        btnEstablecerFechaPrestamo.setOnClickListener(this);
        btnResetPrestamo.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.btnIngPres:
                String strFecha = String.valueOf(fechaP.getDayOfMonth()) + "/" +
                        String.valueOf(fechaP.getMonth()+1) + "/" + String.valueOf(fechaP.getYear());
                try {
                    Prestamo prestamo =  new Prestamo(agenteP.getText().toString(), Float.parseFloat(montoP.getText().toString()), formato.parse(strFecha),
                            descP.getText().toString(), Integer.parseInt(cuotaP.getText().toString()));
                    prestamo.save();

                    fp.agregar(prestamo);
                    Toast.makeText(this, "AGREGADO",
                            Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnConsultarPrestamo:
                try {
                    String fechaPas = dia+"/"+mes+"/"+anio;
                    fp.buscarPrestamo(formato.parse(fechaPas));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnFechaPrestamo:
                showDialog(0);
                break;
            case R.id.btnResetPrestamo:
                fp.llenarListaPrestamos();
                break;
            default:
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, android.app.AlertDialog.THEME_HOLO_DARK, dialogoFecha, anio, mes, dia);
    }

    @Override
    public void onFragmentInteractionListener() {
        fp = (FragmentPrestamo)getSupportFragmentManager().findFragmentById(R.id.fragmentP);
    }
}
