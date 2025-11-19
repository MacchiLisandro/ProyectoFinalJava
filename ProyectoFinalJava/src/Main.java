import Enums.Marca;
import Enums.MetodoDePago;
import Exceptions.DuplicadoException;
import Exceptions.UsuarioYaRegistradoException;
import Gestoras.Taller;
import Models.*;
import org.json.JSONArray;

public class Main {
    public static void main(String[] args) {
        /// BENEGAS
        /// MACCHI
        Taller taller = new Taller();

        taller.cargarTodo();

        MenuPrincipal menu = new MenuPrincipal(taller);

        menu.mostrarMenu();

    }
}