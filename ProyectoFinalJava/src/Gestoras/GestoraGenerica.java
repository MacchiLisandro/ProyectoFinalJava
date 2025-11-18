package Gestoras;

import Interfaces.IJson;
import Models.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;

public class GestoraGenerica<T extends IJson> {

    HashSet<T>contenedor=new HashSet<>();


    public void toJsonArray () throws JSONException {
        JSONArray jsonArray = new JSONArray();
        try{
            for (T t: contenedor){
                jsonArray.put(t.toJson());
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        JsonUtiles.grabarUnJson(jsonArray, "algo.json");
    }

    public void toJsonArray () throws JSONException {
        JSONArray jsonArray = new JSONArray();
        try{
            for (T t: contenedor){
                jsonArray.put(t.toJson());
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        JsonUtiles.grabarUnJson(jsonArray, "algo.json");
    }

}
