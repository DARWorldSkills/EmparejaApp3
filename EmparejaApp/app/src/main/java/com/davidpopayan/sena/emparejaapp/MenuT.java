package com.davidpopayan.sena.emparejaapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MenuT extends AppCompatActivity implements View.OnClickListener{
    //Declaración de variables
    Button btnJugar, btnPuntuacion, btnConfiguracion;
    public static int nivel=4;
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
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.item_dificultad);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final RadioButton rbtnFacil = dialog.findViewById(R.id.rbtnFacil);
                final RadioButton rbtnMedio = dialog.findViewById(R.id.rbtnMedio);
                final RadioButton rbtnDificl = dialog.findViewById(R.id.rbtnDificil);
                Button btnAceptar = dialog.findViewById(R.id.btnAceptar);
                Button btnCancelar = dialog.findViewById(R.id.btnCancelar);

                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rbtnFacil.isChecked()){
                            nivel=4;
                        }

                        if (rbtnMedio.isChecked()){
                            nivel=6;
                        }

                        if (rbtnDificl.isChecked()){
                            nivel=8;
                        }
                        Intent intent = new Intent(MenuT.this,MainActivity.class);
                        startActivity(intent);
                        dialog.cancel();

                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });


                dialog.show();
                break;



            case R.id.btnPuntuacion:
                break;

            case R.id.btnConfiguracion:
                break;
        }

    }
}
