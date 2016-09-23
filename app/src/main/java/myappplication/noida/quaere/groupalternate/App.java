package myappplication.noida.quaere.groupalternate;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by intex on 9/20/2016.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/droidserif_bolditalic.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}