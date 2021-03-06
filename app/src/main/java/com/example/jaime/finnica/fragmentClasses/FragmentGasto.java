package com.example.jaime.finnica.fragmentClasses;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jaime.finnica.R;
import com.example.jaime.finnica.clases.Gasto;
import com.example.jaime.finnica.clases.GastoAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGasto extends ListFragment {
    //ListView listViewGastos;
    GastoAdapter adapter;
    List<Gasto> listaG;
    InterfaceGasto act;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    ListView listView;
    boolean selectedLong;
    int pos;

    public FragmentGasto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fragment_gasto, container, false);

        try {
            listView= (ListView) root.findViewById(android.R.id.list);
            listView.setLongClickable(true);
            listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedLong = true;
                    pos = position;
                    Toast.makeText(getActivity(), "seleccion LONG", Toast.LENGTH_LONG).show();
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
        //Prueba de datos
        llenarLista();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(getListView());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //mostrar dato del item seleccionado
        //Toast.makeText(getActivity(), "Ha pulsado el item con monto " + String.valueOf(listaG.get(position).getMonto()),
        //Toast.LENGTH_SHORT).show();
        if(selectedLong == false){
            actualizar(listaG.get(position), position);
        }else{
            selectedLong = false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (InterfaceGasto)context;
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
                Gasto gasto1 = listaG.get(pos);
                gasto1.delete();
                adapter.remove(listaG.get(pos));

                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Eliminado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void buscar(Date fechaConsulta){
        List<Gasto> listaConsulta = new ArrayList<>();
        for (Gasto gasto : listaG) {
            if(fechaConsulta.getMonth() == gasto.getFechaGasto().getMonth()){
                listaConsulta.add(gasto);
            }
        }
        if(listaConsulta.size()<= 0){
            Toast.makeText(getActivity(), "No hay datos para mostrar", Toast.LENGTH_LONG).show();
        }else {
            listaG = listaConsulta;
            adapter = new GastoAdapter(getActivity(), listaG);
            setListAdapter(adapter);
        }
    }

    public void llenarLista(){
        try {
            System.out.println("Tratar de crear tabla Gasto");

            if(Gasto.isSugarEntity(Gasto.class) && Gasto.count(Gasto.class) > 0){
                listaG = Gasto.listAll(Gasto.class);
                adapter = new GastoAdapter(getActivity(), listaG);
                setListAdapter(adapter);
                System.out.println("Listo y servido");
            }else {
                listaG = new ArrayList<>();
                adapter = new GastoAdapter(getActivity(), listaG);
                setListAdapter(adapter);
                System.out.println("nada que ver pana");
            }
        }catch (Exception e){
            e.getMessage();
        }
    }

    public void agregar(Gasto gasto){
        listaG.add(gasto);
        adapter.notifyDataSetChanged();
    }

    public void actualizar( Gasto gasto, final int index){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.imput_gastos);
        final EditText tv1 = (EditText) dialog.findViewById(R.id.txtActDescGasto);
        final EditText tv2= (EditText)dialog.findViewById(R.id.txtActMontoGasto);
        final EditText tv3 = (EditText) dialog.findViewById(R.id.txtActFechaGasto);
        tv1.setText(gasto.getDescripcion());
        tv2.setText(String.valueOf(gasto.getMonto()));
        tv3.setText(formato.format(gasto.getFechaGasto()));
        Button button = (Button)dialog.findViewById(R.id.btnActGasto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gasto g;
                try {
                    g = listaG.get(index);
                    g.setDescripcion(tv1.getText().toString());
                    g.setMonto(Float.parseFloat(tv2.getText().toString()));
                    g.setFechaGasto(formato.parse(tv3.getText().toString()));
                    g.save();

                    listaG.set(index, g);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }

    public interface InterfaceGasto {
        public void onFragmentInteractionListener();
    }
}
