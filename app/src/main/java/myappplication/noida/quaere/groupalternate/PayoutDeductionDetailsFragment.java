package myappplication.noida.quaere.groupalternate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
 * Created by intex on 10/20/2015.
 */
public class PayoutDeductionDetailsFragment extends Fragment {
    String userId;
    String  payoutDdPaymentAmount,
            payoutDdChequeBank,
            payoutDdChequeDate,
            payoutDdChequeNo,
            payoutDdMemberID,
            payoutDdMemberName,
            payoutDdPaymentDate,
            payoutDdPaymentMode,
            payoutDdPaymentRemark,
            payoutDdTotalAdvance,
            payoutDdResponseCode;
   int noOfObjects ;
    // List<String> cbSerialNoArray = new ArrayList<String>();
    List<String> payoutDdPaymentAmountArray = new ArrayList<String>();
    List<String> payoutDdChequeBankArray = new ArrayList<String>();
    List<String> payoutDdChequeDateArray = new ArrayList<String>();
    List<String> payoutDdChequeNoArray = new ArrayList<String>();
    List<String> payoutDdMemberIDArray = new ArrayList<String>();
    List<String> payoutDdMemberNameArray = new ArrayList<String>();
    List<String> payoutDdPaymentDateArray = new ArrayList<String>();
    List<String> payoutDdPaymentModeArray = new ArrayList<String>();
    List<String> payoutDdPaymentRemarkArray = new ArrayList<String>();
    List<String> payoutDdTotalAdvanceArray = new ArrayList<String>();



    public ArrayList<ModelClass> payoutDdCustomArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    TextView payoutEmptyTv;
    ListView payoutDdListView;
    // String due_emi_url = "http:/demo8.mlmsoftindia.com/ShinePanel.svc/DueEMIReport/"+userId+"/-1/-1/-1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.payout_dd_fragment, container, false);

