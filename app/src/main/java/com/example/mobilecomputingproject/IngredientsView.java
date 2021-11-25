package com.example.mobilecomputingproject;

import java.io.Serializable;

public class IngredientsView implements Serializable {
        private String ingredientName;

        public IngredientsView(String ingredientName) {
            this.ingredientName = ingredientName;
        }

        public String getIngredientName() {
            return ingredientName;
        }
}
