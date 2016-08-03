package com.zizehost.ysdlpapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pushbots.push.Pushbots;
import com.zizehost.ysdlpapp.FragmentLateral.Admision.ResultadosFragment;
import com.zizehost.ysdlpapp.FragmentLateral.Configuracion.FaqFragment;
import com.zizehost.ysdlpapp.FragmentLateral.Principal.CronogramaFragment;
import com.zizehost.ysdlpapp.FragmentLateral.Principal.GuiasFragment;
import com.zizehost.ysdlpapp.sync.SyncAdapterYSDLP;


/**
 * Created by Guillermo on 17/03/2016.
 */
public class PrincipalActivity extends AppCompatActivity {
    // VARIABLES LOCALES
    private DrawerLayout mDrawerLayout;
    customHandler c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Pushbots.sharedInstance().init(this);
        Pushbots.sharedInstance().setCustomHandler(customHandler.class);

        //Setear Toolbar como action bar
        agregarToolbar();

        //Notificacion
        Boolean Notificacion = false;

        if (Notificacion) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Reiniciar...");
            alertDialog.setMessage("Estás seguro?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        // Create Navigation drawer and inlfate layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

        if (isOnline()) {

        } else {
            Snackbar.make(mDrawerLayout, "En estos momentos no tienes acceso a internet, no podrás actualizar el contenido!", Snackbar.LENGTH_LONG).show();
        }


        SyncAdapterYSDLP.inicializarSyncAdapter(this);
        SyncAdapterYSDLP.sincronizarAhora(this, false);

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {

            case R.id.nav_home:
                fragmentoGenerico = new PrincipalFragment();
                setTitle("YSDLP");
                break;
            case R.id.nav_cronograma:
                fragmentoGenerico = new CronogramaFragment();
                setTitle(itemDrawer.getTitle());
                break;
            case R.id.nav_guias:
                fragmentoGenerico = new GuiasFragment();
                setTitle(itemDrawer.getTitle());
                break;
            /**case R.id.nav_a_cronograma:
             fragmentoGenerico = new AdmCronogramaFragment();
             setTitle(itemDrawer.getTitle());
             break;**/
            case R.id.nav_resultados:
                fragmentoGenerico = new ResultadosFragment();
                setTitle(itemDrawer.getTitle());
                break;
            case R.id.nav_faq:
                fragmentoGenerico = new FaqFragment();
                setTitle(itemDrawer.getTitle());
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_sync_noticias) {

            if (isOnline()) {
                SyncAdapterYSDLP.sincronizarAhora(this, false);
                Snackbar.make(mDrawerLayout, "Contenido actualizado correctamente!", Snackbar.LENGTH_LONG).show();

            } else {
                Snackbar.make(mDrawerLayout, "En estos momentos no tienes acceso a internet, no podrás actualizar el contenido!", Snackbar.LENGTH_LONG).show();
            }

            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
}