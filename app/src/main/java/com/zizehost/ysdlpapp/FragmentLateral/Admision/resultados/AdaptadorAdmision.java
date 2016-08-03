package com.zizehost.ysdlpapp.FragmentLateral.Admision.resultados;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zizehost.ysdlpapp.R;

/**
 * Created by Guillermo on 08/04/2016.
 */
public class AdaptadorAdmision extends RecyclerView.Adapter<AdaptadorAdmision.ViewHolder> {

    private Cursor cursor;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView escuelaAdmision;

        public ViewHolder(View v) {
            super(v);
            escuelaAdmision = (TextView) v.findViewById(R.id.nombreEscuelaAdmision);

        }
    }

    public AdaptadorAdmision(Context context) {
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
                .inflate(R.layout.list_card_escuelas, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        final String escuelaAdmision;
        final String urlAdmsion;
        final String urlAdmisionBase;
        final String estadoAdmision;

        escuelaAdmision = cursor.getString(1);
        urlAdmsion = cursor.getString(2);
        urlAdmisionBase = cursor.getString(3);
        estadoAdmision = cursor.getString(4);

        viewHolder.escuelaAdmision.setText(escuelaAdmision);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, DetalleResutadoActivity.class);

                int estadoR = Integer.parseInt(estadoAdmision);

                Log.d("ERROR ",estadoAdmision);
                Log.d("ERROR URL",urlAdmsion);

                if (estadoR==1){
                    intent.putExtra("urlResultado", urlAdmisionBase);
                    intent.putExtra("estadoResultado",estadoAdmision);
                }
                if (estadoR==2){
                    intent.putExtra("urlResultado", urlAdmsion);
                    intent.putExtra("estadoResultado",estadoAdmision);
                }
                if (estadoR==3){
                    intent.putExtra("urlResultado", urlAdmisionBase);
                    intent.putExtra("estadoResultado",estadoAdmision);
                }

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

