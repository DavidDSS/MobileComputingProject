package com.example.mobilecomputingproject;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpoonDataService implements Serializable {
    public String apiKey= "apiKey=706ba02fd189452e96b3fef538d9d6ff";
    Context context;

    public SpoonDataService(Context context) {
        this.context = context;
    }

    public interface recipeByIngredientsResponseListener {
        void onError(String message);
        void onResponse(ArrayList<RecipeModel> recipeList);
    }

    public interface recipeByIdResponseListener {
        void onError(String message);
        void onResponse(ArrayList<RecipeModel>  recipeInfo);
    }

    public void getRecipeByIngredients(String ingredients, String filters, recipeByIngredientsResponseListener apiResponseListener){
        ArrayList<RecipeModel> listOfRecipes = new ArrayList<>();
        String url ="https://api.spoonacular.com/recipes/complexSearch?query="+ingredients+""+filters+"&"+apiKey;
        JsonObjectRequest spoonRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray recipeList = response.getJSONArray("results");
                    for(int i=0; i<recipeList.length();i++){
                        JSONObject recipeInList=recipeList.getJSONObject(i);
                        RecipeModel recipe = new RecipeModel(recipeInList.getInt("id"),
                                recipeInList.getString("title"),recipeInList.getString("image"));
                        listOfRecipes.add(recipe);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                apiResponseListener.onResponse(listOfRecipes);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiResponseListener.onError("Error!");
            }
        });
        RequestSingleton.getInstance(context).addToRequestQueue(spoonRequest);
    }


    public void getRecipeInfoById(int recipeId, recipeByIdResponseListener apiResponseListener){
        ArrayList<RecipeModel> listOfRecipes = new ArrayList<>();
        String url ="https://api.spoonacular.com/recipes/"+recipeId+"/information?includeNutrition=false&"+apiKey;
        JsonObjectRequest spoonRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int recipeId=response.getInt("id");
                    String title=response.getString("title");
                    String image=response.getString("image");
                    Boolean isVegetarian=response.getBoolean("vegetarian");
                    Boolean isGlutenFree=response.getBoolean("glutenFree");
                    Boolean isVegan=response.getBoolean("vegan");
                    Boolean isDairyFree=response.getBoolean("dairyFree");
                    Boolean isHealthy=response.getBoolean("veryHealthy");
                    Double pricePerServing=response.getDouble("pricePerServing");
                    Double readyInMinutes=response.getDouble("readyInMinutes");
                    int servings=response.getInt("servings");
                    JSONArray analyzedInstructions= response.getJSONArray("analyzedInstructions").getJSONObject(0).getJSONArray("steps");
                    JSONArray extendedIngredients= response.getJSONArray("extendedIngredients");
                    ArrayList<String> steps= new ArrayList<String >();
                    ArrayList<String> ingredientList= new ArrayList<String >();;

                    //Get Steps and Ingredients
                    for(int i=0; i<analyzedInstructions.length();i++) {
                        JSONObject step= analyzedInstructions.getJSONObject(i);
                        steps.add(step.getString("step"));
                    }

                    for(int i=0; i<extendedIngredients.length();i++) {
                        JSONObject ingredient= extendedIngredients.getJSONObject(i);
                        ingredientList.add(ingredient.getString("name"));
                    }

                    RecipeModel recipeInfo = new RecipeModel(recipeId,title,image
                    ,isVegetarian, isGlutenFree, isVegan, isDairyFree, isHealthy,
                            pricePerServing, readyInMinutes, servings, steps, ingredientList);

                    listOfRecipes.add(recipeInfo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                apiResponseListener.onResponse(listOfRecipes);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiResponseListener.onError("Error!");
            }
        });
        RequestSingleton.getInstance(context).addToRequestQueue(spoonRequest);
    }
}
