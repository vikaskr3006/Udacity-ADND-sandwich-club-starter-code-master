package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String NAME_OBJECT = "name";
    private static final String MAIN_NAME_STRING = "mainName";
    private static final String ALSO_KNOWN_AS_STRING = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_STRING = "placeOfOrigin";
    private static final String DESCRIPTION_STRING = "description";
    private static final String IMAGE_STRING = "image";
    private static final String INGREDIENTS_STRING = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichDetails = new JSONObject(json);
            JSONObject name = sandwichDetails.getJSONObject(NAME_OBJECT);
            String mainName = name.getString(MAIN_NAME_STRING);
            List<String> alsoKnownAs = convertJsonArrayToList(name.getJSONArray(ALSO_KNOWN_AS_STRING));
            String placeOfOrigin = sandwichDetails.getString(PLACE_OF_ORIGIN_STRING);
            String description = sandwichDetails.getString(DESCRIPTION_STRING);
            String image = sandwichDetails.getString(IMAGE_STRING);
            List<String> ingredients = convertJsonArrayToList(sandwichDetails.getJSONArray(INGREDIENTS_STRING));

            Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

            return sandwich;

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    private static List<String> convertJsonArrayToList(JSONArray jsonArray) {
        List<String> stringList = new ArrayList<>(jsonArray.length());
        for(int i=0; i<jsonArray.length(); i++){
            try {
                stringList.add(jsonArray.get(i).toString());

            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        return stringList;
    }
}
