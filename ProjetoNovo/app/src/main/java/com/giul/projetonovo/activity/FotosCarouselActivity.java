package com.giul.projetonovo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.giul.projetonovo.R;
import com.giul.projetonovo.model.Fotos;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class FotosCarouselActivity extends AppCompatActivity {

    private ArrayList<? extends String> listaFotosRecuperadas = new ArrayList<>();

    private CarouselView carouselView;
    private Fotos fotoSelecionada;
    int NUMBER_OF_PAGES = 5;

    String foto;


    String imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos_carousel);
        //fotoSelecionada = (Fotos) getIntent().getSerializableExtra("fotoSelecionada");


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            listaFotosRecuperadas = extras.getParcelableArrayList("imagens");
            imagem = listaFotosRecuperadas.toString();


            carouselView = findViewById(R.id.carouselView);


            final ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {


                    imageView.setImageResource(Integer.parseInt(listaFotosRecuperadas.get(position)));
                    //String urlString = listaFotosRecuperadas.getFotos().get(position);
                    //Picasso.get().load(urlString).into(imageView);


                }
            };

            carouselView.setPageCount(NUMBER_OF_PAGES);
            carouselView.setImageListener(imageListener);
            carouselView.setImageClickListener(new ImageClickListener() {
                @Override
                public void onClick(int position) {



                    String img = fotoSelecionada.getFotos().get(position);

                    // Toast.makeText(DetalhesProdutoActivity.this, "img " + img, Toast.LENGTH_SHORT).show();
                    //Utilizei  toast acima para conferir se estava pegando a string na posiçao da foto no carouselview
//
                    Intent intent = new Intent(getApplicationContext(), com.giul.projetonovo.activity.Imagem.class);
                    intent.putExtra("foto", img); //nesta linha ja com a string capturada na posiçao passei ela via intent para outra activity(Imagem.class)

                    startActivity(intent);

                    // Toast.makeText(DetalhesProdutoActivity.this, "item clicado" + position, Toast.LENGTH_SHORT).show();

                    //Este toast usei para testar olick e a posiçao capturada
                }
            });


        }
    }
}
