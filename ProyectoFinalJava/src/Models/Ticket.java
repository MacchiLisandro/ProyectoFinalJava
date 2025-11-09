package Models;

import Enums.MetodoDePago;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

public class Ticket {
    private static int contadorIds = 0;
    private int id;
    private ArrayList<ItemTaller> carrito;
    private Cliente cliente;
    private Mecanico mecanico;
    private MetodoDePago metodoDePago;
    private LocalDate fecha;
    private double precioTotal;

    public Ticket(Cliente cliente, Mecanico mecanico, MetodoDePago metodoDePago) {
        this.id = ++ contadorIds;
        this.cliente = cliente;
        this.mecanico = mecanico;
        this.metodoDePago = metodoDePago;
        this.carrito = new ArrayList<>();
        this.fecha = LocalDate.now();
        this.precioTotal = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ItemTaller> getCarrito() {
        return carrito;
    }

    public void setCarrito(ArrayList<ItemTaller> carrito) {
        this.carrito = carrito;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    public MetodoDePago getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(MetodoDePago metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public boolean agregarCarrito (ItemTaller itemTaller){
        if (itemTaller != null){
            return carrito.add(itemTaller);
        } return false;
    }
}
