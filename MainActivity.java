package kupers800.trainingslog.mainlogcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trainingslog.mainlogcode.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText goodTxt, improveTxt, trainingTxt;
    Button saveBtn, dataBtn,
            label1, label21, label2, label22,
            label3, label23, label4, label24,
            label31, label32, label33,label34;
    Intent intent;
    int[] labelsSelect = {0,0,0};
    String[] labels = {"Technique", "Physical", "Mental", "Other"};
    String[] labelsTrain = {"Run", "Bike", "Strength", "Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goodTxt = (EditText) findViewById(R.id.txtGood);
        improveTxt = (EditText) findViewById(R.id.txtImprovements);
        trainingTxt = (EditText) findViewById(R.id.txtTraining);
        saveBtn = (Button) findViewById(R.id.btnSave);
        dataBtn = (Button) findViewById(R.id.btnToDetails);
        label1 = (Button) findViewById(R.id.label1);
        label21 = (Button) findViewById(R.id.label21);
        label31 = (Button) findViewById(R.id.label31);
        label2 = (Button) findViewById(R.id.label2);
        label22 = (Button) findViewById(R.id.label22);
        label32 = (Button) findViewById(R.id.label32);
        label3 = (Button) findViewById(R.id.label3);
        label23 = (Button) findViewById(R.id.label23);
        label33 = (Button) findViewById(R.id.label33);
        label4 = (Button) findViewById(R.id.label4);
        label24 = (Button) findViewById(R.id.label24);
        label34 = (Button) findViewById(R.id.label34);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                String date = simpleDateFormat.format(new Date());
                String good = goodTxt.getText().toString();
                String improvements = improveTxt.getText().toString();
                String training = trainingTxt.getText().toString();
                if (good.isEmpty() || improvements.isEmpty() || training.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Not all fields are filled correctly", Toast.LENGTH_SHORT).show();
                } else {
                    DbHandler dbHandler = new DbHandler(MainActivity.this);
                    dbHandler.insertUserDetails(good, improvements, training, date
                                                ,labels[labelsSelect[0]], labels[labelsSelect[1]]
                                                ,labelsTrain[labelsSelect[2]]);
                    intent = new Intent(MainActivity.this, DetailsActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Details Inserted Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
    }


    public void onFirstSetClicked(View v) {
        int count = 0;
        Button[] labels = {label1, label2, label3, label4};
        if (v.getId() == R.id.label1) {
            count = 0;
        } else if (v.getId() == R.id.label2) {
            count = 1;
        } else if (v.getId() == R.id.label3) {
            count = 2;
        } else if (v.getId() == R.id.label4) {
            count = 3;
        }
        setLabels(count, labels, 1);
    }

    public void onSecondSetClicked(View v) {
        int count = 0;
        Button[] labels = {label21, label22, label23, label24};
        if (v.getId() == R.id.label21) {
            count = 0;
        } else if (v.getId() == R.id.label22) {
            count = 1;
        } else if (v.getId() == R.id.label23) {
            count = 2;
        } else if (v.getId() == R.id.label24) {
            count = 3;
        }
        setLabels(count, labels,2);
    }

    public void onThirdSetClicked(View v) {
        int count = 0;
        Button[] labels = {label31, label32, label33, label34};
        if (v.getId() == R.id.label31) {
            count = 0;
        } else if (v.getId() == R.id.label32) {
            count = 1;
        } else if (v.getId() == R.id.label33) {
            count = 2;
        } else if (v.getId() == R.id.label34) {
            count = 3;
        }
        setLabels(count, labels,3);
    }

    public void setLabels(int count, Button[] labels, int labelIndex) {
        Log.d("COUNT", String.valueOf(count));
        labelsSelect[labelIndex-1] = count;
        for (int i = 0; i < labels.length; i++) {
            if (i == count) {
                labels[i].setBackgroundResource(R.drawable.rounded_button_green);
            } else {
                labels[i].setBackgroundResource(R.drawable.rounded_button_blue);
            }
        }
    }
}