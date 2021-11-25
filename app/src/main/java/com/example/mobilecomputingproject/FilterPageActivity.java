package com.example.mobilecomputingproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
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
        List<RecipeModel> recipeList = (List<RecipeModel>) intent.getSerializableExtra("recipeList");
        Toast.makeText(FilterPageActivity.this, recipeList.toString(), Toast.LENGTH_SHORT).show();
    }
}