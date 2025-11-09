package Models;

import Enums.Marca;
import Exceptions.StockInsuficienteException;

import java.util.Objects;

public class Repuesto extends ItemTaller {
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
}
