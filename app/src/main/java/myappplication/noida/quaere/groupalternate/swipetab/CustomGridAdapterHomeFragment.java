package myappplication.noida.quaere.groupalternate.swipetab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import myappplication.noida.quaere.groupalternate.R;


public class CustomGridAdapterHomeFragment extends BaseAdapter{
	  private Context mContext;
	  private final String[] web;
	  private final int[] Imageid;

	    public CustomGridAdapterHomeFragment(Context c, String[] web, int[] Imageid) {
	        mContext = c;
	        this.Imageid = Imageid;
	        this.web = web;
	    }

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return web.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View grid;
			LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
	        if (convertView == null) {  
	        	
	        	grid = new View(mContext);
				grid = inflater.inflate(R.layout.gridview_image_text_home_adapter, null);
	        	TextView textView = (TextView) grid.findViewById(R.id.grid_text);

			/*Typeface type = Typeface.createFromAsset(mContext.getAssets(), "exo_semi_bold.otf");
				textView.setTypeface(type);*/

	         ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);


	        	textView.setText(web[position]);


	        	imageView.setImageResource(Imageid[position]);
	        } else {
	        	grid = (View) convertView;
	        }
			
			return grid;
		}
}
