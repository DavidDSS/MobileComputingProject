package com.example.mobilecomputingproject;

import org.json.JSONObject;

import java.io.Serializable;

public class RecipeModel implements Serializable {
    private int recipeId;
    private String title;
    private String image;
    private JSONObject recipeInfo;

    public RecipeModel(int recipeId, String title, String image, JSONObject recipeInfo) {
        this.recipeId = recipeId;
        this.title = title;
        this.image = image;
        this.recipeInfo= recipeInfo;
    }

    @Override
    public String toString() {
        return "Recipe: " +
                "recipeId=" + recipeId +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getStringRecipeId() {
        return Integer.toString(recipeId);
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public JSONObject getRecipeInfo() {
        return recipeInfo;
    }

    public void setRecipeInfo(JSONObject recipeInfo) {
        this.recipeInfo = recipeInfo;
    }
}
