package com.davidpopayan.sena.emparejaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GestorDB extends SQLiteOpenHelper {
    //Constructor para la creacion de la base de datos
    public GestorDB(Context context) {
        super(context, "emparejaApp.db",null,1);
    }

    //Método para cuando se crea la base de datos implementar algunas funciones como crear la table SCORE
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SCORE (NOMBRE TEXT, PUNTAJE INTEGER, MODO INTEGER, DIFICULTAD INTEGER)");

    }

    //Método para ingresar valores a la tabla SCORE
    public void inputData(Score score){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE",score.getNombre());
        values.put("PUNTAJE",score.getPuntaje());
        values.put("MODO",score.getModo());
        values.put("DIFICULTAD",score.getDificultad());
        db.insert("SCORE",null,values);
        db.close();
    }

    //Función que lista en orden descendente los valores de la base de datos
    public List<Score> scoreList(int modo, int dificultad){
        List<Score> results = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SCORE WHERE MODO="+modo+" AND DIFICULTAD="+dificultad+" ORDER BY PUNTAJE DESC;",null);
        if (cursor.moveToFirst()){
            do {
                Score score = new Score();
                score.setNombre(cursor.getString(0));
                score.setPuntaje(cursor.getInt(1));
                score.setModo(cursor.getInt(2));
                score.setDificultad(cursor.getInt(3));
                results.add(score);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return results;
    }

    //Método para cuando se actualiza la base de datos implementar algunas funciones
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
