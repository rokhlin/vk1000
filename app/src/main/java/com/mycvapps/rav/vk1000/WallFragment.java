package com.mycvapps.rav.vk1000;

import android.os.AsyncTask;
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
    private List<Post> postsList;//лист статей
    private List<Post> newPosts;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayoutManager mLayoutManager;
    boolean atRes = false;
     AsyncTask at;
    private static String TAG = Fragments.WallFragment.toString();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            ((MainActivity)getActivity()).finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void getFragmentViews(View view) {
        // Initialize recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setOFFSET(0);
                refreshItems();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        mLayoutManager = new LinearLayoutManager(getContext());
        setmLayoutManager(mLayoutManager);

        mRecyclerView.setLayoutManager(mLayoutManager);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, TARGET_USER,
                VKApiConst.OFFSET, getOFFSET(),
                VKApiConst.COUNT, getCOUNT(),
                "filter", "all",
                VKApiConst.EXTENDED, 1,
                VKApiConst.VERSION, 5.37
        ));

        if(TARGET_USER == 0){
            request = VKApi.wall().get(VKParameters.from(//VKApiConst.OWNER_ID, "10479140", //заменить String.valueOf(((MainActivity) getActivity()).getCurUser().getId())
                    VKApiConst.OFFSET, getOFFSET(),
                    VKApiConst.COUNT, getCOUNT(),
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
            postsList = Post.getPosts(response);
            adapter = new MyRecyclerAdapter(getContext(), postsList);
            adapter.setMyClickListenner(new MyRecyclerAdapter.MyClickListenner() {
                @Override
                public void onClick(int index) {
                    Log.d(TAG, "__________________________onClick(int index) =" + index);
                    Log.d(TAG, "__________________________CustomViewHolder onCreateViewHolder   posts.get(current).getId()=" + postsList.get(index).getId());
                    VKRequest request;
                    if(TARGET_USER == 0) {
                        TARGET_USER = 10479140;// ((MainActivity) getActivity()).getCurUser().getId();

                    }
                    Log.d(TAG, "__________________________ID =====" + TARGET_USER+"_"+ postsList.get(index).getId());
                    request = VKApi.wall().getById(VKParameters.from(VKApiConst.POSTS, TARGET_USER+"_"+ postsList.get(index).getId(),
                            VKApiConst.EXTENDED,1,
                            VKApiConst.VERSION, 5.37));

                    startApiCall(request,Fragments.PostFragment);
                }
            });
            mRecyclerView.setAdapter(adapter);
            setmRecyclerView(mRecyclerView);
            progressBar.setVisibility(View.GONE);
        }
    };






    private class WallLoader extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "__________________________WallLoader onPreExecute Success" );
        }

        @Override
        protected Void doInBackground(Void... params) {
            int check = 0;
            atRes = false;
            while (!atRes) {
                if(check == 0){
                    check = 1;
                    getPosts(getOFFSET(), getCOUNT());
                    Log.d(TAG, "___________________doInBackground_______parsing offset=" + getOFFSET() + " count=" + getCOUNT() + " Success");
                    Log.d(TAG, "_______________________doInBackground postsList =" + postsList.size());
                }

                if (atRes){
                    Log.d(TAG, "__________________________atRes =====" + atRes);
                    break;

                }



            }
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.refresh(postsList);
            //adapter = new MyRecyclerAdapter(getContext(), postsList);
            Log.d(TAG, "__________!!!!!!!!!!!!!!!!!!!!!!!!!!!________________onPostExecute postsList.size() =" + postsList.size());

//            updateOffset();
//
//            progressBar.setVisibility(View.GONE);

        }


    }

    private void updateOffset() {
        setOFFSET(getCOUNT() + getOFFSET());
        Log.d(TAG, "__________________________ OFFSET =" + getOFFSET());
    }

//    public boolean getPosts(int offset, int count){
//
//    VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, TARGET_USER,
//            VKApiConst.OFFSET, getOFFSET(),
//            VKApiConst.COUNT, getCOUNT(),
//            "filter", "all",
//            VKApiConst.EXTENDED, 1,
//            VKApiConst.VERSION, 5.37
//    ));
//
//    if(TARGET_USER == 0){
//        request = VKApi.wall().get(VKParameters.from(//VKApiConst.OWNER_ID, "10479140", //заменить String.valueOf(((MainActivity) getActivity()).getCurUser().getId())
//                VKApiConst.OFFSET, getOFFSET(),
//                VKApiConst.COUNT, getCOUNT(),
//                "filter", "all",
//                VKApiConst.EXTENDED,1,
//                VKApiConst.VERSION, 5.37
//        ));
//    }
//
//    Log.d(TAG, "__________________________request  =" + request.toString());
//
//        setMyRequest(request);
//
//
//
//
//        request.executeWithListener(new VKRequest.VKRequestListener() {
//            @Override
//            public void onComplete(VKResponse response) {
//                super.onComplete(response);
//                //парсинг json ответа
//                newPosts = Post.getPosts(response);
//                postsList.addAll(newPosts);
//                Log.d(TAG, "__________________________ request.executeWithListener  postsList.size()) =" + postsList.size());
//                updateOffset();
//                progressBar.setVisibility(View.GONE);
//                at.cancel(true);
//                atRes = true;
//                adapter.notifyItemInserted(postsList.size() - 1);
//
//
//            }
//
//            @Override
//            public void onError(VKError error) {
//                super.onError(error);
//                Log.d(TAG, "__________________________ request.executeWithListener  onError(VKError error)  =" + error.toString());
//                error.toString();
//            }
//        });
//
//
//
//    return true;
//}

    public VKRequest getPosts(int offset, int count){

        VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, TARGET_USER,
                VKApiConst.OFFSET, getOFFSET(),
                VKApiConst.COUNT, getCOUNT(),
                "filter", "all",
                VKApiConst.EXTENDED, 1,
                VKApiConst.VERSION, 5.37
        ));

        if(TARGET_USER == 0){
            request = VKApi.wall().get(VKParameters.from(//VKApiConst.OWNER_ID, "10479140", //заменить String.valueOf(((MainActivity) getActivity()).getCurUser().getId())
                    VKApiConst.OFFSET, getOFFSET(),
                    VKApiConst.COUNT, getCOUNT(),
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


        VKRequest request = getPosts(getOFFSET(), getCOUNT());
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                //парсинг json ответа
                newPosts = Post.getPosts(response);

                Log.d(TAG, "__________________________ Last onComplete  newPosts.size()) =" + newPosts.size());
                adapter.add(newPosts);

                updateOffset();


                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d(TAG, "__________________________ request.executeWithListener  onError(VKError error)");

            }
        });
        return true;
    }


    public boolean refreshItems(){
        Log.d(TAG, "__________________________ refreshItems");
        VKRequest request = getPosts(getOFFSET(),getCOUNT());
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                //парсинг json ответа
                newPosts = Post.getPosts(response);

                postsList = newPosts;
                adapter.refresh(newPosts);

                Log.d(TAG, "__________________________ refreshItems  postsList.size()) =" + postsList.size());
                progressBar.setVisibility(View.GONE);


            }

        });
        Log.d(TAG, "__________________________ onLast finished");
        return true;
    }

    @Override
    protected void setSaveInstanceState(Bundle outState) {

    }

    @Override
    protected void getSaveInstanceState(Bundle savedInstanceState) {

    }



    @Override
    protected void setScrollListener() {
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                onLast();
            }
        });
    }
}
