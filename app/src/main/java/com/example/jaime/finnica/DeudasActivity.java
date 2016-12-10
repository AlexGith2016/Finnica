package com.example.jaime.finnica;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;

import com.example.jaime.finnica.clases.Ingresos;
import com.example.jaime.finnica.clases.IngresosAdapter;
import com.example.jaime.finnica.clases.Prestamo;

import java.util.ArrayList;
import java.util.List;

public class DeudasActivity extends AppCompatActivity {
    Spinner spn;
    List<Prestamo> listaP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deudas);

        spn=(Spinner) findViewById(R.id.cbxpagoG);
        List<String> spinnerArray = new ArrayList<String>();






        try {


            if(Prestamo.isSugarEntity(Prestamo.class) && Prestamo.count(Prestamo.class) > 0){
                listaP = Prestamo.listAll(Prestamo.class);
                for(int i=0;i< Prestamo.count(Prestamo.class);i++ ){

                    spinnerArray.add(listaP.get(i).getDescripcion().toString());

                }



            }else {

                System.out.println("nada que ver pana");
            }
        }catch (Exception e){
            e.getMessage();
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        spn.setAdapter(adapter);


        Resources res = getResources();


        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Registrar");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Deducciones");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);


    }
}
