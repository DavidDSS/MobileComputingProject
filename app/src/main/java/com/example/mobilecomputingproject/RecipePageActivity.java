package com.example.mobilecomputingproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecipePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list_page);

        //Initialize App Toolbar
        Toolbar appToolbar = (Toolbar) findViewById(R.id.nutri_toolbar);

        //Set app toolbar and remove default title
        setSupportActionBar(appToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}
