package com.zizehost.ysdlpapp.FragmentLateral.Principal.guias;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zizehost.ysdlpapp.R;

/**
 * Created by Guillermo on 07/04/2016.
 */
public class AdaptadorGuias extends RecyclerView.Adapter<AdaptadorGuias.ViewHolder> {

    private Cursor cursor;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView escuelaGuia;

        public ViewHolder(View v) {
            super(v);
            escuelaGuia = (TextView) v.findViewById(R.id.nombreGuiasEscuela);

        }
    }

    public AdaptadorGuias(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (cursor != null)
            return cursor.getCount();
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_card_guiasescuelas, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        final String escuelaGuia;
        final String urlGuia;
        final String estadoGuia;

        escuelaGuia = cursor.getString(1);
        urlGuia = cursor.getString(2);
        estadoGuia = cursor.getString(3);

        viewHolder.escuelaGuia.setText(escuelaGuia);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                int estadoR = Integer.parseInt(estadoGuia);

                if (estadoR==0){
                    Toast.makeText(context, "Guia no disponible para esta escuela", Toast.LENGTH_SHORT).show();
                }
                if (estadoR==1){
                    String link = urlGuia;
                    Intent intent = null;
                    intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
                    context.startActivity(intent);
                }

            }
        });
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }

}
