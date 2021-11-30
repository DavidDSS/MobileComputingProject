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
                    RecipeModel recipeInfo = new RecipeModel(response.getInt("id"),
                            response.getString("title"),response.getString("image"));
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
