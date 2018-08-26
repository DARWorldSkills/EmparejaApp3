package com.davidpopayan.sena.emparejaapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Splash extends AppCompatActivity {
    public static String jugador1;
    public static String jugador2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CountDownTimer countDownTimer = new CountDownTimer(1000,2000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                final Dialog dialog = new Dialog(Splash.this);
                dialog.setContentView(R.layout.item_inicio);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final EditText txtJugador1 = dialog.findViewById(R.id.txtJugador1);
                final EditText txtJugador2 = dialog.findViewById(R.id.txtJugador2);
                Button btnAceptar = dialog.findViewById(R.id.btnAceptar);

                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jugador1 = txtJugador1.getText().toString();
                        jugador2 = txtJugador2.getText().toString();
                        if (jugador1.length()<1 || jugador2.length()<1){
                            Toast.makeText(Splash.this, "Faltan campos por ingresar", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent = new Intent(Splash.this,MenuT.class);
                            startActivity(intent);
                            dialog.cancel();
                            finish();
                        }

                    }
                });
                dialog.setCancelable(false);


                dialog.show();
            }
        }.start();
    }
}
