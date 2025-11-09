package Models;

public class Mecanico {

    private boolean admin;
    private String usuario;
    private String contrasenia;


        /// Constructores

    public Mecanico(boolean admin, String usuario, String contrasenia) {
        this.admin = admin;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public Mecanico() {
        this.admin = false;
        this.usuario = "";
        this.contrasenia = "";
    }


    /// Getters Setters

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }


    /// Metodos

    @Override
    public String toString() {
        return "Mecanico{" +
                "admin=" + admin +
                ", usuario='" + usuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }




}
