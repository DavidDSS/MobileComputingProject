package com.example.mobilecomputingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public String mainMessage;

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
        Button nextPageBtn = findViewById(R.id.nextStepBtn);
        EditText userInputText= findViewById(R.id.ingredientEditText);
        ListView ingredientListView= findViewById(R.id.ingredientList);

        //Instantiate spoonDataService for API use
        final SpoonDataService spoonDataService= new SpoonDataService(MainActivity.this);

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
                    public void onResponse(List<RecipeModel> recipeList) {
                        ArrayAdapter arrayAdapter= new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, recipeList);
                        ingredientListView.setAdapter(arrayAdapter);

                        //Got to Filters Page
                        goToFilters(view);
                    }
                });
            }
        });
    }

    public void goToFilters(View view) {
        Intent intent = new Intent(MainActivity.this, FilterPageActivity.class);
        intent.putExtra("mainMessage", "Filter Page");
        startActivity(intent);
    }
}