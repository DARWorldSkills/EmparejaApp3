package com.davidpopayan.sena.emparejaapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

public class AdapterJ extends RecyclerView.Adapter<AdapterJ.Holder>{
    //Declaración de variables
    int [] imagenesJuego;
    int item;
    Context context;
    public static boolean bandera = true;
    private OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void itemClick(int position, ImageView imageView, View itemView);
    }

    //Constructor para la clase AdapterJ
    public AdapterJ(int[] imagenesJuego, int item, Context context) {
        this.imagenesJuego = imagenesJuego;
        this.item = item;
        this.context = context;
    }

    //Setter para la variable mlistener
    public void setMlistener(OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    //Método para la creación del holder
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(item,parent,false);
        Holder holder = new Holder(view,mlistener);
        return holder;
    }

    //Método para llamar los métodos de la clase Holder
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.connectData(imagenesJuego[position]);
    }

    //Método para saber cuantos items mostrar
    @Override
    public int getItemCount() {
        return imagenesJuego.length;
    }

    //Clase Holder la cual nos permite dominar los items
    public class Holder extends RecyclerView.ViewHolder {
        ImageView item= itemView.findViewById(R.id.imgItem);
        public Holder(final View itemView, final OnItemClickListener mlistener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bandera){
                        if (mlistener!=null){
                            int position = getLayoutPosition();
                            if (position!=RecyclerView.NO_POSITION){
                                mlistener.itemClick(position,item,itemView);
                            }
                        }
                    }
                }
            });
        }

        //Método para el ingresar las imagenes al item
        public void connectData (int imagen){
            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize=2;
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),imagen,op);
            item.setImageBitmap(bitmap);

        }
    }
}
