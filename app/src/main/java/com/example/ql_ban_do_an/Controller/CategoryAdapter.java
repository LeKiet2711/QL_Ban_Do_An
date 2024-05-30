package com.example.ql_ban_do_an.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ql_ban_do_an.Model.Category;
import com.example.ql_ban_do_an.Model.Foods;
import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.View.ListFoodActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
    ArrayList<Category> items;
    Context context;

    public CategoryAdapter(ArrayList<Category> items)
    {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        View inflate= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_category,viewGroup,false);

        return new viewholder(inflate);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder viewholder, int i) {
        int adapterPosition = viewholder.getAdapterPosition();
        viewholder.titleTxt.setText(items.get(i).getName());
        switch (i)
        {
            case 0:{
                viewholder.pic.setBackgroundResource(R.drawable.cat_0_background);
                break;
            }
            case 1:{
                viewholder.pic.setBackgroundResource(R.drawable.cat_1_background);
                break;
            }
            case 2:{
                viewholder.pic.setBackgroundResource(R.drawable.cat_2_background);
                break;
            }
            case 3:{
                viewholder.pic.setBackgroundResource(R.drawable.cat_3_background);
                break;
            }
            case 4:{
                viewholder.pic.setBackgroundResource(R.drawable.cat_4_background);
                break;
            }
            case 5:{
                viewholder.pic.setBackgroundResource(R.drawable.cat_5_background);
                break;
            }
            case 6:{
                viewholder.pic.setBackgroundResource(R.drawable.cat_6_background);
                break;
            }
            case 7:{
                viewholder.pic.setBackgroundResource(R.drawable.cat_7_background);
                break;
            }
        }
        String imagePath = items.get(i).getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            int drawableResouceId = context.getResources().getIdentifier(imagePath, "drawable", viewholder.itemView.getContext().getPackageName());

            if (drawableResouceId != 0) {
                Picasso.with(context).load(drawableResouceId).into(viewholder.pic);
            } else {
                // Hình ảnh không tồn tại trong resources:
                viewholder.pic.setImageResource(R.drawable.btn_3); // Thay bằng một resource id ảnh placeholder thực tế
            }
        } else {
            // Xử lý khi imagePath null hoặc rỗng
            viewholder.pic.setImageResource(R.drawable.btn_5); // Thay bằng một resource id ảnh placeholder thực tế
        }

        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListFoodActivity.class);
                intent.putExtra("CategoryId", items.get(i).getId());
                intent.putExtra("CategoryName", items.get(i).getName());

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder  extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.catNameTxt);

            pic=itemView.findViewById(R.id.catImg);
        }
    }
}
