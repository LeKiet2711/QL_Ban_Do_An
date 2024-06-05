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

import com.example.ql_ban_do_an.Helper.ManagmentCart;
import com.example.ql_ban_do_an.Model.Foods;
import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.databinding.ActivityDetailFoodBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailFoodActivity extends AppCompatActivity {
    ActivityDetailFoodBinding binding;
    private ManagmentCart managmentCart;
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
        binding = ActivityDetailFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addControl();
        foods = getIntentExtra();
        addVariable(foods);
        addEvent();
    }

    void addControl() {
        btnBackDetail = (ImageButton) findViewById(R.id.btnBackDetail);
        btnFavourite = (ImageButton) findViewById(R.id.btnFavourite);
        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
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
    }

    void addEvent() {
        managmentCart=new ManagmentCart(this);
        binding.btnBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(tvNum.getText().toString());
                n--;
                if (n <= 0)
                    n = 1;
                num = n;
                tvNum.setText(String.valueOf(n));
                tvTotal.setText("$" + String.valueOf(foods.getPrice() * num));
            }
        });

        binding.tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(tvNum.getText().toString());
                n++;
                num = n;
                tvNum.setText(String.valueOf(n));
                tvTotal.setText("$" + String.valueOf(foods.getPrice() * num));
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foods.setNumberInCart(num);
                managmentCart.insertFood(foods);
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
                .into(binding.imgDetail);

        binding.tvTitleDetail.setText(foods.getTitle());
        binding.tvPriceDetail.setText("$" + String.valueOf(foods.getPrice()));
        binding.tvRateDetail.setText(String.valueOf(foods.getStar()));
        binding.tvTotal.setText("$" + String.valueOf(foods.getPrice() * num));
        binding.tvDescription.setText(foods.getDescription());
        binding.tvDescription.setMovementMethod(new ScrollingMovementMethod());
        binding.tvTimeDetail.setText(String.valueOf(foods.getTimeValue()) + " min");

        binding.ratingBar.setMax(4);
        binding.ratingBar.setNumStars(4);
        binding.ratingBar.setStepSize(0.1f);
        binding.ratingBar.setRating((float) foods.getStar() - 1);
    }
}