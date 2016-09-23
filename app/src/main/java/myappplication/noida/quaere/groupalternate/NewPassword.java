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

/**
 * Created by intex on 12/10/2015.
 */
public class NewPassword extends Activity{
    EditText newPswdEt,cnfrmPswdEt;
    Button newPswdSubmitBtn;
    String newPswd,cnfrmPswd,userId,mobileNo,otp,newPswdRespnceCode;
    String newPswdUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_pswd_layout);
        newPswdEt = (EditText) findViewById(R.id.new_pswd_enterPswdEt);
        cnfrmPswdEt = (EditText) findViewById(R.id.new_pswd_cnfrmPswdEt);
        newPswdSubmitBtn = (Button) findViewById(R.id.new_pswd_submitBtn);
        userId = getIntent().getStringExtra("userId");
        mobileNo = getIntent().getStringExtra("mobileNo");
        otp = getIntent().getStringExtra("otp");


        newPswdSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPswd = newPswdEt.getText().toString().trim();
                cnfrmPswd = cnfrmPswdEt.getText().toString().trim();
                if(newPswd.length()==0){
                    Toast.makeText(getApplicationContext(), "Kindly New Password", Toast.LENGTH_LONG).show();
                    newPswdEt.requestFocus();
                }
              else  if(cnfrmPswd.length()==0){
                    Toast.makeText(getApplicationContext(), "Kindly Enter Confirm Password", Toast.LENGTH_LONG).show();
                    cnfrmPswdEt.requestFocus();
                }
              else  if (!(newPswd.equals(cnfrmPswd))) {
                    Toast.makeText(getApplicationContext(), "Password Doesn't Matches !", Toast.LENGTH_SHORT).show();
                    cnfrmPswdEt.requestFocus();
                }
                else{
                    newPswdUrl = "http://demo8.mlmsoftindia.com/ShinePanel.svc/CommanPasswordChanged/" + userId + "/" + mobileNo + "/" + otp + "/" + newPswd;
                    new newPasswordAsyncTask().execute();
                }

            }
        });
    }

    private class newPasswordAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(newPswdUrl);
                HttpResponse httpResponse = client.execute(post);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("otp response:", response);
                JSONArray jArray = new JSONArray(response);
                JSONObject jObj = jArray.getJSONObject(0);
                newPswdRespnceCode = jObj.getString("MessageCode");
                Log.v("responseCode:", newPswdRespnceCode);

            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                if (newPswdRespnceCode.equals("0")) {
                    Toast.makeText(getApplicationContext(), " Invalid Details ", Toast.LENGTH_LONG).show();
                   // otpEt.requestFocus();

                } else {
                    Toast.makeText(getApplicationContext(), " Password Changed Successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(NewPassword.this, LoginActivity.class);
                    startActivity(i);

                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is Failed ", Toast.LENGTH_LONG).show();

            }
        }
    }
}
