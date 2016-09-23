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
 * Created by intex on 10/14/2015.
 */
public class ChequePayStatusFragment extends Fragment {

    String userId, cheque_pay_url;
    String chequepayFromDate, chequepayToDate, chequecashFromDate, chequecashToDate, chequePayStatus;
    String chequeAmount, chequeChequeNo, chequeChequeDate, chequePartyName, chequeReceiptNo, chequeResponseCode, chequeConfirm, chequeDecline, chequePending;//,detailNo;
    ListView cheque_list_view;

    List<String> chequeAmountArray = new ArrayList<String>();
    List<String> chequeChequeNoArray = new ArrayList<String>();
    List<String> chequeChequeDateArray = new ArrayList<String>();
    List<String> chequePartyNameArray = new ArrayList<String>();
    List<String> chequeReceiptNoArray = new ArrayList<String>();
    List<String> chequeStatusArray = new ArrayList<String>();
    int noOfObjects;
    public ArrayList<ModelClass> customListArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    AlertDialog alert;
    TextView chequeEmptyTv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cheque_pay_status_fragment, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email", "");


        chequepayFromDate = this.getArguments().getString("chequepayFromDate");
        chequepayToDate = this.getArguments().getString("chequepayToDate");
        chequecashFromDate = this.getArguments().getString("chequecashFromDate");
        chequecashToDate = this.getArguments().getString("chequecashToDate");
        chequePayStatus = this.getArguments().getString("chequePayStatus");

        System.out.println("  chequePayStatus:"+ chequePayStatus + " chequepayFromDate:"+ chequepayFromDate + " chequepayToDate:" + chequepayToDate + " cashFromDate:" + chequecashFromDate + " chequecashToDate :" + chequecashToDate);

        cheque_pay_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/PaymentStatus/" + userId + "/Cheque/"+chequepayFromDate+"/"+chequepayToDate+"/"+chequecashFromDate+"/"+chequecashToDate+"/"+chequePayStatus;


        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            new chequePayAsyncTask().execute(cheque_pay_url);

        }
        chequeEmptyTv = (TextView)view.findViewById(R.id.cheque_pay_emptyTv);
        cheque_list_view = (ListView) view.findViewById(R.id.cheque_pay_status_listView);

        return view;
    }

    public class chequePayAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Cheque Pay Status ...");
            pDialog.setIndeterminate(false);
            //  pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(cheque_pay_url);


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Cheque Pay...response :", response);

                try {

                    JSONArray jsonArray = new JSONArray(response);
                    noOfObjects = jsonArray.length();
                   // Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");
                    // pd.dismiss();
                    for (int j = 0; j < jsonArray.length() ; j++) {

                        JSONObject jObj = jsonArray.getJSONObject(j);

                        chequeAmount = jObj.getString("Amount");
                     //   Log.v("Amount :", chequeAmount);

                        chequeChequeNo = jObj.getString("ChequeNo");
                    //    Log.v("ChequeNo :", chequeChequeNo);
                        // mobile.setText(Mobile);

                        chequeChequeDate = jObj.getString("ChequeDate");
                     //   Log.v("ChequeDate :", chequeChequeDate);
                        //login.setText(LoginID);

                        chequePartyName = jObj.getString("PartyName");
                      //  Log.v("PartyName :", chequePartyName);

                        chequeReceiptNo = jObj.getString("RecieptNo");
                      //  Log.v("RecieptNo :", chequeReceiptNo);

                        chequeConfirm = jObj.getString("Confirm");
                       // Log.v("Confirm :", chequeConfirm);

                        chequeDecline = jObj.getString("Decline");
                     //   Log.v("Decline :", chequeDecline);

                        chequePending = jObj.getString("Pending");
                      //  Log.v("Pending :", chequePending);
                        chequeResponseCode = jObj.getString("ResponceCode");

                        if (chequeConfirm.equals("True")) {
                           // System.out.println("Cash Confirm");
                            chequeStatusArray.add("Confirmed");
                        } else if (chequeDecline.equals("True")) {
                          //  System.out.println("Cash Decline");
                            chequeStatusArray.add("Declined");
                        } else if (chequePending.equals("True")) {
                          //  System.out.println("Cash Pending");
                            chequeStatusArray.add("Pending");
                        }
                        // joiningDate.setText(JoiningDate);

                        //detailNoArray.add(detailNo);
                        chequeAmountArray.add(chequeAmount);
                        chequeChequeNoArray.add(chequeChequeNo);
                        chequeChequeDateArray.add(chequeChequeDate);
                        chequePartyNameArray.add(chequePartyName);
                        chequeReceiptNoArray.add(chequeReceiptNo);

                    }
                    for (int k = 0; k < chequeAmountArray.size(); k++) {

                        final ModelClass model = new ModelClass();

                        //******* Firstly take data in model object ******//**//*
                        model.setChequeAmount(chequeAmountArray.get(k));
                        model.setChequeChequeNo(chequeChequeNoArray.get(k));
                        model.setChequeChequeDate(chequeChequeDateArray.get(k));
                        model.setChequePartyName(chequePartyNameArray.get(k));
                        model.setChequeReceiptNo(chequeReceiptNoArray.get(k));
                        model.setChequeStatus(chequeStatusArray.get(k));
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
                if(chequeResponseCode.equals("0")) {
                    cheque_list_view.setEmptyView(chequeEmptyTv);
                    pDialog.dismiss();
                }
                else {

                    cheque_list_view.setAdapter(new ChequePayStatusAdapter(getActivity(), customListArray));
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


    class ChequePayStatusAdapter extends BaseAdapter {
        ArrayList<String> adlist;
        LayoutInflater inflaters;
        private Activity activity;
        private ArrayList<ModelClass> data;
        private LayoutInflater inflators = null;
        ModelClass tempValues;

        public ChequePayStatusAdapter(Activity parent, ArrayList<ModelClass> adlist) {

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
            public TextView chequeAmountTv, chequeChequeNoTv, chequeChequeDateTv, chequePartyNameTv, chequeReceiptNoTv, chequeStatusTv;//payNoTv

            public ViewHolder(View vi){
                chequeAmountTv = (TextView) vi.findViewById(R.id.cheque_status_amountTv);
                chequeChequeNoTv = (TextView) vi.findViewById(R.id.cheque_status_chequeNoTv);
                chequeChequeDateTv = (TextView) vi.findViewById(R.id.cheque_status_chequeDateTv);
                chequePartyNameTv = (TextView) vi.findViewById(R.id.cheque_status_partyNameTv);
                chequeReceiptNoTv = (TextView) vi.findViewById(R.id.cheque_status_receiptNoTv);
                chequeStatusTv = (TextView) vi.findViewById(R.id.cheque_status_StatusTv);
            }
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            ViewHolder holder;
            if (convertView == null) {
                /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
                vi = inflators.inflate(R.layout.cheque_status_rowlist, null);

                /******** View Holder Object to contain tabitem.xml file elements ************/
                holder = new ViewHolder(vi);

                //  holder.payNoTv = (TextView) vi.findViewById(R.id.pbPayoutNoTv);



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
                holder.chequeAmountTv.setText(tempValues.getChequeAmount());
                holder.chequeChequeNoTv.setText(tempValues.getChequeChequeNo());
                holder.chequeChequeDateTv.setText(tempValues.getChequeChequeDate());
                holder.chequePartyNameTv.setText(tempValues.getChequePartyName());
                holder.chequeReceiptNoTv.setText(tempValues.getChequeReceiptNo());
                holder.chequeStatusTv.setText(tempValues.getChequeStatus());


            }
            return vi;
        }


    }

}

