package com.zizehost.ysdlpapp.FragmentPrincipal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zizehost.ysdlpapp.FragmentPrincipal.noticia.AdaptadorNoticias;
import com.zizehost.ysdlpapp.R;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP;

/**
 * Created by Guillermo on 17/03/2016.
 */
public class NoticiasFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorNoticias adapter;
    private ProgressBar progressBar;


    private static final int LOADER_ID_2 = 2;
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks_2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_noticias, container, false);

        //PROGRESS BARR
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_noticias);
        progressBar.setVisibility(View.VISIBLE);

        // Initialize recycler view
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdaptadorNoticias(getActivity());
        recyclerView.setAdapter(adapter);

        mCallbacks_2 = this;
        LoaderManager lm = getLoaderManager();
        lm.initLoader(LOADER_ID_2, null, mCallbacks_2);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ContratoYSDLP.Noticias.URI_CONTENIDO,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        progressBar.setVisibility(View.GONE);
        switch (loader.getId()) {
            case LOADER_ID_2:
                adapter.swapCursor(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

}
