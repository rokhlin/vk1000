package com.mycvapps.rav.vk1000;
/**
 * recyclerView адаптер
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class MyRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private static String TAG = "MyRecyclerAdapter";
    private List<Post> posts;
    private Context mContext;
    private MyClickListenner myClickListenner;

    public MyRecyclerAdapter(Context context, List<Post> items) {
        this.posts = items;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item, null);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myClickListenner != null)
                    myClickListenner.onClick((Integer)v.getTag());
            }
        });

        return new CustomViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        customViewHolder.author.getRootView().setTag(i);
       Post post = posts.get(i);
        List<Attachment> attachments = post.getAttachments();
        setElementVisibility(false, customViewHolder.att_image,
                customViewHolder.att_text,
                customViewHolder.att_title,
                customViewHolder.author,
                customViewHolder.avatar,
                customViewHolder.post_date,
                customViewHolder.post_text);
        //Download image using picasso library

        if(post.getFrom_avatar()!=null){
            PicassoCache.getPicassoInstance(mContext).load(post.getFrom_avatar()).into(customViewHolder.avatar);
            customViewHolder.avatar.setVisibility(View.VISIBLE);
        }
        if(post.getText()!=null){
            customViewHolder.post_text.setText(post.getText());
            customViewHolder.post_text.setVisibility(View.VISIBLE);
        }
        if(post.getFrom_name()!=null){
            customViewHolder.author.setText(post.getFrom_name());
            customViewHolder.author.setVisibility(View.VISIBLE);
        }

        if(post.getDate()>0){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            f.setTimeZone(TimeZone.getTimeZone("GMT"));
            customViewHolder.post_date.setText(f.format(post.getDate() * 1000));
            customViewHolder.post_date.setVisibility(View.VISIBLE);
        }




        if(attachments != null){
            Attachment attachment =attachments.get(0);
            String title = null;
            String text = null;
            String image = null;

            if(attachment instanceof Video){
                title = ((Video) attachment).getTitle();
                text = ((Video) attachment).getDescription();
                image = ((Video) attachment).getPhoto_320();
            }
            else if(attachment instanceof Photo){
                text = ((Photo) attachment).getText();
                image = ((Photo) attachment).getPhoto_604();
            }

            //imageLoader.displayImage(image, customViewHolder.att_image);

            PicassoCache.getPicassoInstance(mContext).load(image).into(customViewHolder.att_image);


            if(image!=null){
                PicassoCache.getPicassoInstance(mContext).load(image).into(customViewHolder.att_image);
                customViewHolder.att_image.setVisibility(View.VISIBLE);
            }
            if(text!=null){
                customViewHolder.att_title.setText(title);
                customViewHolder.att_title.setVisibility(View.VISIBLE);
            }

            if(title!=null){
                customViewHolder.att_text.setText(text);
                customViewHolder.att_text.setVisibility(View.VISIBLE);
            }

        }

    }

    private void setElementVisibility(boolean state, View... views){
        int visibility;

        if(state) visibility = View.VISIBLE;
        else visibility = View.GONE;

        for (View v: views) {
            v.setVisibility(visibility);
        }

    }

    @Override
    public int getItemCount() {
        return (posts != null ? posts.size() : 0);
    }




    public void setMyClickListenner(MyClickListenner value)
    {
        myClickListenner = value;
    }

    public interface MyClickListenner
    {
        void onClick(int index);
    }

    public void add(List<Post> posts1){
        posts.addAll(posts1);
        notifyItemInserted(posts.size() - 1);
        Log.d(TAG, "________________________adapter__add(List<Post> newPosts) =" + posts.size());
    }

    public void refresh(List<Post> posts1){
        posts = posts1;
        Log.d(TAG, "________________________adapter__refresh(List<Post> posts) =" + posts.size());
        notifyDataSetChanged();

    }

}
