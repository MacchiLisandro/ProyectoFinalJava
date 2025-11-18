package Gestoras;

import Interfaces.IJson;
import Models.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONException;
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

    public void toJsonArray () throws JSONException {
        JSONArray jsonArray = new JSONArray();
        try{
            for (T t: ejercito){
                jsonArray.put(t.toJson());
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        JsonUtiles.grabarUnJson(jsonArray, "ejercito.json");
    }

}
