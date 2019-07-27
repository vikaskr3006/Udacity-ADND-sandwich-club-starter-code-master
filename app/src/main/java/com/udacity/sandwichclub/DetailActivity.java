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

    private ImageView mImageIv;
    private TextView mAlsoKnownAsLabel;
    private TextView mAlsoKnownAsTv;
    private TextView mPlaceOfOriginLAbel;
    private TextView mPlaceOfOriginTv;
    private TextView mDescriptionLabel;
    private TextView mDescriptionTv;
    private TextView mIngredientsLabel;
    private TextView mIngredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageIv = findViewById(R.id.image_iv);
        mAlsoKnownAsLabel = findViewById(R.id.also_known_as_label);
        mAlsoKnownAsTv = findViewById(R.id.also_known_as_tv);
        mPlaceOfOriginLAbel = findViewById(R.id.place_of_origin_label);
        mPlaceOfOriginTv = findViewById(R.id.place_of_origin_tv);
        mDescriptionLabel = findViewById(R.id.description_label);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientsLabel = findViewById(R.id.ingredients_label);
        mIngredientsTv = findViewById(R.id.ingredients_tv);

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

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mImageIv);

        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if(alsoKnownAs != null && alsoKnownAs.size() > 0) {
            for(int i=0; i<alsoKnownAs.size(); i++){
                if(i==alsoKnownAs.size()-1){
                    mAlsoKnownAsTv.append(alsoKnownAs.get(i));
                }else{
                    mAlsoKnownAsTv.append(alsoKnownAs.get(i) + ", ");
                }
            }
        }else{
            mAlsoKnownAsLabel.setVisibility(View.GONE);
            mAlsoKnownAsTv.setVisibility(View.GONE);
        }

        if(!sandwich.getPlaceOfOrigin().isEmpty()){
            mPlaceOfOriginTv.append(sandwich.getPlaceOfOrigin());
        }else{
            mPlaceOfOriginLAbel.setVisibility(View.GONE);
            mPlaceOfOriginTv.setVisibility(View.GONE);
        }

        if(!sandwich.getDescription().isEmpty()){
            mDescriptionTv.append(sandwich.getDescription());
        }else{
            mDescriptionLabel.setVisibility(View.GONE);
            mDescriptionTv.setVisibility(View.GONE);
        }

        List<String> ingredients = sandwich.getIngredients();
        if(ingredients != null && ingredients.size() > 0){
            for(int i=0; i<ingredients.size(); i++){
                if(i == ingredients.size()-1){
                    mIngredientsTv.append("\u2022 " + ingredients.get(i));
                }else{
                    mIngredientsTv.append("\u2022 " + ingredients.get(i) + "\n");
                }
            }
        }else{
            mIngredientsLabel.setVisibility(View.GONE);
            mIngredientsTv.setVisibility(View.GONE);
        }
    }
}
