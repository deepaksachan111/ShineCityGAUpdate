package myappplication.noida.quaere.groupalternate.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import myappplication.noida.quaere.groupalternate.R;

/**
 * Created by intex on 3/22/2016.
 */
public class MyProfileActivity extends AppCompatActivity{
    TextView username_tv, user_address_tv, user_dob_tv, user_email_tv, bankname_tv, bank_account_tv,
            banck_branch_tv, ifsc_tv, nomineename_tv, nominee_address_tv, nominee_relation_tv, nominee_mobile_tv;

    String username, userAdd, userDob, userEmail, bankName, bankAccount, bankBranch, ifscCode, NomineeName, nomniAdd, nomniRelation, nomniMobile;
    Button chngepswd;
    String userId;
    String user_profile_url;
    MyProfileAsyncTask profileAsyncTask;
    SharedPreferences prefs;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_activity);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        username_tv = (TextView) findViewById(R.id.username_tv);
        user_address_tv = (TextView) findViewById(R.id.user_address_tv);
        user_dob_tv = (TextView) findViewById(R.id.user_dob_tv);
        user_email_tv = (TextView) findViewById(R.id.user_email_tv);

        bankname_tv = (TextView) findViewById(R.id.bankname_tv);
        bank_account_tv = (TextView) findViewById(R.id.bank_account_tv);
        banck_branch_tv = (TextView) findViewById(R.id.bank_branch_tv);
        ifsc_tv = (TextView) findViewById(R.id.ifsc_tv);

        nomineename_tv = (TextView) findViewById(R.id.nominee_name_tv);
        nominee_address_tv = (TextView) findViewById(R.id.nominee_address_tv);
        nominee_relation_tv = (TextView) findViewById(R.id.nominee_relation_tv);
        nominee_mobile_tv = (TextView) findViewById(R.id.nominee_mobile_tv);

       // chngepswd = (Button) findViewById(R.id.change_pswd_btn);

        prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = prefs.getString("email", "");
        System.out.println("user id :"+userId);
        user_profile_url = "http://demo8.mlmsoftindia.com//ShinePanel.svc/MemberProfile/" + userId;

        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
         //   myProfile();



        }
        // Toast.makeText(getActivity(), "user id is :" + userId, Toast.LENGTH_LONG).show();


       profileAsyncTask = new MyProfileAsyncTask();
       profileAsyncTask.execute();

        /*chngepswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                ChangePasswordFragment enquiryFragment = new ChangePasswordFragment();
                FragmentManager fm = MyProfileActivity.this.getSupportFragmentManager();
                enquiryFragment.setArguments(bundle);
                FragmentTransaction ft = fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.replace(R.id.root_frame, enquiryFragment, "Change Password");
                ft.addToBackStack("Change Password");
                ft.commit();

            }
        });*/
        findViewById(R.id.myProfile_backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class MyProfileAsyncTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MyProfileActivity.this);
             pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient client = new DefaultHttpClient();
                String user_profile_url = "http://demo8.mlmsoftindia.com//ShinePanel.svc/MemberProfile/" + userId;
                HttpPost post = new HttpPost(user_profile_url);


                HttpResponse responsePOST = client.execute(post);
                HttpEntity resEntity = responsePOST.getEntity();
                String response = EntityUtils.toString((resEntity));
                Log.v("My profile response :", response);

                if (response != null) {
                    try {
                        JSONArray jArray = new JSONArray(response);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jObj = jArray.getJSONObject(i);

                            //Getting personal details
                            username = jObj.getString("FirstName");
                            userAdd = jObj.getString("Address");
                            userDob = jObj.getString("DOB");
                            userEmail = jObj.getString("EmailId");

                            //Getting bank details
                            bankName = jObj.getString("BankName");
                            bankAccount = jObj.getString("BankAccounNo");
                            bankBranch = jObj.getString("BankBranchName");
                            ifscCode = jObj.getString("IFSCCode");

                            //Getting Nominee details

                            NomineeName = jObj.getString("NomineeName");
                            nomniAdd = jObj.getString("NomineeAddress");
                            nomniRelation = jObj.getString("NomineeRelation");
                            nomniMobile = jObj.getString("NonineeMobile");

                            //Setting values to text

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                       // Toast.makeText(this, "Server is failed ", Toast.LENGTH_LONG).show();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
              //  Toast.makeText(this,"Server is failed ",Toast.LENGTH_LONG).show();


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            username_tv.setText(username);
            user_address_tv.setText(userAdd);
            user_dob_tv.setText(userDob);
            user_email_tv.setText(userEmail);

            bankname_tv.setText(bankName);
            bank_account_tv.setText(bankAccount);
            banck_branch_tv.setText(bankBranch);
            ifsc_tv.setText(ifscCode);

            nomineename_tv.setText(NomineeName);
            if(nomniAdd.equals(" ")){
                nominee_address_tv.setText("No Data");
            }else{
                nominee_address_tv.setText(nomniAdd);
            }

            if(nomniRelation.equals("")){
                nominee_relation_tv.setText("No Data");
            }else
            {
                nominee_relation_tv.setText(nomniRelation);
            }
            nominee_mobile_tv.setText(nomniMobile);
            pd.dismiss();
        }
    }
}
