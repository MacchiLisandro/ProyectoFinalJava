package Gestoras;

import Exceptions.DuplicadoException;
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
    public void actualizarContadorId (){

    }
    /// Metodos de funcionamiento-----------------------------------------------------------------------------------------

    /// Metodo para ingresar un cliente
    public void agregarCliente(String nombre, String apellido, int dni, int telefono, String email)throws DuplicadoException{
        Cliente clientito = new Cliente(nombre, apellido, dni, telefono, email);
        gestorClientes.agregar(clientito);
    }

    /// Metodo para crear un ticket
    public void crearTicket (){

    }

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
        int nticket = 0;

        try{
            JSONTokener tokener = JsonUtiles.leerUnJson("clientes.json");
            JSONArray arrayClientes = new JSONArray(tokener);
            gestorClientes.contenedor.clear();
            for (int i = 0; i<arrayClientes.length(); i++){
                this.gestorClientes.agregar(Cliente.fromJson(arrayClientes.getJSONObject(i)));

            }

            tokener = JsonUtiles.leerUnJson("mecanicos.json");
            JSONArray arrayMecanicos = new JSONArray();
            gestorMecanicos.contenedor.clear();
            for(int i = 0; i<arrayMecanicos.length(); i++){
                this.gestorMecanicos.agregar(Mecanico.fromJson(arrayMecanicos.getJSONObject(i)));
            }

            tokener = JsonUtiles.leerUnJson("itemTaller.json");
            JSONArray arrayItemTaller = new JSONArray();
            gestorItemTaller.contenedor.clear();
            for(int i = 0; i<arrayItemTaller.length(); i++){
                JSONObject itemJson = arrayItemTaller.getJSONObject(i);
                String tipo = itemJson.getString("tipo");

                if (tipo.equals("Repuesto")){
                    this.gestorItemTaller.agregar(Repuesto.fromJson(arrayItemTaller.getJSONObject(i)));
                }
                if (tipo.equals("Servicio")){
                    this.gestorItemTaller.agregar(Servicio.fromJson(arrayItemTaller.getJSONObject(i)));
                }
            }

            tokener = JsonUtiles.leerUnJson("tickets.json");
            JSONArray  arrayTickets = new JSONArray();
            gestorTickets.clear();
            for(int i = 0; i<arrayTickets.length(); i++){
                this.gestorTickets.add(Ticket.fromJson(arrayTickets.getJSONObject(i)));
            }

        } catch (JSONException e){
            e.printStackTrace();
        } catch (DuplicadoException e){
            System.out.println(e.getMessage());
        }
        Ticket.setContadorIds(nticket);
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
