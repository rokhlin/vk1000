package com.mycvapps.rav.vk1000;
/**
 * Класс для отображения делатей пользователя
 * !!!!! в данный момент отключен
 */

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class LoadUserFragment extends BaseAbstractFragment {
    private static String TAG = "LoadUserFragment";
    public TextView textView, uid ,name, lastname;
    public ImageView avatar;


    void setScrListener() {

    }

    @Override
    public void getFragmentViews(View view) {
        try {
            textView = (TextView) view.findViewById(R.id.response);
            uid = (TextView) view.findViewById(R.id.tv_user_id);
            name = (TextView) view.findViewById(R.id.tv_user_name);
            lastname = (TextView) view.findViewById(R.id.tv_user_lastname);
            avatar = (ImageView)view.findViewById(R.id.iv_user_avatar);
            uid.setText(String.valueOf(curUser.getId()));
            name.setText(curUser.getFirstName());
            lastname.setText(curUser.getLastName());

            PicassoCache.getPicassoInstance(getContext()).load(curUser.getPhoto50()).into(avatar);
        }
        catch (Exception e){
            Log.e(TAG, "__________________________________________________________"+e.getMessage());
        }
    }


    protected void setSaveInstanceState(Bundle outState) {
        outState.putString("StrData", textView.getText().toString());
    }

    protected void getSaveInstanceState(Bundle savedInstanceState) {
        // Check to see if we have a frame in which to embed the details
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            textView.setText(savedInstanceState.getString("StrData"));
        }
    }

}
