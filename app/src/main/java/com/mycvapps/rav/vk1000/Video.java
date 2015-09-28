package com.mycvapps.rav.vk1000;


import org.json.JSONException;
import org.json.JSONObject;

public class Video extends Attachment {
    private static String TAG = "Video.Class";
    private int owner_id;// 6369247,
    private String title;// 'Zaz',
    private int duration;// 240,
    private String description;// '',
    private int date;// 1339650107,
    private int views;// 17,
    private int comments;// 0,
    private String photo_130;// 'http://cs12921.vk...ideo/s_c36d0978.jpg',
    private String photo_320;// 'http://cs12921.vk...ideo/l_52522b6a.jpg',
    private int album_id;// 34836722,
    private String access_key;// '251644d020dd7918db'

    public Video(String type, int id, int owner_id, String title, int duration, String description, int date, int views, int comments, String photo_130, String photo_320, int album_id, String access_key) {
        super(type, id);
        this.owner_id = owner_id;
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.date = date;
        this.views = views;
        this.comments = comments;
        this.photo_130 = photo_130;
        this.photo_320 = photo_320;
        this.album_id = album_id;
        this.access_key = access_key;
    }

    public int getOwner_id() {
        return owner_id;
    }



    public String getTitle() {
        return title;
    }



    public int getDuration() {
        return duration;
    }



    public String getDescription() {
        return description;
    }



    public int getDate() {
        return date;
    }



    public int getViews() {
        return views;
    }


    public int getComments() {
        return comments;
    }



    public String getPhoto_130() {
        return photo_130;
    }



    public String getPhoto_320() {
        return photo_320;
    }



    public int getAlbum_id() {
        return album_id;
    }



    public String getAccess_key() {
        return access_key;
    }



    public static Video getVideo(JSONObject o) throws JSONException {
        final String type = o.getJSONObject("video").optString("type");
        final int id = o.getJSONObject("video").optInt("id");
        final int owner_id = Math.abs(o.getJSONObject("video").optInt(""));// 6369247,
        final String title = o.getJSONObject("video").optString("");// 'Zaz',
        final int duration = Math.abs(o.getJSONObject("video").optInt(""));// 240,
        final String description = o.getJSONObject("video").optString("");// '',
        final int date = Math.abs(o.getJSONObject("video").optInt(""));// 1339650107,
        final int views = Math.abs(o.getJSONObject("video").optInt(""));// 17,
        final int comments = Math.abs(o.getJSONObject("video").optInt(""));// 0,
        final String photo_130 = o.getJSONObject("video").optString("");// 'http://cs12921.vk...ideo/s_c36d0978.jpg',
        final String photo_320 = o.getJSONObject("video").optString("");// 'http://cs12921.vk...ideo/l_52522b6a.jpg',
        final int album_id = Math.abs(o.getJSONObject("video").optInt(""));// 34836722,
        final String access_key = o.getJSONObject("video").optString("");// '251644d020dd7918db'

        return new Video(type,id,owner_id,title,duration,description,date,views,comments,photo_130,photo_320,album_id,access_key);
    }
}
