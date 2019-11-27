package com.br.appanuncios.activity;




import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.chrisbanes.photoview.PhotoView;
import com.br.appanuncios.R;
import com.br.appanuncios.model.Anuncio;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class Imagem extends AppCompatActivity {
  ImageView img ;
  PhotoView imagemview;
  String foto;


    private Anuncio anuncioSelecionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_imagem);

       PhotoView imageview = (PhotoView) findViewById(R.id.imageView10);

        //Aqui crio uma string de nome foto para receber a string passada da activity onde estava carouselView

        foto = (String) this.getIntent().getSerializableExtra("foto");
        Picasso.get().load(String.valueOf(foto)).into(imageview);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // Toast.makeText(Imagem.this, "" + foto, Toast.LENGTH_SHORT).show();


            }
        });
        Picasso.get().load(foto);

        //Uso Biblioteca Picasso para receber a string e abrir na ImageView desta activity


//
    }
    private void sharedImage(){
        // Vamos carregar a imagem em um bitmap
        Bitmap b = BitmapFactory.decodeFile(foto);//decodeResource(getResources(), R.mipmap.ic_launcher_round);
        Intent share = new Intent(Intent.ACTION_SEND);
        //setamos o tipo da imagem
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // comprimomos a imagem
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        // Gravamos a imagem
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Titulo da Imagem", null);
        // criamos uam Uri com o endereço que a imagem foi salva
        Uri imageUri =  Uri.parse(path);
        // Setmaos a Uri da imagem
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        // chama o compartilhamento
        startActivity(Intent.createChooser(share, "Selecione"));
    }
}
