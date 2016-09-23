package myappplication.noida.quaere.groupalternate.mainpage;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
public class CountrySpinnerTask extends AsyncTask<Void, Void, Void> {
    String countryName, countryId;
    ArrayList<String> countryName_list, countryId_list;
    ArrayList<ModelClass> country_list = new ArrayList<ModelClass>();
    CountryAdapter countryAdapter;
    Spinner countrySpinner;
    Activity activity;

    public CountrySpinnerTask(Activity activity, ArrayList<String> countryName_list, ArrayList<String> countryId_list, Spinner countrySpin) {
        this.activity = activity;
        this.countryName_list = countryName_list;
        this.countryId_list = countryId_list;
        this.countrySpinner = countrySpin;


    }

    @Override
    protected Void doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/shinepanel.svc/GetCountry");


        try {
            HttpResponse httpResponse = httpClient.execute(httppost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(httpEntity);
            Log.v("Get Country response:", response);
            JSONArray countryArray = new JSONArray(response);
            for (int i = 0; i < countryArray.length(); i++) {

                JSONObject countryObj = countryArray.getJSONObject(i);
                countryName = countryObj.getString("CountryName");
                countryId = countryObj.getString("CountryID");

              //  Log.i("Countrname",countryName);
              //  Log.i("countryId",countryId);
                countryName_list.add(countryName);
                countryId_list.add(countryId);

                ModelClass m = new ModelClass();
                m.setCountryName(countryName_list.get(i));
                m.setCountryId(countryId_list.get(i));
                country_list.add(m);

            }
        } catch (Exception e) {
            Log.v("Exception in country", e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //try {
            countryAdapter = new CountryAdapter(activity, android.R.layout.simple_spinner_item, country_list);
            countrySpinner.setAdapter(countryAdapter);

       // } catch (Exception e) {
           // Toast.makeText(activity, "Server is not responding "+e.toString(), Toast.LENGTH_LONG).show();
       // }

    }

    public class CountryAdapter extends BaseAdapter {
        private Activity activity;
        private ArrayList<ModelClass> data;
        private LayoutInflater inflators = null;
        ModelClass tempValues;


        public CountryAdapter(Activity context, int textViewResourceId,
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

        public class ViewHolder {
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
                holder.brnchName.setText(tempValues.getCountryName());
            }
            return vi;
        }
    }

}
