package Models;

import Interfaces.IJson;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Persona  implements IJson {


    private String nombre;
    private String apellido;
    private long  dni;
    private long telefono;
    private String email;


    /// Constructores

    public Persona(String nombre, String apellido, long dni, long telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    public Persona() {
        this.nombre ="";
        this.apellido = "";
        this.dni = 0;
        this.telefono = 0;
        this.email = "";
    }



    /// Getters Setters


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    /// Metodos

    @Override
    public String toString() {
<<<<<<< Updated upstream
        return "[" + dni + "] "
                + nombre + " " + apellido
                + " | Tel: " + telefono
                + " | Email: " + email;
=======
        return "[" + dni + "] " + nombre + " " + apellido +
                " | Tel: " + telefono +
                " | Email: " + email;
>>>>>>> Stashed changes
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Persona persona)) return false;
        return dni == persona.dni;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(dni);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object=new JSONObject();

        try{
            object.put("nombre",this.nombre);
            object.put("apellido",this.apellido);
            object.put("dni",this.dni);
            object.put("telefono",this.telefono);
            object.put("email",this.email);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return object;
    }


}
