package com.example.mobilecomputingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {
    //Global variables related to lists and adapters
    ListView ingredientListView;
    ArrayList<IngredientsView> ingredientList;
    IngredientsViewAdapter ingredientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize App Toolbar
        Toolbar appToolbar = (Toolbar) findViewById(R.id.nutri_toolbar);

        //Set app toolbar and remove default title
        setSupportActionBar(appToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Initialize Screen Elements

        //Initialize Buttons
        Button nextPageBtn = findViewById(R.id.nextStepBtn);
        Button addIngredientBtn = findViewById(R.id.addBtn);

        //Initialize User Input Field
        EditText userInputText= findViewById(R.id.ingredientEditText);

        //Initialize Ingredient ListView and Ingredient List
        ingredientListView= findViewById(R.id.ingredientList);
        ingredientList = new ArrayList<IngredientsView>();

        //Initialize Empty Text and Set it
        TextView emptyListText= findViewById(R.id.emptyListText);
        ingredientListView.setEmptyView(emptyListText);

        //Initialize Adapter
        ingredientListAdapter = new IngredientsViewAdapter(this, ingredientList);

        //Set Adapter
        ingredientListView.setAdapter(ingredientListAdapter);

        //Instantiate spoonDataService for API use
        final SpoonDataService spoonDataService= new SpoonDataService(MainActivity.this);

        //Add Ingredient Functionality
        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userInputText.getText().toString().equals("")) {
                    //Add Ingredient to list and display it
                    String userIngredient= userInputText.getText().toString();
                    IngredientsView ingredientsView= new IngredientsView(userIngredient);
                    ingredientList.add(ingredientsView);
                    ingredientListAdapter.notifyDataSetChanged();

                    //Clear User Input
                    userInputText.setText("");

                    //Change style of "next" btn and enable it
                    nextPageBtn.setEnabled(true);
                    nextPageBtn.setBackgroundColor(getResources().getColor(R.color.nutri_green));
                }
            }
        });

        //Next Step Button Functionality
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spoonDataService.getRecipeByIngredients(userInputText.getText().toString(), new SpoonDataService.recipeByIngredientsResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Failed: "+message, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(ArrayList<RecipeModel> recipeList) {
                        //Got to Filters Page
                        goToFilters(view, recipeList);
                    }
                });
            }
        });

    }

    //Go to Filters Page
    public void goToFilters(View view, ArrayList<RecipeModel> recipeList) {
        Intent intent = new Intent(MainActivity.this, FilterPageActivity.class);
        intent.putExtra("recipeList", recipeList);
        startActivity(intent);
    }

    //Remove Ingredient
    public void removeIngredient(View view) {
        int position = ingredientListView.getPositionForView(view);
        ingredientList.remove(position);
        ingredientListAdapter.notifyDataSetChanged();
    }
}