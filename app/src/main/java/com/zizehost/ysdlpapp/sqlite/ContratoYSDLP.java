package com.zizehost.ysdlpapp.sqlite;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Guillermo on 07/04/2016.
 */
public class ContratoYSDLP {

    interface ColumnasNoticias {
        //String N_ID = "id";
        String N_ID_REMOTA = "id_remota";
        String N_TITULO = "titulo";
        String N_FECHA = "fecha";
        String N_IMAGEN = "imagen";
        String N_URL = "url";
        String N_ESTADO = "estado";
        int N_ESTADO_OK = 0;
        String N_PENDIENTE_INSERCION = "pendiente_insercion";
    }

    interface ColumnasEventos {
        //String E_ID = "id";
        String E_ID_REMOTA = "id_remota";
        String E_TITULO = "titulo";
        String E_FECHA = "fecha";
        String E_IMAGEN = "imagen";
        String E_URL = "url";
        String E_ESTADO = "estado";
        int E_ESTADO_OK = 0;
        String E_PENDIENTE_INSERCION = "pendiente_insercion";
    }

    interface ColumnasGuias {
        //String E_ID = "id";
        String G_ID_REMOTA = "id_remota";
        String G_NOMBRE = "nombre";
        String G_URL = "url";
        String G_ESTADO_GUIA = "estadoguia";
        String G_ESTADO = "estado";
        int G_ESTADO_OK = 0;
        String G_PENDIENTE_INSERCION = "pendiente_insercion";
    }

    interface ColumnasAdmision {
        //String E_ID = "id";
        String A_ID_REMOTA = "id_remota";
        String A_NOMBRE = "nombre";
        String A_URL = "url";
        String A_URL_BASE = "urlbase";
        String A_ESTADO_ADMISION = "estadoguia";
        String A_ESTADO = "estado";
        int A_ESTADO_OK = 0;
        String A_PENDIENTE_INSERCION = "pendiente_insercion";
    }

    // [URIS]
    public static final String AUTORIDAD = "com.zizehost.ysdlp";

    public static final Uri URI_BASE = Uri.parse("content://" + AUTORIDAD);

    private static final String RUTA_NOTICIAS = "noticias";
    private static final String RUTA_EVENTOS = "eventos";
    private static final String RUTA_GUIAS = "guias";
    private static final String RUTA_ADMISION = "admision";
    // [/URIS]


    // [TIPOS_MIME]
    public static final String BASE_CONTENIDOS = "ysdlp.";

    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
            + BASE_CONTENIDOS;

    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
            + BASE_CONTENIDOS;

    public static String generarMime(String id) {
        if (id != null) {
            return TIPO_CONTENIDO + id;
        } else {
            return null;
        }
    }

    public static String generarMimeItem(String id) {
        if (id != null) {
            return TIPO_CONTENIDO_ITEM + id;
        } else {
            return null;
        }
    }
    // [/TIPOS_MIME]


    public static class Noticias implements ColumnasNoticias,BaseColumns {
        public static final Uri URI_CONTENIDO =
                URI_BASE.buildUpon().appendPath(RUTA_NOTICIAS).build();

        public static Uri crearUriNoticia(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String obtenerIdNoticia(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static class Eventos implements ColumnasEventos,BaseColumns {
        public static final Uri URI_CONTENIDO =
                URI_BASE.buildUpon().appendPath(RUTA_EVENTOS).build();

        public static Uri crearUriEvento(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String obtenerIdEvento(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static class Guias implements ColumnasGuias,BaseColumns {
        public static final Uri URI_CONTENIDO =
                URI_BASE.buildUpon().appendPath(RUTA_GUIAS).build();

        public static Uri crearUriGuia(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String obtenerIdGuia(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static class Admision implements ColumnasAdmision,BaseColumns {
        public static final Uri URI_CONTENIDO =
                URI_BASE.buildUpon().appendPath(RUTA_ADMISION).build();

        public static Uri crearUriAdmision(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }

        public static String obtenerIdAdmision(Uri uri) {
            return uri.getLastPathSegment();
        }
    }


    private ContratoYSDLP() {
    }

}
