package com.giul.projetonovo.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.giul.projetonovo.Permissoes;
import com.giul.projetonovo.R;
import com.giul.projetonovo.activity.FotosCarouselActivity;
import com.giul.projetonovo.config.ConfiguracaoFirebase;
import com.giul.projetonovo.model.Fotos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;






public class PhotoFragment extends Fragment implements View.OnClickListener  {

    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    @Nullable
    ImageView capture ,cadastro1 ,cadastro2, cadastro3;
    //TextView galeria;
    Button galeria;
    private Activity activity;
    private static final int SELECAO_CAMERA  = 100;
    private static final int SELECAO_GALERIA = 200;
    private List<String> listaFotosRecuperadas = new ArrayList<>();
    private List<String> listaURLFotos = new ArrayList<>();
    //private List<String> fotos;

    private Fotos fotos;
    private StorageReference storage;
    private DatabaseReference anuncioUsuarioRef;
    boolean doubleBackToExitPressedOnce = false;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Permissoes.validarPermissoes(permissoesNecessarias, (Activity) getContext(), 1);



        final View view =  inflater.inflate(R.layout.fragment_photo, container, false);

        //cadastro1 = (ImageView) view.findViewById(R.id.image1);
        //cadastro2 = (ImageView) view.findViewById(R.id.image2);
        //cadastro3 = (ImageView) view.findViewById(R.id.image3);

        inicializarComponentes(view);
        storage = ConfiguracaoFirebase.getFirebaseStorage();
        anuncioUsuarioRef = ConfiguracaoFirebase.getFirebase()
                .child("meus_anuncios")
                .child( ConfiguracaoFirebase.getIdUsuario() );


        galeria = (Button) view.findViewById(R.id.rodar);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(getContext(), FotosCarouselActivity.class));
//                salvarFotos(view);
                //enviarFotos();
                //acessarGaleria();
                Toast.makeText(getContext(), "Falta integrar o carrossel", Toast.LENGTH_SHORT).show();
            }
        });
        capture = (ImageView) view.findViewById(R.id.capture_fragment);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
               //Intent intent = new Intent(getContext(), MapsActivity.class);
              //startActivity(intent);
            }
        });
        return view;





    }

//Em andamento para enviar fotos para firebase
    private void salvarFotoStorage(String urlString, final int totalFotos, int contador){

        //Criar nó no storage
        StorageReference imagemFoto = storage.child("imagens")
                .child("fotos")
                .child( fotos.getIdFoto() )
                .child("imagem"+contador);

        //Fazer upload do arquivo
        UploadTask uploadTask = imagemFoto.putFile( Uri.parse(urlString) );

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                Uri firebaseUrl = taskSnapshot.getDownloadUrl();
                String urlConvertida = firebaseUrl.toString();

                listaURLFotos.add( urlConvertida );

                if( totalFotos == listaURLFotos.size() ){
                    fotos.setFotos( listaURLFotos );
                    fotos.salvar();



                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemErro("Falha ao fazer upload");
                Log.i("INFO", "Falha ao fazer upload: " + e.getMessage());
            }
        });

    }

    private void tirarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, SELECAO_CAMERA);


        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            capture.setImageBitmap(imagem);
        }



        Uri imagemSelecionada = data.getData();
        String caminhoImagem = imagemSelecionada.toString();

        //Configura imagem no ImageView
        if( requestCode == 1 ){
            cadastro1.setImageURI( imagemSelecionada );
        }else if( requestCode == 2 ){
            cadastro2.setImageURI( imagemSelecionada );
        }else if( requestCode == 3 ){
            cadastro3.setImageURI( imagemSelecionada );
        }

        Toast.makeText(getContext(), "foto" + caminhoImagem, Toast.LENGTH_SHORT).show();

        listaFotosRecuperadas.add( caminhoImagem );



//



//        Intent intent = new Intent(getContext(), com.giul.projetonovo.activity.FotosCarouselActivity.class);
//        intent.putExtra("foto", caminhoImagem); //nesta linha ja com a string capturada na posiçao passei ela via intent para outra activity(Imagem.class)
//         startActivity(intent);
//


    }
    private void acessarGaleria(){
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                if (intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(intent, SELECAO_GALERIA );
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        Log.d("onClick", "onClick: " + v.getId() );
        switch ( v.getId() ){
            case R.id.image1 :
                Log.d("onClick", "onClick: " );
                escolherImagem(1);
                break;
            case R.id.image2 :
                escolherImagem(2);
                break;
            case R.id.image3 :
                escolherImagem(3);
                break;
        }

    }

    //Escolhe as imagens ana galeria
    public void escolherImagem(int requestCode){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    private void inicializarComponentes(View view){

        cadastro1 = (ImageView) view.findViewById(R.id.image1);
        cadastro2 = (ImageView) view.findViewById(R.id.image2);
        cadastro3 = (ImageView) view.findViewById(R.id.image3);
        cadastro1.setOnClickListener(this);
        cadastro2.setOnClickListener(this);
        cadastro3.setOnClickListener(this);



    }
    public void enviarFotos() {

        Intent intent = new Intent(getContext(), FotosCarouselActivity.class);
       intent.putParcelableArrayListExtra("imagens", (ArrayList) listaFotosRecuperadas);
       startActivity(intent);
    }
    private void exibirMensagemErro(String texto){
        Toast.makeText(activity, texto, Toast.LENGTH_SHORT).show();
    }
    public void salvarFotos(View view){




        if( listaFotosRecuperadas.size() != 0  ){


//

        }else {
            exibirMensagemErro("Selecione ao menos uma foto!");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for( int permissaoResultado : grantResults ){
            if( permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }

    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            voltar();
        } else {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(getContext(), "Pressione novamente para sair", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void voltar() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);


    }


}
