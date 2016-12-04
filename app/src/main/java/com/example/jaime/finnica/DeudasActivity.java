package com.example.jaime.finnica;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class DeudasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deudas);


        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Registrar");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Deducciones");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);


    }
}
