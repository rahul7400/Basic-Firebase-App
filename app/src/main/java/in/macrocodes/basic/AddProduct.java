package in.macrocodes.basic;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddProduct extends AppCompatActivity {

    TextView currentDate, currentTime, totalAmount, productUnit;
    EditText productName, productPrice, productQuantity;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    LinearLayout unitLayout;
    int count=0;
    Button kg,cm,piece,save;
    String unit="",name, price,quantity;
    String userdate;
    String usertime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initialize();
        clickListners();
        liveAmount();
        updateDate();

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
        save = (Button) findViewById(R.id.btn_save);

        kg = (Button) findViewById(R.id.kg);
        cm = (Button) findViewById(R.id.cm);
        piece = (Button) findViewById(R.id.piece);

    }

    void clickListners() {

        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                new DatePickerDialog(AddProduct.this, date, myCalendar
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase();
            }
        });


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

        mTimePicker = new TimePickerDialog(AddProduct.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                usertime = selectedHour + ":" + selectedMinute;
                currentTime.setText(usertime);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    void FirebaseDatabase(){

        //Uploading data to Firebase
        String type = getIntent().getStringExtra("type");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        HashMap<String,Object> map = new HashMap<>();
        map.put("name",productName.getText().toString());
        map.put("price",productPrice.getText().toString());
        map.put("quantity",productQuantity.getText().toString());
        map.put("unit",unit);
        map.put("date",userdate);
        map.put("time",usertime);
        map.put("type",type);

        reference.child(productName.getText().toString()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddProduct.this, "Product Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddProduct.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

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