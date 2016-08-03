package com.zizehost.ysdlpapp.sync;

import com.zizehost.ysdlpapp.sqlite.ContratoYSDLP;

/**
 * Created by Guillermo on 07/04/2016.
 */
public class Constantes {

    /**
     * Web Service
     */
    public static final String EVENTO_GET_URL = "http://capitulosdenovela.net/AppYSDLP/apis/1_1_0/eventos.php";
    public static final String NOTICIAS_GET_URL = "http://capitulosdenovela.net/AppYSDLP/apis/1_1_0/noticias.php";
    public static final String GUIAS_GET_URL = "http://capitulosdenovela.net/AppYSDLP/apis/1_1_0/escuelas_guias.php";
    public static final String ADMISION_GET_URL = "http://capitulosdenovela.net/AppYSDLP/apis/1_1_0/escuelas_admision.php";

    public static final String NOTICIA_ESTADO = "estado";
    public static final String NOTICIA_NOTICIAS = "noticias";
    public static final String NOTICIA_MENSAJE = "mensaje";
    public static final String NOTICIA_SUCCESS = "1";
    public static final String NOTICIA_FAILED = "2";

    public static final String EVENTO_ESTADO = "estado";
    public static final String EVENTO_EVENTOS = "eventos";
    public static final String EVENTO_MENSAJE = "mensaje";
    public static final String EVENTO_SUCCESS = "1";
    public static final String EVENTO_FAILED = "2";

    public static final String GUIAS_ESTADO = "estado";
    public static final String GUIAS_GUIAS = "guias";
    public static final String GUIAS_MENSAJE = "mensaje";
    public static final String GUIAS_SUCCESS = "1";
    public static final String GUIAS_FAILED = "2";

    public static final String ADMISION_ESTADO = "estado";
    public static final String ADMISION_ADMISION = "admision";
    public static final String ADMISION_MENSAJE = "mensaje";
    public static final String ADMISION_SUCCESS = "1";
    public static final String ADMISION_FAILED = "2";


    public static final String[] PROJECTION_NOTICIAS = new String[]{
            ContratoYSDLP.Noticias._ID,
            ContratoYSDLP.Noticias.N_ID_REMOTA,
            ContratoYSDLP.Noticias.N_TITULO,
            ContratoYSDLP.Noticias.N_FECHA,
            ContratoYSDLP.Noticias.N_IMAGEN,
            ContratoYSDLP.Noticias.N_URL
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_N_ID = 0;
    public static final int COLUMNA_N_ID_REMOTA = 1;
    public static final int COLUMNA_N_TITULO = 2;
    public static final int COLUMNA_N_FECHA = 3;
    public static final int COLUMNA_N_IMAGEN = 4;
    public static final int COLUMNA_N_URL = 5;


    public static final String[] PROJECTION_EVENTOS = new String[]{
            ContratoYSDLP.Eventos._ID,
            ContratoYSDLP.Eventos.E_ID_REMOTA,
            ContratoYSDLP.Eventos.E_TITULO,
            ContratoYSDLP.Eventos.E_FECHA,
            ContratoYSDLP.Eventos.E_IMAGEN,
            ContratoYSDLP.Eventos.E_URL
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_E_ID = 0;
    public static final int COLUMNA_E_ID_REMOTA = 1;
    public static final int COLUMNA_E_TITULO = 2;
    public static final int COLUMNA_E_FECHA = 3;
    public static final int COLUMNA_E_IMAGEN = 4;
    public static final int COLUMNA_E_URL = 5;

    /** GUIAS **/
    public static final String[] PROJECTION_GUIAS = new String[]{
            ContratoYSDLP.Guias._ID,
            ContratoYSDLP.Guias.G_ID_REMOTA,
            ContratoYSDLP.Guias.G_NOMBRE,
            ContratoYSDLP.Guias.G_URL,
            ContratoYSDLP.Guias.G_ESTADO_GUIA
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_G_ID = 0;
    public static final int COLUMNA_G_ID_REMOTA = 1;
    public static final int COLUMNA_G_NOMBRE = 2;
    public static final int COLUMNA_G_URL = 3;
    public static final int COLUMNA_G_ESTADOGUIA = 4;

    /** ADMISION **/
    public static final String[] PROJECTION_ADMISION = new String[]{
            ContratoYSDLP.Admision._ID,
            ContratoYSDLP.Admision.A_ID_REMOTA,
            ContratoYSDLP.Admision.A_NOMBRE,
            ContratoYSDLP.Admision.A_URL,
            ContratoYSDLP.Admision.A_URL_BASE,
            ContratoYSDLP.Admision.A_ESTADO_ADMISION
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA__ID = 0;
    public static final int COLUMNA_A_ID_REMOTA = 1;
    public static final int COLUMNA_A_NOMBRE = 2;
    public static final int COLUMNA_A_URL = 3;
    public static final int COLUMNA_A_URL_BASE = 4;
    public static final int COLUMNA_A_ESTADOADMISION = 5;


    public static final String EVENTO_ACCOUNT_TYPE = "com.zizehost.ysdlp.account";


}
