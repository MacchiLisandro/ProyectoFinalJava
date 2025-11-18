package Gestoras;

import Exceptions.DuplicadoException;
import Exceptions.NoSeEncuentraEnRegistroException;
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

    public void agregarClientes (Cliente cliente){
        try{
            gestorClientes.agregar(cliente);
        } catch (DuplicadoException e){
            System.out.println(e.getMessage());
        }
    }

    public void eliminarClientes (Cliente cliente)throws NoSeEncuentraEnRegistroException{
        try{
            gestorClientes.eliminar(cliente);
        } catch (NoSeEncuentraEnRegistroException e) {
            throw new NoSeEncuentraEnRegistroException("El C");
        }
    }
}
