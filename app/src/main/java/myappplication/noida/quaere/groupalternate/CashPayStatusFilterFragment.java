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
public class CashPayStatusFilterFragment extends Fragment implements View.OnClickListener {
    Button payFromDateBtn, payToDateBtn, cashFromDateBtn, cashToDateBtn, cashPaySubmitBtn;
    TextView payFromDateTv, payToDateTv, cashFormDateTv, cashToDateTv;
    Spinner cashPayStatusSpin;
    String payFromDate, payToDate, cashFromDate, cashToDate, cashPayStatus;
    List<String> cashPay_status_list = new ArrayList<String>();
    ArrayAdapter cashPayStatusAdapter;
    DateDialogFragment datepicker;
    static int DATE_DIALOG_ID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cash_pay_status_filter, container, false);
        payFromDateBtn = (Button) view.findViewById(R.id.cashPayfilter_PaymentFromDateBtn);
        payToDateBtn = (Button) view.findViewById(R.id.cashPayfilter_PaymentToDateBtn);
        cashFromDateBtn = (Button) view.findViewById(R.id.cashPayfilter_CashFromDateBtn);
        cashToDateBtn = (Button) view.findViewById(R.id.cashPayfilter_CashToDateBtn);
        cashPaySubmitBtn = (Button) view.findViewById(R.id.cashPayfilter_getDetailBtn);

        payFromDateTv = (TextView) view.findViewById(R.id.cashPayfilter_PaymentFromDateTv);
        payToDateTv = (TextView) view.findViewById(R.id.cashPayfilter_PaymentToDateTv);
        cashFormDateTv = (TextView) view.findViewById(R.id.cashPayfilter_CashFromDateTv);
        cashToDateTv = (TextView) view.findViewById(R.id.cashPayfilter_CashToDateTv);

        cashPayStatusSpin = (Spinner) view.findViewById(R.id.cashPayfilter_StatusSpinner);

        payFromDateBtn.setOnClickListener(this);
        payToDateBtn.setOnClickListener(this);
        cashFromDateBtn.setOnClickListener(this);
        cashToDateBtn.setOnClickListener(this);
        cashPaySubmitBtn.setOnClickListener(this);

        cashPay_status_list.clear();
        cashPay_status_list.add("All");
        cashPay_status_list.add("Pending");
        cashPay_status_list.add("Confirm");
        cashPay_status_list.add("Decline");
        cashPayStatusAdapter = new ArrayAdapter<String>(getActivity(), R.layout.branch_spinner_row, cashPay_status_list);
        cashPayStatusSpin.setAdapter(cashPayStatusAdapter);

        cashPayStatusSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cashPayStatus = parent.getItemAtPosition(position).toString();
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

            case R.id.cashPayfilter_PaymentFromDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 0;
                datepicker.show(getFragmentManager(), "showDate");

                break;

            case R.id.cashPayfilter_PaymentToDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 1;
                datepicker.show(getFragmentManager(), "showDate");
                break;
            case R.id.cashPayfilter_CashFromDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 2;
                datepicker.show(getFragmentManager(), "showDate");

                break;

            case R.id.cashPayfilter_CashToDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 3;
                datepicker.show(getFragmentManager(), "showDate");
                break;

            case R.id.cashPayfilter_getDetailBtn:
                getCashPaymentDetail();
                break;

        }

    }

    public void getCashPaymentDetail() {

        payFromDate = payFromDateTv.getText().toString().trim();
        payToDate = payToDateTv.getText().toString().trim();
        cashFromDate = cashFormDateTv.getText().toString().trim();
        cashToDate = cashToDateTv.getText().toString().trim();
        if (payFromDate.length() == 0) {
            payFromDate = "-1";

        }
        if (payToDate.length() == 0) {
            payToDate = "-1";
        }
        if ((cashFromDate.length() == 0)) {
            cashFromDate = "-1";
        }
        if ((cashToDate.length() == 0)) {
            cashToDate = "-1";
        }

        // System.out.println("  cashPayStatus:"+ cashPayStatus + " payFromDate:"+ payFromDate + " payToDate:" + payToDate + " cashFromDate:" + cashFromDate + " cashToDate :" + cashToDate);
        Bundle bundle = new Bundle();
        bundle.putString("cashPayStatus", cashPayStatus);
        bundle.putString("payFromDate", payFromDate);
        bundle.putString("payToDate", payToDate);
        bundle.putString("cashFromDate", cashFromDate);
        bundle.putString("cashToDate", cashToDate);

        Fragment fragment = new CashPayStatusFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_content_fragment, fragment,"Cash Status").addToBackStack("Cash Status")
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
            payFromDateTv.setText(day + "-" + (month + 1) + "-" + year);
        if (DATE_DIALOG_ID == 1)
            payToDateTv.setText(day + "-" + (month + 1) + "-" + year);
        if (DATE_DIALOG_ID == 2)
            cashFormDateTv.setText(day + "-" + (month + 1) + "-" + year);
        if (DATE_DIALOG_ID == 3)
            cashToDateTv.setText(day + "-" + (month + 1) + "-" + year);
    }
}
