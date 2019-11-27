package com.br.appanuncios.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.br.appanuncios.R;
import com.br.appanuncios.model.Anuncio;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.io.ByteArrayOutputStream;

public class DetalhesProdutoActivity extends AppCompatActivity {
    private TextView loja;
    private CarouselView carouselView;
    private TextView titulo;
    private TextView descricao;
    private TextView estado;
    private TextView preco;
    private Anuncio anuncioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_detalhes_produto);

        //Configurar toolbar
        getSupportActionBar().setTitle("Detalhe produto");

        //Incializar componentes de interface
        inicializarComponentes();

        //Recupera anúncio para exibicao
        anuncioSelecionado = (Anuncio) getIntent().getSerializableExtra("anuncioSelecionado");

        if( anuncioSelecionado != null ){
            loja.setText(anuncioSelecionado.getNomeLoja());
            titulo.setText( anuncioSelecionado.getTitulo() );
            descricao.setText( anuncioSelecionado.getDescricao() );
            estado.setText( anuncioSelecionado.getEstado() );
            preco.setText( anuncioSelecionado.getValor());

            final ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    String urlString = anuncioSelecionado.getFotos().get( position );
                    Picasso.get().load(urlString).into(imageView);


                }
            };

            carouselView.setPageCount( anuncioSelecionado.getFotos().size() );
            carouselView.setImageListener( imageListener );
            carouselView.setImageClickListener(new ImageClickListener() {
                @Override
                public void onClick(int position) {

                   String img =anuncioSelecionado.getFotos().get(position);

                   // Toast.makeText(DetalhesProdutoActivity.this, "img " + img, Toast.LENGTH_SHORT).show();
                    //Utilizei  toast acima para conferir se estava pegando a string na posiçao da foto no carouselview
//
                    Intent intent = new Intent(getApplicationContext(), com.br.appanuncios.activity.Imagem.class);
                    intent.putExtra("foto", img); //nesta linha ja com a string capturada na posiçao passei ela via intent para outra activity(Imagem.class)

                    startActivity(intent);

                   // Toast.makeText(DetalhesProdutoActivity.this, "item clicado" + position, Toast.LENGTH_SHORT).show();

                    //Este toast usei para testar olick e a posiçao capturada
                }
            });

        }


    } private static final int SOLICITAR_PERMISSAO = 1;
    private void checarPermissao(){

        // Verifica  o estado da permissão de WRITE_EXTERNAL_STORAGE
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // Se for diferente de PERMISSION_GRANTED, então vamos exibir a tela padrão
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, SOLICITAR_PERMISSAO);
        } else {
            // Senão vamos compartilhar a imagem
            sharedImage();
        }
    }

    public void visualizarTelefone(View view){
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", anuncioSelecionado.getTelefone(), null ));
        startActivity( i );
    }

    private void inicializarComponentes(){
        carouselView = findViewById(R.id.carouselView);
        titulo = findViewById(R.id.textTituloDetalhe);
        descricao = findViewById(R.id.textDescricaoDetalhe);
        estado = findViewById(R.id.textEstadoDetalhe);
        preco = findViewById(R.id.textPrecoDetalhe);
        loja = findViewById(R.id.nomeLoja);
    }
    private void sharedImage(){
        // Vamos carregar a imagem em um bitmap
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
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




