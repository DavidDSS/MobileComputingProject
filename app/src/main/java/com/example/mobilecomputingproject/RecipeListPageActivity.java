package com.example.mobilecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

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
        ArrayList<RecipeModel> recipeList = (ArrayList<RecipeModel>) intent.getSerializableExtra("recipeList");

        //Initialize Elements
        recipeListView= findViewById(R.id.recipeList);

        //Initialize Adapter
        recipeListAdapter = new RecipeViewAdapter(this, recipeList);

        //Set Adapter
        recipeListView.setAdapter(recipeListAdapter);
    }
}
