package com.example.jaime.finnica;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.jaime.finnica.clases.Gasto;
import com.example.jaime.finnica.fragmentClasses.FragmentGasto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GastosActivity extends FragmentActivity implements View.OnClickListener, FragmentGasto.InterfaceGasto{

    EditText deduccion;
    EditText descripcion;
    DatePicker fechaGasto;
    Button btnIngresar;
    FragmentGasto fg;
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

        btnIngresar.setOnClickListener(this);
    }


    @Override
    public void onFragmentInteractionListener() {
        fg = (FragmentGasto)getSupportFragmentManager().findFragmentById(R.id.fragmentG);
    }

    @Override
    public void onClick(View v) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
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
    }
}
