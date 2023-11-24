package tn.esprit.pdm.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HospitalBaseAdapter extends BaseAdapter {
    Context context;
    String ListHospital[];
    int ListImages[];
    LayoutInflater inflater;
    public HospitalBaseAdapter(Context ctx, String[] HospitalsList, int[] images){
        this.context = ctx;
        this.ListHospital = ListHospital;
        this.ListImages = images;
       // inflater = layoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return ListHospital.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       // convertView = inflater.inflate(R.layout.activity.activity_custom_list_view, root null)
        return null;
    }
}
