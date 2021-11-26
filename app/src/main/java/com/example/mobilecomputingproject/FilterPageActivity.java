package com.example.mobilecomputingproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilterPageActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_page);

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
        Button finalStepBtn= findViewById(R.id.finalStepBtn);

        finalStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRecipes(view, recipeList);
            }
        });
    }

    //Go to Recipes Page
    public void goToRecipes(View view, ArrayList<RecipeModel> recipeList) {
        Intent intent = new Intent(FilterPageActivity.this, RecipePageActivity.class);
        intent.putExtra("recipeList", recipeList);
        startActivity(intent);
    }
}