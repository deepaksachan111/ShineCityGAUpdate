package myappplication.noida.quaere.groupalternate.swipetab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import myappplication.noida.quaere.groupalternate.LoginActivity;
import myappplication.noida.quaere.groupalternate.R;
import myappplication.noida.quaere.groupalternate.mainpage.AboutUs;
import myappplication.noida.quaere.groupalternate.mainpage.ContactUsActivity;
import myappplication.noida.quaere.groupalternate.mainpage.ProjectsActivity;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.findViewById(R.id.home_contactusbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContactUsActivity.class));
            }
        });
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
      /*  // Check if no view has focus to hide soft key
        View focustview = getActivity().getCurrentFocus();
        if (focustview != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }*/
        final GridView gridView = (GridView) view.findViewById(R.id.gridview_home_fragment);
        gridView.setHorizontalSpacing(2);
        gridView.setVerticalSpacing(2);

        int image[] = new int[]{R.drawable.user_icon_new, R.drawable.phone, R.drawable.project_icon_new, R.drawable.location_icon_new, R.drawable.mail_icon_new, R.drawable.login_new};

        final String text[] = {"AboutUs", "Call Us", "Projects", "ContactUs", "Sms", "Dashboard"};

        final CustomGridAdapterHomeFragment gridAdapter = new CustomGridAdapterHomeFragment(getActivity(), text, image);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View savedView;

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Toast.makeText(getActivity(), "You Clicked at " + text[+position], Toast.LENGTH_SHORT).show();


                switch (position) {
                    //About Us
                    case 0:
                        startActivity(new Intent(getActivity(), AboutUs.class));
                        // view.setBackgroundResource(R.drawable.default_background);
                        break;

                    //Call Us
                    case 1:
                        Intent tollfreeIntent = new Intent(Intent.ACTION_CALL);
                        tollfreeIntent.setData(Uri.parse("tel:18002000480"));
                        startActivityForResult(tollfreeIntent, 2);
                        break;
                    // Projects Activity
                    case 2:
                        startActivity(new Intent(getActivity(), ProjectsActivity.class));
                        break;

                    //Contact us
                    case 3:
                        startActivity(new Intent(getActivity(), ContactUsActivity.class));
                        break;

                    // Messages
                    case 4:
                        Uri uri = Uri.parse("smsto:53030");
                        Intent message = new Intent(Intent.ACTION_SENDTO, uri);
                        message.putExtra("sms_body", "SHINE");
                        // use if you want to get message body
                        startActivityForResult(message, 3);
                        break;

                    //Dashboard
                    case 5:
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        break;
                }


            }


        });

        return view;
    }

}
