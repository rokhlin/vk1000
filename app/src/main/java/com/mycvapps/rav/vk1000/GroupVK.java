package com.mycvapps.rav.vk1000;
/**
 * Класс для парсинга Групп
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupVK {
    private static String TAG = "GroupVK.Class";
    private int id;
    private String name;
    private String screen_name;
    private int is_closed;
    private String type;
    private int is_admin;
    private int is_member;
    private String photo_50;
    private String photo_100;
    private String photo_200;

    public GroupVK(int id, String name, String screen_name,
                   int is_closed, String type, int is_admin,
                   int is_member,
                   String photo_50, String photo_100, String photo_200) {
        this.id = id;
        this.name = name;
        this.screen_name = screen_name;
        this.is_closed = is_closed;
        this.type = type;
        this.is_admin = is_admin;
        this.is_member = is_member;
        this.photo_50 = photo_50;
        this.photo_100 = photo_100;
        this.photo_200 = photo_200;
    }

    public static List<GroupVK> parseItems(JSONArray items){
        final List<GroupVK> groups = new ArrayList<>();
        Log.d(TAG, "________ parseItems________Items.length()=" + items.length());
        for (int i = 0; i < items.length() ; i++) {
            try {
                GroupVK group = parseItem((JSONObject) items.get(0));
                if(group != null) groups.add(group);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return groups;
    }

    private static GroupVK parseItem(JSONObject object) {
        final int id = object.optInt("id");
        final String name = object.optString("name");
        final String screen_name = object.optString("screen_name");
        final int is_closed = object.optInt("is_closed");
        final String type = object.optString("type");
        final int is_admin = object.optInt("is_admin");
        final int is_member = object.optInt("is_member");
        final String photo_50 = object.optString("photo_50");
        final String photo_100 = object.optString("photo_100");
        final String photo_200 = object.optString("photo_200");


        return new GroupVK(id,name,screen_name,is_closed,type,is_admin,is_member,photo_50,photo_100,photo_200);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public int getIs_closed() {
        return is_closed;
    }

    public String getType() {
        return type;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public int getIs_member() {
        return is_member;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public String getPhoto_100() {
        return photo_100;
    }

    public String getPhoto_200() {
        return photo_200;
    }
}
