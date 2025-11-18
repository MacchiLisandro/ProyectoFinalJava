package Gestoras;

import Models.Cliente;
import Models.ItemTaller;
import Models.Mecanico;
import Models.Ticket;

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

    public double calcularGananciaMensual(int mes, int anio) {
        double total = 0;

        for (Ticket t : tickets) {
            if (t.getFecha().getMonthValue() == mes &&
                    t.getFecha().getYear() == anio) {

                total += t.getPrecioTotal();
            }
        }

        return total;
    }
}
