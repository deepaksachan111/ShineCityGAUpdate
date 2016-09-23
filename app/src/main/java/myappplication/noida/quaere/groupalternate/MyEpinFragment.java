package myappplication.noida.quaere.groupalternate;

/**
 * Created by intex on 10/21/2015.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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


public class MyEpinFragment extends Fragment {
    String userId, memberId;
    String epinCreatedDate,
            epinDesignation,
            epinEpinNo,
            epinMamberID,
            epinMemberName,
            epinRegisteredTo,
            epinResponceCode,
            epinUsedDate;
    // List<String> cbSerialNoArray = new ArrayList<String>();
    List<String> epinCreatedDateArray = new ArrayList<String>();
    List<String> epinDesignationArray = new ArrayList<String>();
    List<String> epinEpinNoArray = new ArrayList<String>();
    List<String> epinMamberIDArray = new ArrayList<String>();
    List<String> epinMemberNameArray = new ArrayList<String>();
    List<String> epinRegisteredToArray = new ArrayList<String>();
    List<String> epinUsedDateArray = new ArrayList<String>();


    public ArrayList<ModelClass> myEpinCustomArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    int noOfObjects;
    ListView epinListView;
    String epin_url;//= "http://demo8.mlmsoftindia.com/ShinePanel.svc/EPinDetails/tnabi786/-1/-1/-1/-1/-1";
    TextView epinEmpty;
    String epinStatusName, designationId, statusName, epinNO, epinFromDate, epinToDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_epin_fragment, container, false);
        epinListView = (ListView) view.findViewById(R.id.my_epin_listView);
        epinEmpty = (TextView) view.findViewById(R.id.my_epin_emptyTv);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email", "");
        memberId = sharedPreferences.getString("memberId", "");
       // memberId = getActivity().getIntent().getStringExtra("memberId");

        epinStatusName = this.getArguments().getString("statusName");
        designationId = this.getArguments().getString("designationId");
        epinNO = this.getArguments().getString("epinNO");
        epinFromDate = this.getArguments().getString("epinFromDate");
        epinToDate = this.getArguments().getString("epinToDate");


        epin_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/EPinDetails/" + userId + "/" + epinStatusName + "/" + epinNO + "/" + epinFromDate + "/" + epinToDate + "/" + designationId;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            MyEpinTask productTask = new MyEpinTask();
            productTask.execute();

        }

        return view;
    }


    public class MyEpinTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Epin Details ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient dueEmiClient = new DefaultHttpClient();
            HttpPost dueEmiPost = new HttpPost(epin_url);


            try {
                HttpResponse httpResponse = dueEmiClient.execute(dueEmiPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("My Epin Response:", response);
                // String response = jsonResponse;
                try {

                    JSONArray jsonArray = new JSONArray(response);
                     noOfObjects = jsonArray.length();
                    //   Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);


                        epinCreatedDate = jobj.getString("CreatedDate");

                        epinDesignation = jobj.getString("Designation");

                        epinEpinNo = jobj.getString("EpinNo");

                        epinMamberID = jobj.getString("MamberID");

                        epinMemberName = jobj.getString("MemberName");

                        epinRegisteredTo = jobj.getString("RegisteredTo");

                        epinUsedDate = jobj.getString("UsedDate");

                        epinResponceCode = jobj.getString("ResponceCode");


                        //adding value to array
                        epinCreatedDateArray.add(epinCreatedDate);
                        epinDesignationArray.add(epinDesignation);
                        epinEpinNoArray.add(epinEpinNo);
                        epinMamberIDArray.add(epinMamberID);
                        epinMemberNameArray.add(epinMemberName);
                        epinRegisteredToArray.add(epinRegisteredTo);
                        epinUsedDateArray.add(epinUsedDate);


                    }
                    for (int j = 0; j < epinEpinNoArray.size(); j++) {
                        final ModelClass model = new ModelClass();

                        //take data into model object
                        model.setEpinCreatedDate(epinCreatedDateArray.get(j));
                        model.setEpinDesignation(epinDesignationArray.get(j));
                        model.setEpinEpinNo(epinEpinNoArray.get(j));
                        model.setEpinMamberID(epinMamberIDArray.get(j));
                        model.setEpinMemberName(epinMemberNameArray.get(j));
                        model.setEpinRegisteredTo(epinRegisteredToArray.get(j));
                        model.setEpinUsedDate(epinUsedDateArray.get(j));


                        myEpinCustomArray.add(model);

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
                if (epinResponceCode.equals("0")) {
                    epinListView.setEmptyView(epinEmpty);
                    pDialog.dismiss();
                } else {

                    epinListView.setAdapter(new MyEpinCustomAdapter(getActivity(), myEpinCustomArray, epinStatusName));
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

    class MyEpinCustomAdapter extends BaseAdapter {

        private Activity activity;
        private ArrayList<ModelClass> data;
        private LayoutInflater inflators = null;
        ModelClass tempValues;
        String epinStatus;

        public MyEpinCustomAdapter(Activity parent, ArrayList<ModelClass> adlist, String epinStatus) {

            activity = parent;
            data = adlist;
            inflators = (LayoutInflater) ((Activity) activity)
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.epinStatus = epinStatus;
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

        class ViewHolder1 {
            public TextView epinCreatedDateTv,
                    epinDesignationTv,
                    epinEpinNoTv,
                    epinMamberIDTv,
                    epinMemberNameTv,
                    epinRegisteredToTv,
                    epinUsedDateTv,
                    epinRegisteredTv,
                    epinNewRegisterationTv;

            public ViewHolder1(View vi) {
                epinCreatedDateTv = (TextView) vi.findViewById(R.id.epin_createdDateTv);
                epinDesignationTv = (TextView) vi.findViewById(R.id.epin_designationTv);
                epinEpinNoTv = (TextView) vi.findViewById(R.id.epin_epinNoTv);
                epinMamberIDTv = (TextView) vi.findViewById(R.id.epin_memberIdTv);
                epinMemberNameTv = (TextView) vi.findViewById(R.id.epin_memberNameTv);
                epinRegisteredToTv = (TextView) vi.findViewById(R.id.epin_registeredToTv);
                epinUsedDateTv = (TextView) vi.findViewById(R.id.epin_usedDateTv);
                epinRegisteredTv = (TextView) vi.findViewById(R.id.epin_registeredTv);
                epinNewRegisterationTv = (TextView) vi.findViewById(R.id.epin_newRegistrationTv);


            }

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            final ViewHolder1 holder;
            if (convertView == null) {
                /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
                vi = inflators.inflate(R.layout.my_epin_rowlist, null);
                /******** View Holder Object to contain tabitem.xml file elements ************/
                holder = new ViewHolder1(vi);

                /************ Set holder with LayoutInflater ************/
                vi.setTag(holder);
            } else
                holder = (ViewHolder1) vi.getTag();

            if (data.size() <= 2) {


            } else {
                tempValues = null;
                tempValues = (ModelClass) data.get(position);

                /************ Set Model values in Holder elements ***********/

                holder.epinCreatedDateTv.setText(tempValues.getEpinCreatedDate());
                holder.epinDesignationTv.setText(tempValues.getEpinDesignation());
                holder.epinEpinNoTv.setText(tempValues.getEpinEpinNo());
                holder.epinMamberIDTv.setText(tempValues.getEpinMamberID());
                holder.epinMemberNameTv.setText(tempValues.getEpinMemberName());
                holder.epinRegisteredToTv.setText(tempValues.getEpinRegisteredTo());
                holder.epinUsedDateTv.setText(tempValues.getEpinUsedDate());

                if (epinStatus.equals("Used")) {
                    holder.epinRegisteredTv.setVisibility(View.VISIBLE);
                    holder.epinNewRegisterationTv.setVisibility(View.GONE);
                } else {
                    holder.epinNewRegisterationTv.setVisibility(View.VISIBLE);
                    holder.epinRegisteredTv.setVisibility(View.GONE);


                    holder.epinNewRegisterationTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          //  Toast.makeText(activity, "Registration Clicked", Toast.LENGTH_SHORT).show();
                           // TextView epinEpinNOoTvvvv = (TextView) v.findViewById(R.id.epin_epinNoTv);
                            String EpinNO = holder.epinEpinNoTv.getText().toString().trim();
                           // TextView epinEpinDesignationvvvv = (TextView) v.findViewById(R.id.epin_designationTv);
                            String EpinDesignation =  holder.epinDesignationTv.getText().toString().trim();

                            Intent i = new Intent(activity, EpinRegisterActivity.class);
                            i.putExtra("EpinNO", EpinNO);
                            i.putExtra("EpinDesignation", EpinDesignation);
                            i.putExtra("memberId", memberId);
                            i.putExtra("userId", userId);
                            startActivity(i);
                        }
                    });
                }

            }

            return vi;
        }


    }
}



