package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView originTv;
    TextView descriptionTv;
    TextView ingredientsTv;
    TextView alsoKnownTv;

    TextView originLabel;
    TextView descriptionLabel;
    TextView ingredientsLabel;
    TextView alsoKnownAsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);

        originLabel = findViewById(R.id.originLabel);
        descriptionLabel = findViewById(R.id.descriptionLabel);
        ingredientsLabel = findViewById(R.id.ingredientsLabel);
        alsoKnownAsLabel = findViewById(R.id.alsoKnownLabel);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            // EXTRA_POSITION not found in intent
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
            // Sandwich data unavailable
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //if no information for corresponding textview is returned view will be set to View.GONE
    private void populateUI(Sandwich sandwich) {

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        List<String> ingredientsList = sandwich.getIngredients();

        StringBuilder alsoKnownAsSb = new StringBuilder();
        for (String details : alsoKnownAsList) {
            alsoKnownAsSb.append(details).append("\n");
        }

        if (alsoKnownAsSb.length() > 0) {
            alsoKnownAsSb.setLength(alsoKnownAsSb.length() - 1);
            alsoKnownTv.setText(alsoKnownAsSb.toString());
        } else {
            alsoKnownAsLabel.setVisibility(View.GONE);
            alsoKnownTv.setVisibility(View.GONE);
        }

        StringBuilder ingredientsSb = new StringBuilder();
        for (String details : ingredientsList) {
            ingredientsSb.append(details).append("\n");
        }

        if (ingredientsSb.length() > 0) {
            ingredientsSb.setLength(ingredientsSb.length() - 1);
            ingredientsTv.setText(ingredientsSb.toString());
        } else {
            ingredientsLabel.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()){
            originLabel.setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getDescription().isEmpty()){
            descriptionLabel.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
        } else {
            descriptionTv.setText(sandwich.getDescription());
        }

    }
}
