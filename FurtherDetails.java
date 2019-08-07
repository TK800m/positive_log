package kupers800.trainingslog.mainlogcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.trainingslog.mainlogcode.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FurtherDetails extends AppCompatActivity {
        Intent intent;
        TextView date, good, improve, training, goodLabel, improveLabel, trainingLabel;
        Button back;
        @Override
        protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.further_details);
        date = (TextView)findViewById(R.id.dateTxt);
        good = (TextView)findViewById(R.id.whatTxt);
        improve = (TextView)findViewById(R.id.improveTxt);
        training = (TextView)findViewById(R.id.trainingTxt);
        goodLabel = (TextView)findViewById(R.id.goodLabelTxt);
        improveLabel = (TextView)findViewById(R.id.improveLabelTxt);
        trainingLabel = (TextView)findViewById(R.id.trainingLabelTxt);
        back = (Button)findViewById(R.id.btnBack2);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            int data = bundle.getInt("id_tracker");
            final DbHandler db = new DbHandler(this);
            ArrayList<HashMap<String, String>> userData= db.GetUserByUserId(data);
            date.setText(userData.get(0).get("date"));
            good.setText(userData.get(0).get("name"));
            improve.setText(userData.get(0).get("location"));
            training.setText(userData.get(0).get("designation"));
            goodLabel.setText(userData.get(0).get("labelOne"));
            improveLabel.setText(userData.get(0).get("labelTwo"));
            trainingLabel.setText(userData.get(0).get("labelThree"));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
}
