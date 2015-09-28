package com.mycvapps.rav.vk1000;

import android.os.Bundle;
import android.view.View;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

public class SelectorFragment extends BaseAbstractFragment {
    int OFFSET = 1;
    final int COUNT = 5;

    @Override
    protected void setSaveInstanceState(Bundle outState) {
    }

    @Override
    protected void getSaveInstanceState(Bundle savedInstanceState) {
    }


    @Override
    public void getFragmentViews(View view) {
        view.findViewById(R.id.users_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "id,first_name,last_name,photo_50"));
                request.secure = false;
                request.useSystemLanguage = false;

                startApiCall(request,Fragments.LoadUserFragment);
            }
        });

        view.findViewById(R.id.wall_getById).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, "10479140",
                            VKApiConst.OFFSET, OFFSET,
                            VKApiConst.COUNT, COUNT,
                            "filter", "all",
                            VKApiConst.VERSION, 5.37
                    ));
                    startApiCall(request,Fragments.WallFragment);
                }
            });
    }
}