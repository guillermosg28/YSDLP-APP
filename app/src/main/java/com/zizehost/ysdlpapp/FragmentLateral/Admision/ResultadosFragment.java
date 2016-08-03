package com.zizehost.ysdlpapp.FragmentLateral.Admision;

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

import com.zizehost.ysdlpapp.FragmentLateral.Admision.resultados.AdaptadorAdmision;
import com.zizehost.ysdlpapp.R;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP;

/**
 * Created by Guillermo on 18/03/2016.
 */
public class ResultadosFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorAdmision adapter;
    private ProgressBar progressBar;

    private static final int LOADER_ID_4 = 4;
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks_4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_resultados, container, false);

        // Initialize recycler view

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_resultados);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        adapter = new AdaptadorAdmision(getActivity());
        recyclerView.setAdapter(adapter);


        mCallbacks_4 = this;
        LoaderManager lm = getLoaderManager();
        lm.initLoader(LOADER_ID_4, null, mCallbacks_4);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_resultados);
        progressBar.setVisibility(View.VISIBLE);

        return rootView;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ContratoYSDLP.Admision.URI_CONTENIDO,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        progressBar.setVisibility(View.GONE);
        switch (loader.getId()) {
            case LOADER_ID_4:
                adapter.swapCursor(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


}
