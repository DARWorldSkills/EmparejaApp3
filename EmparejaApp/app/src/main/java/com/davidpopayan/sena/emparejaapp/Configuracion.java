package com.davidpopayan.sena.emparejaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Configuracion extends AppCompatActivity {
    SharedPreferences juegoC;
    EditText txtTiempo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        txtTiempo = findViewById(R.id.txtTiempoC);
        inputData();
    }

    private void inputData() {
        juegoC = getSharedPreferences("juegoC",MODE_PRIVATE);
        int tiempo = juegoC.getInt("tiempo",30);
        txtTiempo.setText(Integer.toString(tiempo));
    }

    public void jugar(View view) {
        try {

            int tiempo = Integer.parseInt(txtTiempo.getText().toString());
            if (tiempo>0){
                SharedPreferences.Editor editor = juegoC.edit();
                editor.putInt("tiempo",tiempo);
                editor.commit();
                Intent intent = new Intent(Configuracion.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Por favor ingrese mayor a 0", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(this, "Por favor ingrese un tiempo valido", Toast.LENGTH_SHORT).show();
        }
    }
}
