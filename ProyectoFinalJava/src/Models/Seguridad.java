package Models;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Seguridad {
    /***
     * este metodo usa el algoritmo SHA-256 para codificar un string:
     * -hace un pasaje de decimal a binario y lo guarda en un arreglo
     * -recorre el arreglo y pasa cada byte a hexadecimal de dos digitos ("%02x")
     * -usa MessageDigest, clase de java que sirve para trabajar con hash
     * @param contraseniaString - clave ingresada por el usuario que se hashea en el metodo
     * @return clave hasheada / codificada
     */
    public static String hashearContrasenia(String contraseniaString){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(contraseniaString.getBytes());
            StringBuilder contraseniaHash = new StringBuilder();
            for( byte b : hash){
                contraseniaHash.append(String.format("%02x", b));
            }
            return contraseniaHash.toString();
        }
        catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
}