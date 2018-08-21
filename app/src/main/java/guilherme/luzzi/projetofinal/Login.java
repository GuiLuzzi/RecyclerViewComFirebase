package guilherme.luzzi.projetofinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import guilherme.luzzi.projetofinal.model.Usuario;

public class Login extends AppCompatActivity {

    private EditText etLogin;
    private EditText etSenha;
    private Button btLogar;

    //Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Refs
        initialize();

        //Ref firebase
        mAuth = FirebaseAuth.getInstance();

        //Verificando se o usuário já logou anteriormente
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser usuario = firebaseAuth.getCurrentUser();

                if (usuario != null) {
                    //Usuário está logado
                    Toast.makeText(
                            getBaseContext(),
                            "Usuário já está logado",
                            Toast.LENGTH_LONG).show();

                    //redirecionar para tela de abertura
                    Intent it = new Intent(Login.this, TelaMenu.class);
                    startActivity(it);
                    finish();

                } else {
                    //Usuário NÃO está logado
                    Toast.makeText(
                            getBaseContext(),
                            "Usuário não está logado ainda",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etLogin.getText().toString().isEmpty() &&
                        !etSenha.getText().toString().isEmpty()) {


                    Usuario u = new Usuario();
                    u.setLogin(etLogin.getText().toString());
                    u.setSenha(etSenha.getText().toString());

                    //Verificar no firebase
                    mAuth.signInWithEmailAndPassword(u.getLogin(), u.getSenha())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getBaseContext(),
                                                "Usuário não autenticado",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getBaseContext(),
                                                "Usuário autenticado com sucesso",
                                                Toast.LENGTH_LONG).show();
                                        //Ir para outra tela
                                        Intent it = new Intent(Login.this, TelaMenu.class);
                                        startActivity(it);
                                        finish();
                                    }//fecha else
                                }//fecha onComplete
                            });
                } else {
                    Toast.makeText(getBaseContext(),
                            "Digite os dados para logar",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }//fecha onCreate

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void initialize () {
        //Refs
        etLogin = findViewById(R.id.lo_et_login);
        etSenha = findViewById(R.id.lo_et_senha);
        btLogar = findViewById(R.id.lo_bt_logar);
    }
}//Fecha classe





