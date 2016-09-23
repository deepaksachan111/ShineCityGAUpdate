package myappplication.noida.quaere.groupalternate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by intex on 10/31/2015.
 */
public class EpinRegisterActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner branchSpinner, designationSpinner, firstNameSpinner, accountTypeSpinner, genderSpinner, countrySpinner, stateSpinner, citySpinner, joiningLegSpinner;
    ArrayList<ModelClass> branch_list = new ArrayList<ModelClass>();
    ArrayList<ModelClass> designation_list = new ArrayList<ModelClass>();
    ArrayList<ModelClass> country_list = new ArrayList<ModelClass>();
    ArrayList<ModelClass> state_list = new ArrayList<ModelClass>();
    ArrayList<ModelClass> city_list = new ArrayList<ModelClass>();
    ArrayList<ModelClass> gender_list = new ArrayList<ModelClass>();
    ArrayList<ModelClass> joiningLeg_list = new ArrayList<ModelClass>();
    ArrayList<String> branchName_list = new ArrayList<String>();
    ArrayList<String> designationId_list = new ArrayList<String>();
    ArrayList<String> designationName_list = new ArrayList<String>();
    ArrayList<String> branchId_list = new ArrayList<String>();
    ArrayList<String> countryName_list = new ArrayList<String>();
    ArrayList<String> countryId_list = new ArrayList<String>();
    ArrayList<String> stateName_list = new ArrayList<String>();
    ArrayList<String> stateId_list = new ArrayList<String>();
    ArrayList<String> cityName_list = new ArrayList<String>();
    ArrayList<String> cityId_list = new ArrayList<String>();
    ArrayList<String> genderNameList = new ArrayList<String>();
    ArrayList<String> genderValueList = new ArrayList<String>();
    ArrayList<String> joiningNameList = new ArrayList<String>();
    ArrayList<String> joiningValueList = new ArrayList<String>();

    List<String> acc_type_list = new ArrayList<String>();
    List<String> mr_list = new ArrayList<String>();

    Button dateBtn, submit;
    DateFormat df;
    BranchAdapter branchAdapter;
    DesignationAdapter designationAdapter;
    StateAdapter stateAdapter;
    CountryAdapter countryAdapter;
    CityAdapter cityAdapter;
    GenderAdapter genderAdapter;
    JoiningLegAdapter joiningLegAdapter;

    ArrayAdapter fNameAdapter, accTypeAdapter;
    static final int DATE_DIALOG_ID = 0;
    int pYear, pMonth, pDay;
    String branch_star, epinCode_star, sponsorCode_star, firstName_star, mobile_star, panNo_star;
    String memberId, epinNO, epinDesignation, userId;
    String null_msg = "field should not be empty";
    TextView branchTv, epinCodeTv, sponsorCodeTv, firstNameTv, mobileTv, panNoTv, dobTv;
    EditText epinCodeEt, sponsorCodeEt, firstNameEt, middleNameEt, lastNameEt, fatherNameEt, addressEt,
            pinCodeEt, mobileEt, emailEt, panNoEt, bankAccEt, bankNameEt, bankBranchEt, micrNoEt, ifscCodeEt;
    String epinCodeValue, epinResponse, reg_dob, designationName, designationId, sponsorCode, title, firstName, middleName, lastName, fatherName, fatherName_replace,
            countryName, countryId, stateName, stateId, address, address_replace, pincode, mobile, email, panNo, accountNo, bankName, bankName_replace, branchName, branchName_replace,
            branchId, accountType, micrNo, ifscCode, cityName, cityId, successResponse, messageText, genderName, genderValue, joiningLegName, joiningLegValue;

    //String url = "http://demo8.mlmsoftindia.com/ShinePanel.svc/EpinSignUp/8738785829164508/1/1/tnabi786/Mr/Deepak/-1/Sachan/Ashok_Sachan/20-11-1991/M/1/1/1/-1/-1/1/823001/7834908329/cde@gmail.com/NA/11223344556677888/Punjab_national_bank/tekari_road/savings/1478541252/124455/19195/L";
    String submitUrl = null;
    String cityUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epin_register_layout);

        branchSpinner = (Spinner) findViewById(R.id.register_branch_spinner);
        designationSpinner = (Spinner) findViewById(R.id.register_designation_spinner);
        firstNameSpinner = (Spinner) findViewById(R.id.register_firstName_spinner);
        accountTypeSpinner = (Spinner) findViewById(R.id.register_accountType_spinner);
        genderSpinner = (Spinner) findViewById(R.id.register_gender_spinner);
        countrySpinner = (Spinner) findViewById(R.id.register_country_spinner);
        stateSpinner = (Spinner) findViewById(R.id.register_state_spinner);
        citySpinner = (Spinner) findViewById(R.id.register_cityspinner);
        joiningLegSpinner = (Spinner) findViewById(R.id.register_JoiningLegspinner);

        dateBtn = (Button) findViewById(R.id.button_Calendar);
        submit = (Button) findViewById(R.id.register_submit_Btn);
        branchTv = (TextView) findViewById(R.id.register_branchTv);
        epinCodeTv = (TextView) findViewById(R.id.register_e_pinCodeTv);
        sponsorCodeTv = (TextView) findViewById(R.id.register_sponsorCodeTv);
        firstNameTv = (TextView) findViewById(R.id.register_firstNameTv);
        mobileTv = (TextView) findViewById(R.id.register_mobileTv);
        panNoTv = (TextView) findViewById(R.id.register_panNoTv);
        dobTv = (TextView) findViewById(R.id.register_dobTv);

        epinCodeEt = (EditText) findViewById(R.id.register_e_pinCodeEt);
        firstNameEt = (EditText) findViewById(R.id.register_firstNameEt);
        middleNameEt = (EditText) findViewById(R.id.register_middleNameEt);
        sponsorCodeEt = (EditText) findViewById(R.id.register_sponsorCodeEt);
        lastNameEt = (EditText) findViewById(R.id.register_lastNameEt);
        fatherNameEt = (EditText) findViewById(R.id.register_fatherNameEt);
        addressEt = (EditText) findViewById(R.id.register_addressEt);
        pinCodeEt = (EditText) findViewById(R.id.register_pinCodeEt);
        mobileEt = (EditText) findViewById(R.id.register_mobileEt);
        emailEt = (EditText) findViewById(R.id.register_emailEt);
        panNoEt = (EditText) findViewById(R.id.register_panNoEt);
        bankAccEt = (EditText) findViewById(R.id.register_AccountNoEt);
        bankNameEt = (EditText) findViewById(R.id.register_bankNameEt);
        bankBranchEt = (EditText) findViewById(R.id.register_bankBranchEt);
        micrNoEt = (EditText) findViewById(R.id.register_MicrNoEt);
        ifscCodeEt = (EditText) findViewById(R.id.register_IfscEt);

        branch_star = getResources().getString(R.string.register_branch_);
        epinCode_star = getResources().getString(R.string.register_e_pin_Code);
        sponsorCode_star = getResources().getString(R.string.register_sponsor_code);
        firstName_star = getResources().getString(R.string.register_first_name);
        mobile_star = getResources().getString(R.string.register_mobile_no);
        panNo_star = getResources().getString(R.string.register_pan_no);


        redStar(sponsorCode_star, sponsorCodeTv);
        redStar(epinCode_star, epinCodeTv);
        redStar(branch_star, branchTv);
        redStar(firstName_star, firstNameTv);
        redStar(mobile_star, mobileTv);
        redStar(panNo_star, panNoTv);

        memberId = getIntent().getStringExtra("memberId");
        epinNO = getIntent().getStringExtra("EpinNO");
        epinDesignation = getIntent().getStringExtra("EpinDesignation");
        userId = getIntent().getStringExtra("userId");
        // Toast.makeText(getApplicationContext(), "mem Id is" + memberId+"  epin NO :"+epinNO+"   designation :"+epinDesignation+"  userId "+userId, Toast.LENGTH_LONG).show();
        epinCodeEt.setText(epinNO);
        sponsorCodeEt.setText(userId);

        epinCodeValue = epinCodeEt.getText().toString().trim();

        branchSpinner.setOnItemSelectedListener(this);
        designationSpinner.setOnItemSelectedListener(this);
        firstNameSpinner.setOnItemSelectedListener(this);
        accountTypeSpinner.setOnItemSelectedListener(this);
        genderSpinner.setOnItemSelectedListener(this);
        countrySpinner.setOnItemSelectedListener(this);
        stateSpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);
        joiningLegSpinner.setOnItemSelectedListener(this);

        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyy");
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        //Calling services for respected spinner dropdown
        new CountrySpinnerTask().execute();
        new StateSpinnerTask().execute();
        //  new CitySpinnerTask().execute();
        new DesignationSpinnerTask().execute();
        new BranchSpinnerTask().execute();


        epinCodeEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                epinCodeValue = epinCodeEt.getText().toString().trim();
                new EpinCheckTask().execute();
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg_dob = dobTv.getText().toString().trim();

                sponsorCode = sponsorCodeEt.getText().toString().trim();
                firstName = firstNameEt.getText().toString().trim();
                middleName = middleNameEt.getText().toString().trim();
                lastName = lastNameEt.getText().toString().trim();
                fatherName = fatherNameEt.getText().toString().trim();
                fatherName_replace = fatherName.replaceAll(" ", "_");
                address = addressEt.getText().toString().trim();
                address_replace = address.replaceAll(" ", "_");
                pincode = pinCodeEt.getText().toString().trim();
                mobile = mobileEt.getText().toString().trim();
                panNo = panNoEt.getText().toString().trim();
                accountNo = bankAccEt.getText().toString().trim();
                email = emailEt.getText().toString().trim();
                branchName = bankBranchEt.getText().toString().trim();
                branchName_replace = branchName.replaceAll(" ", "_");
                bankName = bankNameEt.getText().toString().trim();
                bankName_replace = bankName.replaceAll(" ", "_");
                micrNo = micrNoEt.getText().toString().trim();
                ifscCode = ifscCodeEt.getText().toString().trim();
                EditText[] textfields = {sponsorCodeEt, firstNameEt, lastNameEt, fatherNameEt, addressEt, pinCodeEt, mobileEt, panNoEt, bankAccEt, emailEt, bankBranchEt, bankNameEt, micrNoEt, ifscCodeEt};
                //  if(sponsorCodeE)
                validateEditText(textfields, null_msg);

