package developer.minotta.parcialdos_jerryminotta;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Chat extends AppCompatActivity {

    ListView lv_mensajes;
    EditText et_mensaje;
    ImageButton ib_enviar;

    FirebaseAuth firebaseAuth;


    String nameUser;

    FirebaseListAdapter firebaseListAdapter;


    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        lv_mensajes = findViewById(R.id.lv_mensajes);
        et_mensaje = findViewById(R.id.et_mensaje);
        ib_enviar = findViewById(R.id.ib_send);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        Query chat = databaseReference.child("Chats");

        FirebaseListOptions firebaseListOptions = new FirebaseListOptions.Builder<Mensaje>()
                .setLayout(R.layout.renglon)
                .setQuery(chat, Mensaje.class).build();


        firebaseListAdapter = new FirebaseListAdapter<Mensaje>(firebaseListOptions) {

            @Override
            protected void populateView(@NonNull View v, @NonNull Mensaje model, final int position) {
                TextView nombre = v.findViewById(R.id.tv_emisorName);
                TextView mensaje = v.findViewById(R.id.tv_mensaje);

                nombre.setText(model.emisor);
                nombre.setText(model.mensaje);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        firebaseListAdapter.getRef(position).removeValue();
                    }
                });
            }
        };


        lv_mensajes.setAdapter(firebaseListAdapter);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child("usuarios").child(user.getUid());





        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dato : dataSnapshot.getChildren()){
                    Usuario user = dato.getValue(Usuario.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        ib_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mensaje = et_mensaje.getText().toString();

                Mensaje m = new Mensaje(nameUser, mensaje);

                DatabaseReference publicar = firebaseDatabase.getReference();

                publicar.child("Chat").push().setValue(m);

            }
        });

    }

    @Override
    protected void onStart() {
        firebaseListAdapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        firebaseListAdapter.stopListening();
        super.onStop();
    }
}
