package com.mycvapps.rav.vk1000;
/**
 * Базовый класс для вызова всех Фрагментов, обработчиков
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class BaseAbstractFragment extends Fragment
{
    protected int LOADED_LAYOUT = R.layout.fragment_wall;
    protected int TARGET_USER;
    protected View view;
    protected User curUser;
    protected String FRAGMENT_TAG = "response_view";
    private int OFFSET = 0;
    private final int COUNT = 10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(LOADED_LAYOUT, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        setCurrentUser();
        getFragmentViews(view);
        setScrollListener();

    }



    protected void setScrollListener(){
    }

    public abstract void getFragmentViews(View view);

    public void setLOADED_LAYOUT(int LOADED_LAYOUT) {
        this.LOADED_LAYOUT = LOADED_LAYOUT;
    }

    public int getOffset() {
        return OFFSET;
    }

    public void setOffset(int OFFSET) {
        this.OFFSET = OFFSET;
    }

    public int getCount() {
        return COUNT;
    }

    public void setType(Fragments type) {
        setFRAGMENT_TAG(type.toString());
    }

    public VKRequest  getMyRequest() {
        return ((MainActivity)getActivity()).getMyRequest();
    }

    public void setMyRequest(VKRequest request){
        ((MainActivity)getActivity()).setMyRequest(request);
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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setSaveInstanceState(outState);
    }

    public void startApiCall(VKRequest request,Fragments type) {

        setMyRequest(request);//
        setType(type);
        ((MainActivity)getActivity()).loadFragment(type);
    }

    protected void setCurrentUser(){
        VKRequest reqUser = RequestManager.getUser();
        reqUser.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                curUser = User.getUser(response);
                TARGET_USER = curUser.getId();
                Log.d(FRAGMENT_TAG, "_________________TARGET_USER = " + TARGET_USER);
            }
        });

    }

    protected abstract void setSaveInstanceState(Bundle outState);


    protected abstract void getSaveInstanceState(Bundle savedInstanceState);


    public boolean onLast(){
return false;
    }



}