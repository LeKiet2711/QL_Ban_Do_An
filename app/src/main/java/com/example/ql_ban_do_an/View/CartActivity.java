package com.example.ql_ban_do_an.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.ql_ban_do_an.Controller.CartAdapter;
import com.example.ql_ban_do_an.Helper.ChangeNumberItemsListener;
import com.example.ql_ban_do_an.Helper.ManagmentCart;
import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managementCart;
    private double tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managementCart=new ManagmentCart(this);

        setVariable();
        caculateCart();
        initList();
    }

    private void initList(){
        if(managementCart.getListCart().isEmpty()){
            binding.txtEmpty.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        }else{
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearlayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.cartView.setLayoutManager(linearlayoutManager);
        adapter=new CartAdapter(managementCart.getListCart(), this, () -> caculateCart());
        binding.cartView.setAdapter(adapter);
    }

    private void caculateCart(){
        double percentTax=0.02;
        double delivery=10;

        tax=Math.round(managementCart.getTotalFee()*percentTax*100.0)/100;
        double total=Math.round((managementCart.getTotalFee()+tax+delivery)*100)/100;
        double itemTotal=Math.round(managementCart.getTotalFee()*100)/100;
        binding.txtTotalFee.setText("$"+itemTotal);
        binding.txtTax.setText("$"+tax);
        binding.txtDelivery.setText("$"+delivery);
        binding.txtTotal.setText("$"+total);
    }

    private void setVariable(){
        binding.backBtn.setOnClickListener(v -> finish());
    }

}