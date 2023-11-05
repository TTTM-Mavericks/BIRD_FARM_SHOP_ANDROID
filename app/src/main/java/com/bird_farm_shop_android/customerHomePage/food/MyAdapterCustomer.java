package com.bird_farm_shop_android.customerHomePage.food;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bird_farm_shop_android.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
public class MyAdapterCustomer extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<ProductDTOCustomer> dataList;
    public MyAdapterCustomer(Context context, List<ProductDTOCustomer> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_customer_homepage, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recycleImageCustomer);
        holder.recycleProductNameCustomer.setText("Name: " + dataList.get(position).getProductName());
        holder.recycleProductPriceCustomer.setText("Price: " + dataList.get(position).getProductPrice());
        holder.recycleCardCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodDetailActivityCustomer.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("ProductDescription", dataList.get(holder.getAdapterPosition()).getProductDescription());
                intent.putExtra("ProductName", dataList.get(holder.getAdapterPosition()).getProductName());
                intent.putExtra("FoodKey",dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("ProductPrice", dataList.get(holder.getAdapterPosition()).getProductPrice());
                context.startActivity(intent);
            }
        });
    }
    public void searchDataList(ArrayList<ProductDTOCustomer> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recycleImageCustomer;
    TextView recycleProductNameCustomer, recycleProductPriceCustomer;
    CardView recycleCardCustomer;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recycleImageCustomer = itemView.findViewById(R.id.recycleImageCustomer);
        recycleCardCustomer = itemView.findViewById(R.id.recycleCardCustomer);
        recycleProductNameCustomer = itemView.findViewById(R.id.recycleProductNameCustomer);
        recycleProductPriceCustomer = itemView.findViewById(R.id.recycleProductPriceCustomer);
    }
}