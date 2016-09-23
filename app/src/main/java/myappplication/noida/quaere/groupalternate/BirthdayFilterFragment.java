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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by intex on 12/1/2015.
 */
public class BirthdayFilterFragment extends Fragment implements View.OnClickListener {
    Button bdFromDateBtn, bdToDateBtn, bdGetDetailBtn;
    TextView bdFromDateTv, bdToDateTv;
    Spinner bdMemTypeSpin;
    DateDialogFragment datepicker;
    String bdMemType,bdFromDate,bdToDate;
    static int DATE_DIALOG_ID;
    List<String> bdMemType_list = new ArrayList<String>();
    ArrayAdapter bdMemTypeAdapter;
    int pYear, pMonth, pDay;
    DateFormat df;
    String today;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.birthday_filter_fragment, container, false);
        bdFromDateBtn = (Button) view.findViewById(R.id.bdfilter_FromDateBtn);
        bdToDateBtn = (Button) view.findViewById(R.id.bdfilter_ToDateBtn);
        bdGetDetailBtn = (Button) view.findViewById(R.id.bdfilter_getDetailBtn);

        bdFromDateTv = (TextView) view.findViewById(R.id.bdfilter_FromDateTv);
        bdToDateTv = (TextView) view.findViewById(R.id.bdfilter_ToDateTv);

        bdMemTypeSpin = (Spinner) view.findViewById(R.id.bdfilter_MemTypeSpinner);

        bdFromDateBtn.setOnClickListener(this);
        bdToDateBtn.setOnClickListener(this);
        bdGetDetailBtn.setOnClickListener(this);


       // bdMemType_list.add("All");
        bdMemType_list.clear();
        bdMemType_list.add("Customer");
        bdMemType_list.add("Agent");
        bdMemTypeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.branch_spinner_row, bdMemType_list);
        bdMemTypeSpin.setAdapter(bdMemTypeAdapter);

        bdMemTypeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bdMemType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyy");
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);
        today = pDay + "-" + (pMonth+1) + "-" + pYear;
      //  Toast.makeText(getActivity(),today,Toast.LENGTH_LONG).show();


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bdfilter_FromDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 0;
                datepicker.show(getFragmentManager(), "showDate");

                break;

            case R.id.bdfilter_ToDateBtn:
                datepicker = new DateDialogFragment();
                DATE_DIALOG_ID = 1;
                datepicker.show(getFragmentManager(), "showDate");
                break;
            case R.id.bdfilter_getDetailBtn:
               getBirthdayList();
                break;

        }


    }

    public void getBirthdayList() {
        bdFromDate = bdFromDateTv.getText().toString().trim();
        bdToDate = bdToDateTv.getText().toString().trim();

        if ((bdFromDate.length() == 0)) {
            bdFromDate = "-1";
        }
        if ((bdToDate.length() == 0)) {
            bdToDate = "-1";
        }

        Bundle bundle = new Bundle();
        bundle.putString("bdFromDate", bdFromDate);
        bundle.putString("bdToDate", bdToDate);
        bundle.putString("bdMemType", bdMemType);


        Fragment fragment = new BirthdayListFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_content_fragment, fragment,"Birthday List").addToBackStack("Birthday List")
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
            bdFromDateTv.setText(day + "-" + (month + 1) + "-" + year);
        if (DATE_DIALOG_ID == 1)
            bdToDateTv.setText(day + "-" + (month + 1) + "-" + year);
    }
}
