package myappplication.noida.quaere.groupalternate;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by intex on 9/8/2015.
 */
public class PayoutListCustomAdapter extends BaseAdapter {
    ArrayList<String> adlist;
    LayoutInflater inflaters;
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public PayoutListCustomAdapter(Activity parent, ArrayList<ModelClass> adlist) {

        activity = parent;
        data = adlist;
        inflators = (LayoutInflater) ((Activity) activity)
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        System.out.println("insiiiiiiidddddddeeeeeeeeeee placementDetailAdapter()......");
    }

    @Override
    public int getCount() {
        Log.v("size of dataArray is " + data.size(), "getcount()");
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
        public TextView payoutMember;
        public TextView netAmount;
        public TextView grossAmount;
        public TextView closingDate,payoutNo;//,payoutSrNoTv;

        public ViewHolder(View vi){
            payoutMember = (TextView) vi.findViewById(R.id.payout_memberTv);
            netAmount = (TextView) vi.findViewById(R.id.payout_netamoutTV);
            grossAmount = (TextView) vi.findViewById(R.id.payout_grossAmoutTv);
            closingDate = (TextView) vi.findViewById(R.id.payout_closingDateTv);
            payoutNo = (TextView) vi.findViewById(R.id.payout_noTV);
            // holder.payoutSrNoTv = (TextView) vi.findViewById(R.id.payout_SrNoTv);

        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            /********** Inflate tabitem.xml file for each row ( Defined below ) ************/
            vi = inflators.inflate(R.layout.payout_rowlist, null);
            System.out.println("insiiiiiiidddddddeeeeeeeeeee getViewwwwwww()......");
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
            holder.payoutMember.setText(tempValues.getPayoutMember());
            holder.netAmount.setText(tempValues.getNetAmount());
            holder.grossAmount.setText(tempValues.getGrossAmount());
            holder.closingDate.setText(tempValues.getClosingDate());
            holder.payoutNo.setText(tempValues.getPayoutNo());
          //  holder.payoutSrNoTv.setText(tempValues.getPayotlistNo());


        }
        return vi;
    }


}
