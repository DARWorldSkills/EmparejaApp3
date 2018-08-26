package com.davidpopayan.sena.emparejaapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Declaración de variables
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    TextView txtNombreJ1, txtNombreJ2, txtPuntajeJ1, txtPuntajeJ2, txtTiempo;
    ProgressBar pbTiempo;
    RecyclerView contenedorJuego;
    Animator animator1;
    Animator animator2;

    private int fondoJuego = R.drawable.fondojugar;

    private int [] imagenesJuego = {
            R.drawable.agent,
            R.drawable.butters,
            R.drawable.cartman,
            R.drawable.craig,
            R.drawable.kenny,
            R.drawable.kyle,
            R.drawable.stan,
            R.drawable.wendy
    };

    private int [] imagenesFondo, imagenesAleatorias;
    private List<Integer> imagenesSelect= new ArrayList<>();
    int movimientos, pos1=-1, pos2=-1, nivel=4, salir, canselect, item, columnas, tiempo;
    int inicioJuego, puntajeJ1, puntajeJ2;
    int [] segundos= {0};
    boolean bandera = true;
    boolean bandera1 = true;
    View item1, item2;
    ImageView imagen1, imagen2;
    SharedPreferences juegoC;

    MediaPlayer win;
    MediaPlayer lose;
    MediaPlayer endstage;


    //Método para saber si la actividad está en onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

       // printKeyHash();
        inicializar();
        inputValues();
        turns();
        goGame();
        inputAdapter();


    }

    //Método para inicializar las variables
    private void inicializar() {
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        txtNombreJ1 = findViewById(R.id.txtJugador1);
        txtNombreJ2 = findViewById(R.id.txtJugador2);
        txtPuntajeJ1 = findViewById(R.id.txtPuntajeJ1);
        txtPuntajeJ2 = findViewById(R.id.txtPuntajeJ2);
        txtTiempo = findViewById(R.id.txtTiempo);
        pbTiempo = findViewById(R.id.pbTiempo);
        contenedorJuego = findViewById(R.id.contenedorJuego);

        win = MediaPlayer.create(this,R.raw.smb_up);
        lose = MediaPlayer.create(this,R.raw.smb_pipe);
        endstage = MediaPlayer.create(this,R.raw.smb_stage_clear);
    }


    //Método para ingresar los valores
    private void inputValues() {
        bandera=true;
        bandera1=true;
        juegoC= getSharedPreferences("juegoC",MODE_PRIVATE);
        tiempo = juegoC.getInt("tiempo",30);
        nivel= MenuT.nivel;
        salir = nivel;

        if (nivel==4){
            item = R.layout.item_1;
            columnas=2;
        }

        if (nivel==6){
            item = R.layout.item_2;
            columnas=3;
        }


        if (nivel==8){
            item = R.layout.item_3;
            columnas=4;
        }

        txtNombreJ1.setText(Splash.jugador1);
        txtNombreJ2.setText(Splash.jugador2);
        txtTiempo.setText("Tiempo: "+segundos[0]);
        pbTiempo.setMax(tiempo);
        pbTiempo.setProgress(segundos[0]);

    }

    //Método para generar los turnos aleatorios
    private void turns() {
        inicioJuego = (int) (Math.random() *2)+1;
        if (inicioJuego==1){
            txtNombreJ1.setTextColor(Color.BLACK);
            txtNombreJ2.setTextColor(getColor(R.color.colorGris));
            txtPuntajeJ1.setTextColor(Color.BLACK);
            txtPuntajeJ2.setTextColor(getColor(R.color.colorGris));
            Toast.makeText(this, "Empieza "+Splash.jugador1, Toast.LENGTH_SHORT).show();


        }else{
            txtNombreJ2.setTextColor(Color.BLACK);
            txtNombreJ1.setTextColor(getColor(R.color.colorGris));
            txtPuntajeJ2.setTextColor(Color.BLACK);
            txtPuntajeJ1.setTextColor(getColor(R.color.colorGris));;
            Toast.makeText(this, "Empieza "+Splash.jugador1, Toast.LENGTH_SHORT).show();

        }
        inputPoints();

    }

    //Mpetodo apra ingresar la puntuación
    private void inputPoints() {

        if (puntajeJ1<0){
            puntajeJ1=0;
        }
        if (puntajeJ2<0){
            puntajeJ2=0;
        }

        txtPuntajeJ1.setText(Integer.toString(puntajeJ1));
        txtPuntajeJ2.setText(Integer.toString(puntajeJ2));
    }

    //Método para iniciar el juego
    private void goGame() {
        generateFondo();
        generateSelect();
        generateAleatorias();
        chronomenter();
    }

    //Método para generar las imagenes de fondo
    private void generateFondo() {
        imagenesFondo = new int[nivel*2];
        for (int i =0; i<imagenesFondo.length; i++){
            imagenesFondo[i]=fondoJuego;
        }
    }

    //Método para generar las imagenes a seleccionar
    private void generateSelect() {
        imagenesSelect = new ArrayList<>();
        for (int i=0;i<nivel;i++){
            int tmp = (int) (Math.random() * imagenesJuego.length);
            if (!imagenesSelect.contains(imagenesJuego[tmp])){
                imagenesSelect.add(imagenesJuego[tmp]);
            }else {
                i--;
            }
        }
    }

    //Método para generar las imagenes aleatorias
    private void generateAleatorias() {
        imagenesAleatorias = new int[nivel*2];
        for (int i=0; i<nivel;i++){
            int tmp=0;
            do {
                int valor = (int) (Math.random() * nivel*2);
                if (imagenesAleatorias[valor]==0){
                    imagenesAleatorias[valor] = imagenesSelect.get(i);
                    tmp++;
                }

            }while (tmp<2);
        }
    }

    //Método para correr el tiempo
    private void chronomenter() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (bandera){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bandera1) {
                                segundos[0]++;
                                pbTiempo.setProgress(segundos[0]);
                                txtTiempo.setText("Tiempo: " + segundos[0]);
                                endGame();
                            }
                        }
                    });
                }
            }
        });
        thread.start();
    }

    //Método para finalizar el juego
    private void endGame() {
        if (segundos[0]==tiempo){
            bandera=false;
            bandera1=false;
            win.stop();
            endstage.start();

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.item_resumen);
            TextView txtJugador1 = dialog.findViewById(R.id.txtJugador1);
            TextView txtJugador2 = dialog.findViewById(R.id.txtJugador2);
            TextView txtPunJ1 = dialog.findViewById(R.id.txtResultado1);
            TextView txtPunJ2 = dialog.findViewById(R.id.txtResultado2);
            TextView txtTiempoF = dialog.findViewById(R.id.txtTiempoF);
            Button btnContianuar = dialog.findViewById(R.id.btnContianuar);
            GestorDB gestorDB = new GestorDB(MainActivity.this);
            Score score1 =new Score();
            Score score2 =new Score();
            score1.setNombre(Splash.jugador1);
            score2.setNombre(Splash.jugador2);
            score1.setPuntaje(puntajeJ1);
            score2.setPuntaje(puntajeJ2);
            score1.setModo(1);
            score2.setModo(1);
            score1.setDificultad(nivel);
            score2.setDificultad(nivel);
            score1.setTiempo(segundos[0]);
            score2.setTiempo(segundos[0]);
            gestorDB.inputData(score1);
            gestorDB.inputData(score2);
            txtJugador1.setText(score1.getNombre());
            txtJugador2.setText(score2.getNombre());
            txtPunJ1.setText(Integer.toString(score1.getPuntaje()));
            txtPunJ2.setText(Integer.toString(score2.getPuntaje()));
            txtTiempoF.setText(Integer.toString(segundos[0]));
            btnContianuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.cancel();
                    finish();
                }
            });
            final String messege = Splash.jugador1+" Puntuación: "+puntajeJ1+"\n"+
                    Splash.jugador2+" Puntuación: "+puntajeJ2+"\n"+
                    "Dificultad: "+nivel+"\n"+
                    "Tiempo"+segundos[0]+"\n";
            ImageButton btnTwi = dialog.findViewById(R.id.btnTwi);
            ImageButton btnface = dialog.findViewById(R.id.btnFace);

            btnTwi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.twitter.android");
                    intent.putExtra(Intent.EXTRA_TEXT, messege);

                    try {
                        startActivity(intent);

                    }catch (Exception e){

                        Toast.makeText(MainActivity.this, "No cuentas con esta app", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }
                }
            });

            btnface.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setQuote("")
                            .setContentUrl(Uri.parse(""))
                            .setShareHashtag(new ShareHashtag.Builder()
                                    .setHashtag("")
                                    .build())
                            .build();

                    if (shareDialog.canShow(ShareLinkContent.class)){

                        shareDialog.show(content);
                    }
                }
            });

            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        }
    }

    //Método apra ingresar el adapter al contenedor
    private void inputAdapter() {
        AdapterJ adapterJ = new AdapterJ(imagenesFondo,item,this);
        contenedorJuego.setAdapter(adapterJ);
        contenedorJuego.setLayoutManager(new GridLayoutManager(this,columnas,GridLayoutManager.VERTICAL,false));
        contenedorJuego.setHasFixedSize(true);
        adapterJ.setMlistener(new AdapterJ.OnItemClickListener() {
            @Override
            public void itemClick(int position, ImageView imageView, View itemView) {
                canselect++;
                if (pos1==position || pos2 == position){
                    canselect--;
                }

                if (canselect==1){
                    pos1=position;
                    imagen1=imageView;
                    item1=itemView;
                    item1.setEnabled(false);
                    animator1 = ViewAnimationUtils.createCircularReveal(imagen1,0,imageView.getHeight(),0,imageView.getHeight()*1.5f);
                    animator1.setDuration(400);
                    animator1.start();
                }

                if (canselect==2){
                    pos2=position;
                    imagen2=imageView;
                    item2=itemView;
                    item2.setEnabled(false);
                    animator2 = ViewAnimationUtils.createCircularReveal(imagen2,0,imageView.getHeight(),0,imageView.getHeight()*1.5f);
                    animator2.setDuration(400);
                    animator2.start();
                }
                mostarImagen(position,imageView);

            }
        });

    }

    private void mostarImagen(int position, ImageView imageView) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inSampleSize=3;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imagenesAleatorias[position],op);
        imageView.setImageBitmap(bitmap);
        if (canselect==2){
            movimientos++;
            AdapterJ.bandera=false;
            new ValidarJuego().execute();
        }
    }

    public class ValidarJuego extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AdapterJ.bandera=false;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (imagenesAleatorias[pos1]==imagenesAleatorias[pos2]){
                animator1 = ViewAnimationUtils.createCircularReveal(imagen1,imagen1.getWidth()/2,imagen1.getHeight()/2, imagen1.getHeight()/2, 0);
                animator1.setDuration(400);
                animator1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        item1.setVisibility(View.INVISIBLE);
                        imagen1=null;
                        item1=null;
                    }
                });

                animator2 = ViewAnimationUtils.createCircularReveal(imagen2,imagen2.getWidth()/2,imagen2.getHeight()/2, imagen2.getHeight()/2, 0);
                animator2.setDuration(400);
                animator2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        item2.setVisibility(View.INVISIBLE);
                        imagen2=null;
                        item2=null;
                        AdapterJ.bandera=true;
                        pos1=-1;
                        pos2=-1;
                        canselect=0;
                    }
                });

                animator1.start();animator2.start();

                if (inicioJuego==1){
                    puntajeJ1+=100;
                    inputPoints();
                }else {
                    puntajeJ2+=100;
                    inputPoints();
                }
                win.start();

                salir--;
                if (salir==0){
                    bandera=false;
                    bandera1=false;
                    win.stop();
                    endstage.start();
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.item_resumen);
                    TextView txtJugador1 = dialog.findViewById(R.id.txtJugador1);
                    TextView txtJugador2 = dialog.findViewById(R.id.txtJugador2);
                    TextView txtPunJ1 = dialog.findViewById(R.id.txtResultado1);
                    TextView txtPunJ2 = dialog.findViewById(R.id.txtResultado2);
                    TextView txtTiempoF = dialog.findViewById(R.id.txtTiempoF);
                    Button btnContianuar = dialog.findViewById(R.id.btnContianuar);
                    GestorDB gestorDB = new GestorDB(MainActivity.this);
                    Score score1 =new Score();
                    Score score2 =new Score();

                    score1.setNombre(Splash.jugador1);
                    score2.setNombre(Splash.jugador2);
                    score1.setPuntaje(puntajeJ1);
                    score2.setPuntaje(puntajeJ2);
                    score1.setModo(1);
                    score2.setModo(1);
                    score1.setDificultad(nivel);
                    score2.setDificultad(nivel);
                    score1.setTiempo(segundos[0]);
                    score2.setTiempo(segundos[0]);
                    gestorDB.inputData(score1);
                    gestorDB.inputData(score2);
                    txtJugador1.setText(score1.getNombre());
                    txtJugador2.setText(score2.getNombre());
                    txtPunJ1.setText(Integer.toString(score1.getPuntaje()));
                    txtPunJ2.setText(Integer.toString(score2.getPuntaje()));
                    txtTiempoF.setText(Integer.toString(segundos[0]));
                    btnContianuar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.cancel();
                            finish();
                        }
                    });
                    final String messege = Splash.jugador1+" Puntuación: "+puntajeJ1+"\n"+
                            Splash.jugador2+" Puntuación: "+puntajeJ2+"\n"+
                            "Dificultad: "+nivel+"\n"+
                            "Tiempo"+segundos[0]+"\n";
                    ImageButton btnTwi = dialog.findViewById(R.id.btnTwi);
                    ImageButton btnface = dialog.findViewById(R.id.btnFace);

                    btnTwi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.setPackage("com.twitter.android");
                            intent.putExtra(Intent.EXTRA_TEXT, messege);

                            try {
                                startActivity(intent);

                            }catch (Exception e){

                                Toast.makeText(MainActivity.this, "No cuentas con esta app", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();

                            }
                        }
                    });

                    btnface.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ShareLinkContent content = new ShareLinkContent.Builder()
                                    .setQuote("")
                                    .setContentUrl(Uri.parse(""))
                                    .setShareHashtag(new ShareHashtag.Builder()
                                            .setHashtag("")
                                            .build())
                                    .build();

                            if (shareDialog.canShow(ShareLinkContent.class)){

                                shareDialog.show(content);
                            }

                        }
                    });

                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }


            }else {
                BitmapFactory.Options op = new BitmapFactory.Options();
                op.inSampleSize=3;
                final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),fondoJuego,op);

                animator1 = ViewAnimationUtils.createCircularReveal(imagen1,imagen1.getWidth()/2,imagen1.getHeight()/2, imagen1.getHeight()/2, 0);
                animator1.setDuration(400);
                animator1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Animator animator = ViewAnimationUtils.createCircularReveal(imagen1,imagen1.getWidth()/2,imagen1.getHeight()/2, 0,imagen1.getHeight()/2);
                        animator.setDuration(400);
                        imagen1.setImageBitmap(bitmap);
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                item1.setEnabled(true);

                            }
                        });
                        animator.start();
                    }
                });

                lose.start();

                animator2 = ViewAnimationUtils.createCircularReveal(imagen2,imagen2.getWidth()/2,imagen2.getHeight()/2, imagen2.getHeight()/2, 0);
                animator2.setDuration(400);
                animator2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Animator animator = ViewAnimationUtils.createCircularReveal(imagen2,imagen2.getWidth()/2,imagen2.getHeight()/2, 0,imagen2.getHeight()/2);
                        animator.setDuration(400);
                        imagen2.setImageBitmap(bitmap);
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                item2.setEnabled(true);

                                AdapterJ.bandera=true;
                                pos1=-1;
                                pos2=-1;
                                canselect=0;
                            }
                        });
                        animator.start();
                    }
                });

                animator1.start();animator2.start();
                if (inicioJuego==1){
                    puntajeJ1-=2;
                    inputPoints();
                }else {
                    puntajeJ2-=2;
                    inputPoints();
                }

                if (inicioJuego==1){
                    txtNombreJ2.setTextColor(Color.BLACK);
                    txtNombreJ1.setTextColor(getColor(R.color.colorGris));
                    txtPuntajeJ2.setTextColor(Color.BLACK);
                    txtPuntajeJ1.setTextColor(getColor(R.color.colorGris));
                    inicioJuego=2;


                }else{
                    txtNombreJ1.setTextColor(Color.BLACK);
                    txtNombreJ2.setTextColor(getColor(R.color.colorGris));
                    txtPuntajeJ1.setTextColor(Color.BLACK);
                    txtPuntajeJ2.setTextColor(getColor(R.color.colorGris));
                    inicioJuego=1;

                }


            }

        }
    }





    private void printKeyHash() {


        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.davidpopayan.sena.emparejaapp",PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures){

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }

        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        bandera1=false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        bandera1=false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bandera1=false;
        bandera=false;
    }
}
