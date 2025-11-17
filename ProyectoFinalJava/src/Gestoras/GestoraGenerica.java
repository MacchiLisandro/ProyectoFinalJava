package Gestoras;

import Interfaces.IJson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;

public class GestoraGenerica<T extends IJson> implements IJson {

    HashSet<T>contenedor=new HashSet<>();


    @Override
    public JSONObject toJson() {

        JSONArray arrayJson=new JSONArray();

        for (T elemento: contenedor){
            arrayJson.put(elemento.toJson());
        }
        
        return arrayJson;

    }
}
