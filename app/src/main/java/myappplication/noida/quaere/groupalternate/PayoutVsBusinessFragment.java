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
public class PayoutVsBusinessFragment extends Fragment {
    String userId;
    String  pbNetAmount, pbGrossAmout, pbClosingDate, pbProcessingFee, pbSelfIncm, pbTds, pbTeamIncm,pvbResponseCode,pbPayoutNo;
    List<String> payoutNoArray = new ArrayList<String>();
    List<String> pbNetAmountArray = new ArrayList<String>();
    List<String> pbGrossAmoutArray = new ArrayList<String>();
    List<String> pbSelfIncmArray = new ArrayList<String>();
    List<String> pbTdsArray = new ArrayList<String>();
    List<String> pbClosingDateArray = new ArrayList<String>();
    List<String> pbProcessingFeeArray = new ArrayList<String>();
     int noOfObjects;
    public ArrayList<ModelClass> customPayOutBznsArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    TextView pVbEmptyTv;
    ListView payoutBznsListView;
    String payout_bzns_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/PayoutVsBusiness/" + userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.payout_vs_business_fragment, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email", "");

        payoutBznsListView = (ListView) view.findViewById(R.id.payout_business_listView);
        pVbEmptyTv = (TextView)view.findViewById(R.id.payoutVsBusiness_emptyTv);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            PayoutBzns productTask = new PayoutBzns();
            productTask.execute(payout_bzns_url);
           /* Thread downlineThread = new Thread(new PayoutListThread());
            downlineThread.start();*/


        }

        return view;
    }

    public class PayoutBzns extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Payouts ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/PayoutVsBusiness/" + userId);


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Payout List Response:", response);
                // String response = jsonResponse;
                try {

                    JSONArray jsonArray = new JSONArray(response);
                     noOfObjects = jsonArray.length();
                    Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");

                    for (int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                       Log.v("No of times " + i, "shhhhhhhhh");


                        pbNetAmount = jobj.getString("NetAmount");
                        pbPayoutNo = jobj.getString("PayoutNo");
                        pbGrossAmout = jobj.getString("GrossAmount");

                        pbClosingDate = jobj.getString("ClosingDate");

                        pbProcessingFee = jobj.getString("ProcessingFee");

                        pbSelfIncm = jobj.getString("SelfIncome");

                        pbTds = jobj.getString("TDSAmount");

                        pbTeamIncm = jobj.getString("TeamIncome");
                        pvbResponseCode = jobj.getString("ResponceCode");

                        //adding value to array
                        payoutNoArray.add(pbPayoutNo);
                        pbNetAmountArray.add(pbNetAmount);
                        pbGrossAmoutArray.add(pbGrossAmout);
                        pbClosingDateArray.add(pbClosingDate);
                        pbProcessingFeeArray.add(pbProcessingFee);
                        pbSelfIncmArray.add(pbSelfIncm);
                        pbTdsArray.add(pbTds);

                    }
                    for (int j = 0; j < pbNetAmountArray.size(); j++) {
                        final ModelClass model = new ModelClass();

                        //take data into model object
                        model.setPbPayountNo(payoutNoArray.get(j));
                        model.setPbNetAmount(pbNetAmountArray.get(j));
                        model.setPbGrossAmout(pbGrossAmoutArray.get(j));
                        model.setPbClosingDate(pbClosingDateArray.get(j));
                        model.setPbProcessingFee(pbProcessingFeeArray.get(j));
                        model.setPbTds(pbTdsArray.get(j));
                        model.setPbSelfIncm(pbSelfIncmArray.get(j));

                        customPayOutBznsArray.add(model);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                   System.out.println(" Exception  is caught here ......." + e.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(" Exception  is caught here ......." + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{

                if(pvbResponseCode.equals("0")) {
                    payoutBznsListView.setEmptyView(pVbEmptyTv);
                    pDialog.dismiss();
                }
                else {

                    payoutBznsListView.setAdapter(new PayouBusinessAdapter(getActivity(), customPayOutBznsArray));
                    Toast toast = Toast.makeText(getContext(), "Total Records are :"+noOfObjects, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.getView().setPadding(20, 20, 20, 20);
                    toast.getView().setBackgroundColor(Color.parseColor("#7CB342"));
                    toast.show();
                    pDialog.dismiss();
                }

            }
            catch(Exception e){
                Toast.makeText(getActivity(), "Server is Failed ", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }

        }
    }
}

class PayouBusinessAdapter extends BaseAdapter {
    ArrayList<String> adlist;
    LayoutInflater inflaters;
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public PayouBusinessAdapter(Activity parent, ArrayList<ModelClass> adlist) {

        activity = parent;
        data = adlist;
        inflators = (LayoutInflater) ((Activity) activity)
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // System.out.println("insiiiiiiidddddddeeeeeeeeeee placementDetailAdapter()......");
    }

    @Override
    public int getCount() {
      //  Log.v("size of dataArray is " + data.size(), "getcount()");
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
        public TextView pbNetAmount,pbGrosssAmount,pbSelfIncm,pbTds,pbProcessingfee,pbclosingDate,pbPayNoTv;

        public ViewHolder(View vi){
            pbPayNoTv = (TextView) vi.findViewById(R.id.pbPayoutNoTv);
            pbNetAmount = (TextView) vi.findViewById(R.id.pbNetAmountTv);
            pbGrosssAmount = (TextView) vi.findViewById(R.id.pbGrossAmountTv);
            pbSelfIncm = (TextView) vi.findViewById(R.id.pbSelfIncmTv);
            pbTds = (TextView) vi.findViewById(R.id.pbTdsTv);
            pbProcessingfee = (TextView) vi.findViewById(R.id.pbProcessingFeeTv);
            pbclosingDate = (TextView) vi.findViewById(R.id.pbClosingDateTv);
        }

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
            vi = inflators.inflate(R.layout.payout_vs_business_rowlist, null);
            /******** View Holder Object to contain tabitem.xml file elements ************/
            holder = new ViewHolder(vi);


            /************ Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {

        } else {
            tempValues = null;
            tempValues = (ModelClass) data.get(position);

            /************ Set Model values in Holder elements ***********/
            // System.out.println("insiiidddddddeeeeee setting value to the adapter......");

            // holder.text.setText(tempValues.getType());
            holder.pbPayNoTv.setText(tempValues.getPbPayountNo());
            holder.pbNetAmount.setText(tempValues.getPbNetAmount());
            holder.pbGrosssAmount.setText(tempValues.getPbGrossAmout());
            holder.pbSelfIncm.setText(tempValues.getPbSelfIncm());
            holder.pbTds.setText(tempValues.getPbTds());
            holder.pbProcessingfee.setText(tempValues.getPbProcessingFee());
            holder.pbclosingDate.setText(tempValues.getPbClosingDate());


        }
        return vi;
    }


}
