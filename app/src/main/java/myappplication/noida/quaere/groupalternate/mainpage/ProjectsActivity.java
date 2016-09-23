package myappplication.noida.quaere.groupalternate.mainpage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import myappplication.noida.quaere.groupalternate.LoginActivity;
import myappplication.noida.quaere.groupalternate.R;
import myappplication.noida.quaere.groupalternate.swipetab.MainTabActivity;

/**
 * Created by intex on 2/28/2016.
 */
public class ProjectsActivity extends Activity {
    // TextView home;
    LinearLayout home, misscall_layout, tollfree_layout, sms_layout, contactus_layout, login_layout;
    GridView gridView;
    CustomAdapter adapter;
    ImageView back;

    public static String[] projectsNameList = {"Twin Spire", "Grih Sangam", "Paradise Garden", "Mountain Heaven", "Nature Valley",
            "Zaire Sparkle Valley", "Shine Valley", "Signature Height", "Solitair City", "Chandrok Kashiyana", "Kashiyana",
            "Kutumb Kashiyana", "Pole Star City", "Nature View", "Xhevahire City", "Arise Velvet", "Lode Star", "Samridhi Gullak"};

    public static int[] projectImages = {R.drawable.twin_spire, R.drawable.grih_sangam, R.drawable.paradise_garden,
            R.drawable.mountain_heaven, R.drawable.nature_valley, R.drawable.zaire_sparkle_valley, R.drawable.shine_valley,
            R.drawable.signature_height, R.drawable.solitair_city, R.drawable.chandrok_kashiyana, R.drawable.kashiyanaa,
            R.drawable.kutumb_kashiyana, R.drawable.pole_star_city, R.drawable.nature_view, R.drawable.xhevahire_city,
            R.drawable.arise_velvet, R.drawable.lodestar_logo, R.drawable.samridhi_gullak_logo1,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects_main_page);
        home = (LinearLayout) findViewById(R.id.home_layout);
        misscall_layout = (LinearLayout) findViewById(R.id.misscall_layout);
        tollfree_layout = (LinearLayout) findViewById(R.id.tollfree_layout);
        sms_layout = (LinearLayout) findViewById(R.id.sms_layout);
        contactus_layout = (LinearLayout) findViewById(R.id.contactus_layout);
        login_layout = (LinearLayout) findViewById(R.id.login_layout);
        gridView = (GridView) findViewById(R.id.grid_view);
       back = (ImageView) findViewById(R.id.project_backBtn);
        // gridView.setDrawSelectorOnTop(true);
        //   gridView.setSelector(getResources().getDrawable(R.drawable.gridview_selector));
        gridView.setHorizontalSpacing(6);
        gridView.setVerticalSpacing(6);


        adapter = new CustomAdapter(this, projectsNameList, projectImages);
        gridView.setAdapter(adapter);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectsActivity.this, LoginActivity.class));
            }
        });

        misscall_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:8881232121"));
                startActivityForResult(callIntent, 1);
            }
        });

        tollfree_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tollfreeIntent = new Intent(Intent.ACTION_CALL);
                tollfreeIntent.setData(Uri.parse("tel:18002000480"));
                startActivityForResult(tollfreeIntent, 2);
            }
        });

        sms_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:53030");
                Intent message = new Intent(Intent.ACTION_SENDTO, uri);
                message.putExtra("sms_body", "SHINE");
                // use if you want to get message body
                startActivityForResult(message, 3);

            }
        });
        contactus_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectsActivity.this, ContactUsActivity.class));

            }
        });
        login_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectsActivity.this, MainTabActivity.class));

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // finish();
                onBackPressed();
            }
        });
   /*     gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();


               // Toast.makeText(ProjectsActivity.this, "You Clicked " + name, Toast.LENGTH_LONG).show();
            }
        });*/
    }
    public class CustomAdapter extends BaseAdapter {

        String[] result;
        Context context;
        int[] imageId;
        View savedView;
        private LayoutInflater inflater = null;

        public CustomAdapter(Activity mainActivity, String[] prgmNameList, int[] prgmImages) {
            result = prgmNameList;
            context = mainActivity;
            imageId = prgmImages;
            inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return result.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class Holder {
            TextView tv;
            ImageView img;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();
            final View rowView;

            rowView = inflater.inflate(R.layout.row_grid, null);
            holder.tv = (TextView) rowView.findViewById(R.id.textView1);
            holder.img = (ImageView) rowView.findViewById(R.id.imageView1);

            holder.tv.setText(result[position]);
            holder.img.setImageResource(imageId[position]);

            rowView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Toast.makeText(ProjectsActivity.this, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
                    String projectName = result[position];
                    callingProjectView(projectName);
                    adapter.notifyDataSetChanged();
                    rowView.setBackgroundResource(android.R.color.background_light);
                    if (savedView == null) {
                        savedView = rowView;
                    } else {
                        savedView.setBackgroundResource(android.R.color.background_dark);
                        savedView = rowView;
                    }
                }
            });

            return rowView;
        }
    }

    private void callingProjectView(String projectName) {

        if (projectName.equals("Grih Sangam")) {
            startActivity(new Intent(ProjectsActivity.this, GrihSangam.class));
        } else if (projectName.equals("Chandrok Kashiyana")) {
            startActivity(new Intent(ProjectsActivity.this, ChandrokKashiyana.class));
        } else if (projectName.equals("Kutumb Kashiyana")) {
            startActivity(new Intent(ProjectsActivity.this, KutumbKashiyana.class));
        } else if (projectName.equals("Mountain Heaven")) {
            startActivity(new Intent(ProjectsActivity.this, MountainHeaven.class));
        } else if (projectName.equals("Twin Spire")) {
            startActivity(new Intent(ProjectsActivity.this, TwinSpire.class));
        } else if (projectName.equals("Paradise Garden")) {
            startActivity(new Intent(ProjectsActivity.this, ParadiseGarden.class));
        } else if (projectName.equals("Nature Valley")) {
            startActivity(new Intent(ProjectsActivity.this, NatureValley.class));
        } else if (projectName.equals("Zaire Sparkle Valley")) {
            startActivity(new Intent(ProjectsActivity.this, ZaireSparkleValley.class));
        } else if (projectName.equals("Shine Valley")) {
            startActivity(new Intent(ProjectsActivity.this, ShineValley.class));
        } else if (projectName.equals("Signature Height")) {
            startActivity(new Intent(ProjectsActivity.this, SignatureHeight.class));
        } else if (projectName.equals("Solitair City")) {
            startActivity(new Intent(ProjectsActivity.this, SolitaireCity.class));
        } else if (projectName.equals("Kashiyana")) {
            startActivity(new Intent(ProjectsActivity.this, Kashiyana.class));
        } else if (projectName.equals("Nature View")) {
            startActivity(new Intent(ProjectsActivity.this, ShineNatureView.class));
        } else if (projectName.equals("Xhevahire City")) {
            startActivity(new Intent(ProjectsActivity.this, XhevahireCity.class));
        } else if (projectName.equals("Arise Velvet")) {
            startActivity(new Intent(ProjectsActivity.this, AriseVelvet.class));
        } else if (projectName.equals("Pole Star City")) {
            startActivity(new Intent(ProjectsActivity.this, PoleStarCity.class));
        } else if (projectName.equals("Samridhi Gullak")) {
            startActivity(new Intent(ProjectsActivity.this, SamridhiGullak.class));
        } else if (projectName.equals("Lode Star")) {
            startActivity(new Intent(ProjectsActivity.this, LodeStar.class));
        }
    }

}

