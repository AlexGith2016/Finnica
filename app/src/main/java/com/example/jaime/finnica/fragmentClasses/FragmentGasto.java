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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaime.finnica.R;
import com.example.jaime.finnica.clases.Gasto;
import com.example.jaime.finnica.clases.GastoAdapter;
import com.example.jaime.finnica.clases.Ingresos;
import com.google.common.base.FinalizablePhantomReference;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        //Toast.makeText(getActivity(), "Llenando el fragment", Toast.LENGTH_SHORT).show();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Prueba de datos
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
        /*try {
            listaG = new ArrayList<>();
            Gasto gasto1 = new Gasto("nuevo gasto 1", 1200, formato.parse("15/11/2016"));
            Gasto gasto2 = new Gasto("nuevo gasto 2", 100, formato.parse("22/10/2016"));
            listaG.add(gasto1);
            listaG.add(gasto2);

            //Agregar al adapter
            adapter = new GastoAdapter(getActivity(), listaG);
            setListAdapter(adapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

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
        Toast.makeText(getActivity(), "Creando activity para comunicacion",
                Toast.LENGTH_SHORT).show();
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
                adapter.remove(listaG.get(pos));

                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Eliminado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
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
                Gasto g= null;
                try {
                    g = new Gasto(tv1.getText().toString(), Float.parseFloat(tv2.getText().toString()),
                            formato.parse(tv3.getText().toString()));
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
