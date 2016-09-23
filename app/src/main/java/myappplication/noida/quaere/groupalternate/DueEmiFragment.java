package myappplication.noida.quaere.groupalternate;

/**
 * Created by intex on 10/20/2015.
 */

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

public class DueEmiFragment extends Fragment {
    String userId;
    String  dueEmiCustomerName, dueEmiCustomerId, dueEmidueAmount, dueEmiDueDate, dueEmiPaidAmount, dueEmiPlotNo,dueEmiPlotArea,dueEmiPlotAmount,dueEmiProjectName,dueEmiResponceCode;
    List<String> dueEmiCustomerNameArray = new ArrayList<String>();
    List<String> dueEmiCustomerIdArray = new ArrayList<String>();
    List<String> dueEmidueAmountArray = new ArrayList<String>();
    List<String> dueEmiDueDateArray = new ArrayList<String>();
    List<String> dueEmiPaidAmountArray = new ArrayList<String>();
    List<String> dueEmiPlotNoArray = new ArrayList<String>();
    List<String> dueEmiPlotAreaArray = new ArrayList<String>();
    List<String> dueEmiPlotAmountArray = new ArrayList<String>();
    List<String> dueEmiProjectNameArray = new ArrayList<String>();

    public ArrayList<ModelClass> dueEmiCustomArray = new ArrayList<ModelClass>();
    private ProgressDialog pDialog;
    int noOfObjects;
    ListView dueEmiListView;
    String due_emi_url ;
    TextView emiEmpty;
    String  emiSiteId, emiFromDate, emiToDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.due_emi_fragment, container, false);
        dueEmiListView = (ListView) view.findViewById(R.id.due_emi_listView);
        emiEmpty = (TextView)view.findViewById(R.id.emi_emptyTv);

        userId = getActivity().getIntent().getStringExtra("email");
        emiSiteId = this.getArguments().getString("emiSiteId");
        emiFromDate = this.getArguments().getString("emiFromDate");
        emiToDate = this.getArguments().getString("emiToDate");
        System.out.println("userId"+userId+ "  emiSiteId"+emiSiteId+ "  emiFromDate"+emiFromDate+ "  emiToDate"+emiToDate);



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            due_emi_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/DueEMIReport/"+userId+"/"+emiSiteId+"/"+emiFromDate+"/"+emiToDate;
            DueEmiTask productTask = new DueEmiTask();
            productTask.execute();

        }

        return view;
    }

    public class DueEmiTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Due Emi ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient dueEmiClient = new DefaultHttpClient();
            HttpPost dueEmiPost = new HttpPost(due_emi_url);


            try {
                HttpResponse httpResponse = dueEmiClient.execute(dueEmiPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Due Emi Response:", response);
                // String response = jsonResponse;
                try {

                    JSONArray jsonArray = new JSONArray(response);
                     noOfObjects = jsonArray.length();
                    //   Log.v("Number of json Obj " + noOfObjects, "   pd Objects.....");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);



                        dueEmiCustomerName = jobj.getString("CustomerName");
                     //   Log.v("CustomerName", dueEmiCustomerName);

                        dueEmiCustomerId = jobj.getString("CustomerID");
                       // Log.v("CustomerID ", dueEmiCustomerId);

                        dueEmidueAmount = jobj.getString("DueAmount");
                      //  Log.v("DueAmount", dueEmidueAmount);

                        dueEmiDueDate = jobj.getString("DueDate");
                     //   Log.v("dueEmiDueDate", dueEmiDueDate);

                        dueEmiPaidAmount = jobj.getString("PaidAmount");
                     //   Log.v("PaidAmount", dueEmiPaidAmount);

                        dueEmiPlotNo = jobj.getString("PloNo");
                      //  Log.v("PloNo ", dueEmiPlotNo);

                        dueEmiPlotArea = jobj.getString("PlotArea");
                       // Log.v("PlotArea", dueEmiPlotArea);

                        dueEmiPlotAmount = jobj.getString("PlotAmount");
                       // Log.v("PlotAmount", dueEmiPlotAmount);

                        dueEmiProjectName = jobj.getString("ProjectName");
                     //   Log.v("ProjectName ", dueEmiProjectName);

                        dueEmiResponceCode = jobj.getString("ResponceCode");

                        //adding value to array
                        // cbSerialNoArray.add(cbSerialNo);
                        dueEmiCustomerNameArray.add(dueEmiCustomerName);
                        dueEmiCustomerIdArray.add(dueEmiCustomerId);
                        dueEmidueAmountArray.add(dueEmidueAmount);
                        dueEmiDueDateArray.add(dueEmiDueDate);
                        dueEmiPaidAmountArray.add(dueEmiPaidAmount);
                        dueEmiPlotNoArray.add(dueEmiPlotNo);
                        dueEmiPlotAreaArray.add(dueEmiPlotArea);
                        dueEmiPlotAmountArray.add(dueEmiPlotAmount);
                        dueEmiProjectNameArray.add(dueEmiProjectName);

                    }
                    for (int j = 0; j < dueEmiCustomerNameArray.size(); j++) {
                        final ModelClass model = new ModelClass();

                        //take data into model object
                        // model.setCbSerialNo(cbSerialNoArray.get(j));
                        model.setDueEmiCustomerName(dueEmiCustomerNameArray.get(j));
                        model.setDueEmiCustomerId(dueEmiCustomerIdArray.get(j));
                        model.setDueEmidueAmount(dueEmidueAmountArray.get(j));
                        model.setDueEmiDueDate(dueEmiDueDateArray.get(j));
                        model.setDueEmiPaidAmount(dueEmiPaidAmountArray.get(j));
                        model.setDueEmiPlotNo(dueEmiPlotNoArray.get(j));
                        model.setDueEmiPlotArea(dueEmiPlotAreaArray.get(j));
                        model.setDueEmiPlotAmount(dueEmiPlotAmountArray.get(j));
                        model.setDueEmiProjectName(dueEmiProjectNameArray.get(j));

                        dueEmiCustomArray.add(model);

                    }
                    // p_detail_list.setAdapter(new PlacementDetailAdapter(getActivity(), customListArray));

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(" JSON Exception is caught here ......." + e.toString());
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
                if(dueEmiResponceCode.equals("0")) {
                    dueEmiListView.setEmptyView(emiEmpty);
                    pDialog.dismiss();
                }
                else {

                    dueEmiListView.setAdapter(new DueEmiCustomAdapter(getActivity(), dueEmiCustomArray));
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
    class DueEmiCustomAdapter extends BaseAdapter {

        private Activity activity;
        private ArrayList<ModelClass> data;
        private LayoutInflater inflators = null;
        ModelClass tempValues;

        public DueEmiCustomAdapter(Activity parent, ArrayList<ModelClass> adlist) {

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
            public TextView dueEmiCustomerNameTv, dueEmiCustomerIdTv, dueEmidueAmountTv, dueEmiDueDateTv, dueEmiPaidAmountTv, dueEmiPlotNoTv,dueEmiPlotAreaTv,
                    dueEmiPlotAmountTv,dueEmiProjectNameTv;

            public ViewHolder(View vi){
                dueEmiCustomerNameTv = (TextView) vi.findViewById(R.id.due_emi_customer_nameTv);
                dueEmiCustomerIdTv = (TextView) vi.findViewById(R.id.due_emi_customer_idTv);
                dueEmidueAmountTv = (TextView) vi.findViewById(R.id.due_emi_due_amountTv);
                dueEmiDueDateTv = (TextView) vi.findViewById(R.id.due_emi_due_dateTv);
                dueEmiPaidAmountTv = (TextView) vi.findViewById(R.id.due_emi_paid_amountTv);
                dueEmiPlotNoTv = (TextView) vi.findViewById(R.id.due_emi_plotNoTv);
                dueEmiPlotAreaTv = (TextView) vi.findViewById(R.id.due_emi_plot_areaTv);
                dueEmiPlotAmountTv = (TextView) vi.findViewById(R.id.due_emi_plot_amountTv);
                dueEmiProjectNameTv = (TextView) vi.findViewById(R.id.due_emi_projectNameTv);


            }

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            ViewHolder holder;
            if (convertView == null) {
                /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
                vi = inflators.inflate(R.layout.due_emi_rowlist, null);
                /******** View Holder Object to contain tabitem.xml file elements ************/
                holder = new ViewHolder(vi);

                // holder.cbSrNo = (TextView) vi.findViewById(R.id.cb_SrNoTv);

                /************ Set holder with LayoutInflater ************/
                vi.setTag(holder);
            } else
                holder = (ViewHolder) vi.getTag();

            if (data.size() <= 2) {

                // holder.cbSrNo.setText("No Data is avaiblable");
                holder.dueEmiCustomerNameTv.setText("No Data is avaiblable");
                holder.dueEmiCustomerIdTv.setText("No Data is avaiblable");
                holder.dueEmidueAmountTv.setText("No Data is avaiblable");
                holder.dueEmiPlotNoTv.setText("No Data is avaiblable");
            } else {
                tempValues = null;
                tempValues = (ModelClass) data.get(position);

                /************ Set Model values in Holder elements ***********/
                // System.out.println("insiiidddddddeeeeee setting value to the adapter......");

                // holder.text.setText(tempValues.getType());
                //  holder.cbSrNo.setText(tempValues.getCbSerialNo());
                holder.dueEmiCustomerNameTv.setText(tempValues.getDueEmiCustomerName());
                holder.dueEmiCustomerIdTv.setText(tempValues.getDueEmiCustomerId());
                holder.dueEmidueAmountTv.setText(tempValues.getDueEmidueAmount());
                holder.dueEmiDueDateTv.setText(tempValues.getDueEmiDueDate());
                holder.dueEmiPaidAmountTv.setText(tempValues.getDueEmiPaidAmount());
                holder.dueEmiPlotNoTv.setText(tempValues.getDueEmiPlotNo());
                holder.dueEmiPlotAreaTv.setText(tempValues.getDueEmiPlotArea());
                holder.dueEmiPlotAmountTv.setText(tempValues.getDueEmiPlotAmount());
                holder.dueEmiProjectNameTv.setText(tempValues.getDueEmiProjectName());


            }
            return vi;
        }
    }

}

