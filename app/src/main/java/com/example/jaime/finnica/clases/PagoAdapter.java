package com.example.jaime.finnica.clases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jaime.finnica.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by isi4 on 09/12/2016.
 */
public class PagoAdapter extends ArrayAdapter<Pago> {


    TextView descripcion;
    TextView monto;
    TextView fechaPago;
    TextView prestamo;
    SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");


    public PagoAdapter(Context context, List<Pago> objects) {
        super(context, 0 , objects);
    }




    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_pago, parent, false);
        }

        monto = (TextView) convertView.findViewById(R.id.txtMontoPagoItem);
        descripcion = (TextView) convertView.findViewById(R.id.txtDescripcionPagoItem);
        fechaPago = (TextView) convertView.findViewById(R.id.txtFechaPagoPagoItem);
        prestamo = (TextView) convertView.findViewById(R.id.txtPrestamoPagoItem);


        Pago pago = getItem(position);

        monto.setText(String.valueOf(pago.getMonto()));
        descripcion.setText(pago.getDescripcion());
        fechaPago.setText(formato.format(pago.getFechaPago()));
        prestamo.setText(pago.getPrestamo().getDescripcion());

        return convertView;
    }

}
