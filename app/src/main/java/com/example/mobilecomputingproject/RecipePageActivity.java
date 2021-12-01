package com.example.mobilecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_page);

        //Initialize App Toolbar
        Toolbar appToolbar = (Toolbar) findViewById(R.id.nutri_toolbar);

        //Set app toolbar and remove default title
        setSupportActionBar(appToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Set Up Navigation
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Access Intent and Data
        Intent intent= getIntent();
        ArrayList<RecipeModel> recipeInfo = (ArrayList<RecipeModel>) intent.getSerializableExtra("recipeInfo");
        RecipeModel recipe= recipeInfo.get(0);

        //Display Recipe Name
        TextView recipeName= findViewById(R.id.recipeName);
        recipeName.setText(recipe.getTitle());

        //Display Image
        ImageView recipePic= findViewById(R.id.recipeImage);
        Picasso.get().load(recipe.getImage()).into(recipePic);

        //Display Ingredients
        ListView ingredientListView= findViewById(R.id.ingredientListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, recipe.getIngredients());
        ingredientListView.setAdapter(adapter);


    }

}
