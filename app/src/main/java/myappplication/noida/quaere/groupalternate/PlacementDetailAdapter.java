package myappplication.noida.quaere.groupalternate;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by intex on 9/8/2015.
 */
public class PlacementDetailAdapter extends BaseAdapter {
    ArrayList<String> adlist;
    LayoutInflater inflaters;
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public PlacementDetailAdapter(Activity parent, ArrayList<ModelClass> adlist) {

        activity = parent;
        data = adlist;
        inflators = (LayoutInflater) ((Activity) activity)
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
     //   Log.v("size of dataArray is " + data.size(), "getcount()");
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView memberNameTv;
        public TextView joiningDateTv;
        public TextView loginIdTv;
        public TextView mobileNoTv;//,recordNoTv;

        public ViewHolder(View vi){
            memberNameTv = (TextView) vi.findViewById(R.id.pd_memberNameTv);
            joiningDateTv = (TextView) vi.findViewById(R.id.pd_joiningDateTv);
            loginIdTv = (TextView) vi.findViewById(R.id.pd_loginIdTv);
            mobileNoTv = (TextView) vi.findViewById(R.id.pd_mobileNoTv);
            //   holder.recordNoTv = (TextView) vi.findViewById(R.id.pd_recordNoTv);

        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
            vi = inflators.inflate(R.layout.placement_detail_rowlist, null);

            /******** View Holder Object to contain tabitem.xml file elements ************/
            holder = new ViewHolder(vi);

            /************ Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
           /* holder.memberNameTv.setText("No Data");
            holder.joiningDateTv.setText("No Data");
            holder.loginIdTv.setText("No Data");
            holder.mobileNoTv.setText("No Data");
*/
        } else {
            tempValues = null;
            tempValues = (ModelClass) data.get(position);

            /************ Set Model values in Holder elements ***********/
            // System.out.println("insiiidddddddeeeeee setting value to the adapter......");

            // holder.text.setText(tempValues.getType());
            holder.memberNameTv.setText(tempValues.getMemberName());
            holder.mobileNoTv.setText(tempValues.getMobileNo());
            holder.loginIdTv.setText(tempValues.getLoginId());
            holder.joiningDateTv.setText(tempValues.getJoiningDate());
          //  holder.recordNoTv.setText(tempValues.getPdrecordno());


        }
        return vi;
    }


}
