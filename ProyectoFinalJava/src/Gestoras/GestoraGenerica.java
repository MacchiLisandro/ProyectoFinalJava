package Gestoras;

import Exceptions.DuplicadoException;
import Exceptions.NoSeEncuentraEnRegistroException;
import Interfaces.IJson;
import Models.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

public class GestoraGenerica<T extends IJson>{

    public HashSet<T>contenedor=new HashSet<>();


    public JSONArray toJsonArray () throws JSONException {
        JSONArray jsonArray = new JSONArray();
        try{
            for (T t: contenedor){
                jsonArray.put(t.toJson());
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonArray;
    }


    public void agregar(T t)throws DuplicadoException{
        if (!contenedor.add(t)){
            throw new DuplicadoException("El elemento se encuentra en la coleccion");
        }
    }

    public void eliminar(T t)throws NoSeEncuentraEnRegistroException {
        if(!contenedor.remove(t)){
            throw new NoSeEncuentraEnRegistroException("");
        }
    }

    public String listar (){
        StringBuilder sb = new StringBuilder();
        for (T t: contenedor){
            sb.append(t.toString());
        }
        return sb.toString();
    }

}

