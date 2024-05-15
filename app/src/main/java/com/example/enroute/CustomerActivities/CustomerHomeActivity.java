package com.example.enroute.CustomerActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enroute.Constants.ODataEndpointsNames;
import com.example.enroute.Constants.Status;
import com.example.enroute.MainActivity;
import com.example.enroute.Models.Order;
import com.example.enroute.R;
import com.example.enroute.Services.AuthService;
import com.example.enroute.Services.ODataQueryBuilder;
import com.example.enroute.Services.ODataService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CustomerHomeActivity extends AppCompatActivity {

    private ImageView logoutBtn, makeOrderBtn, getRecommedationsBtn;
    private TextView noOrdersText;
    List<Order> orderList;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        logoutBtn = (ImageView) findViewById(R.id.logout_btn);
        makeOrderBtn = (ImageView) findViewById(R.id.make_order_btn);
        getRecommedationsBtn = (ImageView) findViewById(R.id.recommendations_btn);

        noOrdersText = (TextView) findViewById(R.id.no_orders_made);
        layout = (LinearLayout) findViewById(R.id.orders_layout);

        outputOrders(AuthService.getInstance(CustomerHomeActivity.this).getUser().getId());

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthService.getInstance(CustomerHomeActivity.this).logout();
                Intent main = new Intent(CustomerHomeActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });

        getRecommedationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recommendations = new Intent(CustomerHomeActivity.this, RecommendationsActivity.class);
                startActivity(recommendations);
                finish();
            }
        });

        makeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(CustomerHomeActivity.this, MakeOrderActivity.class);
                startActivity(order);
                finish();
            }
        });
    }

    private void outputOrders(UUID id) {
        orderList = getUserOrders(id);

        InsertToLayout(orderList);
    }

    private List<Order> getUserOrders(UUID id) {
        ODataService<Order> t = new ODataService<Order>(Order.class, this);

        ODataQueryBuilder builder = new ODataQueryBuilder();

        builder.filter("CustomerId eq " + id.toString());
        builder.expand("items($expand=GoodOrdered), AssignedCell($expand=Counter)");

        List<Order> res;
        try {
            res = t.getAll(ODataEndpointsNames.ORDERS, builder);
        } catch (IOException e) {
            res = null;
        }

        return res;
    }

    private void fulfillOrder(Order order) {
        ODataService<Order> t = new ODataService<Order>(Order.class, this);

        try {
            Order initOrder = t.getById(ODataEndpointsNames.ORDERS, order.getId(), null);
            initOrder.setStatus(Status.Fulfilled);

            t.update(ODataEndpointsNames.ORDERS, initOrder.getId().toString(), initOrder);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.succesful_order_pick_up), Toast.LENGTH_SHORT).show();
            restartActivity();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_order_pick_up), Toast.LENGTH_SHORT).show();
        }
    }

    private void InsertToLayout(List<Order> orderList) {
        if(orderList==null || orderList.size() == 0){
            noOrdersText.setVisibility(View.VISIBLE);
            return;
        }

        Collections.reverse(orderList);

        for (Order order : orderList) {
            TextView textView = new TextView(this);

            textView.setText(order.toString());
            textView.setTag(order.getId().toString());

            setDesign(textView, order.getStatus());

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String orderId = textView.getTag().toString();
                    String title = getString(R.string.order_title);
                    String description = makeFullDescription(order);

                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerHomeActivity.this);
                    builder.setTitle(title);
                    builder.setMessage(description);

                    if(order.getStatus().equals(Status.Delivered))
                    {
                        builder.setPositiveButton(getResources().getString(R.string.pick_up) + " \u2705", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fulfillOrder(order);
                            }
                        });
                    }
                    builder.setNeutralButton(getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            layout.addView(textView);
        }
    }

    private String makeFullDescription(Order order) {
        return getResources().getString(R.string.about_order) + ": " +
                order.toString() + "\n\n" +
                getResources().getString(R.string.order_status) + " " + order.getStatus();
    }

    private void setDesign(TextView textView, Status oderStatus)
    {
        textView.setBackgroundResource(getOrderColor(oderStatus));
        textView.setPadding(20, 10, 20, 10);
        Typeface font = ResourcesCompat.getFont(this, R.font.comfortaa);
        textView.setTypeface(font);
        textView.setTextSize(22);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 20, 20, 0);
        textView.setLayoutParams(params);
        textView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int radius = 15;
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
            }
        });
        textView.setClipToOutline(true);
    }

    private int getOrderColor(Status orderStatus)
    {
        return orderStatus==Status.Delivered ? R.color.purple_200 : R.color.ivory;
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}