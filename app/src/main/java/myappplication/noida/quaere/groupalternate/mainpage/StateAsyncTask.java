package myappplication.noida.quaere.groupalternate.mainpage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import myappplication.noida.quaere.groupalternate.ModelClass;
import myappplication.noida.quaere.groupalternate.R;

/**
 * Created by intex on 3/4/2016.
 */
public class StateAsyncTask extends AsyncTask<Void, Void, Void> {

    String stateName, stateId;
    ArrayList<String> stateName_list, stateId_list;
    ArrayList<ModelClass> state_list = new ArrayList<ModelClass>();
    StateAdapter stateAdapter;
    Spinner stateSpinner;
    Activity activity;
    ProgressDialog pd;
    public StateAsyncTask(Activity activity, ArrayList<String> countryName_list, ArrayList<String> countryId_list, Spinner countrySpin) {
        this.activity = activity;
        this.stateName_list = countryName_list;
        this.stateId_list = countryId_list;
        this.stateSpinner = countrySpin;


    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(activity);
        pd.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/shinepanel.svc/GetState");
        try {
            HttpResponse httpResponse = httpClient.execute(httppost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(httpEntity);
            Log.v("Get State response:", response);
            JSONArray stateArray = new JSONArray(response);

            for (int i = 0; i < stateArray.length(); i++) {

                JSONObject stateObj = stateArray.getJSONObject(i);
                stateName = stateObj.getString("StateName");
                stateId = stateObj.getString("StateID");

                stateName_list.add(stateName);
                stateId_list.add(stateId);

                ModelClass m = new ModelClass();
                m.setStateName(stateName_list.get(i));
                m.setStateId(stateId_list.get(i));
                state_list.add(m);

            }
        } catch (Exception e) {
            Log.v("Exception in state s", e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            stateAdapter = new StateAdapter(activity, android.R.layout.simple_spinner_item, state_list);
            stateSpinner.setAdapter(stateAdapter);
            pd.dismiss();
        } catch (Exception e) {
            Toast.makeText(activity, "Server is not responding ", Toast.LENGTH_LONG).show();
            pd.dismiss();
        }
    }

    class StateAdapter extends BaseAdapter {
        private Activity activity;
        private ArrayList<ModelClass> data;
        private LayoutInflater inflators = null;
        ModelClass tempValues;

        public StateAdapter(Activity context, int textViewResourceId,
                            ArrayList<ModelClass> values) {
            this.activity = context;
            this.data = values;
            inflators = (LayoutInflater) ((Activity) context)
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

         public class ViewHolder{
             public TextView brnchName;
             public ViewHolder(View vi) {
                 brnchName = (TextView) vi.findViewById(R.id.branch_spinner_rowTv);
             }
         }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            ViewHolder holder;
            if (convertView == null) {
                vi = inflators.inflate(R.layout.branch_spinner_row, null);
                holder = new ViewHolder(vi);
                vi.setTag(holder);
            } else
                holder = (ViewHolder) vi.getTag();

            if (data.size() <= 0) {
            } else {
                tempValues = null;
                tempValues = (ModelClass) data.get(position);
                holder.brnchName.setText(tempValues.getStateName());
            }
            return vi;
        }
    }

}