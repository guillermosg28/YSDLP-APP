package com.zizehost.ysdlpapp.FragmentPrincipal.noticia;

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
 * Created by Guillermo on 04/04/2016.
 */
public class AdaptadorNoticias extends RecyclerView.Adapter<AdaptadorNoticias.ViewHolder> {

    private Cursor cursor;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView title;
        public TextView fecha;
        public ImageView imagen;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            imagen = (ImageView) v.findViewById(R.id.thumbnail);
            fecha = (TextView) v.findViewById(R.id.fecha);

        }
    }

    public AdaptadorNoticias(Context context) {
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
                .inflate(R.layout.lista_card_noticias, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        final String title;
        String fecha;
        String imagen;
        final String url;

        title = cursor.getString(1);
        fecha = cursor.getString(2);
        imagen = cursor.getString(3);
        url = cursor.getString(4);

        viewHolder.title.setText(title);
        viewHolder.fecha.setText(fecha);
        Picasso.with(context).load(imagen)
                .error(R.drawable.ic_noticia_default)
                .placeholder(R.drawable.ic_noticia_default)
                .into(viewHolder.imagen);



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetalleNoticiaActivity.class);
                intent.putExtra("url", url);
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
