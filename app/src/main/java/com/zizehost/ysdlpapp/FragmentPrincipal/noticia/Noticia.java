package com.zizehost.ysdlpapp.FragmentPrincipal.noticia;

/**
 * Created by Guillermo on 04/04/2016.
 */
public class Noticia {
    public String idNoticia;
    public String title;
    public String fecha;
    public String imagen;
    public String url;

    public Noticia(String idNoticia, String title, String fecha, String imagen, String url) {
        this.idNoticia = idNoticia;
        this.title = title;
        this.fecha = fecha;
        this.imagen = imagen;
        this.url = url;
    }
}