        String payout_bzns_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/CancelledBooking/" + userId;
        payoutDdListView = (ListView) view.findViewById(R.id.payout_dd_listView);
        payoutEmptyTv = (TextView)view.findViewById(R.id.payout_dd_emptyTv);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            PayoutDdTask productTask = new PayoutDdTask();
            productTask.execute();

        }

        return view;
    }

    public class PayoutDdTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Payout Deductions ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/PayoutDeductionDetails/"+userId);


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Payout Dd Response:", response);
                // String response = jsonResponse;
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    noOfObjects = jsonArray.length();
                    //   Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                        payoutDdPaymentAmount = jobj.getString("PaymentAmount");

                        payoutDdChequeBank = jobj.getString("ChequeBank");


                        payoutDdChequeDate = jobj.getString("ChequeDate");

                        payoutDdChequeNo = jobj.getString("ChequeNo");

                        payoutDdMemberID = jobj.getString("MemberID");

                        payoutDdMemberName = jobj.getString("MemberName");

                        payoutDdPaymentDate = jobj.getString("PaymentDate");

                        payoutDdPaymentMode = jobj.getString("PaymentMode");

                        payoutDdPaymentRemark = jobj.getString("PaymentRemark");


                        payoutDdTotalAdvance = jobj.getString("TotalAdvance");
                        payoutDdResponseCode = jobj.getString("ResponceCode");

                        //adding value to array
                        // cbSerialNoArray.add(cbSerialNo);
                        payoutDdPaymentAmountArray.add(payoutDdPaymentAmount);
                        payoutDdChequeBankArray.add(payoutDdChequeBank);
                        payoutDdChequeDateArray.add(payoutDdChequeDate);
                        payoutDdChequeNoArray.add(payoutDdChequeNo);
                        payoutDdMemberIDArray.add(payoutDdMemberID);
                        payoutDdMemberNameArray.add(payoutDdMemberName);
                        payoutDdPaymentDateArray.add(payoutDdPaymentDate);
                        payoutDdPaymentModeArray.add(payoutDdPaymentMode);
                        payoutDdPaymentRemarkArray.add(payoutDdPaymentRemark);
                        payoutDdTotalAdvanceArray.add(payoutDdTotalAdvance);

                    }
                    for (int j = 0; j < payoutDdMemberNameArray.size(); j++) {
                        final ModelClass model = new ModelClass();

                        //take data into model object
                        // model.setCbSerialNo(cbSerialNoArray.get(j));
                        model.setPayoutDdPaymentAmount(payoutDdPaymentAmountArray.get(j));
                        model.setPayoutDdChequeBank(payoutDdChequeBankArray.get(j));
                        model.setPayoutDdChequeDate(payoutDdChequeDateArray.get(j));
                        model.setPayoutDdChequeNo(payoutDdChequeNoArray.get(j));
                        model.setPayoutDdMemberID(payoutDdMemberIDArray.get(j));
                        model.setPayoutDdMemberName(payoutDdMemberNameArray.get(j));
                        model.setPayoutDdPaymentDate(payoutDdPaymentDateArray.get(j));
                        model.setPayoutDdPaymentMode(payoutDdPaymentModeArray.get(j));
                        model.setPayoutDdPaymentRemark(payoutDdPaymentRemarkArray.get(j));
                        model.setPayoutDdTotalAdvance(payoutDdTotalAdvanceArray.get(j));

                        payoutDdCustomArray.add(model);

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
                if(payoutDdResponseCode.equals("0")) {
                    payoutDdListView.setEmptyView(payoutEmptyTv);
                    pDialog.dismiss();
                }
                else {

                    payoutDdListView.setAdapter(new PayoutDdCustomAdapter(getActivity(), payoutDdCustomArray));
                    Toast toast = Toast.makeText(getContext(), "Total Records are : " + noOfObjects, Toast.LENGTH_LONG);
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

class PayoutDdCustomAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public PayoutDdCustomAdapter(Activity parent, ArrayList<ModelClass> adlist) {

        activity = parent;
        data = adlist;
        inflators = (LayoutInflater) ((Activity) activity)
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
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
        public TextView  payoutDdPaymentAmountTv,
                payoutDdChequeBankTv,
                payoutDdChequeDateTv,
                payoutDdChequeNoTv,
                payoutDdMemberIDTv,
                payoutDdMemberNameTv,
                payoutDdPaymentDateTv,
                payoutDdPaymentModeTv,
                payoutDdPaymentRemarkTv,
                payoutDdTotalAdvanceTv;
        public ViewHolder(View vi){
            // holder.cbSrNo = (TextView) vi.findViewById(R.id.cb_SrNoTv);
            payoutDdPaymentAmountTv = (TextView) vi.findViewById(R.id.payout_dd_payamountTv);
            payoutDdChequeBankTv = (TextView) vi.findViewById(R.id.payout_dd_chequebankTv);
            payoutDdChequeDateTv = (TextView) vi.findViewById(R.id.payout_dd_chequeDateTv);
            payoutDdChequeNoTv = (TextView) vi.findViewById(R.id.payout_dd_chequeNoTv);
            payoutDdMemberIDTv = (TextView) vi.findViewById(R.id.payout_dd_memberIdTv);
            payoutDdMemberNameTv = (TextView) vi.findViewById(R.id.payout_dd_memberNameTv);
            payoutDdPaymentDateTv = (TextView) vi.findViewById(R.id.payout_dd_payDateTv);
            payoutDdPaymentModeTv = (TextView) vi.findViewById(R.id.payout_dd_payModeTv);
            payoutDdPaymentRemarkTv = (TextView) vi.findViewById(R.id.payout_dd_payRemarkTv);
            payoutDdTotalAdvanceTv = (TextView) vi.findViewById(R.id.payout_dd_totalAdvanceTv);


        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
            vi = inflators.inflate(R.layout.payout_dd_rowlist, null);
            /******** View Holder Object to contain tabitem.xml file elements ************/
            holder = new ViewHolder(vi);


            /************ Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {

            // holder.cbSrNo.setText("No Data is avaiblable");
            holder.payoutDdPaymentAmountTv.setText("No Data ");
            holder.payoutDdChequeBankTv.setText("No Data ");
            holder.payoutDdChequeDateTv.setText("No Data ");
            holder.payoutDdChequeNoTv.setText("No Data ");
        } else {
            tempValues = null;
            tempValues = (ModelClass) data.get(position);

            /************ Set Model values in Holder elements ***********/


            // holder.text.setText(tempValues.getType());
            //  holder.cbSrNo.setText(tempValues.getCbSerialNo());
            holder.payoutDdPaymentAmountTv .setText(tempValues.getPayoutDdPaymentAmount());
            holder.payoutDdChequeBankTv.setText(tempValues.getPayoutDdChequeBank());
            holder.payoutDdChequeDateTv.setText(tempValues.getPayoutDdChequeDate());
            holder.payoutDdChequeNoTv.setText(tempValues.getPayoutDdChequeNo());
            holder.payoutDdMemberIDTv.setText(tempValues.getPayoutDdMemberID());
            holder.payoutDdMemberNameTv.setText(tempValues.getPayoutDdMemberName());
            holder.payoutDdPaymentDateTv.setText(tempValues.getPayoutDdPaymentDate());
            holder.payoutDdPaymentModeTv.setText(tempValues.getPayoutDdPaymentMode());
            holder.payoutDdPaymentRemarkTv.setText(tempValues.getPayoutDdPaymentRemark());
            holder.payoutDdTotalAdvanceTv.setText(tempValues.getPayoutDdTotalAdvance());



        }
        return vi;
    }


}

