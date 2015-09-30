package com.mycvapps.rav.vk1000;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKWallPostResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class BaseAbstractFragment extends Fragment
{
    protected int LOADED_LAYOUT = R.layout.fragment_wall;
    protected int TARGET_GROUP = 60479154;
    protected int TARGET_ALBUM = 181808365;
    protected int TARGET_USER = 10479140;
    protected View view;
    protected String FRAGMENT_TAG = "response_view";
    private Fragments type;
    private int OFFSET = 0;
    private final int COUNT = 10;
    private RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    public void setmLayoutManager(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public void setType(Fragments type) {
        this.type = type;
        setFRAGMENT_TAG(type.toString());
    }

    public VKRequest  getMyRequest() {
         return ((MainActivity)getActivity()).getMyRequest();
    }

    public void setMyRequest(VKRequest request){
        ((MainActivity)getActivity()).setMyRequest(request);
    }

    public String getFRAGMENT_TAG() {
        return FRAGMENT_TAG;
    }

    public void setFRAGMENT_TAG(String FRAGMENT_TAG) {
        this.FRAGMENT_TAG = FRAGMENT_TAG;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        return inflater.inflate(LOADED_LAYOUT, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setView(view);
        getFragmentViews(view);
        setScrollListener();

    }

    protected void setScrollListener(){

    }

    public abstract void getFragmentViews(View view);

    public int getLOADED_LAYOUT() {
        return LOADED_LAYOUT;
    }

    public void setLOADED_LAYOUT(int LOADED_LAYOUT) {
        this.LOADED_LAYOUT = LOADED_LAYOUT;
    }

    public int getTARGET_GROUP() {
        return TARGET_GROUP;
    }

    public void setTARGET_GROUP(int TARGET_GROUP) {
        this.TARGET_GROUP = TARGET_GROUP;
    }

    public int getOFFSET() {
        return OFFSET;
    }

    public void setOFFSET(int OFFSET) {
        this.OFFSET = OFFSET;
    }

    public int getCOUNT() {
        return COUNT;
    }
    public int getTARGET_ALBUM() {
        return TARGET_ALBUM;
    }



    public void setTARGET_ALBUM(int TARGET_ALBUM) {
        this.TARGET_ALBUM = TARGET_ALBUM;
    }

    protected void showError(VKError error) {
        new AlertDialog.Builder(getActivity())
                .setMessage(error.toString())
                .setPositiveButton("OK", null)
                .show();

        if (error.httpError != null) {
            Log.w("Test", "Error in request or upload", error.httpError);
        }
    }

    protected Bitmap getPhoto() {
        Bitmap b = null;

        try {
            b = BitmapFactory.decodeStream(getActivity().getAssets().open("android.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    protected File getFile() {
        try {
            InputStream inputStream = getActivity().getAssets().open("android.jpg");
            File file = new File(getActivity().getCacheDir(), "android.jpg");
            OutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024]; // or other buffer size
            int read;

            while ((read = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            output.flush();
            output.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void makePost(VKAttachments attachments) {
        makePost(attachments, null);
    }

    protected void makePost(VKAttachments attachments, String message) {
        VKRequest post = VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID, "-" + TARGET_GROUP, VKApiConst.ATTACHMENTS, attachments, VKApiConst.MESSAGE, message));
        post.setModelClass(VKWallPostResult.class);
        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKWallPostResult result = (VKWallPostResult) response.parsedModel;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://vk.com/wall-%d_%s", TARGET_GROUP, result.post_id)));
                startActivity(i);
            }

            @Override
            public void onError(VKError error) {
                showError(error.apiError != null ? error.apiError : error);
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setSaveInstanceState(outState);
    }
    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSaveInstanceState(savedInstanceState);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                Log.v(FRAGMENT_TAG, "___________________________________onScrolled !");
//                visibleItemCount = mLayoutManager.getChildCount();
//                totalItemCount = mLayoutManager.getItemCount();
//                pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
//                Log.v(FRAGMENT_TAG, "___________________________________visibleItemCount ="+visibleItemCount);
//                Log.v(FRAGMENT_TAG, "___________________________________totalItemCount ="+totalItemCount);
//                Log.v(FRAGMENT_TAG, "___________________________________pastVisibleItems ="+pastVisibleItems);
//                if (loading) {
//                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
//                        loading = false;
//                        Log.v(FRAGMENT_TAG, "___________________________________Last Item Wow !");
//                        onLast();
//                        Log.v(FRAGMENT_TAG, "___________________________________Last Item END !");
//                    }
//                    else {
//                        loading = true;
//                    }
//                }
//
//
//
//            }
//        });
    }




    protected BaseAbstractFragment fragmentCreator(Fragments type){
        BaseAbstractFragment fragment = null;

        switch (type){
            case SelectorFragment:
                fragment = new SelectorFragment();
                break;
            case LoadUserFragment:
                fragment = new LoadUserFragment();
            case WallFragment:
                fragment = new WallFragment();
        }
        return fragment;
    }

    public void startApiCall(VKRequest request,Fragments type) {

        setMyRequest(request);//
        setType(type);
        ((MainActivity)getActivity()).loadFragment(type);
    }

    protected abstract void setSaveInstanceState(Bundle outState);


    protected abstract void getSaveInstanceState(Bundle savedInstanceState);


    public boolean onLast(){
return false;
    }
}