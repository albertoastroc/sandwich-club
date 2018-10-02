package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        String mainName = "";
        List<String> alsoKnownAs = null;
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        List<String> ingredients = null;

        try {
            JSONObject sandwichObject = new JSONObject(json);
            JSONObject nameObject = sandwichObject.getJSONObject("name");
            JSONArray alsoKnownAsArray = sandwichObject.getJSONArray("alsoKnownAs");
            JSONArray ingredientsArray = sandwichObject.getJSONArray("ingredients");

            mainName = nameObject.getString("mainName");
            placeOfOrigin = sandwichObject.getString("placeOfOrigin");
            image = sandwichObject.getString("image");

            for (int i = 0; 0 < alsoKnownAsArray.length(); i++){
                String string = alsoKnownAsArray.getString(i);
                alsoKnownAs.add(string);
            }

            for (int i = 0; 0 < ingredientsArray.length(); i++){
                String string = ingredientsArray.getString(i);
                ingredients.add(string);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        return sandwich;
    }



}
