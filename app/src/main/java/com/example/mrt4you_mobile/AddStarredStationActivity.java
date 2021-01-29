package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddStarredStationActivity extends BaseActivity {

    private List<String> redline = new ArrayList<>();
    private Spinner linespinner;
    private Spinner stationspinner;
    private Button confirnBtn;
    private EditText starname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        linespinner = findViewById(R.id.linespinner);
        stationspinner = findViewById(R.id.stationspinner);
        starname=findViewById(R.id.starname);

        confirnBtn=findViewById(R.id.confirmbtn);
        confirnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(starname.getText().toString().equals("")){
                    Toast.makeText(AddStarredStationActivity.this,"Please complete the information!",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(AddStarredStationActivity.this, StarredStationsActivity.class);
                    intent.putExtra("linename", linespinner.getSelectedItem().toString());
                    intent.putExtra("stationname", stationspinner.getSelectedItem().toString());
                    intent.putExtra("starname", starname.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_add_starred_station;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_starred;
    }
}