package com.example.mobilecomputingproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FilterPageActivity extends AppCompatActivity {

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

        //Access Intent and Data
        Intent intent= getIntent();
        String message = intent.getExtras().getString("mainMessage");
        TextView textView = findViewById(R.id.filterText);
        textView.setText(message);
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