package Gestoras;

import Enums.MetodoDePago;
import Exceptions.DuplicadoException;
import Exceptions.NoSeEncuentraEnRegistroException;
import Models.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class Taller {
    private GestoraGenerica<Cliente> gestorClientes;
    private GestoraGenerica<Mecanico> gestorMecanicos;
    private GestoraGenerica<ItemTaller> gestorItemTaller;
    /// Utilizamos un LinkedHashSet porque los tickets son unicos y requieren de un orden de insercion para
    /// leerlos y guardar el contador de id.
    private LinkedHashSet<Ticket> gestorTickets;

    public Taller() {
        gestorClientes = new GestoraGenerica<>();
        gestorMecanicos = new GestoraGenerica<>();
        gestorTickets = new LinkedHashSet();
        gestorItemTaller = new GestoraGenerica<>();
    }
    /// Metodo para actualizar el contador. pendiente---
    /*public void actualizarContadorId() {
        int max = 0;

        for (Ticket t : gestorTickets) {
            if (t.getId() > max) {
                max = t.getId();
            }
        }

        Ticket.setContadorIds(max + 1);
    }
*/
    /// Metodos de funcionamiento-----------------------------------------------------------------------------------------

    /// Metodo para ingresar un cliente
    public void agregarCliente(String nombre, String apellido, int dni, int telefono, String email)throws DuplicadoException{
        gestorClientes.agregar(new Cliente(nombre, apellido, dni, telefono, email));
    }

    public void eliminarCliente(int dni)throws NoSeEncuentraEnRegistroException{
        Cliente cliente = buscarCliente(dni);
        gestorClientes.eliminar(cliente);
    }

    public Cliente buscarCliente (int dni)throws NoSeEncuentraEnRegistroException{
        for (Cliente c: gestorClientes.contenedor){
            if(c.getDni()==dni){
                return c;
            }
        } throw new NoSeEncuentraEnRegistroException("El cliente no se encuentra en la lista");
    }

    /// Metodo para crear un ticket
    /*
    public Ticket crearTicket(Cliente cliente, MetodoDePago metodo) {
        if (mecanicoLogueado == null) {
            throw new RuntimeException("No hay mecánico logueado.");
        }

        /// aca se asigna el mecanico logueado
        Ticket ticket = new Ticket(cliente, mecanicoLogueado, metodo);

        /// se agrega al registro de ticket
        gestorTickets.add(ticket);

        return ticket;
    }
*/
    /// Metodo que calcula ganancias mensuales
    public double calcularGananciaMensual(int mes, int anio) {
        double total = 0;

        for (Ticket t : gestorTickets) {
            if (t.getFecha().getMonthValue() == mes &&
                    t.getFecha().getYear() == anio) {

                total += t.getPrecioTotal();
            }
        }

        return total;
    }

    /// Metodo para guardar todas las collection en JSon
    public void guardarTodo(){
        try{
            JSONArray arrayClientes = gestorClientes.toJsonArray();
            JsonUtiles.grabarUnJson(arrayClientes,"clientes.json");
            JSONArray arrayMecanicos = gestorMecanicos.toJsonArray();
            JsonUtiles.grabarUnJson(arrayMecanicos,"mecanicos.json");
            JSONArray arrayTicket = ticketToJsonArray();
            JsonUtiles.grabarUnJson(arrayTicket,"tickets.json");
            JSONArray arrayItemTaller = gestorItemTaller.toJsonArray();
            JsonUtiles.grabarUnJson(arrayItemTaller, "itemTaller.json");
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void cargarTodo(){
        try{
            cargarClientes();
            cargarMecanicos();
            cargarItemTaller();
            cargarTickets();
        } catch (JSONException e){
            e.printStackTrace();
        } catch (DuplicadoException e){
            System.out.println(e.getMessage());
        }
    }

    private void cargarClientes()throws JSONException, DuplicadoException{
        JSONTokener tokener = JsonUtiles.leerUnJson("clientes.json");
        JSONArray arrayClientes = new JSONArray(tokener);
        gestorClientes.contenedor.clear();
        for (int i = 0; i<arrayClientes.length(); i++){
            this.gestorClientes.agregar(Cliente.fromJson(arrayClientes.getJSONObject(i)));

        }
    }

    private void cargarMecanicos()throws JSONException, DuplicadoException{
        JSONTokener tokener = JsonUtiles.leerUnJson("mecanicos.json");
        JSONArray arrayMecanicos = new JSONArray(tokener);
        gestorMecanicos.contenedor.clear();
        for(int i = 0; i<arrayMecanicos.length(); i++){
            this.gestorMecanicos.agregar(Mecanico.fromJson(arrayMecanicos.getJSONObject(i)));
        }
    }

    private void cargarItemTaller()throws JSONException, DuplicadoException{
        JSONTokener tokener = JsonUtiles.leerUnJson("itemTaller.json");
        JSONArray arrayItemTaller = new JSONArray(tokener);
        gestorItemTaller.contenedor.clear();
        for(int i = 0; i<arrayItemTaller.length(); i++) {
            JSONObject itemJson = arrayItemTaller.getJSONObject(i);
            String tipo = itemJson.getString("tipo");

            if (tipo.equals("Repuesto")) {
                this.gestorItemTaller.agregar(Repuesto.fromJson(arrayItemTaller.getJSONObject(i)));
            }
            if (tipo.equals("Servicio")) {
                this.gestorItemTaller.agregar(Servicio.fromJson(arrayItemTaller.getJSONObject(i)));
            }
        }}

    private void cargarTickets() throws JSONException {
        JSONTokener tokener = JsonUtiles.leerUnJson("tickets.json");
        JSONArray  arrayTickets = new JSONArray(tokener);
        gestorTickets.clear();
        for(int i = 0; i<arrayTickets.length(); i++){
            this.gestorTickets.add(Ticket.fromJson(arrayTickets.getJSONObject(i)));
        }
        Ticket.setContadorIds(gestorTickets.getLast().getId()+1);  ///Si se rompe cambiar a la linea de abajo


        ///actualizarContadorId();  Por si se rompe.
    }

    private JSONArray ticketToJsonArray () throws JSONException {
        JSONArray jsonArray = new JSONArray();
        try{
            for (Ticket t: gestorTickets){
                jsonArray.put(t.toJson());
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void llamarMetodos(){
        Impresora.crearCarpeta();
        Impresora.imprimirListadoClientes(gestorClientes.contenedor);
    }
}
