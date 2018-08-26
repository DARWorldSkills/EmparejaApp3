package com.davidpopayan.sena.emparejaapp;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuT extends AppCompatActivity implements View.OnClickListener{
    //Declaración de variables
    Button btnJugar, btnPuntuacion, btnConfiguracion;

    //Método para identificar en que estado está la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_t);
        inizialite();
        inputOnClickListener();
    }

    //Método para inicializar las vistas
    private void inizialite() {
        btnJugar = findViewById(R.id.btnJugar);
        btnPuntuacion = findViewById(R.id.btnPuntuacion);
        btnConfiguracion = findViewById(R.id.btnConfiguracion);
    }

    private void inputOnClickListener(){
        btnJugar.setOnClickListener(this);
        btnPuntuacion.setOnClickListener(this);
        btnConfiguracion.setOnClickListener(this);
    }

    //Método para escuchar donde se hace click
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnJugar:
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.item_dificultad);

                break;

            case R.id.btnPuntuacion:
                break;

            case R.id.btnConfiguracion:
                break;
        }

    }
}
