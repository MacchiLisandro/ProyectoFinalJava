package Gestoras;

import Models.*;
import org.json.JSONArray;
import org.json.JSONException;

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
            JSONArray arrayMecanicos = gestorMecanicos.toJsonArray();
            JSONArray arrayTicket = ticketToJsonArray();
            JSONArray arrayItemTaller = gestorItemTaller.toJsonArray();
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void cargarTodo(){
        int nticket = 0;
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
}
