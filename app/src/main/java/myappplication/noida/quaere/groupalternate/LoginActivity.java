package myappplication.noida.quaere.groupalternate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by intex on 8/21/2015.
 */
public class LoginActivity extends Activity {
    //Declaration of widgets
    EditText id_editText, pswd_editText;
    Button login_button;
    String email, password;
    String login_url, login_response, login_response_code, memberId;
    TextView forgtpswd, userIdTv, passwordTv;
    ProgressDialog dialog;

    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //String emailPattern1 = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.login_activity);
        id_editText = (EditText) findViewById(R.id.editTextId);
        pswd_editText = (EditText) findViewById(R.id.editTextPswd);
        login_button = (Button) findViewById(R.id.login_button);
        forgtpswd = (TextView) findViewById(R.id.forget_pswdTv);
        userIdTv = (TextView) findViewById(R.id.login_userIdTv);

        passwordTv = (TextView) findViewById(R.id.login_passwordTv);

        id_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    userIdTv.setVisibility(View.VISIBLE);

            }
        });
        pswd_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    passwordTv.setVisibility(View.VISIBLE);
            }
        });

       forgtpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));

            }
        });

        sharedpreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        //when login button will be clicked
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = id_editText.getText().toString().trim();
                password = pswd_editText.getText().toString().trim();
                // savedCredential = true;
                System.out.println("Entered email :" + email + " Entered password :" + password);

                Log.v("Inside on click", "login btn click");
                if (view == login_button) {
                    //null validation
                    if (email.equals("") && password.equals("")) {

                        Toast.makeText(getApplicationContext(),
                                "Please enter UserId and Password",
                                Toast.LENGTH_SHORT).show();
                        id_editText.requestFocus();
                    } else if (email.equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Please enter User id", Toast.LENGTH_SHORT)
                                .show();
                        id_editText.requestFocus();
                    } else if (password.equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Please enter Password", Toast.LENGTH_SHORT).show();
                        pswd_editText.requestFocus();
                    } else {

                        sharedpreferences = getSharedPreferences("MyPrefs",
                                Context.MODE_PRIVATE);
                        editor = sharedpreferences.edit();
                        editor.putString("email", email);
                        // editor.putString("memberId",memberId);
                        editor.putString("password", password);
                        editor.putString("logged", "logged");
                        editor.commit();
                        //sharedPrefernces();*/
                        login_url = "demo8.mlmsoftindia.com/ShinePanel.svc/Shinecity/" + email + "/" + password;

                        // if(email==(towj146 !!tnabi786) && password ==(business))
                        ConnectivityManager conMgr = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));


                        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                        if (netInfo == null) {

                            // Animation.Description.setVisibility(View.INVISIBLE);
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Connection Failed !")
                                    .setMessage("Unable to connect.Please review your network settings.")
                                    .setPositiveButton("OK", null).show();

                        } else {
                            SuccessfulLogin productTask = new SuccessfulLogin();
                            productTask.execute(login_url);
                        }
                    }
                }
            }

        });

        if (sharedpreferences.getString("logged", "").toString().equals("logged")) {
            dialog = ProgressDialog.show(LoginActivity.this, "", "Loading...", true,
                    false);
            Intent intent = new Intent(LoginActivity.this, FragmentMainActivity.class);
            intent.putExtra("email", sharedpreferences.getString("email", ""));
            intent.putExtra("memberId", sharedpreferences.getString("memberId", ""));
            startActivity(intent);
            finish();
            dialog.dismiss();
        }

    }


   /* @Override
    public void onBackPressed() {
        finish();
    }*/

    class SuccessfulLogin extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(LoginActivity.this, "", "Loading...", true,
                    false);
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                HttpClient httpClient = new DefaultHttpClient();

                HttpGet httpget = new HttpGet("http://demo8.mlmsoftindia.com/ShinePanel.svc/Shinecity/" + email + "/" + password);

                System.out.println("User name " + email + " Passwordd is " + password);
                HttpResponse httpResponse = httpClient.execute(httpget);
                HttpEntity httpEntity = httpResponse.getEntity();

                login_response = EntityUtils.toString(httpEntity);
                Log.v("login  response :", login_response);

                JSONArray jsonArray = new JSONArray(login_response);
                JSONObject object = jsonArray.getJSONObject(0);

                login_response_code = object.getString("ResponseCode");

                memberId = object.getString("MemID");
                Log.i("member_id :", memberId);
                editor.putString("memberId", memberId);
                editor.commit();


            } catch (Exception e) {
                Log.v("Login Exception.......", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                if (login_response_code.equals("1")) {

                    Intent intent = new Intent(LoginActivity.this, FragmentMainActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("memberId", memberId);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                } else {
                    Toast.makeText(LoginActivity.this, "Enter Valid Email and Password", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is Failed ", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

        }
    }
}



