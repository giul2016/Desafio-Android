package com.br.appanuncios.activity;


import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;


import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.br.appanuncios.R;
import com.br.appanuncios.model.Anuncio;
import com.squareup.picasso.Picasso;

import me.relex.photodraweeview.PhotoDraweeView;


public class fresco extends AppCompatActivity {

    private Anuncio anuncioSelecionado;
    ImageView img ;
    String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_fresco);

      final PhotoDraweeView photoDraweeView = (PhotoDraweeView) findViewById(R.id.my_image_view);

        //Aqui crio uma string de nome foto para receber a string passada da activity onde estava carouselView

        foto = (String) this.getIntent().getSerializableExtra("foto");
        Picasso.get().load(String.valueOf(foto)).into(photoDraweeView);



//        photoDraweeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Toast.makeText(Imagem.this, "" + foto, Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
        Picasso.get().load(foto);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(foto))
                .build();


        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(photoDraweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);

                        photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                        if (imageInfo == null || photoDraweeView == null) {
                            return;
                        }
                    }
                })
                .setImageRequest(request)
                .build();

        photoDraweeView.setController(controller);

        //Uso Biblioteca Picasso para receber a string e abrir na ImageView desta activity









//        String img = "";
//
//
//
//        Bundle extras = getIntent().getExtras();
//        if(extras != null) {
//            img = extras.getString("img");
//        }
//
//        Resources res = getResources();
//        int resID = res.getIdentifier(img , "drawable", getPackageName());
//        Drawable drawable = res.getDrawable(resID);
//
//        ImageView iv = new ImageView(this);
//        iv.setImageDrawable(drawable);
    }


}
