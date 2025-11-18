package Gestoras;

import Models.Cliente;
import Models.ItemTaller;
import Models.Mecanico;
import Models.Ticket;

public class Taller {
    private GestoraGenerica<Cliente> gestorClientes;
    private GestoraGenerica<Mecanico> gestorMecanicos;
    private GestoraGenerica<Ticket> gestorTickets;
    private GestoraGenerica<ItemTaller> gestorItemTaller;

    public Taller() {
        gestorClientes = new GestoraGenerica<>();
        gestorMecanicos = new GestoraGenerica<>();
        gestorTickets = new GestoraGenerica<>();
        gestorItemTaller = new GestoraGenerica<>();
    }


}
