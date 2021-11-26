package com.example.mobilecomputingproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeViewAdapter  extends ArrayAdapter {

    public RecipeViewAdapter(Context context, ArrayList<RecipeModel> arrayList) {
        super(context, 0, arrayList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View recipeView = view;

        if (recipeView == null)
            recipeView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_custom_list, parent, false);

        ImageView recipeImage = recipeView.findViewById(R.id.recipeImageView);
        TextView recipeName = recipeView.findViewById(R.id.recipeName);
        RecipeModel recipeViewPos = (RecipeModel) getItem(position);

        recipeName.setText(recipeViewPos.getTitle());
        //Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + recipeViewPos.getIngredientName() + ".jpg").into(ingredientImage);
        return recipeView;
    }
}
