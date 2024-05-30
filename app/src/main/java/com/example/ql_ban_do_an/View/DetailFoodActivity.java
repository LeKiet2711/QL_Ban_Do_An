package com.example.ql_ban_do_an.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.ql_ban_do_an.Model.Foods;
import com.example.ql_ban_do_an.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailFoodActivity extends AppCompatActivity {

    ImageButton btnBackDetail, btnFavourite;
    ImageView imgDetail;
    RatingBar ratingBar;
    TextView tvTitleDetail, tvPriceDetail, tvRateDetail, tvTimeDetail, tvDescription, tvMinus;
    TextView tvNum, tvPlus, tvTotal;
    AppCompatButton btnAdd;
    Foods foods;
    private int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        addControl();
        foods = getIntentExtra();
        addVariable(foods);
        addEvent();
    }

    void addControl() {
        btnBackDetail = (ImageButton) findViewById(R.id.btnBackDetail);
        btnFavourite = (ImageButton) findViewById(R.id.btnFavourite);
        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        tvTitleDetail = (TextView) findViewById(R.id.tvTitleDetail);
        tvPriceDetail = (TextView) findViewById(R.id.tvPriceDetail);
        tvRateDetail = (TextView) findViewById(R.id.tvRateDetail);
        tvTimeDetail = (TextView) findViewById(R.id.tvTimeDetail);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvMinus = (TextView) findViewById(R.id.tvMinus);
        tvNum = (TextView) findViewById(R.id.tvNum);
        tvPlus = (TextView) findViewById(R.id.tvPlus);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        btnAdd = (AppCompatButton) findViewById(R.id.btnAdd);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setMax(5);
    }

    void addEvent() {
        btnBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailFoodActivity.this, ListFoodActivity.class);
                startActivity(intent);
            }
        });

        tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(tvNum.getText().toString());
                n--;
                if(n <= 0)
                    n = 1;
                num = n;
                tvNum.setText(String.valueOf(n));
                tvTotal.setText("$" + String.valueOf(foods.getPrice() * num));
            }
        });

        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(tvNum.getText().toString());
                n++;
                num = n;
                tvNum.setText(String.valueOf(n));
                tvTotal.setText("$" + String.valueOf(foods.getPrice() * num));
            }
        });
    }

    Foods getIntentExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("object");
        Foods food = new Foods();

        food.setTitle(bundle.getString("title"));
        food.setImagePath(bundle.getString("img"));
        food.setPrice(bundle.getDouble("price"));
        food.setStar(bundle.getDouble("star"));
        food.setDescription(bundle.getString("des"));
        food.setTimeValue(bundle.getInt("time"));

        return food;
    }


    void addVariable(Foods foods) {
        Picasso.with(getApplicationContext())
                .load(foods.getImagePath())
                .into(imgDetail);

        tvTitleDetail.setText(foods.getTitle());
        tvPriceDetail.setText("$" + String.valueOf(foods.getPrice()));
        tvRateDetail.setText(String.valueOf(foods.getStar()));
        double starValue = foods.getStar();
        ratingBar.setRating((float) 4.5f);
        float s = ratingBar.getRating();
        tvTotal.setText("$" + String.valueOf(foods.getPrice() * num));
        tvDescription.setText(foods.getDescription());
        tvDescription.setMovementMethod(new ScrollingMovementMethod());
        tvTimeDetail.setText(String.valueOf(foods.getTimeValue()) + " min");
    }
}