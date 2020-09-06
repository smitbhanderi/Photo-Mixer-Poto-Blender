package com.photoeditor.blendphotoeditor.blendermixer.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.photoeditor.blendphotoeditor.blendermixer.R;
/*import com.naygrh.multiplephotoblendermixerphotoeditor.R;*/

public class NAGYRHAGA_AppPreferences {
    private static final String COLOR = "COLOR";
    private static String PREFS_NAME;
    private final Editor editor = this.preferences.edit();
    private SharedPreferences preferences;

    public NAGYRHAGA_AppPreferences(Context context) {
        PREFS_NAME = context.getResources().getString(R.string.app_name);
        this.preferences = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public void set_COLOR(String str) {
        this.editor.putString(COLOR, str);
        this.editor.commit();
    }

    public String get_COLOR() {
        return this.preferences.getString(COLOR, "");
    }
}
