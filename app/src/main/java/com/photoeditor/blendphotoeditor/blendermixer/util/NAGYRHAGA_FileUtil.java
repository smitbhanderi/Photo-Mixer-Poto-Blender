package com.photoeditor.blendphotoeditor.blendermixer.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NAGYRHAGA_FileUtil {
    private static final String TAG = "NAGYRHAGA_FileUtil";

    public static String getFolderName(String str) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), str);
        if (file.exists() || file.mkdirs()) {
            return file.getAbsolutePath();
        }
        return "";
    }

    private static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static File getNewFile(Context context, String str) {
        String str2;
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        if (isSDAvailable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getFolderName(str));
            sb.append(File.separator);
            sb.append(format);
            sb.append(".jpg");
            str2 = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(context.getFilesDir().getPath());
            sb2.append(File.separator);
            sb2.append(format);
            sb2.append(".jpg");
            str2 = sb2.toString();
        }
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        return new File(str2);
    }
}
