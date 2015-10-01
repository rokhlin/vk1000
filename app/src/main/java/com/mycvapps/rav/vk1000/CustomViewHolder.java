package com.mycvapps.rav.vk1000;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomViewHolder extends RecyclerView.ViewHolder  {

    protected ImageView avatar, att_image;
    protected TextView post_text, post_date, author, att_title, att_text;
    protected LinearLayout linearLayout;

    public CustomViewHolder(View view) {
        super(view);
        view.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.avatar = (ImageView) view.findViewById(R.id.iv_avatar);
        this.post_text = (TextView) view.findViewById(R.id.tv_post_text);
        this.post_date = (TextView) view.findViewById(R.id.tv_postDate);
        this.author = (TextView)view.findViewById(R.id.tv_author);
        //this.linearLayout = (LinearLayout) view.findViewById(R.id.ll_att_container);

        this.att_text = (TextView) view.findViewById(R.id.tv_att_text);
        this.att_title = (TextView) view.findViewById(R.id.tv_att_title);
        this.att_image = (ImageView) view.findViewById(R.id.iv_att_photo);
    }

}
