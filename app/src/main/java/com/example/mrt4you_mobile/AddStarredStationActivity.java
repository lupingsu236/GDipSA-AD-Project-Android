package com.example.mrt4you_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddStarredStationActivity extends BaseActivity {
    
    private Spinner linespinner;
    private Spinner stationspinner;
    private Button confirnBtn;
    private EditText starname;

    String[] lines;
    String[] NL;
    String[] CL;
    String[] EL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        linespinner = findViewById(R.id.linespinner);
        stationspinner = findViewById(R.id.stationspinner);
        starname = findViewById(R.id.starname);

        lines=new String[]{"East-West Line","Circle Line","North-South Line"};
        NL=new String[]{"NS1 Jurong East","NS2 Bukit Batok","NS3 Bukit Gombak","NS4 Choa Chu Kang",
                "NS5 Yew Tee","NS7 Kranji","NS8 Marsiling","NS9 Woodlands","NS10 Admiralty","NS11 Sembawang",
                "NS12 Canberra","NS13 Yishun","NS14 Khatib","NS15 Yio Chu Kang","NS16 Ang Mo Kio","NS17 Bishan",
                "NS18 Brddell","NS19 Toa Payoh","NS20 Novena","NS21 Newton","NS22 Orchard","NS23 Somerset",
                "NS24 Dhoby Ghaut","NS25 City Hall","NS26 Raffles Place","NS27 Marina Bay","NS28 Marina South Pier"};
        CL=new String[]{"CC1 Dhoby Ghaut","CC2 Bras Basah","CC3 Esplanade","CC4 Promenade","CC5 Nicoll Highway",
                "CC6 Stadium","CC7 Mountbatten","CC8 Dakota","CC9 Paya Lebar","CC10 MacPherson","CC11 Tai Seng",
                "CC12 Bartley","CC13 Serangoon","CC14 Lorong Chuan","CC15 Bishan","CC16 Marymount","CC17 Caldecott",
                "CC18 Bukit Brown","CC19 Botanic Gardens","CC20 Farrer Road","CC21 Holland Village","CC22 Buona Vista",
                "CC23 one-north","CC24 Kent Ridge","CC25 Haw Par Villa","CC26 Pasir Panjang","CC27 Labrador Park",
                "CC28 Telok Blangah","CC29 HarbourFront"};
        EL=new String[]{"EW1 Pasir Ris","EW2 Tampines","EW3 Simei","EW4 Tanah Merah","EW5 Bedok","EW6 Kembangan",
                "EW7 Eunos","EW8 Paya Lebar","EW9 Aljunied","EW10 Kallang","EW11 Lavender","EW12 Bugis","EW13 City Hall",
                "EW14 Raffles Place","EW15 Tanjong Pagar","EW16 Outram Park","EW17 Tiong Bahru","EW18 Redhill","EW19 Queenstown",
                "EW20 Commonwealth","EW21 Buona Vista","EW22 Dover","EW23 Clementi","EW24 Jurong East","EW25 Chinese Garden",
                "EW26 Lakeside","EW27 Boon Lay","EW28 Pioneer","EW29 Joo Koon","EW30 Gul Circle","EW31 Tuas Crescent",
                "EW32 Tuas West Road","EW33 Tuas Link"};



        ArrayAdapter<String> linesAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lines);
        linesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        linespinner.setAdapter(linesAdapter);

        linespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> stationsAdapter = null;
                if (linespinner.getSelectedItem().equals("East-West Line")) {
                    stationsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, EL);
                } else if (linespinner.getSelectedItem().equals("Circle Line")) {
                    stationsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, CL);
                } else {
                    stationsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, NL);
                }
                if (stationsAdapter != null) {
                    stationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    stationspinner.setAdapter(stationsAdapter);
                    stationsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                    setResult(RESULT_OK,intent);
                    finish();
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