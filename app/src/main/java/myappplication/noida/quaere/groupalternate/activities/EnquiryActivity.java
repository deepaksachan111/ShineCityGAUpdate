package myappplication.noida.quaere.groupalternate.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import myappplication.noida.quaere.groupalternate.R;

/**
 * Created by intex on 3/23/2016.
 */
public class EnquiryActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enquiry_activity);
        findViewById(R.id.enquiry_backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
