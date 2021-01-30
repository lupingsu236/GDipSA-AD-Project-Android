package com.example.mrt4you_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class starAdapter extends ArrayAdapter<starinfo> {
    private int resourceId;
    private List<starinfo> list=new ArrayList<>();
    private Context context;

    public starAdapter(Context context, int textviewResourceId, List<starinfo> objects){
        super(context,textviewResourceId,objects);
        resourceId=textviewResourceId;
        this.list=objects;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        starinfo s=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView lt1=view.findViewById(R.id.lt1);
        TextView lt2=view.findViewById(R.id.lt2);
        TextView rt=view.findViewById(R.id.rt);
        ImageView i=view.findViewById(R.id.deleteicon);
        lt1.setText(s.getName());
        lt2.setText(s.getStation());
        rt.setText(s.getLine());
        i.setImageResource(R.drawable.deleteicon);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
