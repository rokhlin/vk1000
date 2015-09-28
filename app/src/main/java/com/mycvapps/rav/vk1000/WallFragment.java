package com.mycvapps.rav.vk1000;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.HashSet;
import java.util.List;

public class WallFragment extends BaseAbstractFragment  {
    public TextView textView;
    private List<Post> postsList;//лист статей
    private HashSet<Integer> ids;
    private String idsstr;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private int OFFSET = 1;
    private static final int COUNT = 10;
    private static String TAG = Fragments.WallFragment.toString();

    @Override
    protected void setSaveInstanceState(Bundle outState) {

    }

    @Override
    protected void getSaveInstanceState(Bundle savedInstanceState) {

    }

    @Override
     public void getFragmentViews(View view) {
        // Initialize recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);


                VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, "10479140", //заменить String.valueOf(((MainActivity) getActivity()).getCurUser().getId())
                        VKApiConst.OFFSET, OFFSET,
                        VKApiConst.COUNT, COUNT,
                        "filter", "all",
                        VKApiConst.EXTENDED,1,
                        VKApiConst.VERSION, 5.37
                ));

                Log.d(TAG, "__________________________request  =" + request.toString());

                processRequestIfRequired(request);





    }


    VKRequest.VKRequestListener wallRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            //парсинг json ответа
            postsList = Post.getPosts(response);
            adapter = new MyRecyclerAdapter(getContext(), postsList);
            mRecyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
    };


    private boolean processRequestIfRequired(VKRequest request) {
        VKRequest oldRequest = getMyRequest();
        if(!oldRequest.equals(request)){
            if (request == null) return false;
            setMyRequest(request);
            request.executeWithListener(wallRequestListener);
        }
        return true;
    }
}
