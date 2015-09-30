package com.mycvapps.rav.vk1000;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

public class LoadUserFragment extends BaseAbstractFragment {
    private static String TAG = "LoadUserFragment";
    private User curUser=null;
    public TextView textView, uid ,name, lastname;
    public ImageView avatar;


    void setScrListener() {

    }

    @Override
    public void getFragmentViews(View view) {
        try {
            textView = (TextView) view.findViewById(R.id.response);
            uid = (TextView) view.findViewById(R.id.tv_user_id);
            name = (TextView) view.findViewById(R.id.tv_user_name);
            lastname = (TextView) view.findViewById(R.id.tv_user_lastname);
            avatar = (ImageView)view.findViewById(R.id.iv_user_avatar);


            processRequestIfRequired();
        }
        catch (Exception e){
            Log.e(TAG, "__________________________________________________________"+e.getMessage());
        }
    }



    private void processRequestIfRequired() {
        VKRequest request = getMyRequest();
            if (request == null) return;
        request.executeWithListener(mRequestListener);
    }


    VKRequest.VKRequestListener mRequestListener = new VKRequest.VKRequestListener()
    {
        @Override
        public void onComplete(VKResponse response)
        {

            ((MainActivity) getActivity()).setCurUser(User.getUser(response));
            curUser = User.getUser(response);
            setResponseText(response.json.toString());//парсинг json ответа
        }

        @Override
        public void onError(VKError error)
        {
            setResponseText(error.toString());//парсинг json ответа если ошибка
        }

        @Override
        public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded,
                               long bytesTotal)
        {
            // you can show progress of the request if you want
        }

        @Override
        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts)
        {
            textView.append(
                    String.format("Attempt %d/%d failed\n", attemptNumber, totalAttempts));
        }
    };

    //установка текста в textview загруженного фрагмента
    protected void setResponseText(String text) {
        if (textView != null) {
            textView.setText(text);
        }
        curUser = ((MainActivity)getActivity()).getCurUser();
        uid.setText(String.valueOf(curUser.getId()));
        name.setText(curUser.getFirstName());
        lastname.setText(curUser.getLastName());

        PicassoCache.getPicassoInstance(getContext()).load(curUser.getPhoto50()).into(avatar);
    }



    @Override
    protected void setSaveInstanceState(Bundle outState) {
        outState.putString("StrData", textView.getText().toString());
    }


    @Override
    protected void getSaveInstanceState(Bundle savedInstanceState) {
        // Check to see if we have a frame in which to embed the details
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            textView.setText(savedInstanceState.getString("StrData"));
        }
    }



}
