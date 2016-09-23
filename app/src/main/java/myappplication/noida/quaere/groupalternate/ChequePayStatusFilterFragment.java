package myappplication.noida.quaere.groupalternate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by intex on 12/1/2015.
 */
public class ChequePayStatusFilterFragment extends Fragment implements View.OnClickListener {
    Button chequepayFromDateBtn, chequepayToDateBtn, chequecashFromDateBtn, chequecashToDateBtn, chequecashPaySubmitBtn;
    TextView chequepayFromDateTv, chequepayToDateTv, chequecashFormDateTv, chequecashToDateTv;
    Spinner chequePayStatusSpin;
    String chequepayFromDate, chequepayToDate, chequecashFromDate, chequecashToDate, chequePayStatus;
    List<String> chequePay_status_list = new ArrayList<String>();
    ArrayAdapter chequePayStatusAdapter;
    DateDialogFragment datepicker;
    static int DATE_DIALOG_ID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cheque_pay_status_filter, container, false);
        chequepayFromDateBtn = (Button) view.findViewById(R.id.chequePayfilter_PaymentFromDateBtn);
        chequepayToDateBtn = (Button) view.findViewById(R.id.chequePayfilter_PaymentToDateBtn);
        chequecashFromDateBtn = (Button) view.findViewById(R.id.chequePayfilter_CashFromDateBtn);
        chequecashToDateBtn = (Button) view.findViewById(R.id.chequePayfilter_CashToDateBtn);
        chequecashPaySubmitBtn = (Button) view.findViewById(R.id.chequePayfilter_getDetailBtn);

        chequepayFromDateTv = (TextView) view.findViewById(R.id.chequePayfilter_PaymentFromDateTv);
        chequepayToDateTv = (TextView) view.findViewById(R.id.chequePayfilter_PaymentToDateTv);
        chequecashFormDateTv = (TextView) view.findViewById(R.id.chequePayfilter_CashFromDateTv);
        chequecashToDateTv = (TextView) view.findViewById(R.id.chequePayfilter_CashToDateTv);

        chequePayStatusSpin = (Spinner) view.findViewById(R.id.chequePayfilter_StatusSpinner);

        chequepayFromDateBtn.setOnClickListener(this);
        chequepayToDateBtn.setOnClickListener(this);
        chequecashFromDateBtn.setOnClickListener(this);
        chequecashToDateBtn.setOnClickListener(this);
        chequecashPaySubmitBtn.setOnClickListener(this);

        chequePay_status_list.clear();
        chequePay_status_list.add("All");
        chequePay_status_list.add("Pending");
        chequePay_status_list.add("Confirm");
        chequePay_status_list.add("Decline");
        chequePayStatusAdapter = new ArrayAdapter<String>(getActivity(), R.layout.branch_spinner_row, chequePay_status_list);
        chequePayStatusSpin.setAdapter(chequePayStatusAdapter);

        chequePayStatusSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chequePayStatus = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.chequePayfilter_PaymentFromDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 0;
                datepicker.show(getFragmentManager(), "showDate");

                break;

            case R.id.chequePayfilter_PaymentToDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 1;
                datepicker.show(getFragmentManager(), "showDate");
                break;
            case R.id.chequePayfilter_CashFromDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 2;
                datepicker.show(getFragmentManager(), "showDate");

                break;

            case R.id.chequePayfilter_CashToDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 3;
                datepicker.show(getFragmentManager(), "showDate");
                break;

            case R.id.chequePayfilter_getDetailBtn:
                getChequePaymentDetail();
                break;

        }

    }

    public void getChequePaymentDetail() {

        chequepayFromDate = chequepayFromDateTv.getText().toString().trim();
        chequepayToDate = chequepayToDateTv.getText().toString().trim();
        chequecashFromDate = chequecashFormDateTv.getText().toString().trim();
        chequecashToDate = chequecashToDateTv.getText().toString().trim();
        if (chequepayFromDate.length() == 0) {
            chequepayFromDate = "-1";

        }
        if (chequepayToDate.length() == 0) {
            chequepayToDate = "-1";
        }
        if ((chequecashFromDate.length() == 0)) {
            chequecashFromDate = "-1";
        }
        if ((chequecashToDate.length() == 0)) {
            chequecashToDate = "-1";
        }

        // System.out.println("  cashPayStatus:"+ cashPayStatus + " payFromDate:"+ payFromDate + " payToDate:" + payToDate + " cashFromDate:" + cashFromDate + " cashToDate :" + cashToDate);
        Bundle bundle = new Bundle();
        bundle.putString("chequePayStatus", chequePayStatus);
        bundle.putString("chequepayFromDate", chequepayFromDate);
        bundle.putString("chequepayToDate", chequepayToDate);
        bundle.putString("chequecashFromDate", chequecashFromDate);
        bundle.putString("chequecashToDate", chequecashToDate);

        Fragment fragment = new ChequePayStatusFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_content_fragment, fragment,"Cheque Status").addToBackStack("Cheque Status")
                .commit();

    }

    @SuppressLint("ValidFragment")
    class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public DateDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            showSetDate(year, monthOfYear, dayOfMonth);
        }

    }

    public void showSetDate(int year, int month, int day) {
        if (DATE_DIALOG_ID == 0)
            chequepayFromDateTv.setText(day + "-" + (month + 1) + "-" + year);
        if (DATE_DIALOG_ID == 1)
            chequepayToDateTv.setText(day + "-" + (month + 1) + "-" + year);
        if (DATE_DIALOG_ID == 2)
            chequecashFormDateTv.setText(day + "-" + (month + 1) + "-" + year);
        if (DATE_DIALOG_ID == 3)
            chequecashToDateTv.setText(day + "-" + (month + 1) + "-" + year);
    }
}
