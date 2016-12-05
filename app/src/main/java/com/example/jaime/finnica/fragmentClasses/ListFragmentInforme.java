package com.example.jaime.finnica.fragmentClasses;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jaime.finnica.R;



import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jaime.finnica.clases.Gasto;
import com.example.jaime.finnica.clases.Ingresos;

import java.util.Date;

/**
 * Created by Jaime on 16/11/2016.
 */
public class ListFragmentInforme extends ListFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment_informes,container,false);

    }






}
