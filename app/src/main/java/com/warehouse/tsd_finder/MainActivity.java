package com.warehouse.tsd_finder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 30, 30, 30);

        TextView tv = new TextView(this);
        tv.setText("TSD Finder\nСервіс пошуку активний");
        tv.setTextSize(18);

        Button btn = new Button(this);
        btn.setText("Активувати сервіс");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startService(new Intent(MainActivity.this, UdpListenService.class));
            }
        });

        layout.addView(tv);
        layout.addView(btn);

        setContentView(layout);
    }
}
