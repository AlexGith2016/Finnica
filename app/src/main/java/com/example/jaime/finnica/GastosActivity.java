package com.example.jaime.finnica;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaime.finnica.clases.Gasto;
import com.example.jaime.finnica.fragmentClasses.FragmentGasto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GastosActivity extends FragmentActivity implements View.OnClickListener, FragmentGasto.InterfaceGasto{

    private int dia, mes, anio;
    private DatePickerDialog.OnDateSetListener dialogoFecha;
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    EditText deduccion;
    EditText descripcion;
    DatePicker fechaGasto;
    Button btnIngresar;
    FragmentGasto fg;

    TextView fechaConsulta;
    Button btnConsultar;
    Button btnEstablecerFechaConsulta;
    Button btnResetGasto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(R.id.tabhost2);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tabgasto1);
        spec.setIndicator("Registrar");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tabgasto2);
        spec.setIndicator("Consultar");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        deduccion = (EditText)findViewById(R.id.txtDedGasto);
        descripcion = (EditText)findViewById(R.id.txtDescGasto);
        fechaGasto = (DatePicker)findViewById(R.id.fechaGasto);
        btnIngresar = (Button)findViewById(R.id.btnIngresarGasto);

        fechaConsulta = (TextView)findViewById(R.id.tvFechaGasto);
        btnConsultar = (Button)findViewById(R.id.btnConsultarGasto);
        btnEstablecerFechaConsulta = (Button)findViewById(R.id.btnFechaGasto);
        btnResetGasto = (Button)findViewById(R.id.btnResetGasto);

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
                fechaConsulta.setText(dia+"/"+mes+"/"+anio);
            }
        };

        btnIngresar.setOnClickListener(this);
        btnConsultar.setOnClickListener(this);
        btnEstablecerFechaConsulta.setOnClickListener(this);
        btnResetGasto.setOnClickListener(this);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, android.app.AlertDialog.THEME_HOLO_DARK, dialogoFecha, anio, mes, dia);
    }

    @Override
    public void onFragmentInteractionListener() {
        fg = (FragmentGasto)getSupportFragmentManager().findFragmentById(R.id.fragmentG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFechaGasto:
                showDialog(0);
                break;
            case R.id.btnIngresarGasto:
                String strFecha = String.valueOf(fechaGasto.getDayOfMonth()) + "/" + String.valueOf(fechaGasto.getMonth()+1) + "/" + String.valueOf(fechaGasto.getYear());

                try {
                    //guardar en lista
                    Gasto gasto =new Gasto(descripcion.getText().toString(),Float.parseFloat(deduccion.getText().toString()), formato.parse(strFecha));
                    gasto.save();
                    fg.agregar(gasto);
                    Toast.makeText(this, "AGREGADO",
                            Toast.LENGTH_SHORT).show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnConsultarGasto:
                try {
                    String fechaPas = dia+"/"+mes+"/"+anio;
                    fg.buscar(formato.parse(fechaPas));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnResetGasto:
                fg.llenarLista();
                break;
            default:
                break;
        }
    }
}
