package myappplication.noida.quaere.groupalternate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import myappplication.noida.quaere.groupalternate.swipetab.DashboardFragment;

/**
 * Created by intex on 9/4/2015.
 */
public class FragmentMainActivity extends FragmentActivity implements View.OnClickListener {
    MainLayout mainLayout;

    TextView tvTitle, tvUsername, epinDetailTv, epinRegisterTv;
    Button btMenu, update, logout_btn;
    LinearLayout expand_layout, epin_expand_layout, myaccount_layout, myProfileLayout, placementDetailLayout, epinLayout, enquiryLayout, dashboardLayout;//trainingLayout,
    ListView myAccount_explistview;
    Boolean flag_account = true;
    Boolean flag_epin = true;
    FragmentManager fm;
    ImageView acc_down_icon, acc_up_icon, epin_down_icon, epin_up_icon;

    String userId, memberId;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Inflate the mainLayout
        mainLayout = (MainLayout) this.getLayoutInflater().inflate(
                R.layout.fragment_main_activity, null);
        setContentView(mainLayout);


        tvTitle = (TextView) findViewById(R.id.activity_main_content_title);
        tvUsername = (TextView) findViewById(R.id.activity_main_userNameTv);

        placementDetailLayout = (LinearLayout) findViewById(R.id.placement_detail_layout);
        myProfileLayout = (LinearLayout) findViewById(R.id.my_profile_layout);
        epinLayout = (LinearLayout) findViewById(R.id.epin_layout);
        //  trainingLayout = (LinearLayout) findViewById(R.id.training_layout);
        enquiryLayout = (LinearLayout) findViewById(R.id.enquiry_layout);
        dashboardLayout = (LinearLayout) findViewById(R.id.dashboard_layout);

        // update = (Button) findViewById(R.id.activity_main_update_btn);
        logout_btn = (Button) findViewById(R.id.activity_main_logoutBtn);
        btMenu = (Button) findViewById(R.id.activity_main_content_button_menu);
        myaccount_layout = (LinearLayout) findViewById(R.id.myaccount_layout);
        expand_layout = (LinearLayout) findViewById(R.id.expandlayout);
        myAccount_explistview = (ListView) findViewById(R.id.expandableListView2);

        epin_expand_layout = (LinearLayout) findViewById(R.id.epin_expandlayout);
        epinDetailTv = (TextView) findViewById(R.id.expandable_epin_Tv);
        epinRegisterTv = (TextView) findViewById(R.id.expandable_register_epin_Tv);

        acc_down_icon = (ImageView) findViewById(R.id.down_arrow);
        acc_up_icon = (ImageView) findViewById(R.id.up_arrow);

        epin_down_icon = (ImageView) findViewById(R.id.epin_down_arrow);
        epin_up_icon = (ImageView) findViewById(R.id.epin_up_arrow);

