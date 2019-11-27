package com.giul.projetonovo.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.Login;
import com.giul.projetonovo.MainActivity;
import com.giul.projetonovo.R;
import com.giul.projetonovo.config.ConfiguracaoFirebase;
import com.giul.projetonovo.helper.Base64Custom;
import com.giul.projetonovo.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import org.w3c.dom.Text;

public class CadastroActivity extends AppCompatActivity {


    private AlertDialog alerta;

    private EditText campoUserName, campoEmail, campoPassword;
    TextView botaoCreateUserRegister,botaoLoginRegister;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    ProgressBar progressBar3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // o seguinte trecho pode ser omitido
        if (android.os.Build.VERSION.SDK_INT > 11 && android.os.Build.VERSION.SDK_INT < 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        } else if (android.os.Build.VERSION.SDK_INT >= 19) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        }

        setContentView(R.layout.activity_cadastro);


        progressBar3 = findViewById(R.id.progressBar3);
        campoUserName = findViewById(R.id.userNameregister);
        campoEmail = findViewById(R.id.emailRegister);
        campoPassword = findViewById(R.id.passwordRegister);
        botaoCreateUserRegister = findViewById(R.id.btCreateUserRegister);
        botaoLoginRegister = (TextView) findViewById(R.id.btLoginRegister);
       botaoLoginRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              // startActivity(new Intent(getApplicationContext(),MapsActivity.class));
               startActivity(new Intent(getApplicationContext(),LoginActivity.class));
           }
       });

        progressBar3.setVisibility(View.INVISIBLE);

        botaoCreateUserRegister = findViewById(R.id.btCreateUserRegister);
        botaoCreateUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoNome = campoUserName.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoPassword.getText().toString();

                //Validar se os campos foram preenchidos
                if ( !textoNome.isEmpty() ){
                    if ( !textoEmail.isEmpty() ){
                        if ( !textoSenha.isEmpty() ){

                            usuario = new Usuario();
                            usuario.setNome( textoNome );
                            usuario.setEmail( textoEmail );
                            usuario.setSenha( textoSenha );
                            progressBar3.setVisibility(View.VISIBLE);
                            cadastrarUsuario();

                        }else {
                            progressBar3.setVisibility(View.INVISIBLE);
                            Toast.makeText(CadastroActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        progressBar3.setVisibility(View.INVISIBLE);
                        Toast.makeText(CadastroActivity.this,

                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    progressBar3.setVisibility(View.INVISIBLE);
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });










    }

    private void exemplo_simples() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Titulo");
        //define a mensagem
        builder.setMessage("Usuario criado com sucesso?");
        //define um botão como positivo
        builder.setPositiveButton("Positivo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(CadastroActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(CadastroActivity.this, "Nao foi possivel cadastrar usuario" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){
                    progressBar3.setVisibility(View.INVISIBLE);

                    String idUsuario = Base64Custom.codificarBase64( usuario.getEmail() );
                    usuario.setIdUsuario( idUsuario );
                    usuario.salvar();
                    campoEmail.setText("");
                    campoPassword.setText("");
                    Toast.makeText(CadastroActivity.this, "Usuario cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        progressBar3.setVisibility(View.INVISIBLE);
                        excecao = "Digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        progressBar3.setVisibility(View.INVISIBLE);
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        progressBar3.setVisibility(View.INVISIBLE);
                        excecao = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        progressBar3.setVisibility(View.INVISIBLE);
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
