package com.zizehost.ysdlpapp.FragmentLateral.Principal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zizehost.ysdlpapp.R;

/**
 * Created by Guillermo on 18/03/2016.
 */
public class CronogramaFragment extends Fragment {

    private static final String TAG = "Main";
    private ProgressDialog progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cronograma, container, false);

        abrirURL(rootView);

        return rootView;
    }

    public void abrirURL(View v) {

        WebView wbv = (WebView) v.findViewById(R.id.webview_cronograma);
        wbv.getSettings().setBuiltInZoomControls(false); // Controles de zoom
        //habilitamos javascript y zoom
        wbv.getSettings().setJavaScriptEnabled(true);
        //browser.getSettings().setBuiltInZoomControls(true);

        progressBar = ProgressDialog.show(getActivity(), "Mensaje: ", "Cargando cronograma...");

        wbv.setWebViewClient(new WebViewClient() {


            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

        });

        wbv.loadData(getString(R.string.pagina_cronograma), "text/html", "utf-8");


    }
}




