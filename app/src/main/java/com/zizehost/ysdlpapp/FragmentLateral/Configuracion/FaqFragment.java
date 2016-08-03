package com.zizehost.ysdlpapp.FragmentLateral.Configuracion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zizehost.ysdlpapp.R;

/**
 * Created by Guillermo on 31/03/2016.
 */
public class FaqFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.faq_mas_informacion);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "http://www.yosoydelapedro.com/App";
                Intent intent = null;
                intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
            }
        });
        return rootView;
    }
}
