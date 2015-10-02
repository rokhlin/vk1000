package com.mycvapps.rav.vk1000;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKRequest;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity.class";
    private User curUser;
    private VKRequest myRequest =null;
    private BaseAbstractFragment fragment = null;

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
                    .add(R.id.container, fragmentCreator(Fragments.WallFragment), Fragments.WallFragment.toString())
                    .commit();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.action_logout:
                loginActivityStart();
                return true;
            case android.R.id.home :
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void loginActivityStart() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.putExtra("state", false);
        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) loginActivityStart();
        else super.onBackPressed();
    }

    public User getCurUser() {
        return curUser;
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }



    protected BaseAbstractFragment fragmentCreator(Fragments type){


        switch (type){
            case SelectorFragment:
                Log.v(TAG, "_________________Load  SelectorFragment");
                fragment = new SelectorFragment();
                fragment.setLOADED_LAYOUT(R.layout.fragment_start);
                break;
            case LoadUserFragment:
                Log.v(TAG, "_________________Load  LoadUserFragment");
                fragment = new LoadUserFragment();
                fragment.setLOADED_LAYOUT(R.layout.fragment_api_call);
                break;
            case WallFragment:
                Log.v(TAG, "_________________Load  WallFragment");
                fragment = new WallFragment();
                fragment.setLOADED_LAYOUT(R.layout.fragment_wall);
                break;
            case PostFragment:
                Log.v(TAG, "_________________Load  PostFragment");
                fragment = new PostFragment();
                fragment.setLOADED_LAYOUT(R.layout.fragment_post);
                break;
        }

        return fragment;
    }

    public void loadFragment(Fragments type) {
        Log.i(TAG, "___________________________________BackStackEntryCount =" + getSupportFragmentManager().getBackStackEntryCount());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentCreator(type), type.toString())
                .addToBackStack(type.toString())
                .commit();
    }

    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    public boolean myRecListener(RecyclerView view, final LinearLayoutManager mLayoutManager){
    view.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            fragment = (WallFragment) getSupportFragmentManager().findFragmentByTag(Fragments.WallFragment.toString());
            visibleItemCount = mLayoutManager.getChildCount();
            totalItemCount = mLayoutManager.getItemCount();
            pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
            Log.i(TAG, "________________________visibleItemCount =" + visibleItemCount
                                                        +" totalItemCount ="+totalItemCount
                                                        +" pastVisibleItems ="+pastVisibleItems);
            if (loading) {
                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    loading = false;
                    fragment.onLast();
                }
                else {
                    loading = true;
                }
            }



        }
    });
    return !loading;
}


}
