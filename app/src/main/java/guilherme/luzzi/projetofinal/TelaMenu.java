package guilherme.luzzi.projetofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class TelaMenu extends AppCompatActivity {

    //menu
    private final int MENU_CADASTRAR = 300;
    private  Toolbar toolbar;
    //drawer
    private Drawer result = null; //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        //Inicio do drawer
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.fundo2)
                .addProfiles(
                        new ProfileDrawerItem().withName("Guilherme Luzzi").withEmail("example.luzzi@gmail.com").withIcon(R.drawable.ic_action_account_circle))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                }).build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home")
                        .withIdentifier(1)
                        .withIcon(GoogleMaterial.Icon.gmd_home),

                    new SecondaryDrawerItem()
                        .withName("Login")
                        .withIdentifier(2).withIcon(GoogleMaterial.Icon.gmd_accessible),

                    new SecondaryDrawerItem()
                    .withName("Cadastro")
                    .withIdentifier(3)
                    .withIcon(GoogleMaterial.Icon.gmd_home)

                     .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                         @Override
                         public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                             switch ((int) drawerItem.getIdentifier()){
                                 case 1:
                                     Toast.makeText(getBaseContext(),
                                             "Você já está na home",
                                             Toast.LENGTH_LONG).show();
                                     break;

                                 case 2:
                                     FirebaseAuth.getInstance().signOut();
                                     Intent i =  new Intent(TelaMenu.this, Login.class);
                                     startActivity(i);
                                     break;

                                 case 3:
                                     Intent it = new Intent(TelaMenu.this, MainActivity.class);
                                     startActivity(it);
                                     break;
                             }
                             return false;
                         }
                     })
                ).build();
    }//fechaq onCreat
}//fecha Classe
