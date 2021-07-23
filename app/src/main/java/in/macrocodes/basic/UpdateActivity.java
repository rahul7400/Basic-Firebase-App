package in.macrocodes.basic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class UpdateActivity extends AppCompatActivity {
    
    TextView currentDate, currentTime, totalAmount, productUnit;
    EditText productName, productPrice, productQuantity;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    LinearLayout unitLayout;
    int count=0;
    Button kg,cm,piece,update;
    String unit="",name, price,quantity;
    String userdate;
    String usertime;
    Product product;
    String type;
    String updateUrl = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        updateUrl = getResources().getString(R.string.url)+"/api/updateTransaction";
        initialize();
        clickListners();
        liveAmount();
        updateDate();
        setOldData();


    }

    void initialize() {
        currentDate = findViewById(R.id.currentDate);
        currentTime = findViewById(R.id.currentTime);
        totalAmount = findViewById(R.id.totalAmount);
        productUnit = findViewById(R.id.productUnit);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productQuantity = findViewById(R.id.productQuantity);
        unitLayout = (LinearLayout) findViewById(R.id.unit_layout);
        update = (Button) findViewById(R.id.btn_update);

        kg = (Button) findViewById(R.id.kg);
        cm = (Button) findViewById(R.id.cm);
        piece = (Button) findViewById(R.id.piece);


    }

    void setOldData(){

        currentDate.setText(getIntent().getStringExtra("pdate"));
        currentTime.setText(getIntent().getStringExtra("ptime"));
        productName.setText(getIntent().getStringExtra("pname"));
        productPrice.setText(getIntent().getStringExtra("pprice"));
        productQuantity.setText(getIntent().getStringExtra("pquantity"));
        productUnit.setText(getIntent().getStringExtra("punit"));
        type = getIntent().getStringExtra("ptype");

        int total = Integer.parseInt(getIntent().getStringExtra("pquantity"))*Integer.parseInt(getIntent().getStringExtra("pprice"));
        totalAmount.setText(String.valueOf(total));
    }
    void clickListners() {

        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                new DatePickerDialog(UpdateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        currentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });
        productUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0){
                    unitLayout.setVisibility(View.VISIBLE);
                    count++;
                }else{
                    unitLayout.setVisibility(View.GONE);
                    count--;
                }

            }
        });
        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unit = "kg";
                productUnit.setText("Kg");
                unitLayout.setVisibility(View.GONE);
            }
        });

        cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unit = "cm";
                productUnit.setText("cm");
                unitLayout.setVisibility(View.GONE);
            }
        });

        piece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unit = "piece";
                productUnit.setText("piece");
                unitLayout.setVisibility(View.GONE);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //FirebaseDatabase();
                apiDatabase();
            }
        });


    }

    void apiDatabase(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        JSONObject object = new JSONObject();
        try {

            int total = Integer.parseInt(productPrice.getText().toString()) * Integer.parseInt(productQuantity.getText().toString());

            Random rand = new Random();

            object.put("id",rand.nextInt(1000));
            object.put("totalPrice",total);
            object.put("name",productName.getText().toString());
            object.put("pricePerItem",productPrice.getText().toString());
            object.put("quantity",productQuantity.getText().toString());
            object.put("unit",productUnit.getText().toString());
            object.put("typeOfTransaction",type);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, updateUrl, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(UpdateActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateActivity.this, "Error - "+error, Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    void updateDate() {

        //Selecting date from date picker

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                userdate = sdf.format(myCalendar.getTime());
                currentDate.setText(userdate);

            }

        };
    }

    void updateTime() {
        //Selecting Time from time picker
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(UpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                usertime = selectedHour + ":" + selectedMinute;
                currentTime.setText(usertime);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }




//    void FirebaseDatabase(){
//
//        //Uploading data to Firebase
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
//
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("name",productName.getText().toString());
//        map.put("price",productPrice.getText().toString());
//        map.put("quantity",productQuantity.getText().toString());
//        map.put("unit",productUnit.getText().toString());
//        map.put("date",currentDate.getText().toString());
//        map.put("time",currentTime.getText().toString());
//        map.put("type",type);
//
//        reference.child(productName.getText().toString()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Toast.makeText(UpdateActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }

    void liveAmount(){
        productPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int price,quantity;
                try{

                    price = Integer.parseInt(productPrice.getText().toString());
                    quantity = Integer.parseInt(productQuantity.getText().toString());
                    totalAmount.setText(String.valueOf(price*quantity));

                }catch (Exception e){
                    quantity =0 ;
                }

            }
        });

        productQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int price,quantity;
                try{

                    price = Integer.parseInt(productPrice.getText().toString());
                    quantity = Integer.parseInt(productQuantity.getText().toString());
                    totalAmount.setText(String.valueOf(price*quantity));

                }catch (Exception e){
                    price =0 ;
                }

            }
        });
    }
}