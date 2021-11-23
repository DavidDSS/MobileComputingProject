package com.example.mobilecomputingproject;

public class IngredientsView{
        private int ingredientImageId;
        private String ingredientName;

        public IngredientsView(int ingredientImageId, String ingredientName) {
            this.ingredientImageId = ingredientImageId;
            this.ingredientName = ingredientName;
        }

        public int getIngredientImageId() {
            return ingredientImageId;
        }

        public String getIngredientName() {
            return ingredientName;
        }
}
