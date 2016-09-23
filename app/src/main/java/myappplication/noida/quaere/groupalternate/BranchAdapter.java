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
 * Created by intex on 11/19/2015.
 */
public class BranchAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public BranchAdapter(Activity context, int textViewResourceId,
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
/*

    public class ViewHolder{
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
            holder.brnchName.setText(tempValues.getBranchName());
        }
        return vi;
    }
}

   class DesignationAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public DesignationAdapter(Activity context, int textViewResourceId,
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
            holder.brnchName.setText(tempValues.getDesignationName());
        }
        return vi;
    }
}

class CountryAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;


    public CountryAdapter(Activity context, int textViewResourceId,
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
    }*/

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
            holder.brnchName.setText(tempValues.getCountryName());
        }
        return vi;
    }
}

class StateAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public StateAdapter(Activity context, int textViewResourceId,
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

   /* public class ViewHolder{
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
            holder.brnchName.setText(tempValues.getStateName());
        }
        return vi;
    }
}

class CityAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public CityAdapter(Activity context, int textViewResourceId,
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

   public class ViewHolder{
        public TextView brnchName;
        public ViewHolder(View vi) {
            brnchName = (TextView) vi.findViewById(R.id.branch_spinner_rowTv);
        }
    }

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
            holder.brnchName.setText(tempValues.getCityName());
        }
        return vi;
    }
}

/**************************** Gender Adapter ********************************************************/
class GenderAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public GenderAdapter(Activity context, int textViewResourceId,
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
    }*/

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
            holder.brnchName.setText(tempValues.getGenderName());
        }
        return vi;
    }
}

/**************************** JoiningLeg Adapter ********************************************************/
class JoiningLegAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ModelClass> data;
    private LayoutInflater inflators = null;
    ModelClass tempValues;

    public JoiningLegAdapter(Activity context, int textViewResourceId,
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
            holder.brnchName.setText(tempValues.getJoiningLegName());
        }
        return vi;
    }
}

 class ViewHolder{
    public TextView brnchName;
    public ViewHolder(View vi) {
        brnchName = (TextView) vi.findViewById(R.id.branch_spinner_rowTv);
    }
}
