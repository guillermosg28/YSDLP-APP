package com.zizehost.ysdlpapp.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.zizehost.ysdlpapp.FragmentPrincipal.eventos.Evento;
import com.zizehost.ysdlpapp.FragmentPrincipal.noticia.Noticia;
import com.zizehost.ysdlpapp.FragmentLateral.Principal.guias.Guias;
import com.zizehost.ysdlpapp.FragmentLateral.Admision.resultados.Admision;
import com.zizehost.ysdlpapp.R;
import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Guillermo on 07/04/2016.
 */
public class SyncAdapterYSDLP extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapterYSDLP.class.getSimpleName();
    ContentResolver resolver;
    private Gson gson = new Gson();


    public SyncAdapterYSDLP(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        resolver = context.getContentResolver();
    }

    public SyncAdapterYSDLP(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        resolver = context.getContentResolver();
    }

    public static void inicializarSyncAdapter(Context context) {
        obtenerCuentaASincronizar(context);
    }


    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              final SyncResult syncResult) {

        Log.i(TAG, "onPerformSync()...");

        boolean soloSubida = extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false);

        if (!soloSubida) {
            realizarSincronizacionLocalNoticias(syncResult);
            realizarSincronizacionLocalEventos(syncResult);
            realizarSincronizacionLocalGuias(syncResult);
            realizarSincronizacionLocalAdmsion(syncResult);
        } else {
            //realizarSincronizacionRemota();
        }

    }

    private void realizarSincronizacionLocalNoticias(final SyncResult syncResult) {
        Log.i(TAG, "Actualizando el cliente.");

        VolleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest(Request.Method.GET, Constantes.NOTICIAS_GET_URL,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGetNoticia(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }
                )
        );
    }


    private void realizarSincronizacionLocalEventos(final SyncResult syncResult) {
        Log.i(TAG, "Actualizando el cliente.");

        VolleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest(Request.Method.GET, Constantes.EVENTO_GET_URL,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGetEventos(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Log.d(TAG, error.networkResponse.toString());

                            }
                        }
                )
        );
    }

    private void realizarSincronizacionLocalGuias(final SyncResult syncResult) {
        Log.i(TAG, "Actualizando el cliente.");

        VolleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest(Request.Method.GET, Constantes.GUIAS_GET_URL,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGetGuias(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Log.d(TAG, error.networkResponse.toString());

                            }
                        }
                )
        );
    }

    private void realizarSincronizacionLocalAdmsion(final SyncResult syncResult) {
        Log.i(TAG, "Actualizando el cliente.");

        VolleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest(Request.Method.GET, Constantes.ADMISION_GET_URL,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGetAdmision(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Log.d(TAG, error.networkResponse.toString());
                            }
                        }
                )
        );
    }


    private void procesarRespuestaGetNoticia(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.NOTICIA_ESTADO);

            switch (estado) {
                case Constantes.NOTICIA_SUCCESS: // EXITO
                    actualizarDatosLocalesNoticias(response, syncResult);
                    break;
                case Constantes.NOTICIA_FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.NOTICIA_MENSAJE);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void procesarRespuestaGetEventos(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.EVENTO_ESTADO);

            switch (estado) {
                case Constantes.EVENTO_SUCCESS: // EXITO
                    actualizarDatosLocalesEventos(response, syncResult);
                    break;
                case Constantes.EVENTO_FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.EVENTO_MENSAJE);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void procesarRespuestaGetGuias(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.GUIAS_ESTADO);

            switch (estado) {
                case Constantes.GUIAS_SUCCESS: // EXITO
                    actualizarDatosLocalesGuias(response, syncResult);
                    break;
                case Constantes.GUIAS_FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.GUIAS_MENSAJE);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void procesarRespuestaGetAdmision(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ADMISION_ESTADO);

            switch (estado) {
                case Constantes.ADMISION_SUCCESS: // EXITO
                    actualizarDatosLocalesAdmsion(response, syncResult);
                    break;
                case Constantes.ADMISION_FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.ADMISION_MENSAJE);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarDatosLocalesNoticias(JSONObject response, SyncResult syncResult) {

        JSONArray gastos = null;

        try {
            // Obtener array "gastos"
            gastos = response.getJSONArray(Constantes.NOTICIA_NOTICIAS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Noticia[] res = gson.fromJson(gastos != null ? gastos.toString() : null, Noticia[].class);
        List<Noticia> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Tabla hash para recibir las entradas entrantes
        HashMap<String, Noticia> expenseMap = new HashMap<String, Noticia>();
        for (Noticia e : data) {
            expenseMap.put(e.idNoticia, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContratoYSDLP.Noticias.URI_CONTENIDO;
        String select = ContratoYSDLP.Noticias.N_ID_REMOTA + " IS NOT NULL";

        Cursor c = resolver.query(uri, Constantes.PROJECTION_NOTICIAS, select, null, null);
        assert c != null;
        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        String id;
        String titulo;
        String fecha;
        String imagen;
        String url;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(Constantes.COLUMNA_N_ID_REMOTA);
            titulo = c.getString(Constantes.COLUMNA_N_TITULO);
            fecha = c.getString(Constantes.COLUMNA_N_FECHA);
            imagen = c.getString(Constantes.COLUMNA_N_IMAGEN);
            url = c.getString(Constantes.COLUMNA_N_URL);

            Noticia match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContratoYSDLP.Noticias.URI_CONTENIDO.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b = match.title != null && !match.title.equals(titulo);
                boolean b1 = match.fecha != null && !match.fecha.equals(fecha);
                boolean b2 = match.imagen != null && !match.imagen.equals(imagen);
                boolean b3 = match.url != null && !match.url.equals(url);

                if (b || b1 || b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoYSDLP.Noticias.N_TITULO, match.title)
                            .withValue(ContratoYSDLP.Noticias.N_FECHA, match.fecha)
                            .withValue(ContratoYSDLP.Noticias.N_IMAGEN, match.imagen)
                            .withValue(ContratoYSDLP.Noticias.N_URL, match.url)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoYSDLP.Noticias.URI_CONTENIDO.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Noticia e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de: " + e.idNoticia);
            ops.add(ContentProviderOperation.newInsert(ContratoYSDLP.Noticias.URI_CONTENIDO)
                    .withValue(ContratoYSDLP.Noticias.N_ID_REMOTA, e.idNoticia)
                    .withValue(ContratoYSDLP.Noticias.N_TITULO, e.title)
                    .withValue(ContratoYSDLP.Noticias.N_FECHA, e.fecha)
                    .withValue(ContratoYSDLP.Noticias.N_IMAGEN, e.imagen)
                    .withValue(ContratoYSDLP.Noticias.N_URL, e.url)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(ContratoYSDLP.AUTORIDAD, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContratoYSDLP.Noticias.URI_CONTENIDO,
                    null,
                    false);
            Log.i(TAG, "Sincronización finalizada.");

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }

    }


    private void actualizarDatosLocalesEventos(JSONObject response, SyncResult syncResult) {

        JSONArray gastos = null;
        try {
            // Obtener array "gastos"
            gastos = response.getJSONArray(Constantes.EVENTO_EVENTOS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Evento[] res = gson.fromJson(gastos != null ? gastos.toString() : null, Evento[].class);
        List<Evento> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Tabla hash para recibir las entradas entrantes
        HashMap<String, Evento> expenseMap = new HashMap<String, Evento>();
        for (Evento e : data) {
            expenseMap.put(e.idEvento, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContratoYSDLP.Eventos.URI_CONTENIDO;
        String select = ContratoYSDLP.Eventos.E_ID_REMOTA + " IS NOT NULL";

        Cursor c = resolver.query(uri, Constantes.PROJECTION_EVENTOS, select, null, null);
        assert c != null;
        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String titulo;
        String fecha;
        String imagen;
        String url;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(Constantes.COLUMNA_E_ID_REMOTA);
            titulo = c.getString(Constantes.COLUMNA_E_TITULO);
            fecha = c.getString(Constantes.COLUMNA_E_FECHA);
            imagen = c.getString(Constantes.COLUMNA_E_IMAGEN);
            url = c.getString(Constantes.COLUMNA_E_URL);

            Evento match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContratoYSDLP.Eventos.URI_CONTENIDO.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b = match.titulo != null && !match.titulo.equals(titulo);
                boolean b1 = match.fecha != null && !match.fecha.equals(fecha);
                boolean b2 = match.imagen != null && !match.imagen.equals(imagen);
                boolean b3 = match.url != null && !match.url.equals(url);

                if (b || b1 || b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoYSDLP.Eventos.E_TITULO, match.titulo)
                            .withValue(ContratoYSDLP.Eventos.E_FECHA, match.fecha)
                            .withValue(ContratoYSDLP.Eventos.E_IMAGEN, match.imagen)
                            .withValue(ContratoYSDLP.Eventos.E_URL, match.url)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoYSDLP.Eventos.URI_CONTENIDO.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Evento e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de: " + e.idEvento);
            ops.add(ContentProviderOperation.newInsert(ContratoYSDLP.Eventos.URI_CONTENIDO)
                    .withValue(ContratoYSDLP.Eventos.E_ID_REMOTA, e.idEvento)
                    .withValue(ContratoYSDLP.Eventos.E_TITULO, e.titulo)
                    .withValue(ContratoYSDLP.Eventos.E_FECHA, e.fecha)
                    .withValue(ContratoYSDLP.Eventos.E_IMAGEN, e.imagen)
                    .withValue(ContratoYSDLP.Eventos.E_URL, e.url)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(ContratoYSDLP.AUTORIDAD, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContratoYSDLP.Eventos.URI_CONTENIDO,
                    null,
                    false);
            Log.i(TAG, "Sincronización finalizada.");

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }

    }

    private void actualizarDatosLocalesGuias(JSONObject response, SyncResult syncResult) {

        JSONArray guias = null;
        try {
            // Obtener array "gastos"
            guias = response.getJSONArray(Constantes.GUIAS_GUIAS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Guias[] res = gson.fromJson(guias != null ? guias.toString() : null, Guias[].class);
        List<Guias> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Tabla hash para recibir las entradas entrantes
        HashMap<String, Guias> expenseMap = new HashMap<String, Guias>();
        for (Guias e : data) {
            expenseMap.put(e.idGuias, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContratoYSDLP.Guias.URI_CONTENIDO;
        String select = ContratoYSDLP.Guias.G_ID_REMOTA + " IS NOT NULL";

        Cursor c = resolver.query(uri, Constantes.PROJECTION_GUIAS, select, null, null);
        assert c != null;
        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String nombre;
        String url;
        String estadoguia;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(Constantes.COLUMNA_G_ID_REMOTA);
            nombre = c.getString(Constantes.COLUMNA_G_NOMBRE);
            url = c.getString(Constantes.COLUMNA_G_URL);
            estadoguia = c.getString(Constantes.COLUMNA_G_ESTADOGUIA);

            Guias match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContratoYSDLP.Guias.URI_CONTENIDO.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b = match.nombre != null && !match.nombre.equals(nombre);
                boolean b1 = match.url != null && !match.url.equals(url);
                boolean b2 = match.estadoguia != null && !match.estadoguia.equals(estadoguia);

                if (b || b1 || b2) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoYSDLP.Guias.G_NOMBRE, match.nombre)
                            .withValue(ContratoYSDLP.Guias.G_URL, match.url)
                            .withValue(ContratoYSDLP.Guias.G_ESTADO_GUIA, match.estadoguia)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoYSDLP.Guias.URI_CONTENIDO.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Guias e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de: " + e.idGuias);
            ops.add(ContentProviderOperation.newInsert(ContratoYSDLP.Guias.URI_CONTENIDO)
                    .withValue(ContratoYSDLP.Guias.G_ID_REMOTA, e.idGuias)
                    .withValue(ContratoYSDLP.Guias.G_NOMBRE, e.nombre)
                    .withValue(ContratoYSDLP.Guias.G_URL, e.url)
                    .withValue(ContratoYSDLP.Guias.G_ESTADO_GUIA, e.estadoguia)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(ContratoYSDLP.AUTORIDAD, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContratoYSDLP.Guias.URI_CONTENIDO,
                    null,
                    false);
            Log.i(TAG, "Sincronización finalizada.");

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }

    }

    private void actualizarDatosLocalesAdmsion(JSONObject response, SyncResult syncResult) {

        JSONArray admision = null;
        try {
            // Obtener array "gastos"
            admision = response.getJSONArray(Constantes.ADMISION_ADMISION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Admision[] res = gson.fromJson(admision != null ? admision.toString() : null, Admision[].class);
        List<Admision> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Tabla hash para recibir las entradas entrantes
        HashMap<String, Admision> expenseMap = new HashMap<String, Admision>();
        for (Admision e : data) {
            expenseMap.put(e.idAdmision, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContratoYSDLP.Admision.URI_CONTENIDO;
        String select = ContratoYSDLP.Admision.A_ID_REMOTA + " IS NOT NULL";

        Cursor c = resolver.query(uri, Constantes.PROJECTION_ADMISION, select, null, null);
        assert c != null;
        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String nombre;
        String url;
        String urlbase;
        String estadoadmision;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(Constantes.COLUMNA_A_ID_REMOTA);
            nombre = c.getString(Constantes.COLUMNA_A_NOMBRE);
            url = c.getString(Constantes.COLUMNA_A_URL);
            urlbase = c.getString(Constantes.COLUMNA_A_URL_BASE);
            estadoadmision = c.getString(Constantes.COLUMNA_A_ESTADOADMISION);

            Admision match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContratoYSDLP.Admision.URI_CONTENIDO.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b = match.nombre != null && !match.nombre.equals(nombre);
                boolean b1 = match.url != null && !match.url.equals(url);
                boolean b2 = match.urlbase != null && !match.urlbase.equals(urlbase);
                boolean b3 = match.estadoadmision != null && !match.estadoadmision.equals(estadoadmision);

                if (b || b1 || b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoYSDLP.Admision.A_NOMBRE, match.nombre)
                            .withValue(ContratoYSDLP.Admision.A_URL, match.url)
                            .withValue(ContratoYSDLP.Admision.A_URL_BASE, match.urlbase)
                            .withValue(ContratoYSDLP.Admision.A_ESTADO_ADMISION, match.estadoadmision)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoYSDLP.Admision.URI_CONTENIDO.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Admision e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de: " + e.idAdmision);
            ops.add(ContentProviderOperation.newInsert(ContratoYSDLP.Admision.URI_CONTENIDO)
                    .withValue(ContratoYSDLP.Admision.A_ID_REMOTA, e.idAdmision)
                    .withValue(ContratoYSDLP.Admision.A_NOMBRE, e.nombre)
                    .withValue(ContratoYSDLP.Admision.A_URL, e.url)
                    .withValue(ContratoYSDLP.Admision.A_URL_BASE, e.urlbase)
                    .withValue(ContratoYSDLP.Admision.A_ESTADO_ADMISION, e.estadoadmision)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(ContratoYSDLP.AUTORIDAD, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContratoYSDLP.Admision.URI_CONTENIDO,
                    null,
                    false);
            Log.i(TAG, "Sincronización finalizada.");

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }

    }

    public static void sincronizarAhora(Context context, boolean onlyUpload) {
        Log.i(TAG, "Realizando petición de sincronización manual.");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        if (onlyUpload)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        ContentResolver.requestSync(obtenerCuentaASincronizar(context),
                context.getString(R.string.provider_authority), bundle);
    }

    public static Account obtenerCuentaASincronizar(Context context) {
        // Obtener instancia del administrador de cuentas
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Crear cuenta por defecto
        Account newAccount = new Account(
                context.getString(R.string.app_name), Constantes.EVENTO_ACCOUNT_TYPE);

        // Comprobar existencia de la cuenta
        if (null == accountManager.getPassword(newAccount)) {

            // Añadir la cuenta al account manager sin password y sin datos de usuario
            if (!accountManager.addAccountExplicitly(newAccount, "", null))
                return null;
        }
        Log.i(TAG, "Cuenta de usuario obtenida.");
        return newAccount;
    }

}