package myappplication.noida.quaere.groupalternate.swipetab;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import myappplication.noida.quaere.groupalternate.CancelBookingFragment;
import myappplication.noida.quaere.groupalternate.DueEmiCustomerListFragment;
import myappplication.noida.quaere.groupalternate.EnquiryFragment;
import myappplication.noida.quaere.groupalternate.EpinRegisterActivity;
import myappplication.noida.quaere.groupalternate.PayoutDeductionDetailsFragment;
import myappplication.noida.quaere.groupalternate.PayoutListFragment;
import myappplication.noida.quaere.groupalternate.PayoutVsBusinessFragment;
import myappplication.noida.quaere.groupalternate.PlacementDetailsFragment;
import myappplication.noida.quaere.groupalternate.R;
import myappplication.noida.quaere.groupalternate.activities.MyProfileActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by intex on 8/31/2015.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    TextView dashboard_username_tv, dashboard_userIdTv, dashboard_dob_tv, dashboard_myProfileTv, dashboard_placementDetailTv, dashboard_enquiryTv,
            dashboard_payoutListTv, dashboard_plotBookingTv, dashboard_chequeTv, dashboard_cashTv, dashboard_dueEmiTv, dashboard_bdListv, dashboard_payVsBznsTv,
            dashboard_cancelBookingTv, dashboard_payoutDdTv, dashboard_signOut, dashboard_epinDetaillTv, dashboard_epinRegisterTv;//dashboard_dueEmiCustomerTv
    LinearLayout myAccountLayout, myAccountLayoutExpand, myEpinLayout, myEpinLayoutExpand;
    String username, userDob, userEmail;
    ImageView myaccount_up, myaccount_down, myEpinUp, myEpinDown, profile_pic;

    String userId, memberId;
    String user_profile_url;
    Boolean flag_account = true;
    private Boolean flag_epin = true;
    FragmentManager fm;
    private static Bitmap Image = null;
    private static final int CAPTURE_IMAGE = 0;
    private static final int SELECT_PICTURE = 1;
    SharedPreferences shre,prefs;
    DashboardAsyncTaks task;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        findViewById(view);

        shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String previouslyEncodedImage = shre.getString("image_data", "");
        fm = getActivity().getSupportFragmentManager();

        prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = prefs.getString("email", "");
        memberId = prefs.getString("memberId", "");


       // System.out.println("User Id "+userId +"  memberId "+memberId);

        user_profile_url = "http://demo8.mlmsoftindia.com//ShinePanel.svc/MemberProfile/" + userId;
        task = new DashboardAsyncTaks();
        task.execute();

        if (!previouslyEncodedImage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            profile_pic.setImageBitmap(bitmap);
        }

        //Toast.makeText(getActivity(), memberId + "  " + memberId, Toast.LENGTH_SHORT).show();
        dashboard_userIdTv.setText(userId);




        return view;

    }


    private void findViewById(View view) {
        dashboard_userIdTv = (TextView) view.findViewById(R.id.dashboard_userIdTv);
        dashboard_username_tv = (TextView) view.findViewById(R.id.dashboard_userNameTv);
        dashboard_dob_tv = (TextView) view.findViewById(R.id.dashboard_dobTv);

        dashboard_myProfileTv = (TextView) view.findViewById(R.id.dashboarad_myProfile);
        dashboard_placementDetailTv = (TextView) view.findViewById(R.id.dashboarad_placementDetails);
        dashboard_enquiryTv = (TextView) view.findViewById(R.id.dashboarad_enquiry);
        dashboard_payoutListTv = (TextView) view.findViewById(R.id.dashboarad_payoutlist);
        dashboard_plotBookingTv = (TextView) view.findViewById(R.id.dashboarad_plot_booking_report);

        dashboard_chequeTv = (TextView) view.findViewById(R.id.dashboarad_cheque_pay);
        dashboard_cashTv = (TextView) view.findViewById(R.id.dashboarad_cash_pay);
        dashboard_dueEmiTv = (TextView) view.findViewById(R.id.dashboarad_dueEmi);
        dashboard_bdListv = (TextView) view.findViewById(R.id.dashboarad_bdList);


        // dashboard_dueEmiCustomerTv = (TextView) view.findViewById(R.id.dashboarad_dueEmiCustomer);
        dashboard_payVsBznsTv = (TextView) view.findViewById(R.id.dashboarad_payVsBzns);
        dashboard_cancelBookingTv = (TextView) view.findViewById(R.id.dashboarad_cancelBooking);
        dashboard_payoutDdTv = (TextView) view.findViewById(R.id.dashboarad_payoutDd);
        dashboard_epinDetaillTv = (TextView) view.findViewById(R.id.dashboard_epin_detailTv);
        dashboard_signOut = (TextView) view.findViewById(R.id.dashboarad_signout);

        dashboard_epinRegisterTv = (TextView) view.findViewById(R.id.dashboard_register_epin_Tv);

        myAccountLayout = (LinearLayout) view.findViewById(R.id.dashboard_myaccount_layout);
        myAccountLayoutExpand = (LinearLayout) view.findViewById(R.id.dashboard_myaccount_expand);
        myEpinLayout = (LinearLayout) view.findViewById(R.id.dashboard_myEpin_layout);
        myEpinLayoutExpand = (LinearLayout) view.findViewById(R.id.dashboard_myEpin_ExpandLayout);
        myaccount_down = (ImageView) view.findViewById(R.id.dashboard_my_acc_down_arrow);
        myaccount_up = (ImageView) view.findViewById(R.id.dashboard_my_acc_up_arrow);
        myEpinUp = (ImageView) view.findViewById(R.id.dashboard_epin_up_arrow);
        myEpinDown = (ImageView) view.findViewById(R.id.dashboard_epin_down_arrow);
        profile_pic = (ImageView) view.findViewById(R.id.dashboard_profile_pic);

       // dashboard_userIdTv.setText(userId);
        myAccountLayout.setOnClickListener(this);
        myEpinLayout.setOnClickListener(this);
        dashboard_myProfileTv.setOnClickListener(this);
        dashboard_placementDetailTv.setOnClickListener(this);
        dashboard_enquiryTv.setOnClickListener(this);
        dashboard_payoutListTv.setOnClickListener(this);
        dashboard_plotBookingTv.setOnClickListener(this);
        dashboard_chequeTv.setOnClickListener(this);
        dashboard_signOut.setOnClickListener(this);
        dashboard_epinDetaillTv.setOnClickListener(this);
        dashboard_epinRegisterTv.setOnClickListener(this);
        dashboard_cashTv.setOnClickListener(this);
        dashboard_dueEmiTv.setOnClickListener(this);
        dashboard_bdListv.setOnClickListener(this);
        profile_pic.setOnClickListener(this);
        //dashboard_dueEmiCustomerTv.setOnClickListener(this);
        dashboard_payVsBznsTv.setOnClickListener(this);
        dashboard_cancelBookingTv.setOnClickListener(this);
        dashboard_payoutDdTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == myAccountLayout) {

            if (flag_account == true) {
                myAccountLayoutExpand.setVisibility(View.VISIBLE);
                myaccount_down.setVisibility(View.GONE);
                myaccount_up.setVisibility(View.VISIBLE);
                flag_account = false;

            } else {
                // myAccount_explistview.setVisibility(View.GONE);
                myAccountLayoutExpand.setVisibility(View.GONE);
                myaccount_up.setVisibility(View.GONE);
                myaccount_down.setVisibility(View.VISIBLE);
                //down_arrow.setVisibility(View.VISIBLE);
                flag_account = true;
            }
        } else if (v == myEpinLayout) {

            if (flag_account == true) {
                myEpinLayoutExpand.setVisibility(View.VISIBLE);
                myEpinDown.setVisibility(View.GONE);
                myEpinUp.setVisibility(View.VISIBLE);
                flag_account = false;

            } else {
                // myAccount_explistview.setVisibility(View.GONE);
                myEpinLayoutExpand.setVisibility(View.GONE);
                myEpinUp.setVisibility(View.GONE);
                myEpinDown.setVisibility(View.VISIBLE);
                //down_arrow.setVisibility(View.VISIBLE);
                flag_account = true;
            }
        }

        else if (v == dashboard_myProfileTv) {
          /*  MyProfileFragment profileFragment = new MyProfileFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.root_frame, profileFragment, "My Profile");
            ft.addToBackStack("My Profile");
            ft.commit();
            ( getActivity()).setTitle("My Profile");*/
            startActivity(new Intent(getActivity(), MyProfileActivity.class));

        } else if (v == dashboard_placementDetailTv) {
           // Toast.makeText(getActivity(),"Access through Dashboard from Favorites",Toast.LENGTH_LONG).show();
          //  startActivity(new Intent(getActivity(), PlacementDetailsActivity.class));
          PlacementDetailsFragment pdFragment = new PlacementDetailsFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.root_frame, pdFragment, "Placement Details");
            ft.addToBackStack("Placement Details");
            //  actionBar.setTitle("Placement Details");
            ft.commit();
            //((FragmentMainActivity) getActivity()).setTitle("Placement Details");
        } else if (v == dashboard_payoutListTv) {
            //Payout list position
           PayoutListFragment plFragment = new PayoutListFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.root_frame, plFragment, "Payout List");
            ft.addToBackStack("Payout List");
            ft.commit();
            //((FragmentMainActivity) getActivity()).setTitle("Payout List");
        } else if (v == dashboard_bdListv) {

            BirthdayFilterFragmentSwipe bdlFragment = new BirthdayFilterFragmentSwipe();
            FragmentTransaction bdtransation = fm.beginTransaction();
            bdtransation.replace(R.id.root_frame, bdlFragment, "Birthday List");
            bdtransation.addToBackStack("Birthday List");
            bdtransation.commit();
           // ((FragmentMainActivity) getActivity()).setTitle("Birthday List");

        } else if (v == dashboard_plotBookingTv) {
            //plot booking report position 1
            PlotBookingFilterFragmentSwipe pbFragment = new PlotBookingFilterFragmentSwipe();
            FragmentTransaction plotFt = fm.beginTransaction();
            plotFt.replace(R.id.root_frame, pbFragment, "Plot Booking");
            plotFt.addToBackStack("Plot Booking ");
            plotFt.commit();
           // ((FragmentMainActivity) getActivity()).setTitle("Plot Booking");


        } else if (v == dashboard_cashTv) {
            // 3 cash  pay status positon
           CashPayStausFilterFragmentSwipe cashfragment = new CashPayStausFilterFragmentSwipe();
            FragmentTransaction cashFt = fm.beginTransaction();
            cashFt.replace(R.id.root_frame, cashfragment, "Cash Status");
            cashFt.addToBackStack("Cash Status");
            cashFt.commit();
          //  ((FragmentMainActivity) getActivity()).setTitle("Cash Status");

        } else if (v == dashboard_chequeTv) {
            // 2 cheque pay status positon
           ChequePayStatusFilterFragmentSwipe chequefragment = new ChequePayStatusFilterFragmentSwipe();
            FragmentTransaction chequeFt = fm.beginTransaction();
            chequeFt.replace(R.id.root_frame, chequefragment, "Cheque Status");
            chequeFt.addToBackStack("Cheque Status");
            chequeFt.commit();
           // ((FragmentMainActivity) getActivity()).setTitle("Cheque Status");

        } else if (v == dashboard_dueEmiTv) {
            //  4 Due emi  positon
            DueEmiCustomerListFragment dueemifrag = new DueEmiCustomerListFragment();
            FragmentTransaction dueemiTrans = fm.beginTransaction();
            dueemiTrans.replace(R.id.root_frame, dueemifrag, "Due Emi");
            dueemiTrans.addToBackStack("Due Emi ");
            dueemiTrans.commit();
          //  ((FragmentMainActivity) getActivity()).setTitle("Due Emi");


        } /*else if (v == dashboard_dueEmiCustomerTv) {
            DueEmiCustomerListFragment emiClFrag = new DueEmiCustomerListFragment();
            FragmentTransaction emiCltrans = fm.beginTransaction();
            emiCltrans.replace(R.id.activity_main_content_fragment, emiClFrag, "Emi CustomerList");
            emiCltrans.addToBackStack("Emi CustomerList");
            emiCltrans.commit();
            ((FragmentMainActivity) getActivity()).setTitle("Emi CustomerList");

        } */ else if (v == dashboard_payVsBznsTv) {
            PayoutVsBusinessFragment pVsbFragment = new PayoutVsBusinessFragment();
            FragmentTransaction ft9 = fm.beginTransaction();
            ft9.replace(R.id.root_frame, pVsbFragment, "Payout Vs Bussiness");
            ft9.addToBackStack("Payout Vs Bussiness");
            ft9.commit();
          //  ((FragmentMainActivity) getActivity()).setTitle("Payout Vs Bussiness");

        } else if (v == dashboard_cancelBookingTv) {
           CancelBookingFragment cbFragment = new CancelBookingFragment();
            FragmentTransaction ft8 = fm.beginTransaction();
            ft8.replace(R.id.root_frame, cbFragment, "Cancel Booking");
            ft8.addToBackStack("Cancel Booking");
            ft8.commit();
            //((FragmentMainActivity) getActivity()).setTitle("Cancel Booking");
        } else if (v == dashboard_payoutDdTv) {
            PayoutDeductionDetailsFragment pddfragment = new PayoutDeductionDetailsFragment();
            FragmentTransaction ddft = fm.beginTransaction();
            ddft.replace(R.id.root_frame, pddfragment, "Deduction Details");
            ddft.addToBackStack("Deduction Details");
            ddft.commit();
          //  ((FragmentMainActivity) getActivity()).setTitle("Deduction Details");

        } else if (v == dashboard_signOut) {
            signOut();

        } else if (v == dashboard_epinDetaillTv) {
           EpinDetailFragmentSwipe epinDetailFragment = new EpinDetailFragmentSwipe();

            Bundle b = new Bundle();
            b.putString("epin","M");
            epinDetailFragment.setArguments(b);
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.root_frame, epinDetailFragment, "E-pin");
            ft.addToBackStack("E-pin");
            ft.commit();
           // ((FragmentMainActivity) getActivity()).setTitle("E-pin");

        } else if (v == dashboard_epinRegisterTv) {
            Intent i = new Intent(getActivity(), EpinRegisterActivity.class);
            i.putExtra("memberId", memberId);
            startActivityForResult(i, 2);

        } else if (v == dashboard_enquiryTv) {
          //  startActivity(new Intent(getActivity(), EnquiryActivity.class));
            EnquiryFragment enquiryFragment = new EnquiryFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.replace(R.id.root_frame, enquiryFragment, "Enquiry");
            ft.addToBackStack(null);
            ft.commit();
        //    ((FragmentMainActivity) getActivity()).setTitle("Enquiry");

        } else if (v == profile_pic) {
            // Toast.makeText(getActivity(),"Here image will be uploaded",Toast.LENGTH_SHORT).show();
            selectImage();

        }
    }

    private void selectImage() {
        final Dialog openDialog = new Dialog(getActivity());
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        openDialog.setContentView(R.layout.upload_pic_dialog);
        Window window = openDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setGravity(Gravity.RIGHT | Gravity.TOP);
        openDialog.getWindow().getAttributes().verticalMargin = 0.09F;
        openDialog.getWindow().getAttributes().horizontalMargin = 0.09F;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        openDialog.show();

        final TextView galleryImgTv = (TextView) openDialog.findViewById(R.id.gallery_image);
        final TextView cameraImgTv = (TextView) openDialog.findViewById(R.id.camera_image);

        galleryImgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, SELECT_PICTURE);
                openDialog.dismiss();

            }
        });

        cameraImgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURE_IMAGE);
                openDialog.dismiss();

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            storeImage(thumbnail);
            onCaptureImageResult(data);

        } else if (requestCode == SELECT_PICTURE) {
            try {
                Uri mImageUri = data.getData();
                Image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
                storeImage(Image);
           /*   if (getOrientation(getActivity(), mImageUri) != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(getOrientation(getActivity(), mImageUri));
                    if (rotateImage != null)
                        rotateImage.recycle();
                    rotateImage = Bitmap.createBitmap(Image, 0, 0, Image.getWidth(), Image.getHeight(), matrix,true);
                  //storeImage(rotateImage);
                    profile_pic.setImageBitmap(rotateImage);
                } else{
                 // storeImage(rotateImage);
                 profile_pic.setImageBitmap(Image);
              }*/

            } catch (FileNotFoundException e) {
               Toast.makeText(getActivity(),"Failde to load Image"+e,Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getActivity(),"Failde to load Image"+e,Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }


    }

    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        storeImage(thumbnail);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        /*thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profile_pic.setImageBitmap(thumbnail);*/
        //  profile_pic.setBackgroundDrawable(drawable);

    }

    private void storeImage(Bitmap thumbnail) {
        // Removing image saved earlier
        shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = shre.edit();
        edit.remove("image_data");
        edit.commit();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;

        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profile_pic.setImageBitmap(thumbnail);
        byte[] b = bytes.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        //saving image to shared preferences
        edit.putString("image_data", encodedImage);
        edit.commit();
    }


    private void signOut() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Exit the app? ")
                .setMessage(
                        "Are you sure you want to sign out of your account ?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                SharedPreferences preferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.remove("logged");
                                editor.remove("email");
                                editor.remove("password");
                                editor.commit();
                                getActivity().finish();

                                /*startActivity(new Intent(
                                        getActivity(),
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

    private class DashboardAsyncTaks extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
          //  pd = new ProgressDialog(getActivity());
           // pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient client = new DefaultHttpClient();
               // String user_profile_url = "http://demo8.mlmsoftindia.com//ShinePanel.svc/MemberProfile/" + userId;
                HttpPost post = new HttpPost(user_profile_url);
                HttpResponse responsePOST = client.execute(post);
                HttpEntity resEntity = responsePOST.getEntity();
                String response = EntityUtils.toString((resEntity));
                Log.v("My profile response :", response);

                if (response != null) {
                    try {
                        JSONArray jArray = new JSONArray(response);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jObj = jArray.getJSONObject(i);
                            //Getting personal details
                            username = jObj.getString("FirstName");

                            userDob = jObj.getString("DOB");
                            userEmail = jObj.getString("EmailId");
                           /* Log.i("userEmail", userEmail);
                            Log.i("userDob", userDob);
                            Log.i("username", username);
*/
                        }
                    } catch (JSONException e) {
                        System.out.println("Exception raised here :"+e.toString());
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
              //  Toast.makeText(getActivity(), "Server is Failed ", Toast.LENGTH_LONG).show();


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          //  pd.dismiss();
            dashboard_username_tv.setText(username);
            dashboard_dob_tv.setText(userDob);

        }
    }
}
