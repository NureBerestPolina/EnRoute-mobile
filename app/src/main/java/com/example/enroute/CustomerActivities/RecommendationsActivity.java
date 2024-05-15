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
import com.example.enroute.ManagementActivities.ShopManagerHomeActivity;
import com.example.enroute.Models.Good;
import com.example.enroute.Models.Order;
import com.example.enroute.Models.Organization;
import com.example.enroute.R;
import com.example.enroute.Services.AuthService;
import com.example.enroute.Services.ODataQueryBuilder;
import com.example.enroute.Services.ODataService;
import com.example.enroute.Services.OrderService;
import com.example.enroute.Services.OrganizationsService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RecommendationsActivity extends AppCompatActivity {

    private ImageView logoutBtn;
    private TextView noRecommendationsText;
    List<Good> recommendationsList;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        logoutBtn = (ImageView) findViewById(R.id.logout_btn);

        noRecommendationsText = (TextView) findViewById(R.id.no_recommendations);
        layout = (LinearLayout) findViewById(R.id.recommendations_layout);

        outputRecommendations(AuthService.getInstance(RecommendationsActivity.this).getUser().getId());

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthService.getInstance(RecommendationsActivity.this).logout();
                Intent main = new Intent(RecommendationsActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }

    private void outputRecommendations(UUID userId) {
        recommendationsList = getUserRecommendations(userId);

        InsertToLayout(recommendationsList);
    }

    private List<Good> getUserRecommendations(UUID userId) {
        OrderService orderService = new OrderService(RecommendationsActivity.this);
        List<Good> res;

        try {
            res = orderService.getOrderRecommendations(userId);
        } catch (IOException e) {
            res = null;
        }

        return res;
    }

    private void makeOrder() {
        Intent makeOrder = new Intent(RecommendationsActivity.this, MakeOrderActivity.class);
        startActivity(makeOrder);
        finish();
    }

    private void InsertToLayout(List<Good> recommendationsList) {
        if(recommendationsList == null || recommendationsList.size() == 0){
            noRecommendationsText.setVisibility(View.VISIBLE);
            return;
        }

        for (Good good : recommendationsList) {
            TextView textView = new TextView(this);

            textView.setText("\uD83D\uDED2 " + good.toString());
            textView.setTag(good.getId().toString());

            setDesign(textView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeOrder();
                }
            });

            layout.addView(textView);
        }
    }

    private void setDesign(TextView textView)
    {
        textView.setBackgroundResource(R.color.pink);
        textView.setPadding(20, 10, 20, 10);
        Typeface font = ResourcesCompat.getFont(this, R.font.comfortaa);
        textView.setTypeface(font);
        textView.setTextSize(22);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 30, 20, 0);
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

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}