package com.example.jaime.finnica;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaime.finnica.clases.Ingresos;
import com.example.jaime.finnica.clases.IngresosAdapter;
import com.example.jaime.finnica.clases.Pago;
import com.example.jaime.finnica.clases.Prestamo;
import com.example.jaime.finnica.fragmentClasses.FragmentIngresos;
import com.example.jaime.finnica.fragmentClasses.FragmentPago;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeudasActivity extends FragmentActivity implements  FragmentPago.InterfacePago {
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private Spinner spn;
    private Spinner spnInforme;
    private EditText descripcion;
    private EditText monto;
    private DatePicker fecha;
    FragmentPago fgm;
    List<Prestamo> listaP;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deudas);

        descripcion=(EditText) findViewById(R.id.txtDesPagoG);
        monto=(EditText)findViewById(R.id.txtMontoPagoG);
        fecha=(DatePicker) findViewById(R.id.dpFechaPagoG);
        spn=(Spinner) findViewById(R.id.cbxpagoG);
        spnInforme=(Spinner) findViewById(R.id.cbxpago);

        List<String> spinnerArray = new ArrayList<String>();





//cargar los prestamos en el spinner
        try {


            if(Prestamo.isSugarEntity(Prestamo.class) && Prestamo.count(Prestamo.class) > 0){
                listaP = Prestamo.listAll(Prestamo.class);
                for(int i=0;i< Prestamo.count(Prestamo.class);i++ ){

                    spinnerArray.add(listaP.get(i).getDescripcion().toString());



                }



            }else {


            }
        }catch (Exception e){
            e.getMessage();
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        spn.setAdapter(adapter);
        spnInforme.setAdapter(adapter);


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

    @Override
    public void onFragmentInteractionListener() {
        fgm = (FragmentPago) getSupportFragmentManager().findFragmentById(R.id.fragmentP);
    }

    public void guardarPago(View view){

        String strFecha = String.valueOf(fecha.getDayOfMonth()) + "/" + String.valueOf(fecha.getMonth()+1) + "/" + String.valueOf(fecha.getYear());

        try {
            //guardar en lista
            Pago pago =new Pago();

            pago.setPrestamo(listaP.get(spn.getSelectedItemPosition()));//PROBAR AQUI
            pago.setDescripcion(descripcion.getText().toString());
            pago.setFechaPago(formato.parse(strFecha));
            pago.setMonto(Float.parseFloat(monto.getText().toString()));
            pago.save();




            fgm.agregar(pago);
            Toast.makeText(this, "AGREGADO",Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void busqueda(View view){
        fgm.buscar(listaP.get(spnInforme.getSelectedItemPosition()).getDescripcion().toString());


    }
}
