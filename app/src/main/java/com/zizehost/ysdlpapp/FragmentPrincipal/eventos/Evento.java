package com.zizehost.ysdlpapp.FragmentPrincipal.eventos;

/**
 * Created by Guillermo on 22/03/2016.
 */
public class Evento {
    public String idEvento;
    public String titulo;
    public String fecha;
    public String imagen;
    public String url;

    public Evento(String idEvento, String titulo, String fecha, String imagen, String url) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.fecha = fecha;
        this.imagen = imagen;
        this.url = url;
    }
}