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
import android.widget.Button;
import android.widget.DatePicker;
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

/**
 * Created by intex on 12/1/2015.
 */
public class DueEmiFilterFragment extends Fragment implements View.OnClickListener {
    Button emiFromDateBtn, emiToDateBtn, emiGetDetailBtn;
    TextView emiFromDateTv, emiToDateTv;
    Spinner emiSiteSpin;
    DateDialogFragment datepicker;
    String emiSiteName, emiSiteId, emiFromDate, emiToDate;
    static int DATE_DIALOG_ID;
    ArrayList<ModelClass> emiSite_list = new ArrayList<ModelClass>();
    ArrayList<String> emiSiteName_list = new ArrayList<String>();
    ArrayList<String> emiSiteId_list = new ArrayList<String>();
    PbFilterSiteAdapter emiSiteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.due_emi_filter_fragment, container, false);

        emiFromDateBtn = (Button) view.findViewById(R.id.dueEmiFilter_FromDateBtn);
        emiToDateBtn = (Button) view.findViewById(R.id.dueEmiFilter_ToDateBtn);
        emiGetDetailBtn = (Button) view.findViewById(R.id.dueEmiFilter_getDetailBtn);

        emiFromDateTv = (TextView) view.findViewById(R.id.dueEmiFilter_FromDateTv);
        emiToDateTv = (TextView) view.findViewById(R.id.dueEmiFilter_ToDateTv);

        emiSiteSpin = (Spinner) view.findViewById(R.id.dueEmifilter_SiteSpinner);

        emiFromDateBtn.setOnClickListener(this);
        emiToDateBtn.setOnClickListener(this);
        emiGetDetailBtn.setOnClickListener(this);

        new EmiSiteSpinnerTask().execute();


        emiSiteSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelClass selectedItem1 = (ModelClass) parent.getItemAtPosition(position);
                emiSiteName = selectedItem1.getSiteName();
                emiSiteId = selectedItem1.getSiteId();
                System.out.println(" Site Name:" + emiSiteName + " Site Id:" + emiSiteId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.dueEmiFilter_FromDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 0;
                datepicker.show(getFragmentManager(), "showDate");

                break;

            case R.id.dueEmiFilter_ToDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 1;
                datepicker.show(getFragmentManager(), "showDate");
                break;
            case R.id.dueEmiFilter_getDetailBtn:
                getEmiReport();
                break;

        }


    }

    public void getEmiReport() {
        emiFromDate = emiFromDateTv.getText().toString().trim();
        emiToDate = emiToDateTv.getText().toString().trim();

        if ((emiFromDate.length() == 0)) {
            emiFromDate = "-1";
        }
        if ((emiToDate.length() == 0)) {
            emiToDate = "-1";
        }

        Bundle bundle = new Bundle();
        bundle.putString("emiFromDate", emiFromDate);
        bundle.putString("emiToDate", emiToDate);
        bundle.putString("emiSiteId", emiSiteId);

        Fragment fragment = new DueEmiFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_content_fragment, fragment,"Due Emi").addToBackStack("Due Emi")
                .commit();
    }

    @SuppressLint("ValidFragment")
    class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public DateDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            showSetDate(year, monthOfYear, dayOfMonth);
        }

    }

    public void showSetDate(int year, int month, int day) {
        if (DATE_DIALOG_ID == 0)
            emiFromDateTv.setText(day + "-" + (month + 1) + "-" + year);
        if (DATE_DIALOG_ID == 1)
            emiToDateTv.setText(day + "-" + (month + 1) + "-" + year);
    }

    class EmiSiteSpinnerTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            emiSiteName_list.clear();
            emiSiteId_list.clear();
            emiSite_list.clear();
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/GetSiteList");

                HttpResponse httpResp = client.execute(post);
                HttpEntity entity = httpResp.getEntity();
                String response = EntityUtils.toString(entity);
                Log.v("site dropdown resp :", response);
                JSONArray branchArray = new JSONArray(response);
                for (int i = 0; i < branchArray.length(); i++) {

                    JSONObject branchObj = branchArray.getJSONObject(i);

                    emiSiteName = branchObj.getString("SiteName");
                    emiSiteId = branchObj.getString("SiteID");
                    emiSiteName_list.add(emiSiteName);
                    emiSiteId_list.add(emiSiteId);

                    ModelClass m = new ModelClass();
                    m.setSiteName(emiSiteName_list.get(i));
                    m.setSiteId(emiSiteId_list.get(i));
                    emiSite_list.add(m);
                }

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                emiSiteAdapter = new PbFilterSiteAdapter(getActivity(), R.layout.branch_spinner_row, emiSite_list);
                emiSiteSpin.setAdapter(emiSiteAdapter);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Server is Failed ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
