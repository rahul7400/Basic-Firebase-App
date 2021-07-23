package in.macrocodes.basic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.macrocodes.basic.Models.Transaction;

public class ProductAdapterClass extends RecyclerView.Adapter<ProductAdapterClass.Viewholder> {
    List<Transaction> productList = new ArrayList<>();
    Context context;
    public ProductAdapterClass(MainActivity mainActivity, List<Transaction> productList) {
        context = mainActivity;
        this.productList=productList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterClass.Viewholder holder, int position) {

        Transaction product = productList.get(position);
        //TransactionModel Lastproduct = productList.get(productList.size()-1);

        holder.pName.setText(product.getName());

        if (product.getDate()==null && product.getTime()==null){
            holder.pDate.setText("No date available");
        }else{
            holder.pDate.setText(product.getDate() +" "+ product.getTime());
        }

        holder.pUnit.setText("1*"+product.getQuantity());

        if (product.getTypeOfTransaction().equalsIgnoreCase("s")){

            holder.pPurchase.setVisibility(View.INVISIBLE);
            holder.pSell.setVisibility(View.VISIBLE);
            holder.pSell.setText(String.valueOf(product.getTotalPrice()));

        }else{
            holder.pPurchase.setVisibility(View.VISIBLE);
            holder.pSell.setVisibility(View.INVISIBLE);
            holder.pPurchase.setText(String.valueOf(product.getTotalPrice()));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("pname",product.getName());
                intent.putExtra("pprice",String.valueOf(product.getTotalPrice()));
                intent.putExtra("pdate",product.getDate());
                intent.putExtra("ptime",product.getTime());
                intent.putExtra("punit",product.getUnit());
                intent.putExtra("pquantity",String.valueOf(product.getQuantity()));
                intent.putExtra("ptype",product.getTypeOfTransaction());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        public TextView pName,pSell,pPurchase,pUnit,pDate;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            pName = (TextView) itemView.findViewById(R.id.pName);
            pSell = (TextView) itemView.findViewById(R.id.pSell);
            pPurchase = (TextView) itemView.findViewById(R.id.pPurchase);
            pUnit = (TextView) itemView.findViewById(R.id.pUnit);
            pDate = (TextView) itemView.findViewById(R.id.pDate);
        }
    }
}
