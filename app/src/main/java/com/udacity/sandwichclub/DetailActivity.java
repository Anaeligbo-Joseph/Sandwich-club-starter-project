package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ImageView imageIv;
    TextView originTv, descriptionTv, ingredientsTv, alsoKnownAsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        originTv = findViewById(R.id.origin_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        TextView origin = findViewById(R.id.origin_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);

        origin.setText(verify(sandwich.getPlaceOfOrigin()));
        description.setText(sandwich.getDescription());

        //modifying and verifying the string array
        String modAlsoKnowAs="";
        for(String s: sandwich.getAlsoKnownAs()){
            modAlsoKnowAs+=s+", ";
        }
        if (modAlsoKnowAs.length() > 0){
            modAlsoKnowAs = modAlsoKnowAs.substring(0, modAlsoKnowAs.length() - 2);
        }
        alsoKnownAs.setText(verify(modAlsoKnowAs));

        //modifying and verifying the string array with numbering -number, spacing and next line
        String modIngredient= "";
        int number=1;
        for (String s: sandwich.getIngredients()) {
            modIngredient += number++ + ".  " + s + "\n\n";
        }
        ingredients.setText(modIngredient);
    }

    private String verify(String s) {
        if(s.equals("")){
            return getString(R.string.missing);
        }
        return s;
    }
}
