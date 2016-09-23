package myappplication.noida.quaere.groupalternate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by intex on 12/12/2015.
 */
public class ChangePasswordFragment extends Fragment {
    String userId;
    Button okbtn;
    EditText oldpswdEt,newpswdEt,cnfrmpswdEt;
    String oldpswd,newpswd,cnfrmpswd,pswdStatus;
    FragmentManager fm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_pswd_layout,container,false);

        oldpswdEt = (EditText) view.findViewById(R.id.editTxt_oldpswd);
        newpswdEt = (EditText) view.findViewById(R.id.editTxt_newpswd);
        cnfrmpswdEt = (EditText) view.findViewById(R.id.editTxt_newpswd_confirm);
        okbtn = (Button) view.findViewById(R.id.setPasswordBtn);
        userId = this.getArguments().getString("userId");
         fm = getActivity().getSupportFragmentManager();
      //  Toast.makeText(getActivity(),userId, Toast.LENGTH_SHORT).show();


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpswd = oldpswdEt.getText().toString();
                newpswd = newpswdEt.getText().toString();
                cnfrmpswd = cnfrmpswdEt.getText().toString();
                if (oldpswd.equals("") && newpswd.equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Old and new Password correctly", Toast.LENGTH_LONG).show();
                    oldpswdEt.requestFocus();
                }
                else if (newpswd.matches(cnfrmpswd)) {
                    try {
                        new ChangePswdClass().execute();

                    } catch (Exception e) {

                        Toast.makeText(getActivity(), "Exception has been cuaght " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Insert the same password", Toast.LENGTH_LONG).show();

                    cnfrmpswdEt.requestFocus();
                }
                System.out.println("user Id " + userId + "  Old password :" + oldpswd + "  new Password :" + newpswd);
            }
        });


        return view;
    }

    public class ChangePswdClass extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {

            try {
                HttpClient httpClient = new DefaultHttpClient();
                // HttpPost httppost1 = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/MemberPasswordChange/" + userId +oldpswd + newpswd);
                HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/MemberPasswordChange/" + userId + "/" + oldpswd + "/" + newpswd);
                HttpResponse httpResponse = httpClient.execute(httppost);

                HttpEntity httpEntity = httpResponse.getEntity();

                String pswdresponse = EntityUtils.toString(httpEntity);
                Log.v("pswd update responsee:", pswdresponse);

                JSONArray jArr = new JSONArray(pswdresponse);

                JSONObject jobj = jArr.getJSONObject(0);


                String pswdRespns = jobj.getString("ResponceMessage");
                Log.v("ResponseMessage", pswdRespns);

                System.out.println("After json object ;;;;;;;;; and passwored is    " + newpswd);
                pswdStatus = jobj.getString("ResponceStatus");
                Log.v("password statust ", pswdStatus);


            } catch (Exception e) {
                //  Toast.makeText(getActivity(),"Server is shut down "+e.toString(),Toast.LENGTH_LONG).show();
                System.out.println(" Exception is caught here ......." + e.toString());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try{
                if (pswdStatus.equals("1")) {
                    Toast.makeText(getActivity(), "Password Updated Successfuly", Toast.LENGTH_LONG).show();
                    MyProfileFragment profileFragment = new MyProfileFragment();
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.replace(R.id.activity_main_content_fragment, profileFragment, "My Profile");
                    ft.addToBackStack("My Profile");
                    ft.commit();

                } else {
                    Toast.makeText(getActivity(), "Invalid Old Password", Toast.LENGTH_LONG).show();
                    oldpswdEt.requestFocus();
                }

            }
            catch(Exception e){
                Toast.makeText(getActivity(),"Server is failed ! ",Toast.LENGTH_LONG).show();
            }


        }
    }
}
