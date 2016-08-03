package com.zizehost.ysdlpapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zizehost.ysdlpapp.FragmentPrincipal.BuscameFragment;
import com.zizehost.ysdlpapp.FragmentPrincipal.EventosFragment;
import com.zizehost.ysdlpapp.FragmentPrincipal.NoticiasFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Guillermo on 17/03/2016.
 */
public class PrincipalFragment extends Fragment {
    private AppBarLayout appBar;
    private TabLayout pestanas;
    private ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        if (savedInstanceState == null) {
            insertarTabs(container);

            // Setear adaptador al viewpager.
            viewPager = (ViewPager) view.findViewById(R.id.pager);
            viewPager.setOffscreenPageLimit(2); //No refresar los tabs (2) - 3 tabs
            poblarViewPager(viewPager);
            pestanas.setupWithViewPager(viewPager);
        }

        //SyncAdapterYSDLP.inicializarSyncAdapter(getActivity());
        //SyncAdapterYSDLP.sincronizarAhora(getActivity(), false);

        return view;
    }

    private void insertarTabs(ViewGroup container) {
        View padre = (View) container.getParent();
        appBar = (AppBarLayout) padre.findViewById(R.id.appbar);
        pestanas = new TabLayout(getActivity());
        pestanas.setTabTextColors(Color.parseColor("#b3dcd8"), Color.parseColor("#ffffff"));
        pestanas.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        appBar.addView(pestanas);
    }

    private void poblarViewPager(ViewPager viewPager) {
        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());
        adapter.addFragment(new NoticiasFragment(), getString(R.string.noticias_tabs));
        adapter.addFragment(new EventosFragment(), getString(R.string.eventos_tabs));
        adapter.addFragment(new BuscameFragment(), getString(R.string.avisos_tabs));
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(pestanas);
    }

    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentos.get(position);
        }

    }

    }





