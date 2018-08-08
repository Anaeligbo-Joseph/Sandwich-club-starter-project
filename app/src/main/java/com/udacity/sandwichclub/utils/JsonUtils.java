package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    //understand the JSONData to use as prior defined in the class Sandwich
    //JSON- has JSONObjects, JSON String and JSONArray here.
    //Java object- the base and name. base here is the top most in hierarchy.
    //Name houses string- mainName and array- alsoKnownAs.
    //base houses JSONObject name and  string- placeOfOrigin, string- description, string- image, array- ingredients
   /* {
        "name":{
                "mainName":"Shawarma",
                "alsoKnownAs":[]
             },
         "placeOfOrigin":"Middle East, Levant",
         "description":"Shawarma also spelled shawurma or shawerma, is a Levantine meat preparation, where lamb, chicken, turkey, beef, veal, or mixed meats are placed on a spit (commonly a vertical spit in restaurants), and may be grilled for as long as a day. Shavings are cut off the block of meat for serving, and the remainder of the block of meat is kept heated on the rotating spit. Shawarma can be served on a plate (generally with accompaniments), or as a sandwich or wrap. Shawarma is usually eaten with tabbouleh, fattoush, taboon bread, tomato, and cucumber. Toppings include tahini, hummus, pickled turnips, and amba.",
         "image":"https://upload.wikimedia.org/wikipedia/commons/thumb/1/16/Shawarmafood.jpg/800px-Shawarmafood.jpg\"," +
         "ingredients":["Shawarma meat (lamb, chicken, turkey, beef) or shawarma falafel","Pita or wrap bread","Chopped or shredded vegetables","Pickles","Assorted condiments"]
    }
*/
    public static Sandwich parseSandwichJson(String json) {
        try {
            //Initialize JSONObject from JSON string
            JSONObject base=new JSONObject(json);

            //JSONObject name  as next in hierarchy from the base
            JSONObject name=base.getJSONObject("name");
            //mainName string
            String mainName=name.getString("mainName");

            //alsoKnownAs array
            JSONArray alsoKnownAs=name.getJSONArray("alsoKnownAs");
            //building the list(JSONArray)
            ArrayList<String> alsoKnownAsList= new ArrayList<String>();
            for (int i=0;i<alsoKnownAs.length();i++){
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }

            //placeOfOrigin string
            String placeOfOrigin=base.getString("placeOfOrigin");
            //description string
            String description=base.getString("description");
            //image string
            String image=base.getString("image");

            //ingredients array
            JSONArray ingredients=base.getJSONArray("ingredients");
            //building the list (JSONArray)
            ArrayList<String> ingredientsList=new ArrayList<String>();
            for (int i=0; i<ingredients.length();i++){
                ingredientsList.add(ingredients.getString(i));
            }

            return new Sandwich(mainName,alsoKnownAsList,placeOfOrigin,description,image,ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}