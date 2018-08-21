package guilherme.luzzi.projetofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import guilherme.luzzi.projetofinal.adapter.ClienteAdapter;
import guilherme.luzzi.projetofinal.model.Cliente;

public class MainActivity extends AppCompatActivity {

    private EditText etNome;
    private RadioGroup rgSexo;
    private RadioButton rdMasculino;
    private RadioButton rdFemino;
    private EditText etCidade;
    private Button btOk;
    private RecyclerView rvClientes;

    private ArrayList<Cliente> clientes;
    private ClienteAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        FirebaseApp.initializeApp(MainActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("clientes");

        clientes = new ArrayList<>();
        adapter = new ClienteAdapter(MainActivity.this, clientes);

        rvClientes.setAdapter(adapter);

        rvClientes.setHasFixedSize(true);
        rvClientes.setLayoutManager( new LinearLayoutManager(this));

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente c = new Cliente();

                c.setNome(etNome.getText().toString());

                if(rgSexo.getCheckedRadioButtonId() == rdMasculino.getId()){
                    c.setSexo(rdMasculino.getText().toString());
                }else{
                    c.setSexo(rdFemino.getText().toString());
                }//fecha else

                c.setCidade(etCidade.getText().toString());

                banco.push().setValue(c);//enviando pro banco

                Toast.makeText(getBaseContext(),
                        "Cliente cadastrado",
                        Toast.LENGTH_SHORT).show();

                Intent it = new Intent(MainActivity.this, TelaMenu.class);
            }//fecha onClick
        });//fecha clickListener

        adapter.setOnItemClickListener(new ClienteAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(getBaseContext(),
                        "Clicou no cliente: "+position+"\ncliente"+clientes.get(position),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(final int position, View v) {
                AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                msg.setTitle("Alerta");
                msg.setMessage("Você tem certeza que deseja excluir o cliente?");
                msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cliente c =  clientes.get(position);

                        banco.child(c.getKey()).removeValue();

                        clientes.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(),
                                "Cliente removido",
                                Toast.LENGTH_LONG).show();
                    }
                });
                msg.setNegativeButton("Não", null);
                msg.show();
            }//fecha itemLongClick
        });//itemOnClick

        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientes.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Cliente c = data.getValue(Cliente.class);
                    c.setKey(data.getKey());
                    clientes.add(c);
                }//fecha for
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }//fecha onCreate
    public void initialize(){
        //Refs
        etNome = findViewById(R.id.ma_et_nome);
        rgSexo = findViewById(R.id.ma_rg_sexo);
        rdMasculino = findViewById(R.id.ma_rd_masculino);
        rdFemino = findViewById(R.id.ma_rd_feminino);
        etCidade = findViewById(R.id.ma_et_cidade);
        btOk = findViewById(R.id.ma_bt_ok);
        rvClientes = findViewById(R.id.ma_rv_clientes);
    }
}//fecha classe
