package com.mycvapps.rav.vk1000;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKRequest;

public class MainActivity extends AppCompatActivity {


    private VKRequest.VKRequestListener mRequestListener;
    private VKRequest myRequest;
    private static String FRAGMENT_TAG = "response_view";

    public VKRequest getMyRequest() {
        return myRequest;
    }

    public void setMyRequest(VKRequest myRequest) {
        this.myRequest = myRequest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SelectorFragment(),FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRequest.cancel();
        Log.d(VKSdk.SDK_TAG, "On destroy");
    }

    public VKRequest.VKRequestListener getmRequestListener() {
        return mRequestListener;
    }

    public void setmRequestListener(VKRequest.VKRequestListener mRequestListener) {
        this.mRequestListener = mRequestListener;
    }

    private void processRequestIfRequired() {
        VKRequest request = null;

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("request")) {
            long requestId = getIntent().getExtras().getLong("request");
            request = VKRequest.getRegisteredRequest(requestId);
            if (request != null)
                request.unregisterObject();
        }

        if (request == null) return;
        myRequest = request;
        request.executeWithListener(mRequestListener);
    }


    protected BaseAbstractFragment fragmentCreator(Fragments type){
        BaseAbstractFragment fragment = null;

        switch (type){
            case SelectorFragment:
                fragment = new SelectorFragment();
                fragment.setLOADED_LAYOUT(R.layout.fragment_start);
                break;
            case LoadUserFragment:
                fragment = new LoadUserFragment();
                fragment.setLOADED_LAYOUT(R.layout.fragment_api_call);
                break;
        }

        return fragment;
    }

    public void loadFragment(Fragments type) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentCreator(type), type.toString())
                .addToBackStack(type.toString())
                .commit();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putCharSequence("response", getFragment().textView.getText());
//        if (myRequest != null) {
//            outState.putLong("request", myRequest.registerObject());
//        }
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        CharSequence response = savedInstanceState.getCharSequence("response");
//        if (response != null) {
//            getFragment().textView.setText(response);
//        }
//
//        long requestId = savedInstanceState.getLong("request");
//        myRequest = VKRequest.getRegisteredRequest(requestId);
//        if (myRequest != null) {
//            myRequest.unregisterObject();
//            myRequest.setRequestListener(mRequestListener);
//        }
//    }
}
