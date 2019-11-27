package com.br.appanuncios.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.br.appanuncios.R;
import com.br.appanuncios.R;
import com.br.appanuncios.model.Anuncio;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jamiltondamasceno
 */

public class AdapterAnuncios extends RecyclerView.Adapter<AdapterAnuncios.MyViewHolder> {

    private List<com.br.appanuncios.model.Anuncio> anuncios;
    private Context context;

    public AdapterAnuncios(List<com.br.appanuncios.model.Anuncio> anuncios, Context context) {
        this.anuncios = anuncios;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anuncio, parent, false);
        return new MyViewHolder( item );
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        com.br.appanuncios.model.Anuncio anuncio = anuncios.get(position);
        holder.titulo.setText( anuncio.getTitulo() );
        holder.valor.setText( anuncio.getValor() );
        holder.loja.setText(anuncio.getNomeLoja());


//        Calendar c = Calendar.getInstance();
//        System.out.println("Current time => "+c.getTime());
//
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        String formattedDate = df.format(c.getTime());
//        // formattedDate have current date/time
//
//
//        holder.Data.setText("Postado : "+formattedDate);
//        holder.Data.setGravity(Gravity.LEFT);
//        holder.Data.setTextSize(12);



        //Pega a primeira imagem da lista
        final List<String> urlFotos = anuncio.getFotos();
        String urlCapa = urlFotos.get(0);

        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click adapter", Toast.LENGTH_SHORT).show();
            }
        });


//        holder.foto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new ImageViewer.Builder(v.getContext(), anuncios)
//                        .setStartPosition(0)
//                        .show();
//            }
//        });

        Picasso.get().load(urlCapa).into(holder.foto);




        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time



        // Now we display formattedDate value in TextView

        holder.Data.setText("Postado : "+formattedDate);
        holder.Data.setGravity(Gravity.LEFT);
        holder.Data.setTextSize(12);

        holder.Data.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView loja;
        TextView titulo;
        TextView valor;
        ImageView foto;
        TextView Data;

        public MyViewHolder(View itemView) {
            super(itemView);
            loja =itemView.findViewById(R.id.loja);
            titulo = itemView.findViewById(R.id.textTitulo);
            valor  = itemView.findViewById(R.id.textPreco);
            foto   = itemView.findViewById(R.id.imageAnuncio);
           Data   = itemView.findViewById(R.id.txtData);

        }
    }


}
