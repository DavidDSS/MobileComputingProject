package com.example.mobilecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeListPageActivity extends AppCompatActivity implements Serializable {
    //Global variables related to lists and adapters
    ListView recipeListView;
    ArrayList<RecipeModel> recipeList;
    RecipeViewAdapter recipeListAdapter;
    ProgressBar spinner;

    //Instantiate spoonDataService for API use
    final SpoonDataService spoonDataService= new SpoonDataService(RecipeListPageActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list_page);

        Toolbar appToolbar = findViewById(R.id.nutri_toolbar);
        setSupportActionBar(appToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Set Up Navigation
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Access Intent and Data
        Intent intent= getIntent();
        recipeList = (ArrayList<RecipeModel>) intent.getSerializableExtra("recipeList");

        //Initialize Elements
        recipeListView= findViewById(R.id.recipeList);
        spinner= (ProgressBar) findViewById(R.id.loadingSpinner);

        //Initialize Adapter
        recipeListAdapter = new RecipeViewAdapter(this, recipeList);

        //Set Adapter
        recipeListView.setAdapter(recipeListAdapter);

        //Initialize Empty Text and Set it
        TextView noSearchresults= findViewById(R.id.noSearchResults);
        recipeListView.setEmptyView(noSearchresults);

    }

    //Go to Recipe Page
    public void goToRecipe(View view) {
        spinner.setVisibility(view.VISIBLE);
        int position = recipeListView.getPositionForView(view);
        RecipeModel selectedRecipe= recipeList.get(position);

        spoonDataService.getRecipeInfoById(selectedRecipe.getRecipeId(), new SpoonDataService.recipeByIdResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(RecipeListPageActivity.this, "Failed: "+message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(ArrayList<RecipeModel> recipeInfo) {
                //Got to Recipe Page
                Intent intent = new Intent(RecipeListPageActivity.this, RecipePageActivity.class);
                intent.putExtra("recipeInfo", recipeInfo);
                spinner.setVisibility(view.GONE);
                startActivity(intent);
            }
        });

    }
}
