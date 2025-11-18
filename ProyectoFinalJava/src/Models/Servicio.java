package Models;

import Interfaces.IJson;
import org.json.JSONException;
import org.json.JSONObject;

public class Servicio extends ItemTaller implements IJson {
    private int tiempoEstimado;
    private String descripcion;

    public Servicio(String nombre, double precio, int tiempoEstimado, String descripcion) {
        super(nombre, precio);
        this.tiempoEstimado = tiempoEstimado;
        this.descripcion = descripcion;
    }
    public Servicio() {
        super("", 0.0);
        this.tiempoEstimado = 0;
        this.descripcion = "";
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    /// To json
/// Con el identificador de tipo es para en ticket hacer una diferencia entre servicio y repuesto
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object = super.toJson();
        try {
            object.put("tiempoEstimado", this.tiempoEstimado);
            object.put("descripcion", this.descripcion);
            object.put("tipo", "Servicio");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }


    /// FromJson
    /// fromJson para reconstruir el objeto desde JSON
    public static Servicio fromJson(JSONObject object) {
        Servicio servicio = new Servicio();
        try {
            servicio.setNombre(object.getString("nombre"));
            servicio.setPrecio(object.getDouble("precio"));
            servicio.setCantidad(object.getInt("cantidad"));
            servicio.setTiempoEstimado(object.getInt("tiempoEstimado"));
            servicio.setDescripcion(object.getString("descripcion"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return servicio;
    }


    /*
    @Override
public JSONObject toJson() throws JSONException {
    JSONObject object = super.toJson();
    try {
        object.put("tiempoEstimado", this.tiempoEstimado);
        object.put("descripcion", this.descripcion);
        object.put("tipo", "Servicio"); // identificador de clase
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return object;
}

/// fromJson para reconstruir el objeto desde JSON
public static Servicio fromJson(JSONObject object) {
    Servicio servicio = new Servicio();
    try {
        servicio.setNombre(object.getString("nombre"));
        servicio.setPrecio(object.getDouble("precio"));
        servicio.setCantidad(object.getInt("cantidad"));
        servicio.setTiempoEstimado(object.getInt("tiempoEstimado"));
        servicio.setDescripcion(object.getString("descripcion"));
        // no necesitamos setear "tipo"
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return servicio;
}


 ///Explicacion

 Cuando tenemos una clase padre abstracta (ItemTaller) y varias clases hijas (Repuesto, Servicio), no podemos instanciar directamente la clase padre. Pero en tu Ticket tenés un ArrayList<ItemTaller> que puede contener cualquier tipo de objeto hijo.

Entonces, al guardar esos objetos en JSON, necesitamos saber de qué tipo de hijo se trata para poder reconstruirlo correctamente al leer el JSON. Por eso:

En toJson() de cada clase hija, agregamos una propiedad "tipo" con el nombre de la clase ("Repuesto" o "Servicio"). Esto no necesita ser un atributo de la clase, porque solo sirve para la serialización.

Al leer el JSON (fromJson()), podemos mirar object.getString("tipo") y decidir si creamos un Repuesto o un Servicio.

Esto nos permite tener una colección heterogénea de ItemTaller, serializarla a JSON y luego reconstruir exactamente los objetos correctos, sin perder información del tipo real.

En resumen:

"tipo" en el JSON es solo un marcador para identificar la clase al deserializar.

No se guarda como atributo en la clase, solo se pone en el JSON.

Permite manejar listas de objetos de distintas subclases de forma segura.





    */

}
