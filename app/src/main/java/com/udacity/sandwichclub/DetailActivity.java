package com.udacity.sandwichclub;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError("Null intent error.");
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError("EXTRA_POSITION not fount in intent.");
            return;
        }

        //get details about all the sandwiches
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        //get the json details for the specific sandwich user picked
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            //parse the json in to a sandwich object (see sandwich.java, and JsonUtils.java)
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JsonException", Toast.LENGTH_SHORT).show();
        }

        if (sandwich == null) {
            closeOnError("sandwich==null");
            return;
        }

        //populate the text views with the parsed json
        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError(String text) {
        finish();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView origin = (TextView) findViewById(R.id.origin_tv);
        origin.setText(sandwich.getPlaceOfOrigin());

        TextView description = (TextView) findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

        TextView ingredients = (TextView) findViewById(R.id.ingredients_tv);
        String formattedIngredients = "";
        for (String ingredient : sandwich.getIngredients()) {
            formattedIngredients += ingredient;
            formattedIngredients += "\n";
        }
        ingredients.setText(formattedIngredients.substring(0, formattedIngredients.length() - 1));

        TextView aka = (TextView) findViewById(R.id.also_known_tv);
        String formattedAka = "";
        for (String otherName : sandwich.getAlsoKnownAs()) {
            formattedAka += otherName;
            formattedAka += "\n";
        }
        //display text (not not the last new-line character)
        aka.setText(formattedAka.substring(0, formattedAka.length() - 1));

    }
}
