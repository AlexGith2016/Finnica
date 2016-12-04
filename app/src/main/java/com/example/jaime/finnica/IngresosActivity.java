package com.example.jaime.finnica;

import android.content.res.Resources;
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

import com.example.jaime.finnica.clases.Ingresos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IngresosActivity extends AppCompatActivity {

    private Spinner categoria;
    private EditText descripcion;
    private DatePicker fecha;
    private EditText monto;
    private Button btnGuardar;
    List<Ingresos> listaIngresos= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos);
        Resources res = getResources();

        //Spinner lista
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Pago préstamo");
        spinnerArray.add("Regalía");
        spinnerArray.add("Salario");
        spinnerArray.add("Otro");

        //Instanciar elementos
        descripcion = (EditText)findViewById(R.id.descIngreso);
        categoria = (Spinner) findViewById(R.id.categoriaIngreso);
        fecha = (DatePicker)findViewById(R.id.fechaGasto);
        monto = (EditText)findViewById(R.id.montoIngreso);
        btnGuardar = (Button)findViewById(R.id.btnGuardarIngreso);


        //poner  adaptador a categoria
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        categoria.setAdapter(adapter);

        //Evento click guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                String strFecha = String.valueOf(fecha.getDayOfMonth()) + "/" + String.valueOf(fecha.getMonth()+1) + "/" + String.valueOf(fecha.getYear());

                try {
                    //guardar en lista
                    listaIngresos.add(new Ingresos(descripcion.getText().toString(),Float.parseFloat(monto.getText().toString()),
                            formato.parse(strFecha), categoria.getSelectedItem().toString()));
                    Date f = formato.parse(strFecha);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Monto: " + listaIngresos.get(listaIngresos.size()-1).getMonto() + ", Fecha: " +
                        listaIngresos.get(listaIngresos.size()-1).getFechaIngreso().toString() +
                        ", Cat: " + listaIngresos.get(listaIngresos.size()-1).getCategoria(), Toast.LENGTH_LONG).show();
                listaIngresos.get(listaIngresos.size()-1).save();
            }
        });

        //TAGS

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
}