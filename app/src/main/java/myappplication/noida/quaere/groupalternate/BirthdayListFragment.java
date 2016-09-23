package myappplication.noida.quaere.groupalternate;

/**
 * Created by intex on 10/17/2015.
 */

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
import android.widget.ImageView;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BirthdayListFragment extends Fragment {
    String userId;
    String birthdayName, birthdayDob, birthdayLoginId, birthdaymemType, birthdayMobile, birthdayResponseCode;
    String bdMemType, bdFromDate, bdToDate;
    // List<String> cbSerialNoArray = new ArrayList<String>();
    List<String> birthdayNameArray = new ArrayList<String>();
    List<String> birthdayDobArray = new ArrayList<String>();
    List<String> birthdayLoginIdArray = new ArrayList<String>();
    List<String> birthdaymemTypeArray = new ArrayList<String>();
    List<String> birthdayMobileArray = new ArrayList<String>();

    public ArrayList<ModelClass> birthdayListArray = new ArrayList<ModelClass>();
    TextView bdEmptyTv;
    private ProgressDialog pDialog;
    int noOfObjects;
    ListView birthdayListView;
    String birthday_url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.birthday_list_fragment, container, false);
        birthdayListView = (ListView) view.findViewById(R.id.birthday_listView);
        bdEmptyTv = (TextView) view.findViewById(R.id.birthdayList_emptyTv);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("email", "");

        bdMemType = this.getArguments().getString("bdMemType");
        bdFromDate = this.getArguments().getString("bdFromDate");
        bdToDate = this.getArguments().getString("bdToDate");


        birthday_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/BirthDayList/" + userId + "/" + bdFromDate + "/" + bdToDate + "/" + bdMemType;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            BirthdayListTask productTask = new BirthdayListTask();
            productTask.execute();

        }

        return view;
    }

    public class BirthdayListTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading BirthDay List ...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(birthday_url);


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Birthday List Response:", response);
                // String response = jsonResponse;
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    noOfObjects = jsonArray.length();
                   // Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                        birthdayName = jobj.getString("DisplayName");
                        // Log.v("DisplayName", birthdayName);

                        birthdayDob = jobj.getString("DOB");


                        birthdayLoginId = jobj.getString("LoginId");

                        birthdaymemType = jobj.getString("MemberType");

                        birthdayMobile = jobj.getString("MobileNo");
                        birthdayResponseCode = jobj.getString("ResponceCode");
                        // Log.v("MobileNo", birthdayMobile);


                        //adding value to array
                        // cbSerialNoArray.add(cbSerialNo);
                        birthdayNameArray.add(birthdayName);
                        birthdayDobArray.add(birthdayDob);
                        birthdayLoginIdArray.add(birthdayLoginId);
                        birthdaymemTypeArray.add(birthdaymemType);
                        birthdayMobileArray.add(birthdayMobile);


                    }
                    for (int j = 0; j < birthdayNameArray.size(); j++) {
                        final ModelClass model = new ModelClass();

                        //take data into model object
                        // model.setCbSerialNo(cbSerialNoArray.get(j));
                        model.setBirthdayName(birthdayNameArray.get(j));
                        model.setBirthdayDob(birthdayDobArray.get(j));
                        model.setBirthdayLoginId(birthdayLoginIdArray.get(j));
                        model.setBirthdaymemType(birthdaymemTypeArray.get(j));
                        model.setBirthdayMobile(birthdayMobileArray.get(j));

                        birthdayListArray.add(model);

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
                if (birthdayResponseCode.equals("0")) {
                    birthdayListView.setEmptyView(bdEmptyTv);
                    pDialog.dismiss();

                } else {

                    birthdayListView.setAdapter(new BirthdayListAdapter(getActivity(), birthdayListArray));
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

    // **************** Adapter Class *****************//
    class BirthdayListAdapter extends BaseAdapter {
        String currentDate, userDate,bdPersonName;
        DateFormat df;
        java.util.Date date1, date2;
        private Activity activity;
        private ArrayList<ModelClass> data;
        private LayoutInflater inflators = null;
        ModelClass tempValues;

        public BirthdayListAdapter(Activity parent, ArrayList<ModelClass> adlist) {

            activity = parent;
            data = adlist;
            inflators = (LayoutInflater) ((Activity) activity)
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            df = new SimpleDateFormat("d-MMM");
            currentDate = df.format(Calendar.getInstance().getTime());
            Log.v("Current Date :", currentDate);

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
            public TextView birthdayNameTv, birthdayDobTv, birthdayLoginIdTv, birthdaymemTypeTv, birthdayMobileTv;
            public ImageView birthday_cakeimg;


        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            ViewHolder holder;
            holder = new ViewHolder();


            if (convertView == null) {
                vi = inflators.inflate(R.layout.birthday_rowlist, null);
                holder = new ViewHolder();
                holder.birthdayNameTv = (TextView) vi.findViewById(R.id.birthday_nameTv);
                holder.birthdayDobTv = (TextView) vi.findViewById(R.id.birthday_dobTv);
                holder.birthdayLoginIdTv = (TextView) vi.findViewById(R.id.birthday_loginIdTv);
                holder.birthdaymemTypeTv = (TextView) vi.findViewById(R.id.birthday_memTypeTv);
                holder.birthdayMobileTv = (TextView) vi.findViewById(R.id.birthday_mobileTv);
                holder.birthday_cakeimg = (ImageView) vi.findViewById(R.id.birthday_cake);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            if (data.size() <= 0) {
                // holder.cbSrNo.setText("No Data is avaiblable");

            } else {
                tempValues = null;
                tempValues = (ModelClass) data.get(position);

                /************ Set Model values in Holder elements ***********/

                //  holder.cbSrNo.setText(tempValues.getCbSerialNo());
                holder.birthdayNameTv.setText(tempValues.getBirthdayName());
                holder.birthdayDobTv.setText(tempValues.getBirthdayDob());
                holder.birthdayLoginIdTv.setText(tempValues.getBirthdayLoginId());
                holder.birthdaymemTypeTv.setText(tempValues.getBirthdaymemType());
                holder.birthdayMobileTv.setText(tempValues.getBirthdayMobile());

                userDate = holder.birthdayDobTv.getText().toString().trim();
            }
            //taking birthddate and parsing it
            try {
                date1 = df.parse(currentDate);
                date2 = df.parse(userDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date1.equals(date2)) {
                bdPersonName = holder.birthdayNameTv.getText().toString().trim();

                holder.birthday_cakeimg.setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(getContext(), "Wish "+bdPersonName+"  a Happy Birthday !", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 50, 50);
                toast.getView().setPadding(20, 20, 20, 20);
                toast.getView().setBackgroundColor(Color.parseColor("#698fc7"));
                toast.show();
                holder.birthday_cakeimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(v.getContext(), "Wish Him.. Happy Birthday !", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 50, 50);
                        toast.getView().setPadding(20, 20, 20, 20);
                        toast.getView().setBackgroundColor(Color.parseColor("#698fc7"));
                        toast.show();
                    }
                });
            } else {
                holder.birthday_cakeimg.setVisibility(View.GONE);
            }
            return vi;
        }
    }
}
