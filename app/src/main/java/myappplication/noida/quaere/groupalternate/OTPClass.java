package myappplication.noida.quaere.groupalternate;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class OTPClass extends Activity {
    EditText otpEt;
    Button otpSubmit;
    String otp,mobileNo,userId,otpResponseCode;
    String validateOtpUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pswd_otp_layout);
        otpEt = (EditText) findViewById(R.id.otp_enterOtpEt);
        otpSubmit = (Button) findViewById(R.id.otp_pswd_submit);

        userId = getIntent().getStringExtra("userId");
        mobileNo = getIntent().getStringExtra("mobileNo");


        otpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = otpEt.getText().toString().trim();
                if(otp.length()==0){
                    Toast.makeText(getApplicationContext(), "Kindly Enter OTP sent to your mobile", Toast.LENGTH_LONG).show();
                    otpEt.requestFocus();
                }
                else {
                    validateOtpUrl = "http://demo8.mlmsoftindia.com/ShinePanel.svc/CommanValidateOTP/"+userId+"/"+otp+"/"+mobileNo;
                    new OtpAsyncTask().execute();
                }

            }
        });
    }

    private class OtpAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(validateOtpUrl);
                HttpResponse httpResponse = client.execute(post);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("otp response:", response);
                JSONArray jArray = new JSONArray(response);
                JSONObject jObj = jArray.getJSONObject(0);
                otpResponseCode = jObj.getString("MessageCode");
                Log.v("responseCode:", otpResponseCode);

            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
          //  startActivity(new Intent(OTPClass.this, NewPassword.class));
            try {
                if (otpResponseCode.equals("0")) {
                    Toast.makeText(getApplicationContext(), " Invalid OTP ", Toast.LENGTH_LONG).show();
                    otpEt.requestFocus();

                } else {
                    // startActivity(new Intent(ForgotPassword.this, OTPClass.class));
                    Intent i = new Intent(OTPClass.this, NewPassword.class);
                    i.putExtra("userId",userId);
                    i.putExtra("mobileNo",mobileNo);
                    i.putExtra("otp",otp);
                    startActivity(i);

                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is Failed ", Toast.LENGTH_LONG).show();

            }
        }
    }
}
