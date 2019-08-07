package kupers800.trainingslog.mainlogcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.trainingslog.mainlogcode.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kupers800 helped by code of tutlane on 05-01-2018.
 */

public class DetailsActivity extends AppCompatActivity {
    Intent intent;
    Boolean LongPressing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        final DbHandler db = new DbHandler(this);
        ArrayList<HashMap<String, String>>tempList = db.GetUsers();
        //reverse order
        final ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        for (int i = tempList.size()-1; i>=0; i--){
           userList.add(tempList.get(i));
        }
        ListView lv = (ListView) findViewById(R.id.user_list);
        ListAdapter adapter = new SimpleAdapter(DetailsActivity.this, userList, R.layout.list_row,new String[]{"name","location","date"}, new int[]{R.id.name, R.id.training, R.id.date});
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!LongPressing) {
                    int id_tracker = Integer.valueOf(userList.get(position).get("id"));
                    Log.d("ID_TRACKER", String.valueOf(id_tracker));
                    intent = new Intent(DetailsActivity.this, FurtherDetails.class);
                    intent.putExtra("id_tracker", id_tracker);
                    startActivity(intent);
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LongPressing = true;
                onDeleting(db, userList, position);
                Log.d("DELETED", "after delete" );
                return false;
            }
        });





        Button back = (Button)findViewById(R.id.btnBack2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //intent = new Intent(DetailsActivity.this,MainActivity.class);
                //startActivity(intent);
            }
        });
    }

    public void onDeleting(final DbHandler db, final ArrayList<HashMap<String,String>> userList, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);        builder.setTitle("Delete or Not");
        builder.setMessage("Do you want to delete this? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast toast = Toast.makeText(getApplicationContext(), "deleted: "+ String.valueOf(userList.get(position).get("name")), Toast.LENGTH_SHORT);
                toast.show();
                Log.d("USERLIST", String.valueOf(userList.get(position).get("id")));
                db.DeleteUser(Integer.valueOf(userList.get(position).get("id")));
                intent = new Intent(DetailsActivity.this, DetailsActivity.class);
                startActivity(intent);
                DetailsActivity.this.finish();
                LongPressing = false;
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                LongPressing = false;
                //do nothing
            }
        });
        builder.show();
    }
}