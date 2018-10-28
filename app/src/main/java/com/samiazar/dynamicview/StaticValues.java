package com.samiazar.dynamicview;

/**
 * Created by USER
 * on 10/10/2018.
 */

public class StaticValues {
    
    public static final String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"" +
            "    android:layout_width=\"match_parent\"" +
            "    android:layout_height=\"match_parent\"" +
            "    android:orientation=\"vertical\"" +
            "    android:background=\"#6688aa\">" +
            "    <TextView" +
            "        android:layout_width=\"wrap_content\"" +
            "        android:layout_height=\"wrap_content\"" +
            "        android:textStyle=\"bold\"" +
            "        android:text=\"Title\"" +
            "        android:layout_gravity=\"center\"" +
            "        android:textColor=\"#6633aa\"" +
            "        android:textSize=\"24dp\"" +
            "        android:layout_margin=\"24dp\"/>" +
            "" +
            "" +
            "    <RelativeLayout" +
            "        android:layout_width=\"match_parent\"" +
            "        android:layout_height=\"300dp\"" +
            "        android:background=\"#aa4488\">" +
            "        " +
            "        <ImageView" +
            "            android:layout_width=\"24dp\"" +
            "            android:layout_height=\"64dp\"" +
            "            android:scaleType=\"centerCrop\"" +
            "            android:layout_alignParentLeft=\"true\"" +
            "            android:src=\"http://api.ariogames.ir/fileuploader/107800\"/>" +
            "" +
            "        <ImageView" +
            "            android:layout_width=\"64dp\"" +
            "            android:layout_height=\"64dp\"" +
            "            android:layout_alignParentRight=\"true\"" +
            "            android:src=\"@drawable/ic_launcher_background\"/>" +
            "" +
            "        <TextView" +
            "            android:id=\"@+id/text_center\"" +
            "            android:layout_width=\"wrap_content\"" +
            "            android:layout_height=\"wrap_content\"" +
            "            android:text=\"Center\"" +
            "            android:layout_centerInParent=\"true\"" +
            "            android:textColor=\"#2211aa\"" +
            "            android:textSize=\"38dp\"/>" +
            "" +
            "        <TextView" +
            "            android:id=\"@+id/text_baseLine\"" +
            "            android:layout_width=\"wrap_content\"" +
            "            android:layout_height=\"wrap_content\"" +
            "            android:text=\"BaseLine\"" +
            "            android:layout_alignBaseline=\"@+id/text_center\"" +
            "            android:layout_toRightOf=\"@+id/text_center\"" +
            "            android:textColor=\"#11ad19\"" +
            "            android:textSize=\"12dp\"/>" +
            "" +
            "        <TextView" +
            "            android:id=\"@+id/text_below\"" +
            "            android:layout_width=\"wrap_content\"" +
            "            android:layout_height=\"wrap_content\"" +
            "            android:text=\"Below\"" +
            "            android:layout_centerHorizontal=\"true\"" +
            "            android:layout_below=\"@+id/text_center\"" +
            "            android:textColor=\"#84aa12\"" +
            "            android:textSize=\"12dp\"/>" +
            "" +
            "        <TextView" +
            "            android:id=\"@+id/text_top\"" +
            "            android:layout_width=\"wrap_content\"" +
            "            android:layout_height=\"wrap_content\"" +
            "            android:text=\"Top\"" +
            "            android:layout_alignLeft=\"@+id/text_center\"" +
            "            android:layout_above=\"@+id/text_center\"" +
            "            android:textColor=\"#84aa12\"" +
            "            android:textSize=\"14dp\"/>" +
            "        " +
            "    </RelativeLayout>" +
            "" +
            "    <LinearLayout" +
            "        android:layout_width=\"match_parent\"" +
            "        android:layout_height=\"26dp\"" +
            "        android:background=\"@color/color2\"" +
            "        android:layout_margin=\"12dp\"/>" +
            "    " +
            "    " +
            "</LinearLayout>";
    
    
//    public static final String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
//            "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"" +
//            "    android:layout_width=\"match_parent\"" +
//            "    android:layout_height=\"match_parent\"" +
//            "    android:orientation=\"vertical\"" +
//            "    android:background=\"#6688aa\">" +
//            "    <TextView" +
//            "        android:layout_width=\"wrap_content\"" +
//            "        android:layout_height=\"wrap_content\"" +
//            "        android:textStyle=\"bold\"" +
//            "        android:text=\"Title\"" +
//            "        android:layout_gravity=\"center\"" +
//            "        android:textColor=\"#6633aa\"" +
//            "        android:textSize=\"64dp\"/>" +
//            "    " +
//            "    <LinearLayout" +
//            "        android:layout_width=\"match_parent\"" +
//            "        android:layout_height=\"26dp\"" +
//            "        android:background=\"#998855\"" +
//            "        android:layout_margin=\"12dp\"/>" +
//            "    " +
//            "    <TextView" +
//            "        android:layout_width=\"wrap_content\"" +
//            "        android:layout_height=\"wrap_content\"" +
//            "        android:textStyle=\"bold\"" +
//            "        android:text=\"SubTitle\"" +
//            "        android:layout_gravity=\"center\"" +
//            "        android:textColor=\"#2211aa\"" +
//            "        android:textSize=\"24dp\"/>" +
//            "" +
//            "</LinearLayout>";
    
//    public static final String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
//            "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"" +
//            "    android:layout_width=\"match_parent\"" +
//            "    android:layout_height=\"match_parent\">" +
//            "" +
//            "    <ImageView" +
//            "        android:id=\"@+id/iv_background\"" +
//            "        android:layout_width=\"match_parent\"" +
//            "        android:layout_height=\"match_parent\"" +
//            "        android:scaleType=\"centerCrop\" />" +
//            "" +
//            "    <RelativeLayout" +
//            "        android:layout_width=\"match_parent\"" +
//            "        android:layout_height=\"96dp\">" +
//            "" +
//            "        <ImageView" +
//            "            android:id=\"@+id/iv_close\"" +
//            "            android:layout_width=\"56dp\"" +
//            "            android:layout_height=\"56dp\"" +
//            "            android:background=\"#e3dbff\" />" +
//            "" +
//            "    </RelativeLayout>" +
//            "" +
//            "    <TextView" +
//            "        android:id=\"@+id/tv_title\"" +
//            "        android:layout_width=\"wrap_content\"" +
//            "        android:layout_height=\"wrap_content\"" +
//            "        android:textSize=\"25sp\"" +
//            "        android:text=\"Well Done Title\"" +
//            "        android:textColor=\"#fff\"" +
//            "        android:layout_marginTop=\"96dp\"" +
//            "        android:layout_centerHorizontal=\"true\"" +
//            "        android:background=\"#dfb760\"" +
//            "        android:padding=\"12dp\"" +
//            "        android:gravity=\"center\" />" +
//            "" +
//            "    <TextView" +
//            "        android:id=\"@+id/tv_subtitle\"" +
//            "        android:layout_width=\"wrap_content\"" +
//            "        android:layout_height=\"wrap_content\"" +
//            "        android:textSize=\"18sp\"" +
//            "        android:text=\"There is Subtitle\"" +
//            "        android:layout_below=\"@+id/tv_title\"" +
//            "        android:textColor=\"#fff\"" +
//            "        android:background=\"#8b4059\"" +
//            "        android:padding=\"8dp\"" +
//            "        android:layout_centerHorizontal=\"true\"" +
//            "        android:gravity=\"center\"" +
//            "        />" +
//            "" +
//            "" +
//            "    <Button" +
//            "        android:id=\"@+id/bt_campaign\"" +
//            "        android:layout_width=\"220dp\"" +
//            "        android:layout_height=\"54dp\"" +
//            "        android:layout_centerHorizontal=\"true\"" +
//            "        android:layout_alignParentBottom=\"true\"" +
//            "        android:layout_marginBottom=\"72dp\"" +
//            "        android:gravity=\"center\"" +
//            "        android:textSize=\"18sp\"" +
//            "        android:textColor=\"#fff\"" +
//            "        android:foreground=\"?attr/selectableItemBackground\"" +
//            "        android:padding=\"4dp\"" +
//            "        android:text=\"Button \"" +
//            "        />" +
//            "" +
//            "    <RelativeLayout" +
//            "        android:layout_width=\"match_parent\"" +
//            "        android:layout_height=\"96dp\"" +
//            "        android:layout_alignParentBottom=\"true\"" +
//            "        android:background=\"#dfb760\">" +
//            "" +
//            "        <TextView" +
//            "            android:id=\"@+id/bt_dont_show_again\"" +
//            "            android:layout_width=\"220dp\"" +
//            "            android:layout_height=\"wrap_content\"" +
//            "            android:layout_centerHorizontal=\"true\"" +
//            "            android:layout_alignParentBottom=\"true\"" +
//            "            android:layout_marginBottom=\"28dp\"" +
//            "            android:gravity=\"center\"" +
//            "            android:text=\"hint text\"" +
//            "            android:textSize=\"14sp\"" +
//            "            android:textColor=\"#fff\"" +
//            "            android:background=\"#dfb760\"" +
//            "            android:padding=\"4dp\"" +
//            "            />" +
//            "" +
//            "    </RelativeLayout>" +
//            "" +
//            "</RelativeLayout>";
}
