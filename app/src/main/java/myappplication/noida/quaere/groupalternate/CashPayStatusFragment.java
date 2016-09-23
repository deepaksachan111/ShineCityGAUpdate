package myappplication.noida.quaere.groupalternate;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
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
public class CashPayStatusFragment extends Fragment {

    String userId, cash_pay_url;

    String payFromDate,payToDate,cashFromDate,cashToDate,cashPayStatus;
    String cashAmount, cashChequeNo, cashChequeDate, cashPartyName, cashReceiptNo, cashRespnseCode, cashConfirm, cashDecline, cashPending;//,detailNo;
    ListView cash_list_view;

    List<String> cashAmountArray = new ArrayList<String>();
    List<String> cashChequeNoArray = new ArrayList<String>();
    List<String> cashChequeDateArray = new ArrayList<String>();
    List<String> cashPartyNameArray = new ArrayList<String>();
    List<String> cashReceiptNoArray = new ArrayList<String>();
    List<String> cashStatusArray = new ArrayList<String>();
    int noOfObjects;
    public ArrayList<ModelClass> customListArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    AlertDialog alert;
   TextView cashEmptyTv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cash_pay_status_fragment, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email", "");

        payFromDate = this.getArguments().getString("payFromDate");
        payToDate = this.getArguments().getString("payToDate");
        cashFromDate = this.getArguments().getString("cashFromDate");
        cashToDate = this.getArguments().getString("cashToDate");
        cashPayStatus = this.getArguments().getString("cashPayStatus");

        System.out.println("  cashPayStatus:"+ cashPayStatus + " payFromDate:"+ payFromDate + " payToDate:" + payToDate + " cashFromDate:" + cashFromDate + " cashToDate :" + cashToDate);

