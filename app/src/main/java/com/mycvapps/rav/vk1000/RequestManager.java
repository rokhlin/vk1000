package com.mycvapps.rav.vk1000;


import android.util.Log;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

public class RequestManager {
    private static String TAG = "RequestManager";

    public static VKRequest getWall(int offset,int count ){
        return VKApi.wall().get(VKParameters.from(
                VKApiConst.OFFSET, offset,
                VKApiConst.COUNT, count,
                "filter", "all",
                VKApiConst.EXTENDED, 1,
                VKApiConst.VERSION, 5.37
        ));

    }
    public static VKRequest getWall(int offset,int count, int user ){
        return VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, user,
                VKApiConst.OFFSET, offset,
                VKApiConst.COUNT, count,
                "filter", "all",
                VKApiConst.EXTENDED, 1,
                VKApiConst.VERSION, 5.37
        ));

    }
    public static VKRequest getWall(int offset,int count, String user ){
        return VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, Integer.parseInt(user),
                VKApiConst.OFFSET, offset,
                VKApiConst.COUNT, count,
                "filter", "all",
                VKApiConst.EXTENDED, 1,
                VKApiConst.VERSION, 5.37
        ));

    }

    public static VKRequest getPost(int id){
        Log.i(TAG, "__________________________postId =" + id);
        return VKApi.wall().getById(VKParameters.from(VKApiConst.POSTS, id,
                VKApiConst.EXTENDED, 1,
                VKApiConst.VERSION, 5.37));
    }
    public static VKRequest getPost(int user, int postId){
        Log.i(TAG, "__________________________postId =" + user + "_" + postId);
        return VKApi.wall().getById(VKParameters.from(VKApiConst.POSTS, user + "_" + postId,
                VKApiConst.EXTENDED, 1,
                VKApiConst.VERSION, 5.37));
    }
    public static VKRequest getPost(String user, int postId){
        Log.i(TAG, "__________________________postId =" + user + "_" + postId);
        return VKApi.wall().getById(VKParameters.from(VKApiConst.POSTS, Integer.parseInt(user) + "_" + postId,
                VKApiConst.EXTENDED, 1,
                VKApiConst.VERSION, 5.37));
    }


}
