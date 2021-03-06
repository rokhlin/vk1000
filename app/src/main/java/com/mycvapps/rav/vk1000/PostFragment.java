package com.mycvapps.rav.vk1000;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class PostFragment extends BaseAbstractFragment {
    private TextView author,att_title,post_text,post_date,att_text,comments,likes;
    private ImageView avatar,att_image,comment,like;
    private Context mContext=getContext();


    void setScrListener() {
    }



    @Override
    public void getFragmentViews(View view) {

        this.avatar = (ImageView) view.findViewById(R.id.iv_avatar);
        this.post_text = (TextView) view.findViewById(R.id.tv_post_text);
        this.post_date = (TextView) view.findViewById(R.id.tv_postDate);
        this.author = (TextView)view.findViewById(R.id.tv_author);

        this.att_text = (TextView) view.findViewById(R.id.tv_att_text);
        this.att_title = (TextView) view.findViewById(R.id.tv_att_title);
        this.att_image = (ImageView) view.findViewById(R.id.iv_att_photo);
        this.comments = (TextView) view.findViewById(R.id.tv_post_comments);
        this.likes = (TextView) view.findViewById(R.id.tv_post_likes);
        this.comment = (ImageView) view.findViewById(R.id.iv_post_comment);
        this.like = (ImageView) view.findViewById(R.id.iv_post_like);
        like.setTag("unchecked");
        this.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getTag().toString()){
                    case "unchecked":
                        like.setImageResource(R.drawable.heart_red);
                        like.setTag("checked");
                        likes.setText((Integer.parseInt(likes.getText().toString()) + 1) + "");
                        break;
                    case "checked":
                        like.setImageResource(R.drawable.heart_grey);
                        like.setTag("unchecked");
                        likes.setText((Integer.parseInt(likes.getText().toString())-1)+"");
                        break;
                }
            }
        });


        processRequestIfRequired();
    }

    @Override
    protected void setSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void getSaveInstanceState(Bundle savedInstanceState) {

    }

    private boolean processRequestIfRequired() {
        VKRequest request = getMyRequest();
        if (request == null) return false;
        request.executeWithListener(postRequestListener);
        return true;
    }

    VKRequest.VKRequestListener postRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            //парсинг json ответа
            Post post = Post.getPosts(response).get(0);
            List<Attachment> attachments = post.getAttachments();
            setElementVisibility(false, att_image, att_text, att_title,
                                        author, avatar, post_date, post_text,comments,likes);
            //Download image using picasso library

            if(post.getFrom_avatar()!=null){
                PicassoCache.getPicassoInstance(mContext).load(post.getFrom_avatar()).into(avatar);
                avatar.setVisibility(View.VISIBLE);
            }
            if(post.getText()!=null){
                post_text.setText(post.getText());
                post_text.setVisibility(View.VISIBLE);
            }
            if(post.getFrom_name()!=null){
                author.setText(post.getFrom_name());
                author.setVisibility(View.VISIBLE);
            }

            if(post.getDate()>0){
                @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                f.setTimeZone(TimeZone.getTimeZone("GMT"));
                post_date.setText(f.format(post.getDate() * 1000));
                post_date.setVisibility(View.VISIBLE);
            }
            Log.d("TAG","_____________________post.getLikes() = "+post.getLikes() );
            Log.d("TAG","_____________________post.getUser_likes() = "+post.getUser_likes() );
            Log.d("TAG","_____________________post.getComments() = "+post.getComments() );

            if(post.getComments()> 0){
                comments.setText(post.getComments()+"");
                comments.setVisibility(View.VISIBLE);
            }

            if(post.getLikes()>0){
                likes.setText(post.getLikes()+"");
                likes.setVisibility(View.VISIBLE);
            }

            if(post.getUser_likes()>0){
                like.setTag("checked");
                like.setImageResource(R.drawable.heart_red);
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


                PicassoCache.getPicassoInstance(mContext).load(image).into(att_image);


                if(image!=null){
                    PicassoCache.getPicassoInstance(mContext).load(image).into(att_image);
                    att_image.setVisibility(View.VISIBLE);
                }

                if(text!=null){
                    att_title.setText(title);
                    att_title.setVisibility(View.VISIBLE);
                }

                if(title!=null){
                    att_text.setText(text);
                    att_text.setVisibility(View.VISIBLE);
                }
            }

        }
    };

    private void setElementVisibility(boolean state, View... views){
        int visibility;

        if(state) visibility = View.VISIBLE;
        else visibility = View.GONE;

        for (View v: views) {
            v.setVisibility(visibility);
        }

    }
}
