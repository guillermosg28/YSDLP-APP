package com.zizehost.ysdlpapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP.Noticias;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP.Eventos;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP.Guias;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP.Admision;

/**
 * Created by Guillermo on 07/04/2016.
 */
public class BaseDatosYSDLP extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "ysdlp.db";

    private static final int VERSION_ACTUAL = 1;

    private final Context contexto;

    interface Tablas {
        String NOTICIAS = "noticias";
        String EVENTOS = "eventos";
        String GUIAS = "guias";
        String ADMISION = "admision";
    }

    public BaseDatosYSDLP(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String cmd = "CREATE TABLE " + Tablas.NOTICIAS + " (" +
                Noticias._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Noticias.N_TITULO + " TEXT, " +
                Noticias.N_FECHA + " TEXT, " +
                Noticias.N_IMAGEN + " TEXT, " +
                Noticias.N_URL + " TEXT," +
                Noticias.N_ID_REMOTA + " TEXT UNIQUE," +
                Noticias.N_ESTADO + " INTEGER NOT NULL DEFAULT " + Noticias.N_ESTADO_OK + "," +
                Noticias.N_PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";

        db.execSQL(cmd);

        String cmd2 = "CREATE TABLE " + Tablas.EVENTOS + " (" +
                Eventos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Eventos.E_TITULO + " TEXT, " +
                Eventos.E_FECHA + " TEXT, " +
                Eventos.E_IMAGEN + " TEXT, " +
                Eventos.E_URL + " TEXT," +
                Eventos.E_ID_REMOTA + " TEXT UNIQUE," +
                Eventos.E_ESTADO + " INTEGER NOT NULL DEFAULT " + Eventos.E_ESTADO_OK + "," +
                Eventos.E_PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        db.execSQL(cmd2);

        String cmd3 = "CREATE TABLE " + Tablas.GUIAS + " (" +
                Guias._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Guias.G_NOMBRE + " TEXT, " +
                Guias.G_URL + " TEXT," +
                Guias.G_ESTADO_GUIA + " TEXT, " +
                Guias.G_ID_REMOTA + " TEXT UNIQUE," +
                Guias.G_ESTADO + " INTEGER NOT NULL DEFAULT " + Guias.G_ESTADO_OK + "," +
                Guias.G_PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";

        db.execSQL(cmd3);

        String cmd4 = "CREATE TABLE " + Tablas.ADMISION + " (" +
                Admision._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Admision.A_NOMBRE + " TEXT, " +
                Admision.A_URL + " TEXT," +
                Admision.A_URL_BASE + " TEXT," +
                Admision.A_ESTADO_ADMISION + " TEXT, " +
                Admision.A_ID_REMOTA + " TEXT UNIQUE," +
                Admision.A_ESTADO + " INTEGER NOT NULL DEFAULT " + Admision.A_ESTADO_OK + "," +
                Admision.A_PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        db.execSQL(cmd4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.NOTICIAS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.EVENTOS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.GUIAS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.ADMISION);
        onCreate(db);
    }


}