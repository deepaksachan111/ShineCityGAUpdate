package myappplication.noida.quaere.groupalternate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by intex on 10/7/2015.
 */
public class EnquiryFragment extends Fragment {
    EditText queryEt, remarksEt;
    String query, query_replace, remarks_replace, remarks, queryStar, userId;
    String enquir_url;
    Button submitQueryBtn;
    TextView queryTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.enquiry_fragment, container, false);
        queryEt = (EditText) view.findViewById(R.id.enquiry_queryEt);
        remarksEt = (EditText) view.findViewById(R.id.enquiry_remarksEt);
        submitQueryBtn = (Button) view.findViewById(R.id.enquiry_submitBtn);
        queryTv = (TextView) view.findViewById(R.id.enquiry_queryTv);
        queryStar = getResources().getString(R.string.enquiry_query);
        userId = getActivity().getIntent().getStringExtra("email");
        EpinRegisterActivity registerActivity = new EpinRegisterActivity();
        registerActivity.redStar(queryStar, queryTv);

        submitQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = queryEt.getText().toString().trim();
                remarks = remarksEt.getText().toString().trim();
                query_replace = query.replaceAll(" ", "_").replace("?", "_");
                remarks_replace = remarks.replaceAll(" ", "_").replace("?", "_");
                enquir_url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/Enquiry/" + userId + "/" + query_replace + "/" + remarks_replace;
                if ((query_replace.length() == 0)) {
                    Toast.makeText(getActivity(), "Please Enter Your Query first ", Toast.LENGTH_LONG).show();
                    queryEt.requestFocus();
                } else {
                    if ((remarks_replace.length() == 0)) {
                        remarks_replace = "-1";
                    }
                    new EnquiryAsyncTask().execute();
                }
            }
        });

        return view;
    }


    private class EnquiryAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(enquir_url);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity);
                Log.v("response :", response);
            } catch (Exception e) {
                System.out.println(" Exception in response " + e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), "Enquiry has been sent successfully", Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        }
    }
}
