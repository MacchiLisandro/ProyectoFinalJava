import Enums.Marca;
import Enums.MetodoDePago;
import Exceptions.DuplicadoException;
import Exceptions.UsuarioYaRegistradoException;
import Gestoras.Taller;
import Models.*;
import org.json.JSONArray;

public class Main {
    public static void main(String[] args) {

    /*JSONArray array=new JSONArray();

    Taller taller = new Taller();

    try {
        taller.agregarCliente("Gasto", "Fasito", 43520235, 223697807, "mbenegas2001@gmail.com");
        taller.agregarCliente("Ramiro", "Lobos", 43521235, 223697807, "mbenegas2001@gmail.com");
    } catch (DuplicadoException e){
        System.out.println(e.getMessage());
    }
        taller.llamarMetodos();
*//*
        Taller taller = new Taller();

        try {
            taller.agregarCliente("Gasto", "Fasito", 43520235, 223697807, "mbenegas2001@gmail.com");
            taller.agregarCliente("Ramiro", "Lobos", 43521235, 223697807, "mbenegas2001@gmail.com");
        } catch (DuplicadoException e){
            System.out.println(e.getMessage());
        }


*/
      /*  /// prueba ticket
        Cliente c = new Cliente("lisandro", "macchi", 123456, 2231111, "lisandro@gmail.com");
        Mecanico m = new Mecanico("mateo","benegas",654321,2235555,"mateo@gmail.com","admin", "1234");
        Ticket t = new Ticket(c,m,MetodoDePago.EFECTIVO);


        Servicio s = new Servicio("lavado de auto", 500,1,"lavado");
        Repuesto r = new Repuesto("rueda", 1000,1,6, Enums.Marca.BMW,900);

        System.out.println(t.agregarCarrito(s));
        System.out.println(t.agregarCarrito(r));

        t.calculaPrecio();

        Impresora.imprimirTicket(t);
*/


// Pasamos el taller creado al menú




        Taller taller = new Taller();
        try {
            taller.registrarse("aaa","bbb",123,223,"asd","aaa","123");
        } catch (UsuarioYaRegistradoException e) {
            throw new RuntimeException(e);
        } catch (DuplicadoException e) {
            throw new RuntimeException(e);
        }
        MenuPrincipal menu = new MenuPrincipal(taller);


        menu.mostrarMenu();


        try {
            taller.agregarCliente("Pepe","Sand", 48999,223505,"pepesand@hotmail" );

        } catch (DuplicadoException e){
            System.out.println(e.getMessage());
        }



    }
}