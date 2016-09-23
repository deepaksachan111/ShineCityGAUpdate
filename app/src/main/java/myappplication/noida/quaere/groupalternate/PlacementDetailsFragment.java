package myappplication.noida.quaere.groupalternate;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
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
public class PlacementDetailsFragment extends Fragment {

    String userId, myDirectUrl, myDownLineUrl;
    String downline_response, selfBussiness, teamBusiness;
    TextView downlineTv, selfBznsTv, teamBznsTv;
    String memberName, joiningDate, loginId, mobileNo, pdResponceCode;//,detailNo;
    ListView p_detail_list;
    TextView pdEmptyTv;
    //List<String> detailNoArray = new ArrayList<String>();
    List<String> memeberNameArray = new ArrayList<String>();
    List<String> joiningDateArray = new ArrayList<String>();
    List<String> loginIdArray = new ArrayList<String>();
    List<String> mobileNoArray = new ArrayList<String>();
    public ArrayList<ModelClass> customListArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    AlertDialog alert;
    int noOfObjects;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.placement_detail_fragment, container, false);
        downlineTv = (TextView) view.findViewById(R.id.downlineTv);
        //   pdEmptyTv = (TextView)view.findViewById(R.id.plac)
      //  userId = getActivity().getIntent().getStringExtra("email");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email","");


        System.out.println("User id is :" + userId);
        //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        myDirectUrl = "http://demo8.mlmsoftindia.com/ShinePanel.svc/Direct/" + userId;
        myDownLineUrl = "http://demo8.mlmsoftindia.com/ShinePanel.svc/BussinessCalculation/" + userId;


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
          /*  Thread downlineThread = new Thread(new MyDownlineThread());
            downlineThread.start();*/

            new DownLineAsyncTask().execute(myDownLineUrl);
            UseService productTask = new UseService();
            productTask.execute(myDirectUrl);
            // myDownLine(myDownLineUrl);

        }

        p_detail_list = (ListView) view.findViewById(R.id.placement_detail_listview);


        downlineTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myDownLine(myDownLineUrl);
                myDownLineView();

            }
        });


        return view;
    }

    public void myDownLineView() {
        // Log.v("inside onClick", "myDownline()");
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT > 10) {
            builder = new AlertDialog.Builder(getActivity(),
                    R.style.DialogSlideAnim);
        } else {
            builder = new AlertDialog.Builder(getActivity());

        }
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View downlineView = inflater.inflate(R.layout.placement_detail_downline, null);

        selfBznsTv = (TextView) downlineView.findViewById(R.id.downline_selfbznsTv);
        teamBznsTv = (TextView) downlineView.findViewById(R.id.downline_teambznsTv);

        selfBznsTv.setText(selfBussiness);
        teamBznsTv.setText(teamBusiness);

        builder.setView(downlineView);

        TextView title = new TextView(getActivity());

        // Customizing title bar of aler dialog
        title.setText(" My Downline ");
        title.setBackgroundColor(Color.WHITE);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);

        builder.setCustomTitle(title);
        builder.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                    // showPopUp();
                });

        Button cancelbtn = new Button(getActivity());
        // customizing cancel button
        cancelbtn.setBackgroundColor(Color.WHITE);
        cancelbtn.setGravity(Gravity.CENTER);
        cancelbtn.setTextColor(Color.parseColor("#77B800"));
        cancelbtn.setTextSize(20);
        cancelbtn.setPadding(10, 10, 10, 10);
        // builder.setCustomButton(cancelbtn);

        alert = builder.create();

        alert.getWindow().setGravity(Gravity.BOTTOM);
        alert.show();
        Button alertbtn = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        alertbtn.setBackgroundColor(Color.WHITE);
        alertbtn.setTextColor(Color.parseColor("#77B800"));
        //  myDownLinemethod();


    }


    public  class UseService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Details ...");
            pDialog.setIndeterminate(false);
            //  pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/Direct/" + userId);


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("PDDDDDD post response :", response);
                // String response = jsonResponse;
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    noOfObjects = jsonArray.length();
                    Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");
                    // pd.dismiss();
                    for (int j = 0; j < jsonArray.length(); j++) {

                        JSONObject jObj = jsonArray.getJSONObject(j);

                        memberName = jObj.getString("MemberName");
                        //   Log.v("No of times " + j, "shhhhhhhhh");
                        int k = j + 1;
                        // detailNo = ""+k;


                        mobileNo = jObj.getString("Mobile");

                        loginId = jObj.getString("LoginID");

                        joiningDate = jObj.getString("JoiningDate");
                        pdResponceCode = jObj.getString("ResponceCode");

                        //detailNoArray.add(detailNo);
                        memeberNameArray.add(memberName);
                        mobileNoArray.add(mobileNo);
                        loginIdArray.add(loginId);
                        joiningDateArray.add(joiningDate);

                    }
                    for (int k = 0; k < memeberNameArray.size(); k++) {

                        final ModelClass model = new ModelClass();

                        //******* Firstly take data in model object ******//**//*
                        model.setMemberName(memeberNameArray.get(k));
                        model.setMobileNo(mobileNoArray.get(k));
                        model.setLoginId(loginIdArray.get(k));
                        model.setJoiningDate(joiningDateArray.get(k));
                        // model.setPdrecordno(detailNoArray.get(k));
                        customListArray.add(model);
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
                if (pdResponceCode.equals("0")) {
                    Toast.makeText(getActivity(), "Placement details is Empty", Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                } else {

                    p_detail_list.setAdapter(new PlacementDetailAdapter(getActivity(), customListArray));
                    Toast toast = Toast.makeText(getContext(), "Total Records are : " + noOfObjects, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.getView().setPadding(20, 20, 20, 20);
                    toast.getView().setBackgroundColor(Color.parseColor("#7CB342"));
                    toast.show();
                    pDialog.dismiss();
                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), "Server is Failed ", Toast.LENGTH_LONG).show();
            }
        }
    }

    class DownLineAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            //  Log.v("inside myDownline()", "inside myDownline()");
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/BussinessCalculation/" + userId);
            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                downline_response = EntityUtils.toString(httpEntity);

                Log.i("Downline responseeee :", downline_response);
                JSONArray array = new JSONArray(downline_response);
                JSONObject obj = array.getJSONObject(0);

                selfBussiness = obj.getString("SelfBussiness");
                //  Log.v("SelfBussiness", selfBussiness);

                teamBusiness = obj.getString("TeamBussiness");
                //  Log.v("TeamBussiness", teamBusiness);


            } catch (Exception e) {
                Log.v("mydownline Exception", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
          /*  try{
                Toast.makeText(getActivity(),"Placement details is Empty",Toast.LENGTH_LONG).show();
            }
            catch(Exception e){
                Toast.makeText(getActivity(),"Server is shut down "+e.toString(),Toast.LENGTH_LONG).show();
            }*/


//            Log.v("Downline response :", downline_response);

        }
    }
}
