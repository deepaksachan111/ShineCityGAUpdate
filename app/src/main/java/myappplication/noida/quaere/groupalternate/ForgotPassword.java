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
public class ForgotPassword extends Activity {
    EditText mobileNoEt, userIdEt;
    Button submit;
    String mobileNo, userId, responseCode;
    String fgetPswdUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pswd_layout);
        mobileNoEt = (EditText) findViewById(R.id.forgot_pswd_mobileEt);
        userIdEt = (EditText) findViewById(R.id.forgot_pswd_IdEt);
        submit = (Button) findViewById(R.id.forgot_pswd_number_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNo = mobileNoEt.getText().toString();
                userId = userIdEt.getText().toString();
                if(userId.length()==0){
                    Toast.makeText(getApplicationContext(), "Kindly Enter User Id", Toast.LENGTH_LONG).show();
                    userIdEt.requestFocus();
                }
              else  if(mobileNo.length()==0){
                    Toast.makeText(getApplicationContext(), "Kindly Enter Mobile No", Toast.LENGTH_LONG).show();
                    mobileNoEt.requestFocus();
                }

                else {
                    fgetPswdUrl = "http://demo8.mlmsoftindia.com/ShinePanel.svc/CommanRequestPassword/" + userId + "/" + mobileNo;
                    new ForgotPswdAsyncTask().execute();
                }

               /*
               Toast toast = Toast.makeText(getApplicationContext(), "Entered Mobile Numer is!  "+mobileNo, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 50, 50);
                toast.getView().setPadding(20, 20, 20, 20);
                toast.getView().setBackgroundColor(Color.parseColor("#698fc7"));
                toast.show();*/

            }
        });
    }

    private class ForgotPswdAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(fgetPswdUrl);
                HttpResponse httpResponse = client.execute(post);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("password response:", response);
                JSONArray jArray = new JSONArray(response);
                JSONObject jObj = jArray.getJSONObject(0);
                responseCode = jObj.getString("MessageCode");
                Log.v("responseCode:", responseCode);

            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                if (responseCode.equals("0")) {
                    // dueEmiClListView.setEmptyView(empty);
                    Toast.makeText(getApplicationContext(), "Enter Valid Details ..", Toast.LENGTH_LONG).show();
                    mobileNoEt.requestFocus();

                } else {
                    Toast.makeText(getApplicationContext(), "OTP has sent to your Mobile Number ", Toast.LENGTH_LONG).show();
                   // startActivity(new Intent(ForgotPassword.this, OTPClass.class));
                    Intent i = new Intent(ForgotPassword.this, OTPClass.class);
                    i.putExtra("userId",userId);
                    i.putExtra("mobileNo",mobileNo);
                    startActivity(i);

                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is Failed ", Toast.LENGTH_LONG).show();

            }
        }
    }
}

