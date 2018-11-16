package developer.minotta.parcialdos_jerryminotta;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    EditText et_correo, et_contra;
    Button btn_registrarse, btn_iniciar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        et_contra = findViewById(R.id.et_contra);
        et_correo = findViewById(R.id.et_correo);
        btn_iniciar = findViewById(R.id.btn_iniciarSesion);
        btn_registrarse = findViewById(R.id.btn_registrarse);


        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String correo = et_correo.getText().toString().trim();
                final String contra = et_contra.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            String Uid = firebaseAuth.getCurrentUser().getUid();

                            Usuario user = new Usuario(correo, contra);

                            databaseReference.child("usuarios").child(Uid).setValue(user);

                            Intent intent= new Intent(MainActivity.this, Chat.class);

                            startActivity(intent);

                        }else{
                            Toast.makeText(MainActivity.this, "Registro Incompleto", Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
        });


        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String correo = et_correo.getText().toString().trim();
                final String contra = et_contra.getText().toString().trim();


                firebaseAuth.signInWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"¡Bienvenido!", Toast.LENGTH_SHORT).show();

                            Intent intent= new Intent(MainActivity.this, Chat.class);

                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Correo o contraseña no valido", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
