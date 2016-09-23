package myappplication.noida.quaere.groupalternate.mainpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import myappplication.noida.quaere.groupalternate.ModelClass;
import myappplication.noida.quaere.groupalternate.R;

/**
 * Created by intex on 3/18/2016.
 */
public class FeedbackActivity extends AppCompatActivity{
    EditText nameEt, addressEt, cityEt, mobileEt, emailEt, feedbackEt;
    Spinner countrySpin, stateSpin;
    Button submit;
    String stateName, stateId;
    ArrayList<String> countryName_list = new ArrayList<>();
    ArrayList<String> countryId_list = new ArrayList<>();
    ArrayList<String> stateName_list = new ArrayList<>();
    ArrayList<String> stateId_list = new ArrayList<>();
    CountrySpinnerTask countrySpinnerTask;
    StateAsyncTask stateAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);

        nameEt = (EditText) findViewById(R.id.feedback_nameEt);
        addressEt = (EditText) findViewById(R.id.feedback_addressEt);
        cityEt = (EditText) findViewById(R.id.feedback_cityEt);
        mobileEt = (EditText) findViewById(R.id.feedback_mobileEt);
        emailEt = (EditText) findViewById(R.id.feedback_emailEt);
        feedbackEt = (EditText) findViewById(R.id.feedback_feedbackEt);
        countrySpin = (Spinner) findViewById(R.id.feedback_countrySpin);
        stateSpin = (Spinner) findViewById(R.id.feedback_stateSpin);
        submit = (Button) findViewById(R.id.feedback_submitBtn);
        findViewById(R.id.feedback_backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        countrySpinnerTask = new CountrySpinnerTask(this, countryName_list, countryId_list, countrySpin);
        countrySpinnerTask.execute();
        stateAsyncTask = new StateAsyncTask(FeedbackActivity.this, stateName_list, stateId_list, stateSpin);
        stateAsyncTask.execute();
        countrySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stateSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelClass selectedItem3 = (ModelClass) parent.getItemAtPosition(position);
                stateName = selectedItem3.getStateName();
                stateId = selectedItem3.getStateId();
                //  Toast.makeText(getApplicationContext(), "stateId  " + stateId, Toast.LENGTH_SHORT).show();
                //getCity(stateId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
