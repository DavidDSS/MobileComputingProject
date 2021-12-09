/*
Griffin Allen, Justin Gula, David Saldana
COSC 3P97 Final Project
Nutri+ (Recipe Finder App)
References:
- Awaiken. “Food API and Recipe API.” Spoonacular Recipe and Food API, https://spoonacular.com/food-api.
- “Volley Overview  :   Android Developers.” Android Developers, https://developer.android.com/training/volley.
- “Callback  :   Android Developers.” Android Developers, https://developer.android.com/reference/javax/security/auth/callback/Callback.
- Picasso, https://square.github.io/picasso/. 
*/

package com.example.mobilecomputingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
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
    EditText userInputText;
    Button nextPageBtn;

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
        nextPageBtn = findViewById(R.id.nextStepBtn);
        Button addIngredientBtn = findViewById(R.id.addBtn);

        //Initialize User Input Field
        userInputText= findViewById(R.id.ingredientEditText);

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

        //Add Ingredient Functionality
        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userInputText.getText().toString().equals("")) {
                    //Add Ingredient to list and display it
                    addIngredientToList();
                }
            }
        });

        //Next Step Button Functionality
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlIngredientString="";
                for(int q=0; q<ingredientList.size(); q++){
                    urlIngredientString+=ingredientList.get(q).getIngredientName();
                    if(q+1!=ingredientList.size()) urlIngredientString+=",";
                }
                //Got to Filters Page
                goToFilters(view, urlIngredientString);
            }
        });

        userInputText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int code, KeyEvent e) {
                if ((e.getAction() == KeyEvent.ACTION_DOWN) && (code == KeyEvent.KEYCODE_ENTER)) {
                    addIngredientToList();
                    return true;
                }
                return false;
            }
        });
    }

    //Go to Filters Page
    public void goToFilters(View view, String ingredientList) {
        Intent intent = new Intent(MainActivity.this, FilterPageActivity.class);
        intent.putExtra("ingredientList", ingredientList);
        startActivity(intent);
    }

    //Remove Ingredient
    public void removeIngredient(View view) {
        int position = ingredientListView.getPositionForView(view);
        ingredientList.remove(position);
        ingredientListAdapter.notifyDataSetChanged();

        if(ingredientList.size()==0){
            //Change style of "next" btn and disable it
            nextPageBtn.setEnabled(false);
            nextPageBtn.setBackgroundColor(getResources().getColor(R.color.btn_disabled));
        }
    }

    public void addIngredientToList(){
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