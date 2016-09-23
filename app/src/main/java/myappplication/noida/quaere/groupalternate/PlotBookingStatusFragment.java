package myappplication.noida.quaere.groupalternate;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by intex on 8/31/2015.
 */
public class PlotBookingStatusFragment extends Fragment {

    String plotBookingSiteName, plotBookingPlotNo, plotBookingHolderId, plotBookingAmount, plotBookingStatus, plotBookingBalanceAmount, plotBookingHolderName, plotBookingMobile, pbResponceCode;

    List<String> plotBookingSiteNameArray = new ArrayList<String>();
    List<String> plotBookingPlotNoArray = new ArrayList<String>();
    List<String> plotBookingHolderIdArray = new ArrayList<String>();
    List<String> plotBookingAmountArray = new ArrayList<String>();
    List<String> plotBookingStatusArray = new ArrayList<String>();
    List<String> plotBookingBalanceAmountArray = new ArrayList<String>();
    List<String> plotBookingHolderNameArray = new ArrayList<String>();
    List<String> plotBookingMobileArray = new ArrayList<String>();

    int noOfObjects;
    public ArrayList<ModelClass> customPlotBookingArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    TextView pbEmptyTv;
    ListView plotBookingListView;
    String siteId, payStatus, plotStatus, userId, plotNo, plotHolderId, fromDate, toDate;
    String plot_booking_status_url = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.plot_booking_status_fragment, container, false);
        plotBookingListView = (ListView) view.findViewById(R.id.plot_booking_status_listView);
        pbEmptyTv = (TextView) view.findViewById(R.id.plot_booking_emptyTv);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email", "");

        siteId = this.getArguments().getString("siteId");
        plotNo = this.getArguments().getString("plotNo");
        plotHolderId = this.getArguments().getString("plotHolderId");
        payStatus = this.getArguments().getString("payStatus");
        plotStatus = this.getArguments().getString("plotStatus");
        fromDate = this.getArguments().getString("fromDate");
        toDate = this.getArguments().getString("toDate");

     //   System.out.println("userId :" + userId + "  siteId :" + siteId + " plotNo :" + plotNo + " plotHolderId :" + plotHolderId + " fromDate :" + fromDate + " toDate  :" + toDate + " plotStatus  :" + plotStatus + " payStatus  :" + payStatus);

        plot_booking_status_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/PlotStatusReport/" + userId + "/" + siteId + "/" + plotNo + "/" + plotHolderId + "/" + fromDate + "/" + toDate + "/" + plotStatus + "/" + payStatus + "/-1";

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            PlotBooking productTask = new PlotBooking();
            productTask.execute();

        }
       /* PlotBooking productTask = new PlotBooking();
        productTask.execute();*/
        return view;
    }

    public class PlotBooking extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Plot Details ...");
            pDialog.setIndeterminate(false);
            //    pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(plot_booking_status_url);


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Plot Booking Response:", response);
                // String response = jsonResponse;
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    noOfObjects = jsonArray.length();

                   // Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");

                    for (int i = 0; i < jsonArray.length() ; i++) {

                        JSONObject jobj = jsonArray.getJSONObject(i);


                        plotBookingSiteName = jobj.getString("SiteName");

                        plotBookingPlotNo = jobj.getString("PlotNo");

                        plotBookingHolderId = jobj.getString("PlotHolderID");

                        plotBookingAmount = jobj.getString("PlotAmount");

                        plotBookingStatus = jobj.getString("BookingStatus");

                        plotBookingBalanceAmount = jobj.getString("BalanceAmount");


                        plotBookingHolderName = jobj.getString("PlotHolderName");

                        plotBookingMobile = jobj.getString("Mobile");
                        pbResponceCode = jobj.getString("ResponceCode");
                        Log.v("Respnse Code",pbResponceCode);

                        //adding value to array
                        // cbSerialNoArray.add(cbSerialNo);
                        plotBookingSiteNameArray.add(plotBookingSiteName);
                        plotBookingPlotNoArray.add(plotBookingPlotNo);
                        plotBookingHolderIdArray.add(plotBookingHolderId);
                        plotBookingAmountArray.add(plotBookingAmount);
                        plotBookingStatusArray.add(plotBookingStatus);
                        plotBookingBalanceAmountArray.add(plotBookingBalanceAmount);
                        plotBookingHolderNameArray.add(plotBookingHolderName);
                        plotBookingMobileArray.add(plotBookingMobile);

                    }
                    for (int j = 0; j < plotBookingSiteNameArray.size(); j++) {
                        final ModelClass model = new ModelClass();

                        //take data into model object
                        // model.setCbSerialNo(cbSerialNoArray.get(j));
                        model.setPlotBookingSiteName(plotBookingSiteNameArray.get(j));
                        model.setPlotBookingPlotNo(plotBookingPlotNoArray.get(j));
                        model.setPlotBookingHolderId(plotBookingHolderIdArray.get(j));
                        model.setPlotBookingAmount(plotBookingAmountArray.get(j));
                        model.setPlotBookingStatus(plotBookingStatusArray.get(j));
                        model.setPlotBookingBalanceAmount(plotBookingBalanceAmountArray.get(j));
                        model.setPlotBookingHolderName(plotBookingHolderNameArray.get(j));
                        model.setPlotBookingMobile(plotBookingMobileArray.get(j));

                        customPlotBookingArray.add(model);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(" Exception is caught here ......." + e.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(" Exception is caught here ......." + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                if (pbResponceCode.equals("0")) {
                    plotBookingListView.setEmptyView(pbEmptyTv);
                    pDialog.dismiss();
                } else {

                    plotBookingListView.setAdapter(new PlotBookingStatusAdapter(getActivity(), customPlotBookingArray));
                    Toast toast = Toast.makeText(getContext(), "Total Records are : " + noOfObjects, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.getView().setPadding(20, 20, 20, 20);
                    toast.getView().setBackgroundColor(Color.parseColor("#7CB342"));
                    toast.show();
                    pDialog.dismiss();
                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), "Server is Failed ", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }

        }
    }
}

