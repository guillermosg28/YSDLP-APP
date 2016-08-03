package com.zizehost.ysdlpapp.FragmentPrincipal.eventos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zizehost.ysdlpapp.R;

/**
 * Created by Guillermo on 06/04/2016.
 */
public class AdaptadorEventos extends RecyclerView.Adapter<AdaptadorEventos.ViewHolder> {

    private Cursor cursor;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView tituloEvento;
        public TextView fechaEvento;
        public ImageView imagenEvento;

        public ViewHolder(View v) {
            super(v);
            tituloEvento = (TextView) v.findViewById(R.id.nombreEvento);
            imagenEvento = (ImageView) v.findViewById(R.id.imagenEvento);
            fechaEvento = (TextView) v.findViewById(R.id.fechaEvento);

        }
    }

    public AdaptadorEventos(Context context) {
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
                .inflate(R.layout.list_card_eventos, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        final String tituloEvento;
        String fechaEvento;
        String imagenEvento;
        final String urlEvento;

        tituloEvento = cursor.getString(1);
        fechaEvento = cursor.getString(2);
        imagenEvento = cursor.getString(3);
        urlEvento = cursor.getString(4);

        viewHolder.tituloEvento.setText(tituloEvento);
        viewHolder.fechaEvento.setText(fechaEvento);
        Picasso.with(context).load(imagenEvento)
                .error(R.drawable.ic_noticia_default)
                .placeholder(R.drawable.ic_noticia_default)
                .into(viewHolder.imagenEvento);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetalleEventoActivity.class);
                intent.putExtra("url", urlEvento);
                context.startActivity(intent);
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