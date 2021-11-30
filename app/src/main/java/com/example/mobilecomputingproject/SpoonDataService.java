package com.example.mobilecomputingproject;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpoonDataService {
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
        void onResponse(RecipeModel recipeInfo);
    }

    public void getRecipeByIngredients(String ingredients, recipeByIngredientsResponseListener apiResponseListener){
        ArrayList<RecipeModel> listOfRecipes = new ArrayList<>();
        String url ="https://api.spoonacular.com/recipes/findByIngredients?ingredients="+ingredients+"&"+apiKey;
        JsonArrayRequest spoonRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0; i< response.length(); i++) {
                        JSONObject recipeInfo = response.getJSONObject(i);
                        RecipeModel recipe = new RecipeModel(recipeInfo.getInt("id"),
                                recipeInfo.getString("title"),recipeInfo.getString("image"),null);
                        recipe.setRecipeId(recipeInfo.getInt("id"));
                        recipe.setTitle(recipeInfo.getString("title"));
                        recipe.setImage(recipeInfo.getString("image"));
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

    public void getRecipeInfoById(int recipeId, recipeByIngredientsResponseListener apiResponseListener){
        ArrayList<RecipeModel> listOfRecipes = new ArrayList<>();
        String url ="https://api.spoonacular.com/recipes/findByIngredients?ingredients="+recipeId+"&"+apiKey;
        JsonArrayRequest spoonRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0; i< response.length(); i++) {
                        JSONObject recipeInfo = response.getJSONObject(i);
                        RecipeModel recipe = new RecipeModel(recipeInfo.getInt("id"),
                                recipeInfo.getString("title"),recipeInfo.getString("image"),null);
                        recipe.setRecipeId(recipeInfo.getInt("id"));
                        recipe.setTitle(recipeInfo.getString("title"));
                        recipe.setImage(recipeInfo.getString("image"));
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
}
