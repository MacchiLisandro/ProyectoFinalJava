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
        this.contrasenia = Seguridad.hashearContrasenia(contrasenia);
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

    public void setContraseniaJson(String contrasenia){
        this.contrasenia = contrasenia;
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


    /// To json

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

    /// Metodo para validar la contraseña al inciar sesion.
    /// Metodo Contraseña


    public boolean verificarContrasenia(String contraseniaIngresada){

        String hash = Seguridad.hashearContrasenia(contraseniaIngresada);
        return contrasenia.equals(hash);

    }

    /// fromJson

    public static Mecanico fromJson(JSONObject object) {
        Mecanico mecanico = new Mecanico(); // constructor vacío
        try {
            ///  Los atributos que tienen como herencia de Persona
            mecanico.setNombre(object.getString("nombre"));
            mecanico.setApellido(object.getString("apellido"));
            mecanico.setDni(object.getInt("dni"));
            mecanico.setTelefono(object.getInt("telefono"));
            mecanico.setEmail(object.getString("email"));

            // Campos propios de Mecanico
            mecanico.setUsuario(object.getString("usuario"));
            mecanico.setContraseniaJson(object.getString("contrasenia")); // se hash dentro del setter

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mecanico;
    }




}
