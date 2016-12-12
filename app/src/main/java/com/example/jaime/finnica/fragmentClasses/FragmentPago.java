package com.example.jaime.finnica.fragmentClasses;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaime.finnica.R;
import com.example.jaime.finnica.clases.Gasto;
import com.example.jaime.finnica.clases.GastoAdapter;
import com.example.jaime.finnica.clases.Ingresos;
import com.example.jaime.finnica.clases.Pago;
import com.example.jaime.finnica.clases.PagoAdapter;
import com.example.jaime.finnica.clases.Prestamo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by isi4 on 05/12/2016.
 */
public class FragmentPago extends ListFragment {

    List<Pago> listaP;
    PagoAdapter adapter;
    InterfacePago act;
    SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
    ListView listView;
    boolean selectedLong;
    int pos;
    List<Prestamo> listaPrest;
    Dialog dialog;
     Spinner spn ;


    public FragmentPago(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fragment_pago, container, false);
        try {
            listView= (ListView) root.findViewById(android.R.id.list);
            listView.setLongClickable(true);
            listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedLong = true;
                    pos = position;

                    view.setSelected(true);
                    System.out.println("exito LONG selected");
                    return false;
                }
            });
            System.out.println("");
        }catch (Exception e){
            e.getMessage();
        }




        return root;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        llenarListaPago();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(getListView());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        if(selectedLong == false){
            actualizar(listaP.get(position), position);
        }else{
            selectedLong = false;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (InterfacePago)context;
        act.onFragmentInteractionListener();

    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem mMenuItemEdit;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle("Borrar registro");//listaG.get(info.position).getDescripcion()
        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_scrolling, menu);
        menu.getItem(0).setTitle("Eliminar");

        mMenuItemEdit = (MenuItem) menu.findItem(R.id.action_settings);
        mMenuItemEdit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Pago pago = listaP.get(pos);
                pago.delete();

                adapter.remove(listaP.get(pos));

                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Eliminado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void buscarPagos(Prestamo prestamo){
        List<Pago> listaConsultaPago = new ArrayList<>();
        for (Pago pago: listaP) {
            if (prestamo.getId() == pago.getPrestamo().getId()){
                listaConsultaPago.add(pago);
            }
        }
        if(listaConsultaPago.size() <= 0){
            Toast.makeText(getActivity(), "No hay datos para mostrar", Toast.LENGTH_LONG).show();
        }else{
            listaP = listaConsultaPago;
            adapter = new PagoAdapter(getActivity(), listaP);
            setListAdapter(adapter);
        }
    }

    public void llenarListaPago(){
        try {
            System.out.println("Tratar de crear tabla Pago");

            if(Pago.isSugarEntity(Pago.class) && Pago.count(Pago.class) > 0){
                listaP = Pago.listAll(Pago.class);
                adapter = new PagoAdapter(getActivity(), listaP);
                setListAdapter(adapter);

            }else {
                listaP = new ArrayList<>();
                adapter = new PagoAdapter(getActivity(),listaP);
                setListAdapter(adapter);

            }
        }catch (Exception e){
            e.getMessage();
        }
    }

    public void agregar(Pago pago){
        listaP.add(pago);
        adapter.notifyDataSetChanged();
    }



    public void actualizar(final Pago pago, final int index){
        dialog = new Dialog(getContext());//PROBAR AQUII
        dialog.setContentView(R.layout.input_pago);

        final EditText tv1 = (EditText) dialog.findViewById(R.id.txtActDescPago);
        final EditText tv2= (EditText)dialog.findViewById(R.id.txtActMontoPago);
        final EditText tv3 = (EditText) dialog.findViewById(R.id.txtActFechaPago);
        final TextView tv4=(TextView) dialog.findViewById(R.id.txtPago);

        spn  = (Spinner) dialog.findViewById(R.id.spinnerPago);
        List<String> spinnerArray = new ArrayList<String>();
       //Spinner
        try {
            if(Prestamo.isSugarEntity(Prestamo.class) && Prestamo.count(Prestamo.class) > 0){
                listaPrest = Prestamo.listAll(Prestamo.class);
                for(int i=0;i< Prestamo.count(Prestamo.class);i++ ){
                    spinnerArray.add(listaPrest.get(i).getDescripcion().toString());
                }
            }else {}
            //****Spinner
        }catch (Exception e){
            e.getMessage();
        }
        ArrayAdapter<String> adap = new ArrayAdapter<String> (getContext(), android.R.layout.simple_list_item_1, spinnerArray);
        adap.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spn.setAdapter(adap);

        //*****
        tv1.setText(pago.getDescripcion());
        tv2.setText(String.valueOf(pago.getMonto()));
        tv3.setText(formato.format(pago.getFechaPago()));
        String prest = pago.getPrestamo().getDescripcion();
        tv4.setText(prest);
        Button button = (Button)dialog.findViewById(R.id.btnActPago);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pago p;
                try {
                    p= listaP.get(index);
                    p.setDescripcion(tv1.getText().toString());
                    p.setMonto(Float.parseFloat(tv2.getText().toString()));
                    p.setFechaPago(formato.parse(tv3.getText().toString()));
                    p.setPrestamo(listaPrest.get(spn.getSelectedItemPosition()));

                    p.save();
                    listaP.set(index, p);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }



    public interface InterfacePago{
        public void onFragmentInteractionListener();
    }


    /*public void buscar(String prestamoDesc){
        List<Pago> listaConsulta = new ArrayList<>();
        for (Pago pago : listaP) {
            if(pago.getPrestamo().getDescripcion()==prestamoDesc)
            {
                listaConsulta.add(pago);
            }
        }
        if(listaConsulta.size()<= 0){
            Toast.makeText(getActivity(), "No hay datos para mostrar", Toast.LENGTH_LONG).show();
        }else {
            listaP = listaConsulta;
            adapter = new PagoAdapter(getActivity(), listaP);
            setListAdapter(adapter);
        }
    }*/

}
