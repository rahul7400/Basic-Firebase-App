package in.macrocodes.basic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button purchaseBtn,sellBtn;
    RecyclerView productListView;
    TextView totalPurchase,totalsell;
    TextView lastDate,lastPurchase,lastSell;
    ProductAdapterClass mAdapter;
    List<Product> productList = new ArrayList<>();
    int sellAmount=0,purchaseAmount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        clickListners();
        Database();

        //Attaching Adapter and LinearLayout with Recyclerview
        mAdapter = new ProductAdapterClass(this,productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productListView.setLayoutManager(linearLayoutManager);
        productListView.setAdapter(mAdapter);

    }

    void clickListners(){
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddProduct.class);
                intent.putExtra("type","purchase");
                startActivity(intent);
            }
        });

        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddProduct.class);
                intent.putExtra("type","sell");
                startActivity(intent);
            }
        });
    }

    void initialize(){
        purchaseBtn = (Button) findViewById(R.id.btn_purchase);
        sellBtn = (Button) findViewById(R.id.btn_sell);
        productListView = (RecyclerView) findViewById(R.id.productListView);
        totalPurchase = (TextView) findViewById(R.id.totalPurchase);
        totalsell = (TextView) findViewById(R.id.totalSell);
        lastDate = (TextView) findViewById(R.id.lastDate);
        lastPurchase = (TextView) findViewById(R.id.lastPurchase);
        lastSell = (TextView) findViewById(R.id.lastSell);
    }

    void Database(){
        productList.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    reference.child(Objects.requireNonNull(dataSnapshot.getKey())).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Product product = snapshot.getValue(Product.class);
                            productList.add(product);
                            mAdapter.notifyDataSetChanged();

                            //Calculating total amount of sell and purchases
                            assert product != null;
                            if (product.getType().equalsIgnoreCase("sell")){
                                int total =  Integer.parseInt(product.getPrice()) * Integer.parseInt(product.getQuantity());
                                sellAmount = sellAmount + total;
                            }else if (product.getType().equalsIgnoreCase("purchase")){
                                int total =  Integer.parseInt(product.getPrice()) * Integer.parseInt(product.getQuantity());
                                purchaseAmount = purchaseAmount + total;
                            }

                            //setting up the value after retrieving
                            totalsell.setText(String.valueOf(sellAmount));
                            totalPurchase.setText(String.valueOf(purchaseAmount));
                            lastPurchase.setText(String.valueOf(purchaseAmount));
                            lastSell.setText(String.valueOf(sellAmount));
                            if (product.getDate()==null){
                                lastDate.setText("No date");
                            }else{
                                lastDate.setText(product.getDate());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}