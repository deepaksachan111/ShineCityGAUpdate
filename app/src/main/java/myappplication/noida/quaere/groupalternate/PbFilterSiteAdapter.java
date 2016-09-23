package myappplication.noida.quaere.groupalternate;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by intex on 11/29/2015.
 */
public class PbFilterSiteAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public PbFilterSiteAdapter(Activity context, int textViewResourceId,
                              ArrayList<ModelClass> values) {
        this.activity = context;
        this.data = values;
        inflators = (LayoutInflater) ((Activity) context)
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
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

    /*  public class ViewHolder{
          public TextView brnchName;
          public ViewHolder(View vi) {
              brnchName = (TextView) vi.findViewById(R.id.branch_spinner_rowTv);
          }
      }
  */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            vi = inflators.inflate(R.layout.branch_spinner_row, null);
            holder = new ViewHolder(vi);
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
        } else {
            tempValues = null;
            tempValues = (ModelClass) data.get(position);
            holder.brnchName.setText(tempValues.getSiteName());
        }
        return vi;
    }
}