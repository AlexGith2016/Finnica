package com.example.jaime.finnica.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jaime.finnica.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by checho on 19/11/2016.
 */
public class GastoAdapter extends ArrayAdapter<Gasto> {

    TextView monto;
    TextView desc;
    TextView fecha;
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public GastoAdapter(Context context, List<Gasto> objects) {
        super(context, 0, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //existe la vista actual
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_gasto, parent, false);
        }

        monto = (TextView) convertView.findViewById(R.id.txtAgentePrestamoItem);
        desc = (TextView) convertView.findViewById(R.id.txtDescPrestamoItem);
        fecha = (TextView) convertView.findViewById(R.id.txtCuotaPrestamoItem);

        //obtener el gasto actual
        Gasto gasto = getItem(position);

        //poner datos en los componentes
        monto.setText(String.valueOf(gasto.getMonto()));
        desc.setText(gasto.getDescripcion());
        fecha.setText(formato.format(gasto.getFechaGasto()));

        return convertView;
    }
}