       LinearLayout activity_main_webview99 =(LinearLayout)findViewById(R.id.linearview_webview);
        activity_main_webview99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(),WebViewMission99.class));
            }
        });


        myProfileLayout.setOnClickListener(this);
        placementDetailLayout.setOnClickListener(this);
        myaccount_layout.setOnClickListener(this);
        epinLayout.setOnClickListener(this);
        //trainingLayout.setOnClickListener(this);
        enquiryLayout.setOnClickListener(this);
        dashboardLayout.setOnClickListener(this);
        logout_btn.setOnClickListener(this);


        // update.setOnClickListener(this);
        //   dropdown_btn.setOnClickListener(this);


        userId = getIntent().getStringExtra("email");
        memberId = getIntent().getStringExtra("memberId");

        // Toast.makeText(getApplicationContext(), "mem Id is" + memberId, Toast.LENGTH_SHORT).show();
        tvUsername.setText(userId);
        fm = FragmentMainActivity.this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
     /*   DashboardFragment fragment = new DashboardFragment();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.replace(R.id.activity_main_content_fragment, fragment, "Dashboard");
        ft.addToBackStack("Dashboard");
        ft.commit();*/
        MyProfileFragment profileFragment = new MyProfileFragment();
        ft.replace(R.id.activity_main_content_fragment, profileFragment, "My Profile");
        ft.addToBackStack("My Profile");
        ft.commit();
        //  update.setVisibility(View.VISIBLE);
        tvTitle.setText("Dashboard");

        btMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleMenu(v);

            }
        });
    }


    public void toggleMenu(View v) {
        mainLayout.toggleMenu();

    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        //  FragmentTransaction ft;
        if (v == myProfileLayout) {
            //   Toast.makeText(getApplicationContext(), "Weolcome to your profile", Toast.LENGTH_SHORT).show();
            MyProfileFragment profileFragment = new MyProfileFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.activity_main_content_fragment, profileFragment, "My Profile");
            ft.addToBackStack("My Profile");
            ft.commit();
            //  update.setVisibility(View.VISIBLE);
            setTitle("My Profile");

            toggleMenu(v);
        } else if (v == placementDetailLayout) {
            // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            PlacementDetailsFragment pdFragment = new PlacementDetailsFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.activity_main_content_fragment, pdFragment, "Placement Details");
            ft.addToBackStack("Placement Details");
            ft.commit();
            setTitle("Placement Details");
            //tvTitle.setText("Placement Details");
            toggleMenu(v);
        } else if (v == myaccount_layout) {

            // update.setVisibility(View.INVISIBLE);
            final String[] myAccountSubList = {"Payout List", "Plot Booking Report", "Cheque Pay Status",
                    "Cash Pay Status", "Due Emi Customer List", "Birthday List", "Payout vs Business", "Cancel Booking Report", "Payout Deduction Details"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    FragmentMainActivity.this, R.layout.my_account_list_row, myAccountSubList);
            myAccount_explistview.setAdapter(adapter);
            if (flag_account == true) {
                expand_layout.setVisibility(View.VISIBLE);
                acc_down_icon.setVisibility(View.GONE);
                acc_up_icon.setVisibility(View.VISIBLE);
                flag_account = false;

            } else {
                // myAccount_explistview.setVisibility(View.GONE);
                expand_layout.setVisibility(View.GONE);
                acc_up_icon.setVisibility(View.GONE);
                acc_down_icon.setVisibility(View.VISIBLE);
                //down_arrow.setVisibility(View.VISIBLE);
                flag_account = true;
            }

            myAccount_explistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    myAccSublist(position, view);
                    // Toast.makeText(getApplicationContext(), "You have clicked " + position, Toast.LENGTH_SHORT).show();
                }
            });

            //tvTitle.setText(" My Account");
            // toggleMenu(v);
        } else if (v == epinLayout) {
            if (flag_epin == true) {
                epin_expand_layout.setVisibility(View.VISIBLE);
                epin_down_icon.setVisibility(View.GONE);
                epin_up_icon.setVisibility(View.VISIBLE);
                flag_epin = false;

            } else {
                // myAccount_explistview.setVisibility(View.GONE);
                epin_expand_layout.setVisibility(View.GONE);
                epin_up_icon.setVisibility(View.GONE);
                epin_down_icon.setVisibility(View.VISIBLE);
                flag_epin = true;
            }

            epinDetailTv.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EpinDetailFilterFragment epinDetailFragment = new EpinDetailFilterFragment();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.activity_main_content_fragment, epinDetailFragment, "E-pin");
                            ft.addToBackStack("E-pin");
                            ft.commit();
                            tvTitle.setText("E-pin");
                            toggleMenu(v);
                        }
                    }
            );

            epinRegisterTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(FragmentMainActivity.this, EpinRegisterActivity.class);
                    i.putExtra("memberId", memberId);
                    startActivityForResult(i, 2);
                }
            });

            //  tvTitle.setText("E-pin");

        }/* else if (v == trainingLayout) {
            // Toast.makeText(getApplicationContext(), "This is all about Shine City", Toast.LENGTH_SHORT).show();
            TrainingFragment trainingFragment = new TrainingFragment();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.activity_main_content_fragment, trainingFragment, "Training");
            ft.addToBackStack("Training");
            ft.commit();
            // update.setVisibility(View.INVISIBLE);
            tvTitle.setText("Training");
            toggleMenu(v);

        } */ else if (v == enquiryLayout) {
            EnquiryFragment enquiryFragment = new EnquiryFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.replace(R.id.activity_main_content_fragment, enquiryFragment, "Enquiry");
            ft.addToBackStack("Enquiry");
            ft.commit();
            //  update.setVisibility(View.INVISIBLE);
            //  btn.setVisibility(View.VISIBLE);
            tvTitle.setText("Enquiry ");
            toggleMenu(v);
        } else if (v == dashboardLayout) {
            DashboardFragment fragment = new DashboardFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.replace(R.id.activity_main_content_fragment, fragment, "Dashboard");
            ft.addToBackStack("Dashboard");
            ft.commit();
            //  update.setVisibility(View.VISIBLE);
            tvTitle.setText("Dashboard");
            toggleMenu(v);
        }

      /*  else if (v == update) {

            Toast.makeText(getApplicationContext(), "Do you want to update your profile ?", Toast.LENGTH_SHORT).show();

        }*/

        else if (v == logout_btn) {
            //dropdownMenu();
            signOut();
            // Toast.makeText(getApplicationContext(), "show the dropdown here ?", Toast.LENGTH_SHORT).show();

        }
    }

    /*  public void dropdownMenu() {

          final PopupMenu popupMenu = new PopupMenu(FragmentMainActivity.this,dropdown_btn);
          popupMenu.getMenuInflater().inflate(R.menu.menu_splash_screen,popupMenu.getMenu());


          popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
              @Override
              public boolean onMenuItemClick(MenuItem menuItem) {
                  Toast.makeText(getApplicationContext(), "You have Selected  "+ menuItem.getTitle(), Toast.LENGTH_LONG).show();

                  return true;
              }
          });
          popupMenu.show();
      }
  */
    public void myAccSublist(int position, View view) {
        int subposition = position;
        View v = view;
        switch (subposition) {
            case 0:
                //Payout list position
                PayoutListFragment plFragment = new PayoutListFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.activity_main_content_fragment, plFragment, "Payout List");
                ft.addToBackStack("Payout List");
                ft.commit();

                tvTitle.setText("Payout List");
                toggleMenu(v);

                break;

            case 1:
                //plot booking report position 1
                PlotBookingFilterFragment pbFragment = new PlotBookingFilterFragment();
                FragmentTransaction plotFt = fm.beginTransaction();
                plotFt.replace(R.id.activity_main_content_fragment, pbFragment, "Plot Booking");
                plotFt.addToBackStack("Plot Booking ");
                plotFt.commit();

                tvTitle.setText("Plot Booking ");
                toggleMenu(v);
                break;

            case 2:
                // 2 cheque pay status positon
                ChequePayStatusFilterFragment chequefragment = new ChequePayStatusFilterFragment();
                FragmentTransaction chequeFt = fm.beginTransaction();
                chequeFt.addToBackStack(null);
                chequeFt.replace(R.id.activity_main_content_fragment, chequefragment, "Cheque Status");
                chequeFt.addToBackStack("Cheque Status");
                chequeFt.commit();

                tvTitle.setText("Cheque Status");
                toggleMenu(v);
                break;

            case 3:
                // 3 cash  pay status positon
                CashPayStatusFilterFragment cashfragment = new CashPayStatusFilterFragment();
                FragmentTransaction cashFt = fm.beginTransaction();
                cashFt.addToBackStack(null);
                cashFt.replace(R.id.activity_main_content_fragment, cashfragment, "Cash Status");
                cashFt.addToBackStack("Cash Status");
                cashFt.commit();

                tvTitle.setText("Cash Status");
                toggleMenu(v);

                break;

            case 4:
                //  4 Due emi  positon
                DueEmiFilterFragment dueemifrag = new DueEmiFilterFragment();
                FragmentTransaction dueemiTrans = fm.beginTransaction();
                dueemiTrans.replace(R.id.activity_main_content_fragment, dueemifrag, "Due Emi");
                dueemiTrans.addToBackStack("Due Emi ");
                dueemiTrans.commit();

                tvTitle.setText("Due Emi");
                toggleMenu(v);
                break;

            case 5:
                BirthdayFilterFragment bdlFragment = new BirthdayFilterFragment();
                FragmentTransaction bdtransation = fm.beginTransaction();
                bdtransation.replace(R.id.activity_main_content_fragment, bdlFragment, "Birthday List");
                bdtransation.addToBackStack("Birthday List");
                bdtransation.commit();

                tvTitle.setText("Birthday List");
                toggleMenu(v);
                break;

            /*case 6:
*//*
                DueEmiCustomerListFragment emiClFrag = new DueEmiCustomerListFragment();
                FragmentTransaction emiCltrans = fm.beginTransaction();
                emiCltrans.replace(R.id.activity_main_content_fragment, emiClFrag, "Emi CustomerList");
                emiCltrans.addToBackStack("Emi CustomerList");
                emiCltrans.commit();

                tvTitle.setText("Emi CustomerList");
                toggleMenu(v);

                break;*/

            case 6:

                PayoutVsBusinessFragment pVsbFragment = new PayoutVsBusinessFragment();
                FragmentTransaction ft9 = fm.beginTransaction();
                ft9.replace(R.id.activity_main_content_fragment, pVsbFragment, "Payout Vs Bussiness");
                ft9.addToBackStack("Payout Vs Bussiness");
                ft9.commit();

                tvTitle.setText("Payout Vs Business");
                toggleMenu(v);
                break;

            case 7:

                CancelBookingFragment cbFragment = new CancelBookingFragment();
                FragmentTransaction ft8 = fm.beginTransaction();
                ft8.replace(R.id.activity_main_content_fragment, cbFragment, "Cancel Booking");
                ft8.addToBackStack("Cancel Booking");
                ft8.commit();

                tvTitle.setText("Cancel Booking");
                toggleMenu(v);

                break;
            case 8:
                PayoutDeductionDetailsFragment pddfragment = new PayoutDeductionDetailsFragment();
                FragmentTransaction ddft = fm.beginTransaction();
                ddft.replace(R.id.activity_main_content_fragment, pddfragment, "Deduction Details");
                ddft.addToBackStack("Deduction Details");
                ddft.commit();

                tvTitle.setText("Deduction Details");
                toggleMenu(v);

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            //  Toast.makeText(this, "Epin is successfully registered", Toast.LENGTH_LONG).show();
        } else {
            //   Toast.makeText(this, "Something wrong.. Kindly try again to register", Toast.LENGTH_LONG).show();
        }
    }

    public void signOut() {

        AlertDialog.Builder builder = new AlertDialog.Builder(FragmentMainActivity.this);
        builder.setTitle("Exit the app? ")
                .setMessage(
                        "Are you sure you want to sign out of your account ?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.remove("logged");
                                editor.remove("email");
                                editor.remove("password");
                                editor.commit();
                                finish();

                              /*  startActivity(new Intent(
                                        getApplicationContext(),
                                        LoginActivity.class));*/
                            }
                        });

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();

    }

    public void onBackPressed() {
        super.onBackPressed();
        if (mainLayout.isMenuShown()) {
            mainLayout.toggleMenu();

        }
        int framentCount = this.getSupportFragmentManager().getBackStackEntryCount();
        if (framentCount != 0) {
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(framentCount - 1);
            String str = backEntry.getName(); //the tag of the fragment
            if (str.equals("Placement Details"))
                tvTitle.setText("Placement Details");
            else if (str.equals("Payout List"))
                tvTitle.setText("Payout List");
            else if (str.equals("Plot Booking"))
                tvTitle.setText("Plot Booking");
            else if (str.equals("Cheque Status"))
                tvTitle.setText("Cheque Status");
            else if (str.equals("Cash Status"))
                tvTitle.setText("Cash Status");
            else if (str.equals("Due Emi"))
                tvTitle.setText("Due Emi");
            else if (str.equals("Birthday List"))
                tvTitle.setText("Birthday List");
            else if (str.equals("Emi CustomerList"))
                tvTitle.setText("Emi CustomerList");
            else if (str.equals("Payout Vs Bussiness"))
                tvTitle.setText("Payout Vs Bussiness");
            else if (str.equals("Cancel Booking"))
                tvTitle.setText("Cancel Booking");
            else if (str.equals("Deduction Details"))
                tvTitle.setText("Deduction Details");
            else if (str.equals("E-pin"))
                tvTitle.setText("E-pin");
            else if (str.equals("Training"))
                tvTitle.setText("Training");
            else if (str.equals("Enquiry"))
                tvTitle.setText("Enquiry");
            else if (str.equals("My Profile")) {
                tvTitle.setText("My Profile");
            }
        } else {
          finish();
        }


    }

}