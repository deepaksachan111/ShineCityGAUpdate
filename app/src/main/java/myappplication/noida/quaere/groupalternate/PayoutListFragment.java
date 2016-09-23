package myappplication.noida.quaere.groupalternate;


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
public class PayoutListFragment extends Fragment {
    String userId;
    String memberName, netAmount, grossAmout, closingDate,payoutNo,payoutlistResponceCode;
    List<String> payoutMemberArray = new ArrayList<String>();
    List<String> netAmountArray = new ArrayList<String>();
    List<String> grossAmountArray = new ArrayList<String>();
    List<String> closingDateArray = new ArrayList<String>();
    List<String> payoutNoArray = new ArrayList<String>();
  //  List<String> pyotlistNoArray = new ArrayList<String>();
    int noOfObjects;
    public ArrayList<ModelClass> customPayOutListArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    String payotlistNo;
    ListView payoutListView;
    String payout_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/PayoutList/" + userId;
    TextView plEmptyTv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.payoutlist_fragment, container, false);

       // userId = getActivity().getIntent().getStringExtra("email");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email","");


        payoutListView = (ListView) view.findViewById(R.id.payout_listView);
        plEmptyTv = (TextView)view.findViewById(R.id.payout_list_emptyTv);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            PayoutList productTask = new PayoutList();
            productTask.execute(payout_url);
           /* Thread downlineThread = new Thread(new PayoutListThread());
            downlineThread.start();*/


        }

        return view;
    }

    public class PayoutList extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading PayoutList ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/PayoutList/" + userId);


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Payout List Response:", response);
                // String response = jsonResponse;
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    noOfObjects = jsonArray.length();
                  //  Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");
                    // pd.dismiss();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                        Log.v("No of times " + i, "shhhhhhhhh");
                        int k = i+1;
                        payotlistNo = ""+k;
                        memberName = jobj.getString("MemberName");
                       // Log.v("Member Name", memberName);

                        netAmount = jobj.getString("NetAmount");
                       //Log.v("netAmount ", netAmount);

                        grossAmout = jobj.getString("GrossAmount");
                       // Log.v("grossAmout Name", grossAmout);

                        closingDate = jobj.getString("ClosingDate");
                       // Log.v("ClosingDate", closingDate);

                        payoutNo = jobj.getString("PayoutNo");
                       // Log.v("payout No ",payoutNo);
                        payoutlistResponceCode = jobj.getString("ResponceCode");
                        //adding value to array
                        payoutMemberArray.add(memberName);
                        netAmountArray.add(netAmount);
                        grossAmountArray.add(grossAmout);
                        closingDateArray.add(closingDate);
                        payoutNoArray.add(payoutNo);

                      //  pyotlistNoArray.add(payotlistNo);

                    }
                    for (int j = 0; j < payoutMemberArray.size(); j++) {
                        final ModelClass model = new ModelClass();

                        //take data into model object
                        model.setPayoutMember(payoutMemberArray.get(j));
                        model.setNetAmount(netAmountArray.get(j));
                        model.setGrossAmount(grossAmountArray.get(j));
                        model.setClosingDate(closingDateArray.get(j));
                        model.setPayoutNo(payoutNoArray.get(j));
                      //  model.setPayotlistNo(pyotlistNoArray.get(j));

                        customPayOutListArray.add(model);

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
                if(payoutlistResponceCode.equals("0")) {
                    payoutListView.setEmptyView(plEmptyTv);
                    pDialog.dismiss();
                }
                else {

                    payoutListView.setAdapter(new PayoutListCustomAdapter(getActivity(), customPayOutListArray));
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
    /*public class PayoutListThread implements Runnable {
        @Override
        public void run() {
            HttpClient payoutClient = new DefaultHttpClient();
            String payout_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/PayoutList/" + userId;
            HttpPost post = new HttpPost(payout_url);

            try {
                HttpResponse responsePOST = payoutClient.execute(post);
                HttpEntity resEntity = responsePOST.getEntity();
                String response = EntityUtils.toString((resEntity));
                Log.v("Payout response :", response);
                JSONArray jArr = new JSONArray(response);
                for (int i = 0; i < jArr.length(); i++) {
                    JSONObject jobj = jArr.getJSONObject(i);

                    memberName = jobj.getString("MemberName");
                    Log.v("Member Name", memberName);

                    netAmount = jobj.getString("NetAmount");
                    Log.v("netAmount ", netAmount);

                    grossAmout = jobj.getString("GrossAmount");
                    Log.v("grossAmout Name", grossAmout);

                    closingDate = jobj.getString("ClosingDate");
                    Log.v("ClosingDate", closingDate);

                    //adding value to array
                    payoutMemberArray.add(memberName);
                    netAmountArray.add(netAmount);
                    grossAmountArray.add(grossAmout);
                    closingDateArray.add(closingDate);

                }
                for (int j = 0; j <= payoutMemberArray.size(); j++) {
                    final ModelClass model = new ModelClass();

                    //take data into model object
                    model.setMemberName(payoutMemberArray.get(j));
                    model.setNetAmount(netAmountArray.get(j));
                    model.setGrossAmount(grossAmountArray.get(j));
                    model.setClosingDate(closingDateArray.get(j));

                    customPayOutListArray.add(model);

                }


            } catch (Exception e) {

            }
        }
    }
*/


