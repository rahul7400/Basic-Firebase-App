package in.macrocodes.basic;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapterClass extends RecyclerView.Adapter<ProductAdapterClass.Viewholder> {
    List<Product> productList = new ArrayList<>();
    Context context;
    public ProductAdapterClass(MainActivity mainActivity, List<Product> productList) {
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

        Product product = productList.get(position);
        Product Lastproduct = productList.get(productList.size()-1);

        holder.pName.setText(product.getName());

        if (product.getDate()==null && product.getTime()==null){
            holder.pDate.setText("No date available");
        }else{
            holder.pDate.setText(product.getDate() +" "+ product.getTime());
        }

        holder.pUnit.setText("1*"+product.getQuantity());

        if (product.getType().equalsIgnoreCase("sell")){

            holder.pPurchase.setVisibility(View.INVISIBLE);
            holder.pSell.setVisibility(View.VISIBLE);
            int total = Integer.parseInt(product.getPrice())*Integer.parseInt(product.getQuantity());
            holder.pSell.setText(String.valueOf(total));

        }else{
            holder.pPurchase.setVisibility(View.VISIBLE);
            holder.pSell.setVisibility(View.INVISIBLE);
            int total = Integer.parseInt(product.getPrice())*Integer.parseInt(product.getQuantity());
            holder.pPurchase.setText(String.valueOf(total));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("pname",product.getName());
                intent.putExtra("pprice",product.getPrice());
                intent.putExtra("pdate",product.getDate());
                intent.putExtra("ptime",product.getTime());
                intent.putExtra("punit",product.getUnit());
                intent.putExtra("pquantity",product.getQuantity());
                intent.putExtra("ptype",product.getType());
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
