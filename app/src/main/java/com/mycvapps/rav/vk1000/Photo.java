package com.mycvapps.rav.vk1000;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo extends Attachment {
    private static String TAG = "Photo.Class";
    private int album_id;// -7,
    private int owner_id;// -36459871,
    private int user_id;// 100,
    private String photo_75;// 'http://cs7066.vk....a8f/4RIoajkC1Ys.jpg',
    private String photo_130;// 'http://cs7066.vk....a90/j66FvRXjS8Q.jpg',
    private String photo_604;// 'http://cs7066.vk....a91/8Uxan-a3FGg.jpg',
    private String photo_807;// 'http://cs7066.vk....a92/AELn4yje7-o.jpg',
    private int width;// 450,
    private int height;// 610,
    private String text;// '',
    private long date;// 1442678731,
    private int post_id;// 96125,
    private String access_key;// '06eb60ef10f670ff70'

    public Photo(String type, int id, int album_id, int owner_id, int user_id, String photo_75, String photo_130, String photo_604, String photo_807, int width, int height, String text, long date, int post_id, String access_key) {
        super(type, id);
        this.album_id = album_id;
        this.owner_id = owner_id;
        this.user_id = user_id;
        this.photo_75 = photo_75;
        this.photo_130 = photo_130;
        this.photo_604 = photo_604;
        this.photo_807 = photo_807;
        this.width = width;
        this.height = height;
        this.text = text;
        this.date = date;
        this.post_id = post_id;
        this.access_key = access_key;
    }

    public int getAlbum_id() {
        return album_id;
    }


    public int getOwner_id() {
        return owner_id;
    }



    public int getUser_id() {
        return user_id;
    }



    public String getPhoto_75() {
        return photo_75;
    }



    public String getPhoto_130() {
        return photo_130;
    }



    public String getPhoto_604() {
        return photo_604;
    }



    public String getPhoto_807() {
        return photo_807;
    }



    public int getWidth() {
        return width;
    }



    public int getHeight() {
        return height;
    }



    public String getText() {
        return text;
    }



    public long getDate() {
        return date;
    }



    public int getPost_id() {
        return post_id;
    }



    public String getAccess_key() {
        return access_key;
    }



    public static Photo getPhoto(JSONObject o) throws JSONException {
        final String type = o.optString("type");
        final int id = o.getJSONObject("photo").optInt("id");
        final int album_id = Math.abs(o.getJSONObject("photo").optInt("album_id"));// -7,
        final int owner_id = Math.abs(o.getJSONObject("photo").optInt("owner_id"));// -36459871,
        final int user_id = Math.abs(o.getJSONObject("photo").optInt("user_id"));// 100,
        final String photo_75 = o.getJSONObject("photo").optString("photo_75");// 'http://cs7066.vk....a8f/4RIoajkC1Ys.jpg',
        final String photo_130 = o.getJSONObject("photo").optString("photo_130");// 'http://cs7066.vk....a90/j66FvRXjS8Q.jpg',
        final String photo_604 = o.getJSONObject("photo").optString("photo_604");// 'http://cs7066.vk....a91/8Uxan-a3FGg.jpg',
        final String photo_807 = o.getJSONObject("photo").optString("photo_807");// 'http://cs7066.vk....a92/AELn4yje7-o.jpg',
        final int width = o.getJSONObject("photo").optInt("width");// 450,
        final int height = o.getJSONObject("photo").optInt("height");// 610,
        final String text = o.getJSONObject("photo").optString("text");// '',
        final long date = o.getJSONObject("photo").optLong("date");// 1442678731,
        final int post_id = o.getJSONObject("photo").optInt("post_id");// 96125,
        final String access_key = o.getJSONObject("photo").optString("access_key");// '06eb60ef10f670ff70'

        Log.d(TAG, "__________________Photo_________getPhoto="+type+id+album_id+owner_id+user_id+photo_75+photo_130+photo_604+photo_807+width+height+text+date+post_id+access_key);
        return new Photo(type,id,album_id,owner_id,user_id,photo_75,photo_130,photo_604,photo_807,width,height,text,date,post_id,access_key);
    }
}
