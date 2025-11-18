package Models;

import Enums.Marca;
import Exceptions.StockInsuficienteException;
import Interfaces.IJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Repuesto extends ItemTaller implements IJson {
    private int id;
    private int stock;
    private Marca marca;
    private double costo;

    public Repuesto(String nombre, double precio, int id, int stock, Marca marca, double costo) {
        super(nombre, precio);
        this.id = id;
        this.stock = stock;
        this.marca = marca;
        this.costo = costo;
    }
    public Repuesto() {
        super("", 0.0);
        this.id = 0;
        this.stock = 0;
        this.marca = null;
        this.costo = 0.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Repuesto repuesto)) return false;
        if (!super.equals(o)) return false;
        return id == repuesto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    public double venderRepuesto(int cantidad)throws StockInsuficienteException {
        if (cantidad < this.stock){
            throw new StockInsuficienteException("No hay stock suficiente");
        }
        this.stock -= cantidad;
        return this.getPrecio() * cantidad;
    }

    public void modificarStock(int cantidad){
        this.setStock(cantidad);
    }



    /// To json

    /// Con el identificador de tipo es para en ticket hacer una diferencia entre servicio y repuesto
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object = super.toJson();
        try {
            object.put("id", this.id);
            object.put("stock", this.stock);
            object.put("marca", this.marca);
            object.put("costo", this.costo);
            object.put("tipo", "Repuesto");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    /// FromJson
    /// fromJson para reconstruir el objeto desde JSON
    public static Repuesto fromJson(JSONObject object) {
        Repuesto repuesto = new Repuesto();
        try {
            repuesto.setNombre(object.getString("nombre"));
            repuesto.setPrecio(object.getDouble("precio"));
            repuesto.setCantidad(object.getInt("cantidad"));
            repuesto.setId(object.getInt("id"));
            repuesto.setStock(object.getInt("stock"));
            repuesto.setMarca(Marca.valueOf(object.getString("marca")));
            repuesto.setCosto(object.getDouble("costo"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return repuesto;
    }

    /*
    @Override
public JSONObject toJson() throws JSONException {
    JSONObject object = super.toJson();
    try {
        object.put("id", this.id);
        object.put("stock", this.stock);
        object.put("marca", this.marca);
        object.put("costo", this.costo);
        object.put("tipo", "Repuesto"); // identificador de clase
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return object;
}

/// fromJson para reconstruir el objeto desde JSON
public static Repuesto fromJson(JSONObject object) {
    Repuesto repuesto = new Repuesto();
    try {
        repuesto.setNombre(object.getString("nombre"));
        repuesto.setPrecio(object.getDouble("precio"));
        repuesto.setCantidad(object.getInt("cantidad"));
        repuesto.setId(object.getInt("id"));
        repuesto.setStock(object.getInt("stock"));
        repuesto.setMarca(Marca.valueOf(object.getString("marca")));
        repuesto.setCosto(object.getDouble("costo"));
        // no necesitamos setear "tipo", solo usarlo al leer
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return repuesto;
}


    */
}
