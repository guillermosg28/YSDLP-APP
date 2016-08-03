package com.zizehost.ysdlpapp.FragmentPrincipal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.zizehost.ysdlpapp.FragmentPrincipal.eventos.AdaptadorEventos;
import com.zizehost.ysdlpapp.R;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP;

/**
 * Created by Guillermo on 17/03/2016.
 */
public class EventosFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorEventos adapter;
    private ProgressBar progressBar;

    private static final int LOADER_ID = 1;
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_eventos, container, false);

        //PROGRESS BARR
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_eventos);
        progressBar.setVisibility(View.VISIBLE);

        // Initialize recycler view
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_eventos);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdaptadorEventos(getActivity());
        recyclerView.setAdapter(adapter);

        mCallbacks = this;
        LoaderManager lm = getLoaderManager();
        lm.initLoader(LOADER_ID, null, mCallbacks);

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ContratoYSDLP.Eventos.URI_CONTENIDO,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        progressBar.setVisibility(View.GONE);
        switch (loader.getId()) {
            case LOADER_ID:
                adapter.swapCursor(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
