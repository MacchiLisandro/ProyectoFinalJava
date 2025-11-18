package Models;

import Interfaces.IJson;
import org.json.JSONException;
import org.json.JSONObject;

public class Mecanico extends Persona implements IJson {

    private String usuario;
    private String contrasenia;


    public Mecanico(String nombre, String apellido, int dni, int telefono, String email, String usuario, String contrasenia) {
        super(nombre, apellido, dni, telefono, email);
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    /// Constructores

    public Mecanico() {
        super("", "",0, 0, "");
        this.usuario = "";
        this.contrasenia = "";
    }



    /// Getters Setters

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
        this.contrasenia = Seguridad.hashearContrasenia(contrasenia);
    }


    /// Metodos

    @Override
    public String toString() {
        return super.toString() +
                "Mecanico{" +
                ", usuario='" + usuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }


    @Override
    public JSONObject toJson() throws JSONException {

        JSONObject object=super.toJson();
        try{
            object.put("usuario",this.usuario);
            object.put("contrasenia",this.contrasenia);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
