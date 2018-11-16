package developer.minotta.parcialdos_jerryminotta;

public class Usuario {

    private String correo, constra;


    public Usuario(){

    }

    public Usuario(String correo, String constra) {
        this.correo = correo;
        this.constra = constra;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getConstra() {
        return constra;
    }

    public void setConstra(String constra) {
        this.constra = constra;
    }
}
