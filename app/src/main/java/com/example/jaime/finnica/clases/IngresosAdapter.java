package com.example.jaime.finnica.clases;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jaime.finnica.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Jaime on 04/12/2016.
 */
public class IngresosAdapter extends ArrayAdapter<Ingresos> {
    TextView Categoria;
    TextView Descripcion;
    TextView Fecha;
    TextView monto;


    public IngresosAdapter(Context context, List<Ingresos> objects){
        super(context,0,objects);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //existe la vista actual
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_fragment_informes, parent, false);
        }

        Categoria =(TextView)convertView.findViewById(R.id.txtCategoriaIngresosItem);
        Descripcion =(TextView)convertView.findViewById(R.id.txtDescripcionIngresosItem);
        Fecha = (TextView) convertView.findViewById(R.id.txtFechaGastoItem);
        monto =(TextView)convertView.findViewById(R.id.txtMontoIngresosItem);

        //obtener el gasto actual
        Ingresos ingreso = getItem(position);

        //poner datos en los componentes
        monto.setText(String.valueOf(ingreso.getMonto()));
        Descripcion.setText(ingreso.getDescripcion());
        Fecha.setText(ingreso.getFechaIngreso().getDay()+"/"+ingreso.getFechaIngreso().getMonth()+"/"+(ingreso.getFechaIngreso().getYear()+1900));

        return convertView;
    }




}
