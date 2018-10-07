package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json)  {

        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        JSONObject sandwichObject = null;
        Sandwich sandwich = new Sandwich();

        try {
            sandwichObject = new JSONObject(json);

            JSONObject nameObject = sandwichObject.getJSONObject("name");
            String mainName = nameObject.getString("mainName");
            JSONArray alsoKnownAsArray = nameObject.getJSONArray("alsoKnownAs");

            String placeOfOrigin = sandwichObject.getString("placeOfOrigin");
            String description = sandwichObject.getString("description");
            String image = sandwichObject.getString("image");
            JSONArray ingredientsArray = sandwichObject.getJSONArray("ingredients");

            //if array not null add array items to it's List
            if (!(alsoKnownAsArray.isNull(0))){
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    String string = alsoKnownAsArray.optString(i);
                    alsoKnownAs.add(string);
                }
            }

            if (!(ingredientsArray.isNull(0))){
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    String string = ingredientsArray.optString(i);
                    ingredients.add(string);
                }

            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
            } catch (JSONException e) {
                e.printStackTrace();
        } return sandwich;


    }


}
