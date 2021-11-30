package com.example.mobilecomputingproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class FilterPageActivity extends AppCompatActivity implements Serializable {

    RelativeLayout pageLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_page);

        pageLayout = (RelativeLayout) findViewById(R.id.filterRelativeLayout);

        setupCusisines();
        setupDiets();

        Toolbar appToolbar = findViewById(R.id.nutri_toolbar);
        setSupportActionBar(appToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Set Up Navigation
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Instantiate spoonDataService for API use
        final SpoonDataService spoonDataService= new SpoonDataService(FilterPageActivity.this);

        //Access Intent and Data
        Intent intent= getIntent();
        String ingredientList = (String) intent.getSerializableExtra("ingredientList");

        //Initialize Elements
        Button finalStepBtn= findViewById(R.id.finalStepBtn);

        finalStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filterList="";
                spoonDataService.getRecipeByIngredients(ingredientList, filterList, new SpoonDataService.recipeByIngredientsResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(FilterPageActivity.this, "Failed: "+message, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(ArrayList<RecipeModel> recipeList) {
                        //Got to Filters Page
                        goToRecipes(view, recipeList);
                    }
                });
            }
        });

    }

    //Go to Recipes Page
    public void goToRecipes(View view, ArrayList<RecipeModel> recipeList) {
        Intent intent = new Intent(FilterPageActivity.this, RecipeListPageActivity.class);
        intent.putExtra("recipeList", recipeList);
        startActivity(intent);
    }

    public void setupDiets(){
        String[] diets = {
                "Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian",
                "Ovo-Vegetarian", "Vegan", "Pescetarian", "Paleo",
                "Primal", "Low FODMAP", "Whole30"
        };
        createCheckboxArray(diets, "DietCheckbox", R.id.dietText, 56000);
    }


    public void setupCusisines(){
        String[] cuisines = {
                "African", "American", "British", "Cajun", "Caribbean",
                "Chinese", "Eastern European", "European", "French", "German", "Greek",
                "Indian", "Irish", "Italian", "Japanese", "Jewish", "Korean",
                "Latin American", "Mediterranean", "Mexican", "Middle Eastern",
                "Nordic", "Southern", "Spanish", "Thai", "Vietnamese"
        };
        createCheckboxArray(cuisines, "CuisineCheckbox", R.id.cuisineText, 57000);
    }

    public void createCheckboxArray(String[] array, String tagName, int placeBelowID, int idStart){
        for (int i=0; i<array.length; i++) {
            CheckBox temp =  new CheckBox(this);
            temp.setTag(array[i]+tagName);
            temp.setId(idStart+i);
            temp.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            if (i>0) {
                if((i%3)>0) params.addRule(RelativeLayout.RIGHT_OF, idStart+i-1);
                if ((i/3)!=0) params.addRule(RelativeLayout.BELOW, idStart+i-3);
                else params.addRule(RelativeLayout.BELOW, placeBelowID);
                System.out.println(findViewById(idStart+i-1).toString());
            }
            else params.addRule(RelativeLayout.BELOW, placeBelowID);

            temp.setText(array[i]);
            temp.setLayoutParams(params);
            pageLayout.addView(temp, params);
        }
    }
}