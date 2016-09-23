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
public class CancelBookingFragment extends Fragment {
    String userId;
    String  cbCustomerName, cbSiteName, cbPlotNo, cbBookingId, cbBookingDate, cbCancelDate,cbRespponseCode;//cbSerialNo,
   // List<String> cbSerialNoArray = new ArrayList<String>();
    List<String> cbCustomerNameArray = new ArrayList<String>();
    List<String> cbSiteNameArray = new ArrayList<String>();
    List<String> cbPlotNoArray = new ArrayList<String>();
    List<String> cbBookingIdArray = new ArrayList<String>();
    List<String> cbBookingDateArray = new ArrayList<String>();
    List<String> cbCancelDateArray = new ArrayList<String>();

    public ArrayList<ModelClass> customCancelBookingArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
   TextView cbEmptyTv;
    ListView cancelBookingListView;
    int noOfObjects;
    String payout_bzns_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/CancelledBooking/" + userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cancel_booking_fragment, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email", "");
        cancelBookingListView = (ListView) view.findViewById(R.id.cancel_booking_listView);
        cbEmptyTv= (TextView)view.findViewById(R.id.cancel_booking_emptyTv);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            CancelBooking productTask = new CancelBooking();
            productTask.execute(payout_bzns_url);
           /* Thread downlineThread = new Thread(new PayoutListThread());
            downlineThread.start();*/


        }

        return view;
    }

    public class CancelBooking extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Cancelled ...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/CancelledBooking/" + userId);


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Cancel Booking Respon:", response);
                // String response = jsonResponse;
                try {

                    JSONArray jsonArray = new JSONArray(response);
                     noOfObjects = jsonArray.length();
                    //   Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                        //  int k = i+1;
                        //   Log.v("No of times " + i, "shhhhhhhhh");

                        //   cbSerialNo =""+k;

                        cbCustomerName = jobj.getString("CustomerName");
                        // Log.v("CustomerName", cbCustomerName);

                        cbSiteName = jobj.getString("SiteName");
                        //  Log.v("SiteName ", cbSiteName);

                        cbPlotNo = jobj.getString("PlotNo");
                        //  Log.v("PlotNo", cbPlotNo);

                        cbBookingId = jobj.getString("BookingID");
                        //  Log.v("BookingID", cbBookingId);

                        cbBookingDate = jobj.getString("BookingDate");
                        //  Log.v("BookingDate", cbBookingDate);

                        cbCancelDate = jobj.getString("CancelDate");
                        //  Log.v("CancelDate ", cbCancelDate);
                        cbRespponseCode = jobj.getString("ResponceCode");
                        //adding value to array
                        // cbSerialNoArray.add(cbSerialNo);
                        cbCustomerNameArray.add(cbCustomerName);
                        cbSiteNameArray.add(cbSiteName);
                        cbPlotNoArray.add(cbPlotNo);
                        cbBookingIdArray.add(cbBookingId);
                        cbBookingDateArray.add(cbBookingDate);
                        cbCancelDateArray.add(cbCancelDate);

                    }
                    for (int j = 0; j < cbCustomerNameArray.size(); j++) {
                        final ModelClass model = new ModelClass();

                        //take data into model object
                        // model.setCbSerialNo(cbSerialNoArray.get(j));
                        model.setCbCustomerName(cbCustomerNameArray.get(j));
                        model.setCbSiteName(cbSiteNameArray.get(j));
                        model.setCbPlotNo(cbPlotNoArray.get(j));
                        model.setCbBookingId(cbBookingIdArray.get(j));
                        model.setCbBookingDate(cbBookingDateArray.get(j));
                        model.setCbCancelDate(cbCancelDateArray.get(j));

                        customCancelBookingArray.add(model);

                    }
                    // p_detail_list.setAdapter(new PlacementDetailAdapter(getActivity(), customListArray));

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
                if (cbRespponseCode.equals("0")) {
                    cancelBookingListView.setEmptyView(cbEmptyTv);
                    pDialog.dismiss();
                } else {

                    cancelBookingListView.setAdapter(new CancelBookingAdapter(getActivity(), customCancelBookingArray));
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



    class CancelBookingAdapter extends BaseAdapter {

        private Activity activity;
        private ArrayList<ModelClass> data;
        private LayoutInflater inflators = null;
        ModelClass tempValues;

        public CancelBookingAdapter(Activity parent, ArrayList<ModelClass> adlist) {

            activity = parent;
            data = adlist;
            inflators = (LayoutInflater) ((Activity) activity)
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // System.out.println("insiiiiiiidddddddeeeeeeeeeee placementDetailAdapter()......");
        }

        @Override
        public int getCount() {
            Log.v("size of dataArray is " + data.size(), "getcount()");
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
            public TextView cbCustName,cbSitename,cbPlotNo,cbBookingId,cbBookingDate,cbCancelDate;

            public ViewHolder(View vi){
                // holder.cbSrNo = (TextView) vi.findViewById(R.id.cb_SrNoTv);
                cbCustName = (TextView) vi.findViewById(R.id.cb_custmrnameTv);
                cbSitename = (TextView) vi.findViewById(R.id.cb_siteNameTv);
                cbPlotNo = (TextView) vi.findViewById(R.id.cb_plotNoTv);
                cbBookingId = (TextView) vi.findViewById(R.id.cb_bookingIdTv);
                cbBookingDate = (TextView) vi.findViewById(R.id.cb_bookingDateTv);
                cbCancelDate = (TextView) vi.findViewById(R.id.cb_cancelDateTv);
            }

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            ViewHolder holder;
            if (convertView == null) {
                /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
                vi = inflators.inflate(R.layout.cancel_booking_rowlist, null);
                /******** View Holder Object to contain tabitem.xml file elements ************/
                holder = new ViewHolder(vi);


                /************ Set holder with LayoutInflater ************/
                vi.setTag(holder);
            } else
                holder = (ViewHolder) vi.getTag();

            if (data.size() <= 0) {
                // holder.cbSrNo.setText("No Data is avaiblable");
                holder.cbCustName.setText("No Data is avaiblable");
                holder.cbSitename.setText("No Data is avaiblable");
                holder.cbPlotNo.setText("No Data is avaiblable");
                holder.cbBookingId.setText("No Data is avaiblable");
            } else {
                tempValues = null;
                tempValues = (ModelClass) data.get(position);

                /************ Set Model values in Holder elements ***********/

                //  holder.cbSrNo.setText(tempValues.getCbSerialNo());
                holder.cbCustName.setText(tempValues.getCbCustomerName());
                holder.cbSitename.setText(tempValues.getCbSiteName());
                holder.cbPlotNo.setText(tempValues.getCbPlotNo());
                holder.cbBookingId.setText(tempValues.getCbBookingId());
                holder.cbBookingDate.setText(tempValues.getCbBookingDate());
                holder.cbCancelDate.setText(tempValues.getCbCancelDate());


            }
            return vi;
        }


    }
}


