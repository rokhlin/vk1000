package com.mycvapps.rav.vk1000;

import android.util.Log;

import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User {
    private static String TAG = "User.Class";
    private int id;
    private String firstName;
    private String lastName;
    private String photo50;

    public User(int id, String firstName, String lastName, String photo50) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo50 = photo50;
    }




    public static User fromJSON(JSONObject object) {
        final int id = object.optInt("id");
        final String firstName = object.optString("first_name");
        final String lastName = object.optString("last_name");
        final String photo50 = object.optString("photo_50");
        Log.d(TAG, "________ fromJSON________id,firstName,lastName,photo50="+ id+firstName+lastName+photo50);
         return new User(id,firstName,lastName,photo50);
    }

    public static ArrayList<User> fromJSONArray(JSONArray array){
        final ArrayList<User> users = new ArrayList<>();
        Log.d(TAG, "________ fromJSONArray________array.length()="+array.length());
        for (int i = 0; i < array.length() ; i++) {
            try {
                User user = fromJSON(array.getJSONObject(i));
                if(user != null) users.add(user);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return users;
    }


    public static ArrayList<User> getUsers(VKResponse response){
        ArrayList<User> users;
        try {
            JSONArray array = response.json.getJSONArray("response");
            users = fromJSONArray(array);
        } catch (JSONException e) {
            e.printStackTrace();
            JSONObject object = response.json;
            users = new ArrayList<>();
            users.add(fromJSON(object));
            Log.d(TAG, "________ getUsers________json"+ object.toString());
        }
        return users;
    }

    public static User getUser(VKResponse response){
        Log.d(TAG, "________ getUser________getUsers(response).size()="+ getUsers(response).size());
        return getUsers(response).get(0);
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto50() {
        return photo50;
    }

    public void setPhoto50(String photo50) {
        this.photo50 = photo50;
    }
}
