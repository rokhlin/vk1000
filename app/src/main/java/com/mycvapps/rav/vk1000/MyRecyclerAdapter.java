package com.mycvapps.rav.vk1000;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class MyRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private static String TAG = "MyRecyclerAdapter";
    private List<Post> posts;
    private Context mContext;
    private int count = 0;
    private MyClickListenner myClickListenner;
    public MyRecyclerAdapter(Context context, List<Post> items) {
        this.posts = items;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_item, null);

        view.setTag(count++);
        Log.d(TAG, "__________________________CustomViewHolder onCreateViewHolder   current=" + (count - 1));
        Log.d(TAG, "__________________________CustomViewHolder onCreateViewHolder   posts.get(current).getId()=" + posts.get(count-1).getId());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myClickListenner != null)
                    myClickListenner.onClick((Integer)v.getTag());
            }
        });
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int current = count;
//                        count ++;
//
//                        Log.d(TAG, "__________________________CustomViewHolder onCreateViewHolder   current=" + current);
//                        Log.d(TAG, "__________________________CustomViewHolder onCreateViewHolder   posts.get(current).getId()=" + posts.get(current).getId());
//                        VKRequest request = VKApi.wall().get(VKParameters.from(
//                                VKApiConst.OFFSET, posts.get(current).getId(),
//                                VKApiConst.COUNT, 1,
//                                "filter", "all",
//                                VKApiConst.VERSION, 5.37
//                        ));
//                        BaseAbstractFragment baseAbstractFragment = new WallFragment() {
//                            @Override
//                            public void getFragmentViews(View view) {
//                            }
//
//                            @Override
//                            protected void setSaveInstanceState(Bundle outState) {
//                            }
//
//                            @Override
//                            protected void getSaveInstanceState(Bundle savedInstanceState) {
//                            }
//                        };
//                        baseAbstractFragment.startApiCall(request, Fragments.WallFragment);
//
//                    }
//                });
        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
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
        customViewHolder.post_date.setText(f.format(post.getDate() * 1000));

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

    public void setMyClickListenner(MyClickListenner value)
    {
        myClickListenner = value;
    }

    public interface MyClickListenner
    {
        void onClick(int index);
    }

}
