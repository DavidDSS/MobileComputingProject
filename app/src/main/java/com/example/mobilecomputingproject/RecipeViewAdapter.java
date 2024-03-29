package com.example.mobilecomputingproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView recipeId = recipeView.findViewById(R.id.recipeId);
        RecipeModel recipeViewPos = (RecipeModel) getItem(position);

        recipeName.setText(recipeViewPos.getTitle());
        recipeId.setText(Integer.toString(recipeViewPos.getRecipeId()));
        Picasso.get().load(recipeViewPos.getImage()).into(recipeImage);
        return recipeView;
    }
}
