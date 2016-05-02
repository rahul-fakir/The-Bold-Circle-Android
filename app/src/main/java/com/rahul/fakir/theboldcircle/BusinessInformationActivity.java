package com.rahul.fakir.theboldcircle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BusinessInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_information);
        final Button btnLocateNearestStore = (Button) findViewById(R.id.btnLocateNearestStore);
        assert btnLocateNearestStore != null;
        btnLocateNearestStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusinessInformationActivity.this, StoreLocationActivity.class);
                startActivity(intent);
            }
        });
    }
}
