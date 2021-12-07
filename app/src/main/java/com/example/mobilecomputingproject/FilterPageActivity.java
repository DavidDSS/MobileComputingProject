package com.example.mobilecomputingproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ArrayList;

public class FilterPageActivity extends AppCompatActivity implements Serializable {

    RelativeLayout pageLayout;
    ArrayList<CheckBox> cuisines;
    NumberPicker dietPicker;
    String[] diets;
    ProgressBar spinner;

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

        spinner= (ProgressBar) findViewById(R.id.loadingSpinner);

        finalStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(view.VISIBLE);
                String filterList="";
                filterList = getFilters();
                System.out.println(filterList);
                spoonDataService.getRecipeByIngredients(ingredientList, filterList, new SpoonDataService.recipeByIngredientsResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(FilterPageActivity.this, "Failed: "+message, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(ArrayList<RecipeModel> recipeList) {
                        spinner.setVisibility(View.GONE);
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
        diets = new String[]{
                "None", "Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian",
                "Ovo-Vegetarian", "Vegan", "Pescetarian", "Paleo",
                "Primal", "Low FODMAP", "Whole30"
        };
        createDietsRadioButtons(diets, "DietRadioButton", R.id.dietText, 56000);
    }


    public void setupCusisines(){
        String[] cuisines = {
                "African", "American", "British", "Cajun", "Caribbean",
                "Chinese", "Eastern\nEuropean", "European", "French", "German", "Greek",
                "Indian", "Irish", "Italian", "Japanese", "Jewish", "Korean",
                "Latin\nAmerican", "Mediterranean", "Mexican", "Middle\nEastern",
                "Nordic", "Southern", "Spanish", "Thai", "Vietnamese"
        };
        this.cuisines = new ArrayList<>();
        createCheckboxTable(cuisines, "CuisineCheckbox", R.id.cuisineText, 57000);
    }

    public void createCheckboxTable(String[] array, String tagName, int placeBelowID, int idStart){
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int rotation = window.getDefaultDisplay().getRotation();
        int numCol = 0;
        if ((rotation==Surface.ROTATION_0)||(rotation==Surface.ROTATION_180)) numCol=3;
        else if ((rotation==Surface.ROTATION_90)||(rotation==Surface.ROTATION_270)) numCol=2;

        TableLayout table = (TableLayout) findViewById(R.id.cuisineTable);
        int remainder = array.length%numCol;
        for (int i=0; i<array.length/numCol+1; i++) {
            if (i<array.length/numCol || (i==array.length/numCol && remainder>=1)){
                TableRow row = new TableRow(this);
                for (int j=0; j<=numCol-1; j++){
                    if (j==numCol-2 && remainder==numCol-2 && i==array.length/numCol) break;
                    if (j==numCol-1 && remainder==numCol-1 && i==array.length/numCol) break;
                    CheckBox temp =  new CheckBox(this);
                    temp.setTag(array[(i*numCol)+j]+tagName);
                    temp.setId(idStart+(i*numCol)+j);
                    temp.setText(array[(i*numCol)+j]);
                    temp.setHeight(125);
                    row.addView(temp);
                    cuisines.add(temp);

                }
                table.addView(row);

            }
        }
    }

    public void createDietsRadioButtons(String[] array, String tagName, int placeBelowID, int idStart){
        dietPicker = (NumberPicker) findViewById(R.id.dietPicker);
        dietPicker.setWrapSelectorWheel(false);
        dietPicker.setMinValue(0);
        dietPicker.setMaxValue(array.length-1);
        dietPicker.setDisplayedValues(array);

    }

    public String getFilters(){
        String dietFilter = diets[dietPicker.getValue()];
        String cuisineFilter = "";
        for (CheckBox cuisine : cuisines) {
            if (cuisine.isChecked()) {
                if (cuisineFilter!="") cuisineFilter+=",";
                cuisineFilter += cuisine.getText();
            }

        }

        // "None" is necessary so we don't get a 0 in the display
        if (dietFilter!="None" && cuisineFilter!=""){
            return "&diet="+dietFilter+"&"+"cuisine="+cuisineFilter;
        } else if (dietFilter!="None"){
            return "&diet="+dietFilter;
        } else if (cuisineFilter!=""){
            return "&cuisine="+cuisineFilter;
        } else {
            return "";
        }
    }

}