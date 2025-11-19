import Enums.Marca;
import Enums.MetodoDePago;
import Exceptions.DuplicadoException;
import Exceptions.UsuarioYaRegistradoException;
import Gestoras.Taller;
import Models.*;
import org.json.JSONArray;

public class Main {
    public static void main(String[] args) {
        Taller taller = new Taller();
        taller.cargarTodo();
        /*try {
            taller.registrarse("Lisandro","Macchi",1234567,223,"lisandro@gmail.com","lisandro","lisandro");
            taller.registrarse("Mateo","Benegas",1234789,223,"mateo@gmail.com","mateo","mateo");
            taller.guardarTodo();
        } catch (UsuarioYaRegistradoException e) {
            throw new RuntimeException(e);
        } catch (DuplicadoException e) {
            throw new RuntimeException(e);
        }*/
        MenuPrincipal menu = new MenuPrincipal(taller);


        menu.mostrarMenu();
    }
}