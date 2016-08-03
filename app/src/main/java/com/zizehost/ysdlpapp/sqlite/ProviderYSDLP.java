package com.zizehost.ysdlpapp.sqlite;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;


import com.zizehost.ysdlpapp.sqlite.BaseDatosYSDLP.Tablas;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP.Noticias;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP.Eventos;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP.Guias;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP.Admision;


/**
 * Created by Guillermo on 07/04/2016.
 */
public class ProviderYSDLP extends ContentProvider {


    public static final String TAG = "Provider";
    public static final String URI_NO_SOPORTADA = "Uri no soportada";

    private BaseDatosYSDLP helper;

    private ContentResolver resolver;


    public ProviderYSDLP() {
    }

    // [URI_MATCHER]
    public static final UriMatcher uriMatcher;

    // Casos
    public static final int NOTICIAS = 300;
    public static final int NOTICIAS_ID = 301;

    public static final int EVENTOS = 400;
    public static final int EVENTOS_ID = 401;

    public static final int GUIAS = 500;
    public static final int GUIAS_ID = 501;

    public static final int ADMISION = 600;
    public static final int ADMISION_ID = 601;

    public static final String AUTORIDAD = "com.zizehost.ysdlp";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTORIDAD, "noticias", NOTICIAS);
        uriMatcher.addURI(AUTORIDAD, "noticias/*", NOTICIAS_ID);

        uriMatcher.addURI(AUTORIDAD, "eventos", EVENTOS);
        uriMatcher.addURI(AUTORIDAD, "eventos/*", EVENTOS_ID);

        uriMatcher.addURI(AUTORIDAD, "guias", GUIAS);
        uriMatcher.addURI(AUTORIDAD, "guias/*", GUIAS_ID);

        uriMatcher.addURI(AUTORIDAD, "admision", ADMISION);
        uriMatcher.addURI(AUTORIDAD, "admision/*", ADMISION_ID);
    }
    // [/URI_MATCHER]

    @Override
    public boolean onCreate() {
        helper = new BaseDatosYSDLP(getContext());
        resolver = getContext().getContentResolver();
        return true;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete: " + uri);

        SQLiteDatabase bd = helper.getWritableDatabase();
        String id;
        int afectados;

        switch (uriMatcher.match(uri)) {

            case NOTICIAS_ID:
                id = Noticias.obtenerIdNoticia(uri);
                afectados = bd.delete(Tablas.NOTICIAS,
                        Noticias._ID + "=" + "\"" + id + "\""
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;

            case EVENTOS_ID:
                id = Eventos.obtenerIdEvento(uri);
                afectados = bd.delete(Tablas.EVENTOS,
                        Eventos._ID + "=" + "\"" + id + "\""
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;

            case GUIAS_ID:
                id = Guias.obtenerIdGuia(uri);
                afectados = bd.delete(Tablas.GUIAS,
                        Guias._ID + "=" + "\"" + id + "\""
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;

            case ADMISION_ID:
                id = Admision.obtenerIdAdmision(uri);
                afectados = bd.delete(Tablas.ADMISION,
                        Admision._ID + "=" + "\"" + id + "\""
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }
        return afectados;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case NOTICIAS:
                return ContratoYSDLP.generarMime("noticias");
            case NOTICIAS_ID:
                return ContratoYSDLP.generarMimeItem("noticias");
            case EVENTOS:
                return ContratoYSDLP.generarMime("eventos");
            case EVENTOS_ID:
                return ContratoYSDLP.generarMimeItem("eventos");
            case GUIAS:
                return ContratoYSDLP.generarMime("guias");
            case GUIAS_ID:
                return ContratoYSDLP.generarMimeItem("guias");
            case ADMISION:
                return ContratoYSDLP.generarMime("admision");
            case ADMISION_ID:
                return ContratoYSDLP.generarMimeItem("admision");
            default:
                throw new UnsupportedOperationException("Uri desconocida =>" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.d(TAG, "Inserción en " + uri + "( " + values.toString() + " )\n");

        SQLiteDatabase bd = helper.getWritableDatabase();
        String id = null;

        switch (uriMatcher.match(uri)) {
            case NOTICIAS:
                bd.insertOrThrow(Tablas.NOTICIAS, null, values);
                notificarCambio(uri);
                return Noticias.crearUriNoticia(values.getAsString(Noticias._ID));

            case EVENTOS:
                bd.insertOrThrow(Tablas.EVENTOS, null, values);
                notificarCambio(uri);
                return Eventos.crearUriEvento(values.getAsString(Eventos._ID));

            case GUIAS:
                bd.insertOrThrow(Tablas.GUIAS, null, values);
                notificarCambio(uri);
                return Guias.crearUriGuia(values.getAsString(Guias._ID));

            case ADMISION:
                bd.insertOrThrow(Tablas.ADMISION, null, values);
                notificarCambio(uri);
                return Admision.crearUriAdmision(values.getAsString(Admision._ID));

            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }

    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Obtener base de datos
        SQLiteDatabase bd = helper.getReadableDatabase();

        // Comparar Uri
        int match = uriMatcher.match(uri);

        // string auxiliar para los ids
        String id;

        Cursor c;

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (match) {
            case NOTICIAS:
                sortOrder = "fecha DESC";
                c = bd.query(Tablas.NOTICIAS, projection,
                        selection, selectionArgs,null, null, sortOrder);
                break;
            case NOTICIAS_ID:
                id = Noticias.obtenerIdNoticia(uri);
                c = bd.query(Tablas.NOTICIAS, projection,
                        Noticias._ID + " = ?",
                        new String[]{id}, null, null, null);
                break;

            case EVENTOS:
                sortOrder = "fecha DESC";
                c = bd.query(Tablas.EVENTOS, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case EVENTOS_ID:
                id = Eventos.obtenerIdEvento(uri);
                c = bd.query(Tablas.EVENTOS, projection,
                        Eventos._ID + " = ?",
                        new String[]{id}, null, null, null);
                break;

            case GUIAS:
                sortOrder = "nombre ASC";
                c = bd.query(Tablas.GUIAS, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case GUIAS_ID:
                id = Guias.obtenerIdGuia(uri);
                c = bd.query(Tablas.GUIAS, projection,
                        Guias._ID + " = ?",
                        new String[]{id}, null, null, null);
                break;

            case ADMISION:
                sortOrder = "nombre ASC";
                c = bd.query(Tablas.ADMISION, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case ADMISION_ID:
                id = Admision.obtenerIdAdmision(uri);
                c = bd.query(Tablas.ADMISION, projection,
                        Guias._ID + " = ?",
                        new String[]{id}, null, null, null);
                break;

            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }

        c.setNotificationUri(resolver, uri);

        return c;

    }


    private void notificarCambio(Uri uri) {
        resolver.notifyChange(uri, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase bd = helper.getWritableDatabase();
        String id;
        int afectados;

        switch (uriMatcher.match(uri)) {
            case NOTICIAS_ID:
                id = Noticias.obtenerIdNoticia(uri);
                afectados = bd.update(Tablas.NOTICIAS, values,
                        Noticias.N_ID_REMOTA + "=" + "\"" + id + "\""
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;

            case EVENTOS_ID:
                id = Eventos.obtenerIdEvento(uri);
                afectados = bd.update(Tablas.EVENTOS, values,
                        Eventos.E_ID_REMOTA + "=" + "\"" + id + "\""
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;

            case GUIAS_ID:
                id = Eventos.obtenerIdEvento(uri);
                afectados = bd.update(Tablas.GUIAS, values,
                        Guias.G_ID_REMOTA + "=" + "\"" + id + "\""
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;

            case ADMISION_ID:
                id = Admision.obtenerIdAdmision(uri);
                afectados = bd.update(Tablas.ADMISION, values,
                        Admision.A_ID_REMOTA + "=" + "\"" + id + "\""
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }

        return afectados;
    }
}
