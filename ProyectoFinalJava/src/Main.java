import Exceptions.DuplicadoException;
import Gestoras.Taller;
import org.json.JSONArray;

public class Main {
    public static void main(String[] args) {

    JSONArray array=new JSONArray();

    Taller taller = new Taller();

    try {
        taller.agregarCliente("Gasto", "Fasito", 43520235, 223697807, "mbenegas2001@gmail.com");
        taller.agregarCliente("Ramiro", "Lobos", 43521235, 223697807, "mbenegas2001@gmail.com");
    } catch (DuplicadoException e){
        System.out.println(e.getMessage());
    }
        taller.llamarMetodos();
    }
}