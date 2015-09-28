package com.mycvapps.rav.vk1000;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class MyRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<Post> posts;
    private Context mContext;


    public MyRecyclerAdapter(Context context, List<Post> items) {
        this.posts = items;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item, null);

        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
       Post post = posts.get(i);
        List<Attachment> attachments = post.getAttachments();

        //Download image using picasso library
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));

        imageLoader.displayImage(post.getFrom_avatar(), customViewHolder.avatar); // Запустили асинхронный показ картинки
        customViewHolder.post_text.setText(post.getText());
        customViewHolder.author.setText(post.getFrom_name());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        f.setTimeZone(TimeZone.getTimeZone("GMT"));
        customViewHolder.post_date.setText(f.format(post.getDate()*1000));

        if(attachments != null){
            Attachment attachment =attachments.get(0);
            String title = "";
            String text = "";
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

            imageLoader.displayImage(image, customViewHolder.att_image);
            customViewHolder.att_title.setText(title);
            customViewHolder.att_text.setText(text);

        }

    }

    @Override
    public int getItemCount() {
        return (posts != null ? posts.size() : 0);
    }
}
