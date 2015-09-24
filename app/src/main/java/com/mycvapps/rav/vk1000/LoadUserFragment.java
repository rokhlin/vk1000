package com.mycvapps.rav.vk1000;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

public class LoadUserFragment extends BaseAbstractFragment {
    private static String TAG = "LoadUserFragment";

    public TextView textView;

    @Override
    public void getFragmentViews(View view) {
        try {
            textView = (TextView) view.findViewById(R.id.response);
            processRequestIfRequired();
        }
        catch (Exception e){
            Log.e(TAG, "__________________________________________________________"+e.getMessage());
        }
    }

    private void processRequestIfRequired() {
        VKRequest request = getMyRequest();
//            if (request != null)
//                request.unregisterObject();
            if (request == null) return;
        request.executeWithListener(mRequestListener);
    }


    VKRequest.VKRequestListener mRequestListener = new VKRequest.VKRequestListener()
    {
        @Override
        public void onComplete(VKResponse response)
        {
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
        //BaseAbstractFragment fragment = getFragment();
        if (textView != null) {
            textView.setText(text);
        }
    }

    @Override
    protected void setSaveInstanceData(Bundle outState) {
        outState.putString("StrData", textView.getText().toString());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void getSaveInstanceData(Bundle savedInstanceState) {
        // Check to see if we have a frame in which to embed the details
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            textView.setText(savedInstanceState.getString("StrData","null"));
        }
    }



}
