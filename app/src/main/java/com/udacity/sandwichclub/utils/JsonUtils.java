package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //create a json object
        JSONObject jsonObject = new JSONObject(json);

        //get origin
        String origin = jsonObject.getString("placeOfOrigin");
        if (origin.length() < 1) {
            origin = "Unknown";
        }

        //get name part of json (contains given name and aka-names
        JSONObject sandwichName = jsonObject.getJSONObject("name");

        //get common name
        String mainName = sandwichName.getString("mainName");

        //get names sandwich is also known by
        List<String> alsoKnownAs = new ArrayList<String>();
        //get JSONArray
        JSONArray akaArray = sandwichName.getJSONArray("alsoKnownAs");
        //if there is an AKA name (if not use "sandwich only known by...")
        if (akaArray.length() > 0) {
            //iterate though the array
            for (int i = 0; i < akaArray.length(); i++) {
                String text = akaArray.getString(i);
                alsoKnownAs.add(text);
            }
        } else {
            alsoKnownAs.add("Sandwich only known by common name.");
        }


        //description
        String description = "";
        try {
            description = jsonObject.getString("description");
        } catch (Exception ee) {
            description = "Description is unavailable";
        }

        //image
        String image = jsonObject.getString("image");

        //ingredients
        List<String> ingredients = new ArrayList<String>();
        JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
        for (int i=0; i<ingredientsArray.length(); i++){
            String text = ingredientsArray.getString(i);
            ingredients.add(Integer.toString(i+1)+". "+text);
        }

        //return a new sandwich object
        return new Sandwich(mainName, alsoKnownAs, origin, description, image, ingredients);
    }
}
