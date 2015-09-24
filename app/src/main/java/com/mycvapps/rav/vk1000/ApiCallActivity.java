package com.mycvapps.rav.vk1000;

import android.support.v7.app.AppCompatActivity;

public class ApiCallActivity extends AppCompatActivity {

//    private VKRequest myRequest;
//
//    private static final String FRAGMENT_TAG = "response_view";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_api_call);
//
//        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new BaseAbstractFragment(), FRAGMENT_TAG)
//                    .addToBackStack(null)
//                    .commit();
//        }
//
//        processRequestIfRequired();
//    }
//
//    private BaseAbstractFragment getFragment() {
//        return (BaseAbstractFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
//    }
//
//    private void processRequestIfRequired() {
//        VKRequest request = null;
//
//        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("request")) {
//            long requestId = getIntent().getExtras().getLong("request");
//            request = VKRequest.getRegisteredRequest(requestId);
//            if (request != null)
//                request.unregisterObject();
//        }
//
//        if (request == null) return;
//        myRequest = request;
//        request.executeWithListener(mRequestListener);
//    }
//
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
//
//    protected void setResponseText(String text) {
//        BaseAbstractFragment fragment = getFragment();
//        if (fragment != null && fragment.textView != null) {
//            fragment.textView.setText(text);
//        }
//    }
//
//
//    VKRequest.VKRequestListener mRequestListener = new VKRequest.VKRequestListener()
//    {
//        @Override
//        public void onComplete(VKResponse response)
//        {
//            setResponseText(response.json.toString());
//        }
//
//        @Override
//        public void onError(VKError error)
//        {
//            setResponseText(error.toString());
//        }
//
//        @Override
//        public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded,
//                               long bytesTotal)
//        {
//            // you can show progress of the request if you want
//        }
//
//        @Override
//        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts)
//        {
//            getFragment().textView.append(
//                    String.format("Attempt %d/%d failed\n", attemptNumber, totalAttempts));
//        }
//    };
//
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy()
//    {
//        super.onDestroy();
//        myRequest.cancel();
//        Log.d(VKSdk.SDK_TAG, "On destroy");
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        int id = item.getItemId();
//
//        if (id == android.R.id.home)
//        {
//            finish();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class BaseAbstractFragment extends Fragment
//    {
//        public TextView textView;
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState)
//        {
//            View v = inflater.inflate(R.layout.fragment_api_call, container, false);
//            textView = (TextView) v.findViewById(R.id.response);
//            return v;
//        }
//    }
}