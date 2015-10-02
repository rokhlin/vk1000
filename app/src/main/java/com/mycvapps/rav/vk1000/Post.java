package com.mycvapps.rav.vk1000;
/**
 * Класс для парсинга постов
 */
import android.util.Log;

import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private static String TAG = "Post.Class";
    private int id;
    private int owner_id;
    private int from_id;
    private long date;
    private String text;
    public static int sCount;
    private String from_name;
    private String from_avatar;
    private List<Attachment> attachments;
    private int comments;
    private int likes;
    private int user_likes;


    public Post(int id, int owner_id, int from_id, long date, String text, int comments, int likes, int user_likes) {
        this.id = id;
        this.owner_id = owner_id;
        this.from_id = from_id;
        this.date = date;
        this.text = text;
        this.comments = comments;
        this.likes = likes;
        this.user_likes = user_likes;

    }

    public Post(int id, int owner_id, int from_id, long date, String text, int comments, int likes, int user_likes,List<Attachment> attachments) {
        this.id = id;
        this.owner_id = owner_id;
        this.from_id = from_id;
        this.date = date;
        this.text = text;
        this.comments = comments;
        this.likes = likes;
        this.user_likes = user_likes;
        this.attachments = attachments;
    }

    private static Post parseItem(JSONObject object) throws JSONException {
        int id = object.optInt("id");
        int owner = object.optInt("owner_id");
        int from = object.optInt("from_id");
        long date = object.optLong("date");
        String text = object.optString("text");
        int comments;
        int likes;
        int user_likes;
        try {
            comments = object.getJSONObject("comments").getInt("count");
            likes = object.getJSONObject("likes").getInt("count");
            user_likes = object.getJSONObject("likes").getInt("user_likes");
        }
        catch (JSONException e){
            e.printStackTrace();
            comments = 0;
            likes = 0;
            user_likes =0;
        }

        List<Attachment> att =  parseAttachments(object);
        if(att.size()>0){
            if(att.get(0) != null){
                return new Post(id,owner,from,date,text,comments,likes,user_likes, att);
            }
            else {
                return new Post(id,owner,from,date,text, comments, likes, user_likes);
            }
        }
        else {
            return new Post(id,owner,from,date,text, comments,likes,user_likes);
        }
    }

    private static List<Attachment> parseAttachments(JSONObject object) {
        List<Attachment> jAttachements = new ArrayList<>();
        try {
            JSONArray att = object.getJSONArray("attachments");
            for (int i = 0; i <att.length() ; i++) {
                Attachment attachment = parseAttachment((JSONObject) att.get(i));
                if(attachment != null)
                jAttachements.add(attachment);
            }

        } catch (JSONException e) {
            Log.d(TAG, "_______________________parseAttachments(JSONObject object)= error Attachments not found");
          // e.printStackTrace();//Uncomment for debug
        }
        return jAttachements;
    }

    private static Attachment parseAttachment(JSONObject o) throws JSONException {
        final String type = o.optString("type");
        switch (type){
            case "photo":
                return Photo.getPhoto(o);
            case "video":
                return Video.getVideo(o);
            default:
                return null;
        }

    }


    private static List<Post> parseItems(JSONArray items){
        final List<Post> posts = new ArrayList<>();
        Log.d(TAG, "________ parseItems________Items.length()="+items.length());
        for (int i = 0; i < items.length() ; i++) {
            try {
                Post post = parseItem((JSONObject) items.get(i));
                if(post != null) posts.add(post);
            } catch (JSONException e1) {
                Log.d(TAG, "_______________________parseItems(JSONArray items)= error, Posts not found");
                //e1.printStackTrace();//Uncomment for debug
            }
        }
        return posts;
    }

    public static List<Post> getPosts(VKResponse response){
        List<Post> posts = null;
        List<GroupVK> groups = null;
        List<User> users = null;
        try {
            //Log.d(TAG, "________ getPosts________response="+response.json.toString());//Uncomment for debug
            JSONArray items = ((JSONObject) response.json.get("response")).getJSONArray("items");
            JSONArray groupVKs = ((JSONObject) response.json.get("response")).getJSONArray("groups");
            JSONArray profiles = ((JSONObject) response.json.get("response")).getJSONArray("profiles");


            posts = parseItems(items);
            groups = GroupVK.parseItems(groupVKs);
            users = User.fromJSONArray(profiles);
        } catch (JSONException e) {
            Log.e(TAG, "_______________________getPosts(VKResponse response)= error to parse items or groupVK or profiles");
           // e.printStackTrace();//Uncomment for debug
        }
        try {
            sCount = (int) ((JSONObject) response.json.get("response")).get("count");
            Log.d(TAG, "________ getPosts________count=" + sCount);
        } catch (JSONException e) {
            Log.e(TAG, "_______________________getPosts(VKResponse response)= error to parse sCount");
            //e.printStackTrace();//Uncomment for debug
        }


        if (posts != null) {
            for (int i = 0; i <posts.size() ; i++) {
                int id = posts.get(i).getFrom_id();
                for (GroupVK group : groups){
                    if(group.getId() == id){
                        Post newPost = posts.get(i);
                        newPost.setFrom_name(group.getName());
                        newPost.setFrom_avatar(group.getPhoto_50());
                        posts.set(i,newPost);
                        break;
                    }
                }
                for (User user : users){
                    if(user.getId() == id){
                        Post newPost = posts.get(i);
                        newPost.setFrom_name(user.getFirstName() + " " + user.getLastName());
                        newPost.setFrom_avatar(user.getPhoto50());
                        posts.set(i,newPost);
                        break;
                    }
                }
            }
        }

        return posts;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getFrom_avatar() {
        return from_avatar;
    }

    public void setFrom_avatar(String from_avatar) {
        this.from_avatar = from_avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }


    public int getFrom_id() {
        return from_id;
    }

    public long getDate() {
        return date;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static int getsCount() {
        return sCount;
    }

    public int getComments() {
        return comments;
    }

    public int getLikes() {
        return likes;
    }

    public int getUser_likes() {
        return user_likes;
    }
}

