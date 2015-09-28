package com.mycvapps.rav.vk1000;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class PostFragment extends BaseAbstractFragment {
    private Post post;//лист статей
    private TextView author,att_title,text,date,att_text;
    private ImageView avatar,att_image;

    @Override
    public void getFragmentViews(View view) {
        this.avatar = (ImageView) view.findViewById(R.id.iv_avatar);
        this.text = (TextView) view.findViewById(R.id.tv_post_text);
        this.date = (TextView) view.findViewById(R.id.tv_post_date);
        this.author = (TextView)view.findViewById(R.id.tv_author);
        //this.linearLayout = (LinearLayout) view.findViewById(R.id.ll_att_container);

        this.att_text = (TextView) view.findViewById(R.id.tv_att_text);
        this.att_title = (TextView) view.findViewById(R.id.tv_att_title);
        this.att_image = (ImageView) view.findViewById(R.id.iv_att_photo);
        processRequestIfRequired();
    }

    @Override
    protected void setSaveInstanceState(Bundle outState) {

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
            post = Post.getPosts(response).get(0);


            ImageLoader imageLoader = ImageLoader.getInstance(); // Получили экземпляр
            imageLoader.init(ImageLoaderConfiguration.createDefault(getContext())); // Проинициализировали конфигом по умолчанию
            imageLoader.displayImage(post.getFrom_avatar(), avatar); // Запустили асинхронный показ картинки

            author.setText(post.getFrom_name());
            text.setText(post.getText());
            if(post.getAttachments() != null){
                Attachment attachment =post.getAttachments().get(0);
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
                    image = ((Photo) attachment).getPhoto_130();
                }

                imageLoader.displayImage(image, att_image);
                att_title.setText(title);
                att_text.setText(text);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                f.setTimeZone(TimeZone.getTimeZone("GMT"));
                date.setText(f.format(post.getDate()*1000));
            }

        }
    };
}
