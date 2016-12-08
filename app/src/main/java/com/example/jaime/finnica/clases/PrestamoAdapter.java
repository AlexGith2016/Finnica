package com.example.jaime.finnica.clases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jaime.finnica.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by checho on 04/12/2016.
 */

public class PrestamoAdapter extends ArrayAdapter<Prestamo> {


    SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
    TextView monto;
    TextView desc;
    TextView fecha;
    TextView agente;
    TextView cuota;
    public PrestamoAdapter(Context context, List<Prestamo> objects) {
        super(context, 0 , objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_prestamo, parent, false);
        }

        monto = (TextView) convertView.findViewById(R.id.txtMontoPrestamoItem);
        desc = (TextView) convertView.findViewById(R.id.txtDescPrestamoItem);
        fecha = (TextView) convertView.findViewById(R.id.txtFechaPrestamoItem);
        agente = (TextView) convertView.findViewById(R.id.txtAgentePrestamoItem);
        cuota = (TextView) convertView.findViewById(R.id.txtCuotaPrestamoItem);

        Prestamo prestamo = getItem(position);

        agente.setText(prestamo.getAgenteFinanciero());
        desc.setText(prestamo.getDescripcion());
        fecha.setText(formato.format(prestamo.getFecha()));
        monto.setText(String.valueOf(prestamo.getMontoEntrada()));
        cuota.setText(String.valueOf(prestamo.getnCuotas()));

        return convertView;
    }
}
