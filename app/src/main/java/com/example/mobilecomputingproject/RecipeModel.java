package com.example.mobilecomputingproject;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeModel implements Serializable {
    private int recipeId;
    private String title;
    private String image;
    private Boolean isVegetarian;
    private Boolean isVegan;
    private Boolean isDairyFree;
    private Boolean isHealthy;
    private Float pricePerServing;
    private Float readyInMinutes;
    private int servings;
    private ArrayList<String> steps;
    private ArrayList<String> ingredients;

    public RecipeModel(int recipeId, String title, String image) {
        this.recipeId = recipeId;
        this.title = title;
        this.image = image;
    }

    public RecipeModel(int recipeId, String title, String image, Boolean isVegetarian, Boolean isVegan, Boolean isDairyFree, Boolean isHealthy, Float pricePerServing, Float readyInMinutes, int servings, ArrayList<String> steps, ArrayList<String> ingredients) {
        this.recipeId = recipeId;
        this.title = title;
        this.image = image;
        this.isVegetarian = isVegetarian;
        this.isVegan = isVegan;
        this.isDairyFree = isDairyFree;
        this.isHealthy = isHealthy;
        this.pricePerServing = pricePerServing;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    public int getRecipeId() {
        return recipeId;
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

    public Boolean getVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public Boolean getVegan() {
        return isVegan;
    }

    public void setVegan(Boolean vegan) {
        isVegan = vegan;
    }

    public Boolean getDairyFree() {
        return isDairyFree;
    }

    public void setDairyFree(Boolean dairyFree) {
        isDairyFree = dairyFree;
    }

    public Boolean getHealthy() {
        return isHealthy;
    }

    public void setHealthy(Boolean healthy) {
        isHealthy = healthy;
    }

    public Float getPricePerServing() {
        return pricePerServing;
    }

    public void setPricePerServing(Float pricePerServing) {
        this.pricePerServing = pricePerServing;
    }

    public Float getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(Float readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "RecipeModel{" +
                "recipeId=" + recipeId +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
