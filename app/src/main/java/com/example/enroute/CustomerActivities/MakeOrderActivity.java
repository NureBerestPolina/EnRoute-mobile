package com.example.enroute.CustomerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enroute.Models.Category;
import com.example.enroute.Models.Good;
import com.example.enroute.Models.Organization;
import com.example.enroute.Models.PickupCounter;
import com.example.enroute.R;
import com.example.enroute.RequestModels.MakeOrderRequest;
import com.example.enroute.Services.AuthService;
import com.example.enroute.Services.GoodsService;
import com.example.enroute.Services.OrderService;

import java.io.IOException;
import java.util.UUID;

public class MakeOrderActivity extends AppCompatActivity {

    private OrderService orderService;
    private Organization selectedOrganization;
    private Category selectedCategory;

    private EditText countInput;
    private TextView outputTotal;
    private Spinner shopsSpinner, categoriesSpinner, goodsSpinner, countersSpinner;
    private Button makeOrderBtn;
    private ImageView backBtn;

    private UUID customerId, orderedGoodId, deliveryCounterId;
    private int count = 1;
    private double price, totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        customerId = AuthService.getInstance(MakeOrderActivity.this).getUser().getId();

        initLayoutElements();
        initSpinners();

        makeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MakeOrderRequest orderRequest = getNewOrderRequest();
                    placeOrder(orderRequest);
                    returnHome();
                }
                catch (Exception e) {
                    Toast.makeText(MakeOrderActivity.this, getResources().getString(R.string.failed_order), Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });

        countInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    count = Integer.parseInt(countInput.getText().toString());
                }
                catch (Exception ex)
                {
                    count = 0;
                }

                setTotalOutput(price);
            }
        });
    }

    @NonNull
    private MakeOrderRequest getNewOrderRequest() {
        count = Integer.parseInt(countInput.getText().toString());
        return new MakeOrderRequest(customerId, orderedGoodId, deliveryCounterId, count);
    }

    private void initLayoutElements() {
        shopsSpinner = (Spinner) findViewById(R.id.organizations_spinner);
        categoriesSpinner = (Spinner) findViewById(R.id.categories_spinner);
        goodsSpinner = (Spinner) findViewById(R.id.goods_spinner);
        countersSpinner = (Spinner) findViewById(R.id.counters_spinner);
        countInput = (EditText) findViewById(R.id.order_count);
        outputTotal = (TextView) findViewById(R.id.order_total);
        makeOrderBtn = (Button) findViewById(R.id.make_order_btn);
        backBtn = (ImageView) findViewById(R.id.back_home_btn);
    }

    private void initSpinners() {
        orderService = new OrderService(this);
        orderService.seedOrganizations(shopsSpinner);
        orderService.seedCategories(categoriesSpinner);

        shopsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOrganization = (Organization) parent.getItemAtPosition(position);
                orderService.seedCounters(countersSpinner, selectedOrganization.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedOrganization = null;
            }
        });

        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = (Category) parent.getItemAtPosition(position);
                orderService.seedGoods(goodsSpinner, selectedOrganization.getId(), selectedCategory.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = null;
            }
        });

        countersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deliveryCounterId = ((PickupCounter) parent.getItemAtPosition(position)).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       goodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                orderedGoodId = ((Good) parent.getItemAtPosition(position)).getId();
                price = ((Good) parent.getItemAtPosition(position)).getPrice();
                setTotalOutput(price);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setTotalOutput(0);
            }
        });
    }

    private void setTotalOutput(double price) {
        outputTotal.setText(
                getResources().getString(R.string.total) + " " + price*count
        );
    }

    private void placeOrder (MakeOrderRequest order)
    {
        try {
            orderService.makeOrder(order);
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.succesful_order), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_order), Toast.LENGTH_SHORT).show();
        }
    }

    private void returnHome()
    {
        Intent home = new Intent(MakeOrderActivity.this, CustomerHomeActivity.class);
        startActivity(home);
        finish();
    }
}