class PlotBookingStatusAdapter extends BaseAdapter {
    ArrayList<String> adlist;
    LayoutInflater inflaters;
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public PlotBookingStatusAdapter(Activity parent, ArrayList<ModelClass> adlist) {

        activity = parent;
        data = adlist;
        inflators = (LayoutInflater) ((Activity) activity)
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // Log.v("size of dataArray is " + data.size(), "getcount()");
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView plotBookingSiteNameTv, plotBookingPlotNoTv, plotBookingHolderIdTv, plotBookingAmountTv, plotBookingStatusTv, plotBookingBalanceAmountTv, plotBookingHolderNameTv, plotBookingMobileTv;

        public ViewHolder(View vi) {
            //  holder.payNoTv = (TextView) vi.findViewById(R.id.pbPayoutNoTv);
            plotBookingSiteNameTv = (TextView) vi.findViewById(R.id.plot_booking_siteNameTv);
            plotBookingPlotNoTv = (TextView) vi.findViewById(R.id.plot_booking_plotNoTv);
            plotBookingHolderIdTv = (TextView) vi.findViewById(R.id.plot_booking_plotHolderIdTv);
            plotBookingAmountTv = (TextView) vi.findViewById(R.id.plot_booking_plotAmountTv);
            plotBookingStatusTv = (TextView) vi.findViewById(R.id.plot_booking_plotStatusTv);
            plotBookingBalanceAmountTv = (TextView) vi.findViewById(R.id.plot_booking_balanceAmountTv);
            plotBookingHolderNameTv = (TextView) vi.findViewById(R.id.plot_booking_HolderNameTv);
            plotBookingMobileTv = (TextView) vi.findViewById(R.id.plot_booking_mobileNoTv);


        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
            vi = inflators.inflate(R.layout.plot_booking_status_rowlist, null);

            /******** View Holder Object to contain tabitem.xml file elements ************/
            holder = new ViewHolder(vi);


            /************ Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
           /* holder.memberNameTv.setText("No Data");
            holder.joiningDateTv.setText("No Data");
            holder.loginIdTv.setText("No Data");
            holder.mobileNoTv.setText("No Data");
*/
        } else {
            tempValues = null;
            tempValues = (ModelClass) data.get(position);

            /************ Set Model values in Holder elements ***********/
            // System.out.println("insiiidddddddeeeeee setting value to the adapter......");

            // holder.text.setText(tempValues.getType());
            //holder.payNoTv.setText(tempValues.getPbPayountNo());
            holder.plotBookingSiteNameTv.setText(tempValues.getPlotBookingSiteName());
            holder.plotBookingPlotNoTv.setText(tempValues.getPlotBookingPlotNo());
            holder.plotBookingHolderIdTv.setText(tempValues.getPlotBookingHolderId());
            holder.plotBookingAmountTv.setText(tempValues.getPlotBookingAmount());
            holder.plotBookingStatusTv.setText(tempValues.getPlotBookingStatus());
            holder.plotBookingBalanceAmountTv.setText(tempValues.getPlotBookingBalanceAmount());
            holder.plotBookingHolderNameTv.setText(tempValues.getPlotBookingHolderName());
            holder.plotBookingMobileTv.setText(tempValues.getPlotBookingMobile());


        }
        return vi;
    }


}
