package com.example.mobilecomputingproject;

public class RecipeModel {
    private int recipeId;
    private String title;
    private String image;

    public RecipeModel(int recipeId, String title, String image) {
        this.recipeId = recipeId;
        this.title = title;
        this.image = image;
    }

    public RecipeModel(){
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
}
