package Models;

import Enums.MetodoDePago;
import Exceptions.NoSeEncuentraEnCarritoException;
import Interfaces.IJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

public class Ticket implements IJson {
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

    public static void setContadorIds(int contadorIds) {
        Ticket.contadorIds = contadorIds;
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

    /// Revisar si es con contraseña o administrador
    public void eliminarCarrito (ItemTaller itemTaller) throws NoSeEncuentraEnCarritoException {
        if(!carrito.contains(itemTaller)){
            throw new NoSeEncuentraEnCarritoException("Ese item no se encuentra en el carrito");
        } carrito.remove(itemTaller);
    }
    public void modificarCantidad (ItemTaller itemTaller, int cantidad)throws NoSeEncuentraEnCarritoException{
        if(!carrito.contains(itemTaller)){
            throw new NoSeEncuentraEnCarritoException("Ese item no se encuentra en el carrito");
        }
        for (ItemTaller i : carrito){
            if(i.equals(itemTaller)){
                i.setCantidad(cantidad);
            }
        }
    }

    public void calculaPrecio (){
        double suma = 0;
        for (ItemTaller i : carrito){
            suma += i.getPrecio() * i.getCantidad();
        } suma += (suma * metodoDePago.getRecargo());
        this.precioTotal = suma;
    }
  /// To Json   /// Agregamos el "tipo" para saber qué clase es al reconstruir, lo que habiamos comentado en Repuesto Y Servicio
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        try {
            object.put("Id", this.id);
            JSONArray jsonArray = new JSONArray();

            /// Aca serializamos cada item del carrito.
            for (ItemTaller item : carrito) {
                JSONObject itemJson = item.toJson();


                if (item instanceof Repuesto) {
                    itemJson.put("tipo", "Repuesto");
                } else if (item instanceof Servicio) {
                    itemJson.put("tipo", "Servicio");
                }

                jsonArray.put(itemJson);
            }

            object.put("carrito", jsonArray);
            object.put("cliente", this.cliente.toJson());
            object.put("mecanico", this.mecanico.toJson());
            object.put("metodoDePago", this.metodoDePago.name());
            object.put("fecha", this.fecha.toString());
            object.put("precioTotal", this.precioTotal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }



           /// FromJson

    public static Ticket fromJson(JSONObject object) {
        Ticket ticket = null;
        try {
            // Reconstruimos cliente y mecánico
            Cliente cliente = Cliente.fromJson(object.getJSONObject("cliente"));
            Mecanico mecanico = Mecanico.fromJson(object.getJSONObject("mecanico"));
            MetodoDePago metodo = MetodoDePago.valueOf(object.getString("metodoDePago"));

            ticket = new Ticket(cliente, mecanico, metodo);
            ticket.setId(object.getInt("Id"));
            ticket.setPrecioTotal(object.getDouble("precioTotal"));
            ticket.setFecha(LocalDate.parse(object.getString("fecha")));

            // Reconstruimos carrito con los tipos correctos
            ticket.setCarrito(new ArrayList<>());
            JSONArray arrayJson = object.getJSONArray("carrito");
            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject itemJson = arrayJson.getJSONObject(i);
                String tipo = itemJson.getString("tipo"); // leemos el tipo

                if (tipo.equals("Repuesto")) {
                    ticket.getCarrito().add(Repuesto.fromJson(itemJson));
                } else if (tipo.equals("Servicio")) {
                    ticket.getCarrito().add(Servicio.fromJson(itemJson));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    /*
Explicacion
Cada ItemTaller se guarda con su tipo para poder reconstruir correctamente la clase hija.

No necesitamos un atributo tipo en la clase, solo se agrega en el JSON.

Al leer el JSON, usamos ese "tipo" para saber si creamos un Repuesto o un Servicio.

Esto mantiene el carrito heterogéneo y permite deserializarlo sin perder la información del tipo real.

*/

}
