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
                "Chinese", "Eastern European", "European", "French", "German", "Greek",
                "Indian", "Irish", "Italian", "Japanese", "Jewish", "Korean",
                "Latin American", "Mediterranean", "Mexican", "Middle Eastern",
                "Nordic", "Southern", "Spanish", "Thai", "Vietnamese"
        };
        this.cuisines = new ArrayList<>();
        createCheckboxTable(cuisines, "CuisineCheckbox", R.id.cuisineText, 57000);
    }

    public void createCheckboxTable(String[] array, String tagName, int placeBelowID, int idStart){
        TableLayout table = (TableLayout) findViewById(R.id.cuisineTable);
        int remainder = array.length%3;
        for (int i=0; i<array.length/3+1; i++) {
            if (i<array.length/3 || (i==array.length/3 && remainder>=1)){
                TableRow row = new TableRow(this);
                for (int j=0; j<=2; j++){
                    if (j==1 && remainder==1 && i==array.length/3) break;
                    if (j==2 && remainder==2 && i==array.length/3) break;
                    CheckBox temp =  new CheckBox(this);
                    temp.setTag(array[(i*3)+j]+tagName);
                    temp.setId(idStart+(i*3)+j);
                    temp.setText(array[(i*3)+j]);
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