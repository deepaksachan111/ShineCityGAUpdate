package myappplication.noida.quaere.groupalternate.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myappplication.noida.quaere.groupalternate.ModelClass;
import myappplication.noida.quaere.groupalternate.PlacementDetailsFragment;
import myappplication.noida.quaere.groupalternate.R;

/**
 * Created by intex on 3/22/2016.
 */
public class PlacementDetailsActivity extends AppCompatActivity {
    String userId, myDirectUrl, myDownLineUrl;
    String downline_response, selfBussiness, teamBusiness;
    TextView downlineTv, selfBznsTv, teamBznsTv;
    String memberName, joiningDate, loginId, mobileNo, pdResponceCode;//,detailNo;
    ListView p_detail_list;
    TextView pdEmptyTv;
    //List<String> detailNoArray = new ArrayList<String>();
    List<String> memeberNameArray = new ArrayList<String>();
    List<String> joiningDateArray = new ArrayList<String>();
    List<String> loginIdArray = new ArrayList<String>();
    List<String> mobileNoArray = new ArrayList<String>();
    public ArrayList<ModelClass> customListArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    AlertDialog alert;
    int noOfObjects;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placement_detail_activity);
        downlineTv = (TextView) findViewById(R.id.downlineTv);
        prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = prefs.getString("email", "");
        myDirectUrl = "http://demo8.mlmsoftindia.com/ShinePanel.svc/Direct/" + userId;
        myDownLineUrl = "http://demo8.mlmsoftindia.com/ShinePanel.svc/BussinessCalculation/" + userId;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //new DownLineAsyncTask().execute(myDownLineUrl);
          //  PlacementDetailsFragment.UseService productTask = new PlacementDetailsFragment().new UseService();
          //  productTask.execute(myDirectUrl);
            // myDownLine(myDownLineUrl);

            getSupportFragmentManager().beginTransaction().add(R.id.root_frame, new PlacementDetailsFragment()).commit();

        }
        findViewById(R.id.placementD_backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
