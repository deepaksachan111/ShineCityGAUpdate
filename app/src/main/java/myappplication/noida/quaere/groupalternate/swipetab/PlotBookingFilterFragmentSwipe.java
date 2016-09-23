package myappplication.noida.quaere.groupalternate.swipetab;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import myappplication.noida.quaere.groupalternate.ModelClass;
import myappplication.noida.quaere.groupalternate.PbFilterSiteAdapter;
import myappplication.noida.quaere.groupalternate.PlotBookingStatusFragment;
import myappplication.noida.quaere.groupalternate.R;

/**
 * Created by intex on 9/22/2016.
 */
public class PlotBookingFilterFragmentSwipe extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button submit;
    Spinner siteSpin, payStatusSpin, plotStatusSpin;
    EditText plotNoEt, plotHolderIdEt;
    TextView fromDateTv, fromDateCalendarTv, toDateTv, toDateCalendarTv;
    String siteName, siteId,payStatus,plotStatus,userId,plotNo,plotHolderId,fromDate,toDate;
    static  int DATE_DIALOG_ID ;
    int pYear, pMonth, pDay;
    DateFormat df;
    ArrayList<ModelClass> site_list = new ArrayList<ModelClass>();
    ArrayList<String> siteName_list = new ArrayList<String>();
    ArrayList<String> siteId_list = new ArrayList<String>();
    List<String> payment_status_list = new ArrayList<String>();
    List<String> plot_status_list = new ArrayList<String>();

    PbFilterSiteAdapter pbfilterSiteAdapter;
    ArrayAdapter plostStatusAdapter,paymentStatusAdapter;
    DateDialogFragment datepicker;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.plot_booking_filter, container, false);
        siteSpin = (Spinner) view.findViewById(R.id.pbfilter_site_spinner);
        payStatusSpin = (Spinner) view.findViewById(R.id.pbfilter_payStatus_spinner);
        plotStatusSpin = (Spinner) view.findViewById(R.id.pbfilter_plotStatus_spinner);

        plotNoEt = (EditText) view.findViewById(R.id.pbfilter_plotNoEt);
        plotHolderIdEt = (EditText) view.findViewById(R.id.pbfilter_plotHolderEt);

        fromDateTv = (TextView) view.findViewById(R.id.pbfilter_fromDateTv);
        fromDateCalendarTv = (TextView) view.findViewById(R.id.pbfilter_fromDateCalendar);
        toDateTv = (TextView) view.findViewById(R.id.pbfilter_toDateTv);
        toDateCalendarTv = (TextView) view.findViewById(R.id.pbfilter_toDateCalendar);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email", "");

        submit = (Button) view.findViewById(R.id.pbfilter_submitBtn);

        fromDateCalendarTv.setOnClickListener(this);
        toDateCalendarTv.setOnClickListener(this);
        submit.setOnClickListener(this);

        new SiteSpinnerTask().execute();
        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyy");
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        siteSpin.setOnItemSelectedListener(this);
        payStatusSpin.setOnItemSelectedListener(this);

        plotStatusSpin.setOnItemSelectedListener(this);

        //Plot Status Dropdown implementation
        plot_status_list.clear();
        plot_status_list.add("All");
        plot_status_list.add("Booked");
        plot_status_list.add("TempBooked");
        plot_status_list.add("Alloted");
        plot_status_list.add("Registered");
        plot_status_list.add("Cancelled");
        plostStatusAdapter = new ArrayAdapter<String>(getActivity(), R.layout.branch_spinner_row, plot_status_list);
        //  fNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plotStatusSpin.setAdapter(plostStatusAdapter);

        //Plot Status Dropdown implementation
        payment_status_list.clear();
        payment_status_list.add("All");
        payment_status_list.add("Clear");
        payment_status_list.add("Pending");
        payment_status_list.add("Decline");
        paymentStatusAdapter = new ArrayAdapter<String>(getActivity(), R.layout.branch_spinner_row, payment_status_list);
        //  fNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payStatusSpin.setAdapter(paymentStatusAdapter);



        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.pbfilter_fromDateCalendar:
                datepicker=new DateDialogFragment();
                DATE_DIALOG_ID = 1;
                datepicker.show(getFragmentManager(), "showDate");

                break;

            case R.id.pbfilter_toDateCalendar:
                datepicker=new DateDialogFragment();
                DATE_DIALOG_ID = 0;
                datepicker.show(getFragmentManager(), "showDate");
                break;

            case R.id.pbfilter_submitBtn:
                submitMethod();
                break;

        }

    }

    public void submitMethod() {
        plotNo = plotNoEt.getText().toString().trim();
        plotHolderId = plotHolderIdEt.getText().toString().trim();
        fromDate = fromDateTv.getText().toString().trim();
        toDate = toDateTv.getText().toString();
        if(plotNo.length()==0){
            plotNo = "-1";

        }
        if(plotHolderId.length()==0){
            plotHolderId = "-1";
        }
        if((fromDate.length()==0)){
            fromDate = "-1";
        }
        if(toDate.length()==0){
            toDate = "-1";
        }
        // System.out.println("userId :" + userId + "  siteId :" + siteId + " plotNo :" + plotNo + " plotHolderId :" + plotHolderId + " fromDate :" + fromDate + " toDate  :" + toDate+ " plotStatus  :"+plotStatus+ " payStatus  :"+payStatus);
        // pbFilterSubmitUrl = "http://demo8.mlmsoftindia.com/ShinePanel.svc/PlotStatusReport/"+userId+"/"+siteId+"/"+plotNo+"/"+plotHolderId+"/"+fromDate+"/"+toDate+"/"+plotStatus+"/"+payStatus+"/-1";
        Bundle bundle = new Bundle();
        bundle.putString("siteId", siteId);
        bundle.putString("plotNo", plotNo);
        bundle.putString("plotHolderId", plotHolderId);
        bundle.putString("fromDate", fromDate);
        bundle.putString("toDate", toDate);
        bundle.putString("plotStatus", plotStatus);
        bundle.putString("payStatus", payStatus);

        Fragment fragment = new PlotBookingStatusFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_content_fragment, fragment,"Plot Booking").addToBackStack("Plot Booking")
                .commit();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {

            case R.id.pbfilter_site_spinner:
                ModelClass selectedItem1 = (ModelClass) parent.getItemAtPosition(position);
                siteName = selectedItem1.getSiteName();
                siteId = selectedItem1.getSiteId();
                System.out.println(" Site Name :"+siteName + " Site Id :"+siteId);
                break;

            case R.id.pbfilter_payStatus_spinner:
                payStatus = payStatusSpin.getItemAtPosition(position).toString();
                System.out.println(" payStatus Name :"+payStatus);
                break;

            case R.id.pbfilter_plotStatus_spinner:
                plotStatus = plotStatusSpin.getItemAtPosition(position).toString();
                System.out.println(" plotStatus Name :"+plotStatus );
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class SiteSpinnerTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            siteName_list.clear();
            siteId_list.clear();
            site_list.clear();
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

                    siteName = branchObj.getString("SiteName");
                    siteId = branchObj.getString("SiteID");
                    siteName_list.add(siteName);
                    siteId_list.add(siteId);

                    ModelClass m = new ModelClass();
                    m.setSiteName(siteName_list.get(i));
                    m.setSiteId(siteId_list.get(i));
                    site_list.add(m);
                }

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                pbfilterSiteAdapter = new PbFilterSiteAdapter(getActivity(), R.layout.branch_spinner_row, site_list);
                siteSpin.setAdapter(pbfilterSiteAdapter);
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
            fromDateTv.setText(day+"-"+(month+1)+"-"+year);
        else
            toDateTv.setText(day+"-"+(month+1)+"-"+year);
    }
}
