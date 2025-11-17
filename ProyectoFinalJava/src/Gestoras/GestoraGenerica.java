package Gestoras;

import Interfaces.IJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;

public class GestoraGenerica<T extends IJson> implements IJson {

    HashSet<T>contenedor=new HashSet<>();


    @Override
    public JSONObject toJson() throws JSONException {}

}
