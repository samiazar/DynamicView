package com.samiazar.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;


import com.samiazar.library.util.ImageDownloader;
import com.samiazar.library.util.ParentType;
import com.samiazar.library.util.TagKey;
import com.samiazar.library.util.TagValue;
import com.samiazar.library.util.ParsingUtil;
import com.samiazar.library.util.Utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by USER
 * on 10/13/2018.
 */

public class XmlImageView extends XmlView {

    private final String TAG = getClass().getName();

    public XmlImageView(Context context, XmlPullParser xmlParser, ParentType parentType) {
        super(context, xmlParser, parentType);
        view = new ImageView(context);
    }

    @Override
    public View getView() throws IOException, XmlPullParserException {
        initBasicAttribute();
        if (layoutParams == null) return null;
        view.setLayoutParams(layoutParams);

        // add attribute of self view
        initImageViewAttribute();

        xmlParser.next();
        //end of parsing this view
        return view;
    }

    void initImageViewAttribute() throws XmlPullParserException {
        readSrc();
        readAdjustViewBounds();
        readCropToPadding();
        readScaleType();
        readTint();
    }

    @SuppressLint("StaticFieldLeak")
    private void readSrc() throws XmlPullParserException {
        String srcValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Src);
        if (srcValue!=null && !srcValue.equals("")) {
            String link = ParsingUtil.parseLink(srcValue);
            if (!link.equals("")) {
                new ImageDownloader(view) {
                    @Override
                    public void onLoadSource(WeakReference<View> viewWeakReference, Bitmap bitmap) {
                        ((ImageView) viewWeakReference.get()).setImageBitmap(bitmap);
                    }
                }.execute(link);
            } else {
                int src = ParsingUtil.parseResource(srcValue, view.getContext());
                if (src == 0)
                    throw new XmlPullParserException("can not find a resource in R.java class. do you sure add source in res folder?");
                ((ImageView) view).setImageResource(src);
            }
        }
    }

    private void readAdjustViewBounds() throws XmlPullParserException {
        String adjustViewBoundsValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AdjustViewBounds);
        if (adjustViewBoundsValue!=null && !adjustViewBoundsValue.equals("")) {
            try {
                boolean adjustViewBounds = Boolean.valueOf(adjustViewBoundsValue);
                ((ImageView) view).setAdjustViewBounds(adjustViewBounds);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("the value of adjustViewBounds must be boolean");
            }
        }
    }

    private void readCropToPadding() throws XmlPullParserException {
        String cropToPaddingValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.CropToPadding);
        if (cropToPaddingValue!=null && !cropToPaddingValue.equals("")) {
            try {
                boolean cropToPadding = Boolean.valueOf(cropToPaddingValue);
                ((ImageView) view).setCropToPadding(cropToPadding);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("the value of cropToPadding must be boolean");
            }
        }
    }

    private void readScaleType() throws XmlPullParserException {
        String scaleTypeValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.ScaleType);
        if (scaleTypeValue!=null && !scaleTypeValue.equals("")) {
            switch (scaleTypeValue) {
                case TagValue.Center:
                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER);
                    break;
                case TagValue.CenterCrop:
                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
                    break;
                case TagValue.FitXY:
                    ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
                    break;
                case TagValue.CenterInside:
                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    break;
                case TagValue.FitCenter:
                    ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case TagValue.FitEnd:
                    ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_END);
                    break;
                case TagValue.FitStart:
                    ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_START);
                    break;
                case TagValue.Matrix:
                    ((ImageView) view).setScaleType(ImageView.ScaleType.MATRIX);
                    break;
                default:
                    throw new XmlPullParserException("the attribute scaleType value must be one of these " +
                            "{center, centerCrop, fitXY, centerInside, fitCenter, firStart, fitEnd, matrix}");
            }
        }
    }

    private void readTint() throws XmlPullParserException {
        String tint = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Tint);
        int color = 0;
        if (tint!=null && !tint.equals("")) {
            color = ParsingUtil.parseColor(tint);
            if (color == 0) throw new XmlPullParserException("the color define in tint for ImageView is incorrect");
        }

        String tintMode = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Tint);
        PorterDuff.Mode mode = null;
        if (tintMode!=null && !tintMode.equals("")) {
            switch (tintMode) {
                case TagValue.Add:
                    mode = PorterDuff.Mode.ADD;
                    break;
                case TagValue.Multiply:
                    mode = PorterDuff.Mode.MULTIPLY;
                    break;
                case TagValue.Screen:
                    mode = PorterDuff.Mode.SCREEN;
                    break;
                case TagValue.Src_Atop:
                    mode = PorterDuff.Mode.SRC_ATOP;
                    break;
                case TagValue.Src_In:
                    mode = PorterDuff.Mode.SRC_IN;
                    break;
                case TagValue.Src_Over:
                    mode = PorterDuff.Mode.SRC_OVER;
                    break;
                default:
                    throw new XmlPullParserException("tint mode only support for these values: add, multiply, screen, src_in, scr_atop, src_over");
            }
        }

        if (color == 0) return;

        if (mode != null)
            ((ImageView) view).setColorFilter(color, mode);
        else
            ((ImageView) view).setColorFilter(color);
    }


}