/*

                System.out.println("epinCodeValue :" + epinCodeValue + "  branchId :" + branchId + " designationId :" + designationId + " sponsorCode :" + sponsorCode + " title :" + title + " firstName  :" + firstName);
                System.out.println("lastName  :" + lastName + "reg_dob  :" + reg_dob + "Father Name  :" + fatherName_replace + " gender :" + genderValue + " countryId :" + countryId + " stateId:" + stateId + " Joining Leg " + joiningLegValue);
                System.out.println("cityId  :" + cityId + "address  :" + address_replace + "pincode  :" + pincode + " mobile :" + mobile + " email :" + email + " panNo:" + panNo + " accountNo :" + accountNo + " bankName :" + bankName_replace);
                System.out.println("branchName  :" + branchName + "accountType  :" + accountType + "micrNo  :" + micrNo + " ifscCode :" + ifscCode + " email :" + email + " panNo:" + panNo + " accountNo :" + accountNo + " bankName :" + bankName);
*/

                submitUrl = "http://demo8.mlmsoftindia.com/ShinePanel.svc/EpinSignUp/" + epinCodeValue + "/" + branchId + "/" + designationId + "/" + sponsorCode + "/" + title + "/" + firstName + "/" + -1 + "/" + lastName + "/" + fatherName_replace + "/" + reg_dob + "/" + genderValue + "/" + countryId + "/" + stateId + "/" + address_replace + "/" + -1 + "/" + -1 + "/" + cityId + "/" + pincode + "/" + mobile + "/" + email + "/" + panNo + "/" + accountNo + "/" + bankName_replace + "/" + branchName_replace + "/" + accountType + "/" + micrNo + "/" + ifscCode + "/" + memberId + "/" + joiningLegValue;//";
                new EpinRegisterTask().execute();

            }
        });


        //first Nmame implementation
        mr_list.clear();
        mr_list.add("Mr");
        mr_list.add("Ms");
        fNameAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_text, mr_list);
        //  fNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstNameSpinner.setAdapter(fNameAdapter);

        genderMethod();
        joiningLegMethod();

        acc_type_list.clear();
        acc_type_list.add("Savings");
        acc_type_list.add("Current");
        accTypeAdapter = new ArrayAdapter<String>(this, R.layout.branch_spinner_row, acc_type_list);
        // accTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(accTypeAdapter);


    }

    //********************
    public void validateEditText(EditText[] textfields, String null_msg) {
        for (int i = 0; i < textfields.length; i++) {
            EditText currentField = textfields[i];
            if (currentField.getText().toString().length() <= 0) {
                currentField.setError(null_msg);
                currentField.requestFocus();
            }
        }
    }


    public void joiningLegMethod() {
        for (int i = 0; i < 2; i++) {
            joiningNameList.add("Left");
            joiningNameList.add("Right");
            joiningValueList.add("L");
            joiningValueList.add("R");
            ModelClass m = new ModelClass();
            m.setJoiningLegName(joiningNameList.get(i));
            m.setJoiningLegValue(joiningValueList.get(i));
            joiningLeg_list.add(m);
        }

        joiningLegAdapter = new JoiningLegAdapter(EpinRegisterActivity.this, R.layout.branch_spinner_row, joiningLeg_list);
        joiningLegSpinner.setAdapter(joiningLegAdapter);
    }

    //gender Method
    public void genderMethod() {

        for (int i = 0; i < 2; i++) {
            genderNameList.add("Male");
            genderNameList.add("Female");
            genderValueList.add("M");
            genderValueList.add("F");
            ModelClass m = new ModelClass();
            m.setGenderName(genderNameList.get(i));
            m.setGenderValue(genderValueList.get(i));
            gender_list.add(m);
        }

        genderAdapter = new GenderAdapter(EpinRegisterActivity.this, R.layout.branch_spinner_row, gender_list);
        genderSpinner.setAdapter(genderAdapter);
    }


    // *************** Functionality of  Putting red star beside mandatory fields in the form
    public void redStar(String simple, TextView text) {
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(builder);
    }

    // ************** Date picker from calendar funcationality
    DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();

                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }

    private void updateDisplay() {

        dobTv.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pDay).append("-")
                        .append(pMonth + 1).append("-")
                        .append(pYear).append(" "));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {

            //  Branch Spinner
            case R.id.register_branch_spinner:
                ModelClass selectedItem = (ModelClass) parent.getItemAtPosition(position);
                branchName = selectedItem.getBranchName();
                branchId = selectedItem.getBranchId();
                break;

            //  Designation Spinner
            case R.id.register_designation_spinner:
                ModelClass selectedItem1 = (ModelClass) parent.getItemAtPosition(position);
                designationName = selectedItem1.getDesignationName();
                designationId = selectedItem1.getDesignationId();
                break;

            //  Title Spinner
            case R.id.register_firstName_spinner:
                title = firstNameSpinner.getItemAtPosition(position).toString();
                break;

            //  Gender Spinner
            case R.id.register_gender_spinner:
                ModelClass genderItem = (ModelClass) parent.getItemAtPosition(position);
                genderName = genderItem.getGenderName();
                genderValue = genderItem.getGenderValue();
                System.out.println("Gender Name :" + genderName + " Gender Value :" + genderValue);
                break;

            //  Country Spinner
            case R.id.register_country_spinner:
                ModelClass selectedItem2 = (ModelClass) parent.getItemAtPosition(position);
                countryName = selectedItem2.getCountryName();
                countryId = selectedItem2.getCountryId();
                break;


            //  State Spinner
            case R.id.register_state_spinner:
                ModelClass selectedItem3 = (ModelClass) parent.getItemAtPosition(position);
                stateName = selectedItem3.getStateName();
                stateId = selectedItem3.getStateId();
                getCity(stateId);

                break;

            //  City Spinner
            case R.id.register_cityspinner:
                ModelClass selectedItem4 = (ModelClass) parent.getItemAtPosition(position);
                cityName = selectedItem4.getCityName();
                cityId = selectedItem4.getCityId();
                System.out.println("cityName :" + cityName + " cityId Value :" + cityId);
                //  cityAdapter.notifyDataSetChanged();
                break;

            //  AccountType Spinner
            case R.id.register_accountType_spinner:
                accountType = accountTypeSpinner.getItemAtPosition(position).toString();
                break;

            //  JoiningLeg Spinner
            case R.id.register_JoiningLegspinner:
                ModelClass jLegItem = (ModelClass) parent.getItemAtPosition(position);
                joiningLegName = jLegItem.getJoiningLegName();
                joiningLegValue = jLegItem.getJoiningLegValue();
                System.out.println("joiningLegName:" + joiningLegName + " joiningLegValue :" + joiningLegValue);
                break;
        }
    }

    public void getCity(String stateId) {
        String stateValue = stateId;
        cityUrl = "http://demo8.mlmsoftindia.com/shinepanel.svc/CityList/" + stateValue;
        city_list.clear();
        cityName_list.clear();
        cityId_list.clear();
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(cityUrl);
        try {
            HttpResponse httpResponse = httpClient.execute(httppost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(httpEntity);
            Log.v("Get City response:", response);
            JSONArray stateArray = new JSONArray(response);

            for (int i = 0; i < stateArray.length(); i++) {

                JSONObject cityObj = stateArray.getJSONObject(i);
                cityName = cityObj.getString("CityName");
                cityId = cityObj.getString("CityID");

                cityName_list.add(cityName);
                cityId_list.add(cityId);

                ModelClass m = new ModelClass();
                m.setCityName(cityName_list.get(i));
                m.setCityId(cityId_list.get(i));
                city_list.add(m);

            }
        } catch (Exception e) {
            Log.v("Exception in state s", e.toString());
        }
        try {
            cityAdapter = new CityAdapter(EpinRegisterActivity.this, android.R.layout.simple_spinner_item, city_list);

            citySpinner.setAdapter(cityAdapter);
            cityAdapter.notifyDataSetChanged();
            //city_list.clear(); npe
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Server is not responding ", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // ********************** *******  Get Branch List from server
    public class BranchSpinnerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/shinepanel.svc/GetBranch");
            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Get Branch response:", response);

                JSONArray branchArray = new JSONArray(response);
                for (int i = 0; i < branchArray.length(); i++) {

                    JSONObject branchObj = branchArray.getJSONObject(i);

                    branchName = branchObj.getString("BranchName");
                    branchId = branchObj.getString("BranchID");
                    branchName_list.add(branchName);
                    branchId_list.add(branchId);

                    ModelClass m = new ModelClass();
                    m.setBranchName(branchName_list.get(i));
                    m.setBranchId(branchId_list.get(i));
                    branch_list.add(m);
                }

            } catch (Exception e) {
                Log.v("Excepton in Branch", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                branchAdapter = new BranchAdapter(EpinRegisterActivity.this, R.layout.branch_spinner_row, branch_list);
                branchSpinner.setAdapter(branchAdapter);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is Failed ", Toast.LENGTH_LONG).show();
            }
        }
    }

    // **************************************** Get Designation List from server
    public class DesignationSpinnerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/shinepanel.svc/GetDesignation");


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v(" Designation response:", response);

                JSONArray designationArray = new JSONArray(response);
                for (int i = 0; i < designationArray.length(); i++) {

                    JSONObject designationObj = designationArray.getJSONObject(i);
                    designationName = designationObj.getString("DesignationName");
                    designationId = designationObj.getString("DesignationID");

                    designationName_list.add(designationName);
                    designationId_list.add(designationId);

                    ModelClass m = new ModelClass();
                    m.setDesignationName(designationName_list.get(i));
                    m.setDesignationId(designationId_list.get(i));
                    designation_list.add(m);
                }
            } catch (Exception e) {
                Log.v("Excepton in Designation", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                designationAdapter = new DesignationAdapter(EpinRegisterActivity.this, R.layout.branch_spinner_row, designation_list);
                designationSpinner.setAdapter(designationAdapter);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is Failed ", Toast.LENGTH_LONG).show();
            }
        }
    }


    // ******************************************** Get State List from server
    public class StateSpinnerTask extends AsyncTask<Void, Void, Void> {
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
                stateAdapter = new StateAdapter(EpinRegisterActivity.this, android.R.layout.simple_spinner_item, state_list);
                stateSpinner.setAdapter(stateAdapter);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is not responding ", Toast.LENGTH_LONG).show();
            }
        }

    }


    //Get City List from server
   /* private class CitySpinnerTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(cityUrl);
            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Get City response:", response);
                JSONArray stateArray = new JSONArray(response);

                for (int i = 0; i < stateArray.length(); i++) {

                    JSONObject cityObj = stateArray.getJSONObject(i);
                    cityName = cityObj.getString("CityName");
                    cityId = cityObj.getString("CityID");

                    cityName_list.add(cityName);
                    cityId_list.add(cityId);

                    ModelClass m = new ModelClass();
                    m.setCityName(cityName_list.get(i));
                    m.setCityId(cityId_list.get(i));
                    city_list.add(m);

                }
            } catch (Exception e) {
                Log.v("Exception in state s", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                cityAdapter = new CityAdapter(EpinRegisterActivity.this, android.R.layout.simple_spinner_item, city_list);
                cityAdapter.notifyDataSetChanged();
                citySpinner.setAdapter(cityAdapter);
                //city_list.clear();
                //


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is not responding " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }*/

    // ************************************* Get Country List from server
    public class CountrySpinnerTask extends AsyncTask<Void, Void, Void> {

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
            try {
                countryAdapter = new CountryAdapter(EpinRegisterActivity.this, android.R.layout.simple_spinner_item, country_list);
                countrySpinner.setAdapter(countryAdapter);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is not responding ", Toast.LENGTH_LONG).show();
            }

        }
    }

    // ************************ Epin validation functionality is done here.....
    public class EpinCheckTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/shinepanel.svc/CheckEPin/" + epinCodeValue);


            try {
                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Get E-Pin response:", response);

                JSONArray branchArray = new JSONArray(response);
                for (int i = 0; i < branchArray.length(); i++) {

                    JSONObject epinObj = branchArray.getJSONObject(i);
                    epinResponse = epinObj.getString("ResponceCode");


                }
            } catch (Exception e) {
                Log.v("Exception caught here", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                if (epinResponse.equals("0")) {
                    // Toast.makeText(getApplicationContext(), "Invalid Epin", Toast.LENGTH_LONG).show();
                    epinCodeEt.setError("Invalid Epin");
                    epinCodeEt.requestFocus();
                } else {
                    epinCodeEt.setError("valid Pin");

                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is not responding ", Toast.LENGTH_LONG).show();
            }
        }
    }

    // ******************************** Functinality or method called when submit button is clicked
    private class EpinRegisterTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                String encoded = URLEncoder.encode(submitUrl, "UTF-8");
                HttpPost httppost = new HttpPost(submitUrl);
                //HttpPost httppost = new HttpPost("http://demo8.mlmsoftindia.com/ShinePanel.svc/EpinSignUp/"+epinCodeValue+"/"+branchId+"/"+designationId+"/"+sponsorCode+"/"+title+"/"+firstName+"/"+-1+"/"+lastName+"/"+fatherName_replace+"/"+reg_dob+"/"+gender+"/"+countryId+"/"+stateId+"/"+address_replace+"/"+-1+"/"+-1+"/"+cityId+"/"+pincode+"/"+mobile+"/"+email+"/"+panNo+"/"+accountNo+"/"+bankName_replace+"/"+branchName_replace+"/"+accountType+"/"+micrNo+"/"+ifscCode+"/"+memberId+"/"+loginLeg);

                HttpResponse httpResponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);
                Log.v("Get Success response:", response);

                JSONArray branchArray = new JSONArray(response);
                for (int i = 0; i < branchArray.length(); i++) {

                    JSONObject epinObj = branchArray.getJSONObject(0);
                    successResponse = epinObj.getString("ResponseMessage");
                    messageText = epinObj.getString("MessageText");

                }
            } catch (Exception e) {
                Log.v("Exception caught here", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                if (successResponse.equals("1")) {
                    Toast.makeText(getApplicationContext(), "Epin Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent in = new Intent();
                    setResult(2, in);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_LONG).show();
                    dobTv.requestFocus();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server is not responding ", Toast.LENGTH_LONG).show();
            }
        }
    }
}