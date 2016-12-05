package com.example.jaime.finnica;

import android.app.Fragment;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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


import com.example.jaime.finnica.clases.Ingresos;

import com.example.jaime.finnica.fragmentClasses.FragmentIngresos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IngresosActivity extends FragmentActivity implements  FragmentIngresos.InterfaceIngreso {

    private Spinner categoria;
    private EditText descripcion;
    private DatePicker fecha;
    private EditText monto;
    private Button btnGuardar;
    DatePicker fechaIngreso;
    FragmentIngresos fgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos);


        //Spinner
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Pago préstamo");
        spinnerArray.add("Regalía");
        spinnerArray.add("Salario");
        spinnerArray.add("Otro");

        descripcion = (EditText) findViewById(R.id.descIngreso);
        categoria = (Spinner) findViewById(R.id.categoriaIngreso);
        fecha = (DatePicker) findViewById(R.id.fechaIngreso);
        monto = (EditText) findViewById(R.id.montoIngreso);
        btnGuardar = (Button) findViewById(R.id.btnGuardarIngreso);






        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        categoria.setAdapter(adapter);

        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Ingresar");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Informe financiero");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);


    }

    @Override
    public void onFragmentInteractionListener() {
        fgm = (FragmentIngresos) getSupportFragmentManager().findFragmentById(R.id.fragmentI);
    }

    public void onClick(View v) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha = String.valueOf(fechaIngreso.getDayOfMonth()) + "/" + String.valueOf(fechaIngreso.getMonth()+1) + "/" + String.valueOf(fechaIngreso.getYear());

        try {
            //guardar en lista
            Ingresos ingreso =new Ingresos();
            ingreso.setCategoria(categoria.getAdapter().toString());
            ingreso.setDescripcion(descripcion.getText().toString());
            ingreso.setFechaIngreso(formato.parse(strFecha));
            ingreso.setMonto(Float.parseFloat(monto.getText().toString()));
            ingreso.save();




            fgm.agregar(ingreso);
            Toast.makeText(this, "AGREGADO",Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }





}
