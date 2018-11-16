package developer.minotta.parcialdos_jerryminotta;

public class Mensaje {

    public String emisor, mensaje;

    public Mensaje(String emisor, String mensaje) {
        this.emisor = emisor;
        this.mensaje = mensaje;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
