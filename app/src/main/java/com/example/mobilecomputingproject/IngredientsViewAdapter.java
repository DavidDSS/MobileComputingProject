package com.example.mobilecomputingproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IngredientsViewAdapter extends ArrayAdapter {

    public IngredientsViewAdapter(Context context, ArrayList<IngredientsView> arrayList) {
        super(context, 0, arrayList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View ingredientView = view;

        if (ingredientView == null) ingredientView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_custom_list, parent, false);

        ImageView ingredientImage = ingredientView.findViewById(R.id.ingredientImageView);
        TextView ingredientName = ingredientView.findViewById(R.id.ingredientName);
        IngredientsView ingredientViewPos = (IngredientsView) getItem(position);

        ingredientName.setText(ingredientViewPos.getIngredientName());
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+ingredientViewPos.getIngredientName()+".jpg").into(ingredientImage);
        return ingredientView;
    }
}
