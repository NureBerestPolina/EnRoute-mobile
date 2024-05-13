package com.example.enroute.ManagementActivities;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enroute.Constants.ODataEndpointsNames;
import com.example.enroute.Constants.Status;
import com.example.enroute.CustomerActivities.CustomerHomeActivity;
import com.example.enroute.CustomerActivities.MakeOrderActivity;
import com.example.enroute.MainActivity;
import com.example.enroute.Models.Order;
import com.example.enroute.Models.Organization;
import com.example.enroute.R;
import com.example.enroute.Services.AuthService;
import com.example.enroute.Services.ODataQueryBuilder;
import com.example.enroute.Services.ODataService;
import com.example.enroute.Services.OrganizationsService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ShopManagerHomeActivity extends AppCompatActivity {

    private ImageView logoutBtn;
    private TextView noDeliveriesNeededText;
    Organization organization;
    List<Order> deliveryList;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_manager_home);

        logoutBtn = (ImageView) findViewById(R.id.logout_btn);

        noDeliveriesNeededText = (TextView) findViewById(R.id.no_deliveries_needed);
        layout = (LinearLayout) findViewById(R.id.deliveries_layout);

        outputDeliveries(AuthService.getInstance(ShopManagerHomeActivity.this).getUser().getId());

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthService.getInstance(ShopManagerHomeActivity.this).logout();
                Intent main = new Intent(ShopManagerHomeActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }

    private void outputDeliveries(UUID managerId) {
        deliveryList = getOrganizationDeliveries(managerId);

        InsertToLayout(deliveryList);
    }

    private List<Order> getOrganizationDeliveries(UUID managerId) {
        OrganizationsService organizationsService = new OrganizationsService(ShopManagerHomeActivity.this);
        organization = organizationsService.getOrganizationByManagerId(managerId);

        ODataService<Order> t = new ODataService<Order>(Order.class, this);

        ODataQueryBuilder builder = new ODataQueryBuilder();

        builder.expand("items($expand=GoodOrdered), AssignedCell($expand=Counter)");

        List<Order> res;
        try {
            res = t.getAll(ODataEndpointsNames.ORDERS, builder);
        } catch (IOException e) {
            res = null;
        }

        return res;
    }

    private void deliverOrder(Order order) {
        ODataService<Order> t = new ODataService<Order>(Order.class, this);

        try {
            Order initOrder = t.getById(ODataEndpointsNames.ORDERS, order.getId(), null);
            initOrder.setStatus(Status.Delivered);

            t.update(ODataEndpointsNames.ORDERS, initOrder.getId().toString(), initOrder);
            restartActivity();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.universal_fail), Toast.LENGTH_SHORT).show();
        }
    }

    private void InsertToLayout(List<Order> deliveryList) {
        if(deliveryList==null || deliveryList.size() == 0){
            noDeliveriesNeededText.setVisibility(View.VISIBLE);
            return;
        }

        Collections.reverse(deliveryList);

        for (Order order : deliveryList) {
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(ShopManagerHomeActivity.this);
                    builder.setTitle(title);
                    builder.setMessage(description);

                    if(order.getStatus().equals(Status.New))
                    {
                        builder.setPositiveButton(getResources().getString(R.string.approve) + " \u2705", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deliverOrder(order);
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
        return getResources().getString(R.string.order_title) + ": " +
                order.toString() + "\n\n" +
                getResources().getString(R.string.order_status) + " " + order.getStatus() + "\n\n" +
                getResources().getString(R.string.counter_placement) + " " +
                order.getAssignedCell().getCounter().getAddress() + " (" +
                order.getAssignedCell().getCounter().getPlacementDescription() + ")" + "\n\n" +
                getResources().getString(R.string.open_key) + " " + order.getAssignedCell().getCellOpenKey();
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
        return orderStatus==Status.New ? R.color.green : R.color.ivory;
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}