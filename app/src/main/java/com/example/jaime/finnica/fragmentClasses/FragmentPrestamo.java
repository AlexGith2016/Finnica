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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jaime.finnica.R;
import com.example.jaime.finnica.clases.Gasto;
import com.example.jaime.finnica.clases.GastoAdapter;
import com.example.jaime.finnica.clases.Prestamo;
import com.example.jaime.finnica.clases.PrestamoAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentPrestamo extends ListFragment {
    PrestamoAdapter adapterP;
    List<Prestamo> listaP;
    InterfacePrestamo actP;
    SimpleDateFormat formatoP = new SimpleDateFormat("dd/MM/yyyy");
    ListView listViewP;
    boolean selectedLongP;
    int posP;

    public FragmentPrestamo() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            System.out.println("Tratar de crear tabla Prestamo");

            if(Prestamo.isSugarEntity(Prestamo.class) && Prestamo.count(Prestamo.class) > 0){
                listaP = Prestamo.listAll(Prestamo.class);
                adapterP = new PrestamoAdapter(getActivity(), listaP);
                setListAdapter(adapterP);
                System.out.println("Listo y servido");
            }else {
                listaP = new ArrayList<>();
                adapterP = new PrestamoAdapter(getActivity(), listaP);
                setListAdapter(adapterP);
                System.out.println("nada que ver pana");
            }
        }catch (Exception e){
            e.getMessage();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prestamo, container, false);

        try {
            listViewP= (ListView) root.findViewById(android.R.id.list);
            listViewP.setLongClickable(true);
            listViewP.setChoiceMode(listViewP.CHOICE_MODE_SINGLE);
            listViewP.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedLongP = true;
                    posP = position;
                    Toast.makeText(getActivity(), "seleccion LONG", Toast.LENGTH_LONG).show();
                    view.setSelected(true);
                    System.out.println("exito LONG selected");
                    return false;
                }
            });
        }catch (Exception e){
            e.getMessage();
        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(getListView());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(!selectedLongP){
            actualizar(listaP.get(position), position);
        }else{
            selectedLongP = false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        actP = (InterfacePrestamo)context;
        actP.onFragmentInteractionListener();
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

        mMenuItemEdit = menu.findItem(R.id.action_settings);
        mMenuItemEdit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Prestamo prestamo = listaP.get(posP);
                prestamo.delete();
                adapterP.remove(listaP.get(posP));

                adapterP.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Eliminado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void agregar(Prestamo prestamo){
        listaP.add(prestamo);
        adapterP.notifyDataSetChanged();
    }

    public void actualizar(Prestamo prestamo, final int index){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.imput_prestamo);
        final EditText tv1 = (EditText) dialog.findViewById(R.id.txtActDescPrestamo);
        final EditText tv2= (EditText)dialog.findViewById(R.id.txtActMontoPrestamo);
        final EditText tv3 = (EditText) dialog.findViewById(R.id.txtActFechaPrestamo);
        final EditText tv4 = (EditText) dialog.findViewById(R.id.txtActAgentePrestamo);
        final EditText tv5 = (EditText) dialog.findViewById(R.id.txtActCuotaPrestamo);
        tv1.setText(prestamo.getDescripcion());
        tv2.setText(String.valueOf(prestamo.getMontoEntrada()));
        tv3.setText(formatoP.format(prestamo.getFecha()));
        tv4.setText(prestamo.getAgenteFinanciero());
        tv5.setText(String.valueOf(prestamo.getnCuotas()));
        Button button = (Button)dialog.findViewById(R.id.btnActPrestamo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prestamo p;
                try {
                    p = listaP.get(index);
                    p.setAgenteFinanciero(tv4.getText().toString());
                    p.setMontoEntrada(Float.parseFloat(tv2.getText().toString()));
                    p.setFecha(formatoP.parse(tv3.getText().toString()));
                    p.setDescripcion(tv1.getText().toString());
                    p.setnCuotas(Integer.parseInt(tv5.getText().toString()));
                    p.save();

                    listaP.set(index, p);
                    adapterP.notifyDataSetChanged();
                    dialog.dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }

    public interface InterfacePrestamo {
        void onFragmentInteractionListener();
    }
}
