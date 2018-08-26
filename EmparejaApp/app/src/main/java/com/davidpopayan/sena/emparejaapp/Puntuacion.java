package com.davidpopayan.sena.emparejaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Puntuacion extends AppCompatActivity implements View.OnClickListener{
    TextView txtPrimero, txtSegundo, txtTercero, txtCuarto, txtQuinto;
    RadioButton rbtnFacil, rbtnMedio, rbtnDificil;
    int dificultad=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion);
        inizialite();
        inputOnClickListener();
        cleanV();
        inputData();
    }

    private void inputData() {
        GestorDB gestorDB = new GestorDB(this);
        List<Score> scoreList = gestorDB.scoreList(dificultad);
        if (scoreList.size()>0){
            txtPrimero.setText(scoreList.get(0).getNombre()+" "+scoreList.get(0).getPuntaje()+" "+scoreList.get(0).getTiempo());
        }else {
            cleanV();
            Toast.makeText(this, "No hay punutaciones disponibles", Toast.LENGTH_SHORT).show();
        }

        if (scoreList.size()>1){
            txtSegundo.setText(scoreList.get(1).getNombre()+" "+scoreList.get(1).getPuntaje()+" "+scoreList.get(1).getTiempo());
        }else {
            txtSegundo.setText("");
            txtTercero.setText("");
            txtCuarto.setText("");
            txtQuinto.setText("");
        }


        if (scoreList.size()>2){
            txtTercero.setText(scoreList.get(2).getNombre()+" "+scoreList.get(2).getPuntaje()+" "+scoreList.get(2).getTiempo());
        }else {
            txtTercero.setText("");
            txtCuarto.setText("");
            txtQuinto.setText("");
        }

        if (scoreList.size()>3){
            txtCuarto.setText(scoreList.get(3).getNombre()+" "+scoreList.get(3).getPuntaje()+" "+scoreList.get(3).getTiempo());
        }else {
            txtCuarto.setText("");
            txtQuinto.setText("");

        }

        if (scoreList.size()>4){
            txtQuinto.setText(scoreList.get(4).getNombre()+" "+scoreList.get(4).getPuntaje()+" "+scoreList.get(4).getTiempo());
        }else {
            txtQuinto.setText("");
        }

    }

    private void cleanV() {
        txtPrimero.setText("");
        txtSegundo.setText("");
        txtTercero.setText("");
        txtCuarto.setText("");
        txtQuinto.setText("");
    }


    private void inizialite() {
        txtPrimero = findViewById(R.id.txtOro);
        txtSegundo = findViewById(R.id.txtPlata);
        txtTercero = findViewById(R.id.txtBronces);
        txtCuarto = findViewById(R.id.txtCuarto);
        txtQuinto = findViewById(R.id.txtQuinto);
        rbtnFacil = findViewById(R.id.rbtnFacil);
        rbtnMedio = findViewById(R.id.rbtnMedio);
        rbtnDificil = findViewById(R.id.rbtnDificil);

    }
    private void inputOnClickListener() {
        rbtnFacil.setOnClickListener(this);
        rbtnMedio.setOnClickListener(this);
        rbtnDificil.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rbtnFacil:
                dificultad=4;
                inputData();
                break;

            case R.id.rbtnMedio:
                dificultad=6;
                inputData();
                break;

            case R.id.rbtnDificil:
                dificultad=8;
                inputData();
                break;


        }
    }

    public void salir(View view) {
        finish();
    }
}
