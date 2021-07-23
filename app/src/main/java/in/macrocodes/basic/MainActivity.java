package in.macrocodes.basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.macrocodes.basic.Models.Transaction;

public class MainActivity extends AppCompatActivity {

    Button purchaseBtn,sellBtn;
    RequestQueue requestQueue;
    RecyclerView productListView;
    TextView totalPurchase,totalsell;
    TextView lastDate,lastPurchase,lastSell;
    ProductAdapterClass mAdapter;
    List<Transaction> productList = new ArrayList<>();
    int sellAmount=0,purchaseAmount=0;
    String GetUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetUrl = getResources().getString(R.string.url)+"/api/getTransactionList";
        initialize();
        clickListners();
        //Database();
        apiDatabase();
        getTotalPurchaseSell();

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
                intent.putExtra("type","p");
                startActivity(intent);
            }
        });

        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddProduct.class);
                intent.putExtra("type","s");
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

    void apiDatabase(){

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GetUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray array = response.getJSONArray("response");

                    for (int i=0;i<array.length();i++){

                        JSONObject rec = array.getJSONObject(i);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Transaction transaction = gson.fromJson(rec.toString(),Transaction.class);


                        //Setting up the total purchase and total sell amounts on UI

                            if (transaction.getDate()==null){
                                lastDate.setText("No date");
                            }else{
                                lastDate.setText(transaction.getDate());
                            }

                        productList.add(transaction);
                        mAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    void getTotalPurchaseSell(){
        String Totalurl = getResources().getString(R.string.url)+"/api/GetTotalPurchaseAndSell";
        JsonObjectRequest request = new JsonObjectRequest(Totalurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray array = response.getJSONArray("response");

                    totalsell.setText(String.valueOf(array.get(1)));
                    lastSell.setText(String.valueOf(array.get(1)));

                    totalPurchase.setText(String.valueOf(array.get(0)));
                    lastPurchase.setText(String.valueOf(array.get(0)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }
//
//    void Database(){
//        productList.clear();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull  DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    reference.child(Objects.requireNonNull(dataSnapshot.getKey())).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Product product = snapshot.getValue(Product.class);
//                            productList.add(product);
//                            mAdapter.notifyDataSetChanged();
//
//                            //Calculating total amount of sell and purchases
//                            assert product != null;
//                            if (product.getType().equalsIgnoreCase("sell")){
//                                int total =  Integer.parseInt(product.getPrice()) * Integer.parseInt(product.getQuantity());
//                                sellAmount = sellAmount + total;
//                            }else if (product.getType().equalsIgnoreCase("purchase")){
//                                int total =  Integer.parseInt(product.getPrice()) * Integer.parseInt(product.getQuantity());
//                                purchaseAmount = purchaseAmount + total;
//                            }
//
//                            //setting up the value after retrieving
//                            totalsell.setText(String.valueOf(sellAmount));
//                            totalPurchase.setText(String.valueOf(purchaseAmount));
//                            lastPurchase.setText(String.valueOf(purchaseAmount));
//                            lastSell.setText(String.valueOf(sellAmount));
//                            if (product.getDate()==null){
//                                lastDate.setText("No date");
//                            }else{
//                                lastDate.setText(product.getDate());
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull  DatabaseError error) {
//
//            }
//        });
//    }
}