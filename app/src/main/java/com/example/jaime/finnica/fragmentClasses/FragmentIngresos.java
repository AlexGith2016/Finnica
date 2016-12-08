package com.example.jaime.finnica.fragmentClasses;
import android.support.v4.app.Fragment;
import android.app.Dialog;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.jaime.finnica.clases.Ingresos;
import com.example.jaime.finnica.clases.IngresosAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaime on 04/12/2016.
 */
public class FragmentIngresos extends ListFragment {
    List<Ingresos> listaI;
    IngresosAdapter adapter;
    InterfaceIngreso act;
    SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
    ListView listView;
    boolean selectedLong;
    int pos;

    public FragmentIngresos(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fragment_ingresos, container, false);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            System.out.println("Tratar de crear tabla Ingresos");

            if(Ingresos.isSugarEntity(Ingresos.class) && Ingresos.count(Ingresos.class) > 0){
                listaI = Ingresos.listAll(Ingresos.class);
                adapter = new IngresosAdapter(getActivity(), listaI);
                setListAdapter(adapter);
                System.out.println("Listo y servido");
            }else {
                listaI = new ArrayList<>();
                adapter = new IngresosAdapter(getActivity(),listaI);
                setListAdapter(adapter);
                System.out.println("nada que ver pana");
            }
        }catch (Exception e){
            e.getMessage();
        }



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
            actualizar(listaI.get(position), position);
        }else{
            selectedLong = false;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (InterfaceIngreso)context;
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
                Ingresos ingreso = listaI.get(pos);
                ingreso.delete();

                adapter.remove(listaI.get(pos));

                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Eliminado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }






    public void agregar(Ingresos ingreso){
        listaI.add(ingreso);
        adapter.notifyDataSetChanged();
    }


    public void actualizar( Ingresos ingreso, final int index){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.input_ingresos);

        final EditText tv1 = (EditText) dialog.findViewById(R.id.txtActDescIngresos);
        final EditText tv2= (EditText)dialog.findViewById(R.id.txtActMontoIngresos);
        final EditText tv3 = (EditText) dialog.findViewById(R.id.txtActFechaIngresos);
        final Spinner spn  = (Spinner) dialog.findViewById(R.id.spinnerCategoria);
        final TextView tv4=(TextView) dialog.findViewById(R.id.txtCategoria);
//Spinner
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Pago préstamo");
        spinnerArray.add("Regalía");
        spinnerArray.add("Salario");
        spinnerArray.add("Otro");

        ArrayAdapter<String> adap = new ArrayAdapter<String> (getContext(), android.R.layout.simple_list_item_1, spinnerArray);

        adap.setDropDownViewResource(android.R.layout.simple_list_item_1);

        spn.setAdapter(adap);

        //*****

        tv1.setText(ingreso.getDescripcion());
        tv2.setText(String.valueOf(ingreso.getMonto()));
        tv3.setText(formato.format(ingreso.getFechaIngreso()));
        String cat = ingreso.getCategoria();
        tv4.setText(cat);

        Button button = (Button)dialog.findViewById(R.id.btnActIngresos);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingresos i= null;
                try {

                    i= listaI.get(index);
                    i.setCategoria(spn.getSelectedItem().toString());
                    i.setDescripcion(tv1.getText().toString());
                    i.setMonto(Float.parseFloat(tv2.getText().toString()));
                    i.setFechaIngreso(formato.parse(tv3.getText().toString()));
                    i.save();

                    listaI.set(index, i);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }

    public interface InterfaceIngreso{
        public void onFragmentInteractionListener();
    }

}
