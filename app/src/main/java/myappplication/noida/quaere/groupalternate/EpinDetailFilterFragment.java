package myappplication.noida.quaere.groupalternate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by intex on 12/1/2015.
 */
public class EpinDetailFilterFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    Spinner epinStatusSpin,epinDesignationSpin;
    EditText epinNoEt;
    Button epinFromDateBtn,epinToDateBtn,epinSubmitBtn;
    TextView epinFromDateTv,epinToDateTv;
    ArrayList<String> designationId_list = new ArrayList<String>();
    ArrayList<String> designationName_list = new ArrayList<String>();
    ArrayList<ModelClass> designation_list = new ArrayList<ModelClass>();
    List<String> epin_status_list = new ArrayList<String>();
    ArrayAdapter epinStatusAdapter;
    String designationName,designationId,statusName,epinNO,epinFromDate,epinToDate;
    DesignationAdapter designationAdapter;
    DateDialogFragment datepicker;
    static  int DATE_DIALOG_ID ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.epin_detail_filter, container, false);
        epinStatusSpin = (Spinner) view.findViewById(R.id.pinDetailFilter_StatusSpinner);
        epinDesignationSpin = (Spinner) view.findViewById(R.id.pinDetailFilter_DesignationSpinner);

        epinNoEt = (EditText) view.findViewById(R.id.pinDetailFilter_EpinNoEt);
        epinFromDateTv = (TextView) view.findViewById(R.id.pinDetailFilter_FromDateTv);
        epinToDateTv = (TextView) view.findViewById(R.id.pinDetailFilter_ToDateTv);

        epinFromDateBtn = (Button) view.findViewById(R.id.pinDetailFilter_FromDateBtn);
        epinToDateBtn = (Button) view.findViewById(R.id.pinDetailFilter_ToDateBtn);
        epinSubmitBtn = (Button) view.findViewById(R.id.pinDetailFilter_getDetailBtn);

        epinStatusSpin.setOnItemSelectedListener(this);
        epinDesignationSpin.setOnItemSelectedListener(this);

        epinFromDateBtn.setOnClickListener(this);
        epinToDateBtn.setOnClickListener(this);
        epinSubmitBtn.setOnClickListener(this);


        new DesignationSpinnerTask().execute();
        //Plot Status Dropdown implementation
        epin_status_list.clear();
        epin_status_list.add("Unused");
        epin_status_list.add("Used");
        epinStatusAdapter = new ArrayAdapter<String>(getActivity(), R.layout.branch_spinner_row, epin_status_list);
        epinStatusSpin.setAdapter(epinStatusAdapter);



        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.pinDetailFilter_FromDateBtn:
                datepicker=new DateDialogFragment();
                DATE_DIALOG_ID = 1;
                datepicker.show(getFragmentManager(), "showDate");

                break;

            case R.id.pinDetailFilter_ToDateBtn:
                datepicker=new DateDialogFragment();
                DATE_DIALOG_ID = 0;
                datepicker.show(getFragmentManager(), "showDate");
                break;

            case R.id.pinDetailFilter_getDetailBtn:
                getEpinDetail();
                break;

        }


    }

    public void getEpinDetail() {
        epinNO = epinNoEt.getText().toString().trim();
        epinFromDate = epinFromDateTv.getText().toString().trim();
        epinToDate = epinToDateTv.getText().toString().trim();

        if(epinNO.length()==0){
            epinNO = "-1";

        }
        if(epinFromDate.length()==0){
            epinFromDate = "-1";
        }
        if((epinToDate.length()==0)){
            epinToDate = "-1";
        }
       // System.out.println("  statusName:"+ statusName + " designationName:"+ designationId + " epinNO:" + epinNO + " epinFromDate:" + epinFromDate + " epinToDate :" + epinToDate);



        Bundle bundle = new Bundle();
        bundle.putString("statusName", statusName);
        bundle.putString("designationId", designationId);
        bundle.putString("epinNO", epinNO);
        bundle.putString("epinFromDate", epinFromDate);
        bundle.putString("epinToDate", epinToDate);


          Fragment fragment = new MyEpinFragment();
          FragmentManager fragmentManager = getFragmentManager();
         fragment.setArguments(bundle);
          fragmentManager.beginTransaction()
                  .replace(R.id.activity_main_content_fragment, fragment, "E-pin").addToBackStack("E-pin")
                  .commit();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {

            case R.id.pinDetailFilter_StatusSpinner:
                statusName =  epinStatusSpin.getItemAtPosition(position).toString();
                break;

            case R.id.pinDetailFilter_DesignationSpinner:
                ModelClass selectedItem1 = (ModelClass) parent.getItemAtPosition(position);
                designationName = selectedItem1.getDesignationName();
                designationId = selectedItem1.getDesignationId();

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // **************************************** Get Designation List from server
    class DesignationSpinnerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            designationName_list.clear();
            designationId_list.clear();
            designation_list.clear();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/shinepanel.svc/GetDesignation");


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v(" Designation response:", response);

                JSONArray designationArray = new JSONArray(response);
                for (int i = 0; i < designationArray.length(); i++) {

                    JSONObject designationObj = designationArray.getJSONObject(i);
                    designationName = designationObj.getString("DesignationName");
                    designationId = designationObj.getString("DesignationID");

                    designationName_list.add(designationName);
                    designationId_list.add(designationId);

                    ModelClass m = new ModelClass();
                    m.setDesignationName(designationName_list.get(i));
                    m.setDesignationId(designationId_list.get(i));
                    designation_list.add(m);
                }
            } catch (Exception e) {
                Log.v("Excepton in Designation", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                designationAdapter = new DesignationAdapter(getActivity(), R.layout.branch_spinner_row, designation_list);
                epinDesignationSpin.setAdapter(designationAdapter);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Server is Failed ", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("ValidFragment")
    class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        public DateDialogFragment()
        {
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            showSetDate(year,monthOfYear,dayOfMonth);
        }

    }

    public void showSetDate(int year,int month,int day) {
        if(DATE_DIALOG_ID==1)
            epinFromDateTv.setText(day+"-"+(month+1)+"-"+year);
        else
            epinToDateTv.setText(day+"-"+(month+1)+"-"+year);
    }
}