        cash_pay_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/PaymentStatus/" + userId + "/Cash/"+payFromDate+"/"+payToDate+"/"+cashFromDate+"/"+cashToDate+"/"+cashPayStatus;

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            new CashPayAsyncTask().execute();

        }

        cash_list_view = (ListView) view.findViewById(R.id.cash_pay_status_listView);
        cashEmptyTv = (TextView)view.findViewById(R.id.cash_pay_emptyTv);
        return view;
    }

    public class CashPayAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Cash Pay Status ...");
            pDialog.setIndeterminate(false);
            //  pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(cash_pay_url);


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Cash Pay.....response :", response);

                try {

                    JSONArray jsonArray = new JSONArray(response);
                     noOfObjects = jsonArray.length();
                  //  Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");
                    // pd.dismiss();
                    for (int j = 0; j < noOfObjects; j++) {

                        JSONObject jObj = jsonArray.getJSONObject(j);

                        cashAmount = jObj.getString("Amount");
                    //    Log.v("Amount :", cashAmount);

                        cashChequeNo = jObj.getString("ChequeNo");
                       // Log.v("ChequeNo :", cashChequeNo);
                        // mobile.setText(Mobile);

                        cashChequeDate = jObj.getString("ChequeDate");
                       // Log.v("ChequeDate :", cashChequeDate);
                        //login.setText(LoginID);

                        cashPartyName = jObj.getString("PartyName");
                      //  Log.v("PartyName :", cashPartyName);

                        cashReceiptNo = jObj.getString("RecieptNo");
                     //   Log.v("RecieptNo :", cashReceiptNo);

                        cashConfirm = jObj.getString("Confirm");
                      //  Log.v("Confirm :", cashConfirm);

                        cashDecline = jObj.getString("Decline");
                       // Log.v("Decline :", cashDecline);

                        cashPending = jObj.getString("Pending");
                     //   Log.v("Pending :", cashPending);
                        cashRespnseCode = jObj.getString("ResponceCode");

                        if (cashConfirm.equals("True")) {
                          //  System.out.println("Cash Confirm");
                            cashStatusArray.add("Confirmed");
                        }

                        else if (cashDecline.equals("True")) {
                          //  System.out.println("Cash Decline");
                            cashStatusArray.add("Declined");
                        }

                        else if (cashPending.equals("True")) {
                          //  System.out.println("Cash Pending");
                            cashStatusArray.add("Pending");
                        }
                        // joiningDate.setText(JoiningDate);

                        //detailNoArray.add(detailNo);
                        cashAmountArray.add(cashAmount);
                        cashChequeNoArray.add(cashChequeNo);
                        cashChequeDateArray.add(cashChequeDate);
                        cashPartyNameArray.add(cashPartyName);
                        cashReceiptNoArray.add(cashReceiptNo);

                    }
                    for (int k = 0; k < cashAmountArray.size(); k++) {

                        final ModelClass model = new ModelClass();

                        //******* Firstly take data in model object ******//**//*
                        model.setCashAmount(cashAmountArray.get(k));
                        model.setCashChequeNo(cashChequeNoArray.get(k));
                        model.setCashChequeDate(cashChequeDateArray.get(k));
                        model.setCashPartyName(cashPartyNameArray.get(k));
                        model.setCashReceiptNo(cashReceiptNoArray.get(k));
                        model.setCashStatus(cashStatusArray.get(k));
                        // model.setPdrecordno(detailNoArray.get(k));

                        customListArray.add(model);
                    }


                    // p_detail_list.setAdapter(new PlacementDetailAdapter(getActivity(), customListArray));

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(" Json Cash Pay Exception thrown ......." + e.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(" Service Cash Pay Exception thrown ......." + e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            try{
                if(cashRespnseCode.equals("0")) {
                    cash_list_view.setEmptyView(cashEmptyTv);
                    pDialog.dismiss();
                }
                else {

                    cash_list_view.setAdapter(new CashPayStatusAdapter(getActivity(), customListArray));
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

    class CashPayStatusAdapter extends BaseAdapter {
        ArrayList<String> adlist;
        LayoutInflater inflaters;
        private Activity activity;
        private ArrayList<ModelClass> data;
        private LayoutInflater inflators = null;
        ModelClass tempValues;

        public CashPayStatusAdapter(Activity parent, ArrayList<ModelClass> adlist) {

            activity = parent;
            data = adlist;
            inflators = (LayoutInflater) ((Activity) activity)
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            //Log.v("size of dataArray is " + data.size(), "getcount()");
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
            public TextView cashAmountTv,cashChequeNoTv,cashChequeDateTv,cashPartyNameTv,cashReceiptNoTv,cashStatusTv;

            public ViewHolder(View vi){
                cashAmountTv = (TextView) vi.findViewById(R.id.cash_status_amountTv);
                cashChequeNoTv = (TextView) vi.findViewById(R.id.cash_status_chequeNoTv);
                cashChequeDateTv = (TextView) vi.findViewById(R.id.cash_status_chequeDateTv);
                cashPartyNameTv = (TextView) vi.findViewById(R.id.cash_status_partyNameTv);
                cashReceiptNoTv = (TextView) vi.findViewById(R.id.cash_status_receiptNoTv);
                cashStatusTv = (TextView) vi.findViewById(R.id.cash_status_StatusTv);


            }

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            ViewHolder holder;
            if (convertView == null) {
                /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
                vi = inflators.inflate(R.layout.cash_pay_status_rowlist, null);

                holder = new ViewHolder(vi);

                //  holder.payNoTv = (TextView) vi.findViewById(R.id.pbPayoutNoTv);

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
                //holder.payNoTv.setText(tempValues.getPbPayountNo());
                holder.cashAmountTv.setText(tempValues.getCashAmount());
                holder.cashChequeNoTv.setText(tempValues.getCashChequeNo());
                holder.cashChequeDateTv.setText(tempValues.getCashChequeDate());
                holder.cashPartyNameTv.setText(tempValues.getCashPartyName());
                holder.cashReceiptNoTv.setText(tempValues.getCashReceiptNo());
                holder.cashStatusTv.setText(tempValues.getCashStatus());


            }
            return vi;
        }


    }

}
