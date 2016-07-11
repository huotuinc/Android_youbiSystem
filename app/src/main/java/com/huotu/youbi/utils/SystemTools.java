package com.huotu.youbi.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class SystemTools {

    public static byte[] readInputStream(InputStream inStream) {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            return outStream.toByteArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } finally {
            try {
                inStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
        return null;
    }

    /**
     * 指定格式返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * @param time yyy-MM-dd HH:mm
     * @return
     */
    public static long getLongTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm", Locale.CHINA);
        Date date;
        try {
            date = sdf.parse(time);
            Calendar c = Calendar.getInstance();
            TimeZone tz = TimeZone.getTimeZone("GMT");
            c.setTimeZone(tz);
            c.setTime(date);

            return c.getTimeInMillis();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取手机系统SDK版本
     *
     * @return 如API 17 则返回 17
     */
    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统版本
     *
     * @return 形如2.3.3
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    @SuppressLint("NewApi")
    public static void loadBackground(TextView view, Drawable drawable, String text) {
        if (SystemTools.getSDKVersion() >= 16) {
            view.setText(text);
            view.setBackground(drawable);
        } else {
            view.setText(text);
            view.setBackgroundDrawable(drawable);
        }
    }

    @SuppressLint("NewApi")
    public static void loadBackground(View view, Drawable drawable) {
        if (SystemTools.getSDKVersion() >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    //获取系统颜色
    public static int obtainColor(String colorStr) {
        String[] colors = colorStr.split(",");
        return Color.argb(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]), Integer.parseInt(colors[3]));
    }

    public static int obtainAlphaColor(String colorStr) {
        String[] colors = colorStr.split(",");
        return Color.argb(12, Integer.parseInt(colors[1]), Integer.parseInt(colors[2]), Integer.parseInt(colors[3]));
    }

    /**
     * 图片渐变
     *
     * @param view
     */
    public static void setFlickerAnimation(ImageView view) {
        final Animation animation = new AlphaAnimation(0, 1); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setRepeatCount(3); // Repeat animation infinitely
        animation.setFillAfter(true);
        view.setAnimation(animation);
        animation.startNow();
    }

    public static void setRotateAnimation(ImageView view) {
        final Animation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // Change alpha from fully visible to invisible
        animation.setDuration(1000); // duration - half a second
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setFillAfter(true);
        view.setAnimation(animation);
        animation.startNow();
    }

    /**
     * 设置字体theme
     * @param view
     */
    /*public static void setFontStyle(TextView view, BaseApplication application )
    {
        view.setTypeface ( application.font );
    }*/

    /**
     * 封装分享链接
     *
     * @return
     */
    /*public static String shareUrl(BaseApplication application, String url)
    {
        String param = "gduid=" + application.readUserId ();
        if(application.obtainMerchantUrl ().equals (url) )
        {
            //其他界面
            return  url + "?" + param;
        }
        //首页
        else if(application.obtainMerchantUrl ().equals ( url.substring ( 0, url.indexOf ( "?" )) ))
        {
            return  url.replace ( url.substring ( url.indexOf ( "?" ) + 1, url.length () ), param );
        }
        else if(url.contains ( "unionid" ) && url.contains ( "sign" ) && url.contains ( "appid" ) && url.contains ( "timestamp" ))
        {
            //原生界面进入界面
            return  url.replace ( url.substring ( url.indexOf ( "timestamp=" ), url.length () ), param );
        }
        else
        {
            if(url.contains ( "?" ))
            {
                //其他界面
                return  url + "&" + param;
            }
            else
            {
                //其他界面
                return  url + "?" + param;
            }

        }
    }*/
    public static Bitmap readBitmapFromSD(String iconName) {
        String dir = Environment.getExternalStorageDirectory() + File.separator + "buyer" + File.separator + "icon" + File.separator;
        String iconPath = dir + iconName + ".png";
        File iconFile = new File(iconPath);
        //若该文件存在
        if (iconFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(iconPath);
            return bitmap;
        }
        return null;
    }

    /**
     * 获取assets配置文件
     *
     * @param mContext
     * @return
     */
    public static List<String> obtainEmoji(Context mContext, String fileName) {
        try {
            List<String> emojis = new ArrayList<String>();
            InputStream in = mContext.getResources().getAssets().open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                emojis.add(str);
            }

            return emojis;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 设置部分文字的颜色
     *
     * @param view
     * @param color
     */
    public static void multiTextColor(TextView view, int start, int end, String color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(view.getText().toString());
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));
        builder.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(builder);
    }

    public static void killAppDestory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //2.2之前操作
        if (8 < SystemTools.getSDKVersion()) {
            am.restartPackage(context.getPackageName());
        } else if (8 <= SystemTools.getSDKVersion()) {
            am.killBackgroundProcesses(context.getPackageName());
        }
    }
}
