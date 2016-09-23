package myappplication.noida.quaere.groupalternate;

/**
 * Created by intex on 10/20/2015.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class DueEmiCustomerListFragment extends Fragment {
    String userId;
    String  emiClcontactNo,
            emiClLastPaidDate,
            emiCllastPaidEMIDueDate,
            emiClplotArea,
            emiClplotBookingDate ,
            emiClplotHolderName ,
            emiClplotNo,
            emiClplotRate ,
            emiClplotStatus ,
            emiClplotType ,
            emiClsiteName ,
            emiCltotalPaidAmount,
            emiClResponceCode;
    // List<String> cbSerialNoArray = new ArrayList<String>();
    List<String> emiClcontactNoArray = new ArrayList<String>();
    List<String> emiCllastPaidDateArray = new ArrayList<String>();
    List<String> emiCllastPaidEMIDueDateArray = new ArrayList<String>();
    List<String> emiClplotAreaArray = new ArrayList<String>();
    List<String> emiClplotBookingDateArray = new ArrayList<String>();
    List<String> emiClplotHolderNameArray = new ArrayList<String>();
    List<String> emiClplotNoArray = new ArrayList<String>();
    List<String> emiClplotRateArray = new ArrayList<String>();
    List<String> emiClplotStatusArray = new ArrayList<String>();

    List<String> emiClplotTypeArray = new ArrayList<String>();
    List<String> emiClsiteNameArray = new ArrayList<String>();
    List<String> emiCltotalPaidArray = new ArrayList<String>();

    public ArrayList<ModelClass> dueEmiCustomerCustomArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    TextView empty;
    ListView dueEmiClListView;
   // String due_emi_url = "http:/demo8.mlmsoftindia.com/ShinePanel.svc/DueEMIReport/"+userId+"/-1/-1/-1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.due_emi_customerlist_fragment, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email", "");
        dueEmiClListView = (ListView) view.findViewById(R.id.due_emi_customerlist_listView);
         empty =  (TextView)view.findViewById(R.id.emi_customer_emptyTv);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            DueEmiCusotomerTask productTask = new DueEmiCusotomerTask();
            productTask.execute();

        }

        return view;
    }

    public class DueEmiCusotomerTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Due emi customer ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/DefaulterList/"+userId+"/-1");


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Due Emi Cuser Response:", response);
                // String response = jsonResponse;
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    final int noOfObjects = jsonArray.length();
                    //   Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                        //  int k = i+1;
                        //   Log.v("No of times " + i, "shhhhhhhhh");

                        //   cbSerialNo =""+k;

                        emiClcontactNo = jobj.getString("ContactNo");

                        emiClLastPaidDate = jobj.getString("LastPaidDate");
                        //Log.v("emiClLastPaidDate ", emiClLastPaidDate);

                        emiCllastPaidEMIDueDate = jobj.getString("LastPaidEMIDueDate");
                       // Log.v("LastPaidEMIDueDate", emiCllastPaidEMIDueDate);

                        emiClplotArea = jobj.getString("PlotArea");
                       // Log.v("PlotArea", emiClplotArea);

                        emiClplotBookingDate = jobj.getString("PlotBookingDate");
                       // Log.v("PlotBookingDate", emiClplotBookingDate);

                        emiClplotHolderName = jobj.getString("PlotHolderName");
                       //  Log.v("PlotHolderName ", emiClplotHolderName);

                        emiClplotNo = jobj.getString("PlotNo");
                       // Log.v("PlotNo", emiClplotNo);

                        emiClplotRate = jobj.getString("PlotRate");
                      //  Log.v("PlotRate", emiClplotRate);

                        emiClplotStatus = jobj.getString("PlotStatus");
                       // Log.v("PlotStatus ", emiClplotStatus);


                        emiClplotType = jobj.getString("PlotType");

                        emiClsiteName = jobj.getString("SiteName");
                       // Log.v("SiteName ", emiClsiteName);

                        emiCltotalPaidAmount = jobj.getString("TotalPaidAmount");
                       // Log.v("TotalPaidAmount ", emiCltotalPaidAmount);

                        emiClResponceCode = jobj.getString("ResponceCode");

                        //adding value to array
                        // cbSerialNoArray.add(cbSerialNo);
                        emiClcontactNoArray.add(emiClcontactNo);
                        emiCllastPaidDateArray.add(emiClLastPaidDate);
                        emiCllastPaidEMIDueDateArray.add(emiCllastPaidEMIDueDate);
                        emiClplotAreaArray.add(emiClplotArea);
                        emiClplotBookingDateArray.add(emiClplotBookingDate);
                        emiClplotHolderNameArray.add(emiClplotHolderName);
                        emiClplotNoArray.add(emiClplotNo);
                        emiClplotRateArray.add(emiClplotRate);
                        emiClplotStatusArray.add(emiClplotStatus);
                        emiClplotTypeArray.add(emiClplotType);
                        emiClsiteNameArray.add(emiClsiteName);
                        emiCltotalPaidArray.add(emiCltotalPaidAmount);

                    }
                    for (int j = 0; j < emiClcontactNoArray.size(); j++) {
                        final ModelClass model = new ModelClass();

                        //take data into model object
                        // model.setCbSerialNo(cbSerialNoArray.get(j));
                        model.setEmiClcontactNo(emiClcontactNoArray.get(j));
                        model.setEmiClLastPaidDate(emiCllastPaidDateArray.get(j));
                        model.setEmiCllastPaidEMIDueDate(emiCllastPaidEMIDueDateArray.get(j));
                        model.setEmiClplotArea(emiClplotAreaArray.get(j));
                        model.setEmiClplotBookingDate(emiClplotBookingDateArray.get(j));
                        model.setEmiClplotHolderName(emiClplotHolderNameArray.get(j));
                        model.setEmiClplotNo(emiClplotNoArray.get(j));
                        model.setEmiClplotRate(emiClplotRateArray.get(j));
                        model.setEmiClplotStatus(emiClplotStatusArray.get(j));
                        model.setEmiClplotType(emiClplotTypeArray.get(j));
                        model.setEmiClsiteName(emiClsiteNameArray.get(j));
                        model.setEmiCltotalPaidAmount(emiCltotalPaidArray.get(j));

                        dueEmiCustomerCustomArray.add(model);

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
            try{
                if(emiClResponceCode.equals("0")) {
                    dueEmiClListView.setEmptyView(empty);
                    pDialog.dismiss();
                }
                else {

                    dueEmiClListView.setAdapter(new DueEmiCustomerCustomAdapter(getActivity(), dueEmiCustomerCustomArray));
                    pDialog.dismiss();
                }

            }
            catch(Exception e){
                Toast.makeText(getActivity(), "Server is Failed ", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }


        }
    }

    class DueEmiCustomerCustomAdapter extends BaseAdapter {

        private Activity activity;
        private ArrayList<ModelClass> data;
        private LayoutInflater inflators = null;
        ModelClass tempValues;

        public DueEmiCustomerCustomAdapter(Activity parent, ArrayList<ModelClass> adlist) {

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
            public TextView emiClcontactNoTv,
                    emiClLastPaidDateTv,
                    emiCllastPaidEMIDueDateTv,
                    emiClplotAreaTv,
                    emiClplotBookingDateTv ,
                    emiClplotHolderNameTv ,
                    emiClplotNoTv,
                    emiClplotRateTv ,
                    emiClplotStatusTv ,
                    emiClplotTypeTv ,
                    emiClsiteNameTv ,
                    emiCltotalPaidAmountTv ;
            public ViewHolder(View vi){
                emiClcontactNoTv = (TextView) vi.findViewById(R.id.emi_customer_contactTv);
                emiClLastPaidDateTv = (TextView) vi.findViewById(R.id.emi_customer_lastPayDateTv);
                emiCllastPaidEMIDueDateTv = (TextView) vi.findViewById(R.id.emi_customer_lastEmiDueDateTv);
                emiClplotAreaTv = (TextView) vi.findViewById(R.id.emi_customer_plotAreaTv);
                emiClplotBookingDateTv = (TextView) vi.findViewById(R.id.emi_customer_BookingDateTv);
                emiClplotHolderNameTv = (TextView) vi.findViewById(R.id.emi_customer_holderTv);
                emiClplotNoTv = (TextView) vi.findViewById(R.id.emi_customer_plotNoTv);
                emiClplotRateTv = (TextView) vi.findViewById(R.id.emi_customer_plotRateTv);
                emiClplotStatusTv = (TextView) vi.findViewById(R.id.emi_customer_bookingStatusTv);
                emiClplotTypeTv = (TextView) vi.findViewById(R.id.emi_customer_plotTypeTv);
                emiClsiteNameTv = (TextView) vi.findViewById(R.id.emi_customer_siteNameTv);
                emiCltotalPaidAmountTv = (TextView) vi.findViewById(R.id.emi_customer_totalPaymentTv);

            }

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            ViewHolder holder;
            if (convertView == null) {
                /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
                vi = inflators.inflate(R.layout.due_emi_customer_rowlist, null);
                /******** View Holder Object to contain tabitem.xml file elements ************/
                holder = new ViewHolder(vi);

                // holder.cbSrNo = (TextView) vi.findViewById(R.id.cb_SrNoTv);


                /************ Set holder with LayoutInflater ************/
                vi.setTag(holder);
            } else
                holder = (ViewHolder) vi.getTag();

            if (data.size() <= 0) {

                // holder.cbSrNo.setText("No Data is avaiblable");
                holder.emiClcontactNoTv.setText("No Data is avaiblable");
                holder.emiClLastPaidDateTv.setText("No Data is avaiblable");
                holder.emiClplotRateTv.setText("No Data is avaiblable");
                holder.emiClsiteNameTv.setText("No Data is avaiblable");
            } else {
                tempValues = null;
                tempValues = (ModelClass) data.get(position);

                /************ Set Model values in Holder elements ***********/
                // System.out.println("insiiidddddddeeeeee setting value to the adapter......");

                // holder.text.setText(tempValues.getType());
                //  holder.cbSrNo.setText(tempValues.getCbSerialNo());
                holder.emiClcontactNoTv .setText(tempValues.getDueEmiCustomerName());
                holder.emiClLastPaidDateTv.setText(tempValues.getDueEmiCustomerId());
                holder.emiCllastPaidEMIDueDateTv.setText(tempValues.getDueEmidueAmount());
                holder.emiClplotAreaTv.setText(tempValues.getDueEmiDueDate());
                holder.emiClplotBookingDateTv.setText(tempValues.getDueEmiPaidAmount());
                holder.emiClplotHolderNameTv.setText(tempValues.getDueEmiPlotNo());
                holder.emiClplotNoTv.setText(tempValues.getDueEmiPlotArea());
                holder.emiClplotRateTv.setText(tempValues.getDueEmiPlotAmount());
                holder.emiClplotStatusTv.setText(tempValues.getDueEmiProjectName());
                holder.emiClplotTypeTv.setText(tempValues.getDueEmiPlotArea());
                holder.emiClsiteNameTv.setText(tempValues.getDueEmiPlotAmount());
                holder.emiCltotalPaidAmountTv.setText(tempValues.getDueEmiProjectName());


            }
            return vi;
        }


    }


}

