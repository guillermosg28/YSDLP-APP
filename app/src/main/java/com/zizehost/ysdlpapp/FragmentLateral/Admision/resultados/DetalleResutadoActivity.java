package com.zizehost.ysdlpapp.FragmentLateral.Admision.resultados;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.zizehost.ysdlpapp.R;

/**
 * Created by Guillermo on 22/03/2016.
 */
public class DetalleResutadoActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_resultados);

        Bundle bundle = getIntent().getExtras();

        //Metodo para cerrar noticia
        Button buttonCerrarResultado;
        buttonCerrarResultado = (Button) findViewById(R.id.buttonCerrarResultado);
        buttonCerrarResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        int estadoR = Integer.parseInt(bundle.getString("estadoResultado"));

        if (estadoR==3){
            Toast.makeText(this, "Resultados no disponible para esta escuela", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            abrirURL(bundle.getString("urlResultado"));
        }

    }

    public void abrirURL(String url) {
        if (VerificarInternet()) {
            WebView wbv = (WebView) findViewById(R.id.webview_detalle_resultados);
            wbv.getSettings().setBuiltInZoomControls(false); // Controles de zoom
            //habilitamos javascript y zoom
            wbv.getSettings().setJavaScriptEnabled(true);
            //browser.getSettings().setBuiltInZoomControls(true);

            progressBar = ProgressDialog.show(DetalleResutadoActivity.this, "Mensaje: ", "Cargando resultados...");

            wbv.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    if (Uri.parse(url).getHost().equals("capitulosdenovela.net")) {
                        return false;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                public void onPageFinished(WebView view, String url) {
                    Log.i(TAG, "Finished loading URL: " +url);
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                }

            });

            wbv.loadUrl(url);
        } else {
            Toast.makeText(this, "ERROR : En estos momentos no tienes conexion a internet", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean VerificarInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // estamos conectados?
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.v(ACCESSIBILITY_SERVICE, "No hay conexión a intenet");
            return false;
        }
    }

}