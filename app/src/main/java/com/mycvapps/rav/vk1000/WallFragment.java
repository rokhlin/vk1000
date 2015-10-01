package com.mycvapps.rav.vk1000;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.List;

public class WallFragment extends BaseAbstractFragment  {
    private static String TAG = Fragments.WallFragment.toString();
    private List<Post> posts;//лист статей
    private List<Post> newPosts;//обновленный лист статей
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private EndlessRecyclerOnScrollListener mOnScrollListener;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            getActivity().finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void getFragmentViews(View view) {
        // Initialize recycler view
        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mOnScrollListener= new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                onLast();
            }
        };

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


        setmLayoutManager(mLayoutManager);///&&&&&&&&&&&&
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, TARGET_USER,
                VKApiConst.OFFSET, getOffset(),
                VKApiConst.COUNT, getCount(),
                "filter", "all",
                VKApiConst.EXTENDED, 1,
                VKApiConst.VERSION, 5.37
        ));

        if(TARGET_USER == 0){
            request = VKApi.wall().get(VKParameters.from(//VKApiConst.OWNER_ID, "10479140", //заменить String.valueOf(((MainActivity) getActivity()).getCurUser().getId())
                    VKApiConst.OFFSET, getOffset(),
                    VKApiConst.COUNT, getCount(),
                    "filter", "all",
                    VKApiConst.EXTENDED,1,
                    VKApiConst.VERSION, 5.37
            ));
        }
        processRequestIfRequired(request);

    }



    private boolean processRequestIfRequired(VKRequest request) {
        VKRequest oldRequest = getMyRequest();

        if(oldRequest == null){
            if (request == null) return false;
            setMyRequest(request);
            request.executeWithListener(wallRequestListener);
        }
        else if(!oldRequest.equals(request)){
            if (request == null) return false;
            setMyRequest(request);
            request.executeWithListener(wallRequestListener);
        }

        updateOffset();
        return true;
    }





    VKRequest.VKRequestListener wallRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            //парсинг json ответа
            posts = Post.getPosts(response);
            mAdapter = new MyRecyclerAdapter(getContext(), posts);
            mAdapter.setMyClickListenner(new MyRecyclerAdapter.MyClickListenner() {
                @Override
                public void onClick(int index) {
                    Log.d(TAG, "__________________________onClick(int index) =" + index);
                    Log.i(TAG, "__________________________CustomViewHolder onCreateViewHolder   posts.get(current).getId()=" + posts.get(index).getId());
                    VKRequest request;
                    if (TARGET_USER == 0) {
                        TARGET_USER = 10479140;// ((MainActivity) getActivity()).getCurUser().getId();

                    }
                    Log.i(TAG, "__________________________ID =====" + TARGET_USER + "_" + posts.get(index).getId());
                    request = VKApi.wall().getById(VKParameters.from(VKApiConst.POSTS, TARGET_USER + "_" + posts.get(index).getId(),
                            VKApiConst.EXTENDED, 1,
                            VKApiConst.VERSION, 5.37));

                    startApiCall(request, Fragments.PostFragment);
                }
            });
            mRecyclerView.setAdapter(mAdapter);
            mProgressBar.setVisibility(View.GONE);
        }
    };




    private void updateOffset() {
        setOffset(getCount() + getOffset());
        Log.i(TAG, "__________________________ OFFSET =" + getOffset());
    }


    public VKRequest setRequest(int offset, int count){

        VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, TARGET_USER,
                VKApiConst.OFFSET, offset,
                VKApiConst.COUNT, count,
                "filter", "all",
                VKApiConst.EXTENDED, 1,
                VKApiConst.VERSION, 5.37
        ));

        if(TARGET_USER == 0){
            request = VKApi.wall().get(VKParameters.from(//VKApiConst.OWNER_ID, "10479140", //заменить String.valueOf(((MainActivity) getActivity()).getCurUser().getId())
                    VKApiConst.OFFSET, offset,
                    VKApiConst.COUNT, count,
                    "filter", "all",
                    VKApiConst.EXTENDED,1,
                    VKApiConst.VERSION, 5.37
            ));
        }

        Log.d(TAG, "__________________________request  =" + request.toString());

        setMyRequest(request);
        return request;
    }


    @Override
    public boolean onLast(){


        VKRequest request = setRequest(getOffset(), getCount());
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                //парсинг json ответа
                newPosts = Post.getPosts(response);

                Log.i(TAG, "__________________________ Last onComplete  newPosts.size()) =" + newPosts.size());
                mAdapter.add(newPosts);

                updateOffset();


                mProgressBar.setVisibility(View.GONE);


            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d(TAG, "__________________________ request.executeWithListener  onError(VKError error)");

            }
        });
        return true;
    }


    public void refresh(){
        setOffset(0);
        VKRequest request = setRequest(getOffset(), getCount());
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                //парсинг json ответа
                newPosts = Post.getPosts(response);
                posts = newPosts;
                mAdapter.refresh(posts);

                Log.i(TAG, "__________________________ refresh  posts.size()) =" + posts.size());
                mSwipeRefreshLayout.setRefreshing(false);
                updateOffset();
                mOnScrollListener.reset(0,true);
            }
        });

    }

    @Override
    protected void setSaveInstanceState(Bundle outState) {

    }

    @Override
    protected void getSaveInstanceState(Bundle savedInstanceState) {

    }



    @Override
    protected void setScrollListener() {
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }
}
