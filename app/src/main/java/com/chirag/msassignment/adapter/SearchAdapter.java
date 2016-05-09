package com.chirag.msassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.chirag.msassignment.R;
import com.chirag.msassignment.model.Search;
import com.chirag.msassignment.util.ImageViewTarget;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by chirag on 08/05/16.
 *
 * Custom BaseAdapter to display search images response which will get from json.
 */
public class SearchAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Search> searchList;
    private LayoutInflater inflater;
    private int lastPosition = -1;
    public SearchAdapter(Context context,ArrayList<Search> searchArrayList) {
        this.searchList = searchArrayList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<Search> searchArrayList){
        this.searchList = searchArrayList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(searchList != null) {
            return searchList.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(searchList != null && !searchList.isEmpty()) {
            return searchList.get(position);
        }else{
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Search searchItem = searchList.get(position);
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.search_list_item,parent,false);
            viewHolder.titleTv = (TextView) convertView.findViewById(R.id.image_name_tv);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.image_iv);
            viewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.titleTv.setText(searchItem.getTitle());
        viewHolder.mImageView.setMinimumHeight(searchItem.getThumbnail().getHeight());

        //ImageViewTarget is the implementation of Target interface.
        Target target = new ImageViewTarget(viewHolder.mImageView, viewHolder.mProgressBar);
        viewHolder.mImageView.setTag(target);

        Picasso.with(context)
                .load(searchItem.getThumbnail().getSource())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.image_error)
                .into(target);

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = position;

        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView titleTv;
        ImageView mImageView;
        ProgressBar mProgressBar;
    }
}
