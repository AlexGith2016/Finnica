package com.example.jaime.finnica.fragmentClasses;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaime.finnica.R;

/**
 * Created by isi4 on 05/12/2016.
 */
public class FragmentPago extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fragment_pago, container, false);
        return root;
    }
}
