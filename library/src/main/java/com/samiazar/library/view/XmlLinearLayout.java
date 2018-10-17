package com.samiazar.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.samiazar.library.XmlParser;
import com.samiazar.library.util.ParentType;
import com.samiazar.library.util.TagKey;
import com.samiazar.library.util.TagValue;
import com.samiazar.library.util.TagView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by USER
 * on 10/13/2018.
 */

public class XmlLinearLayout extends XmlView {


    public XmlLinearLayout(Context context, XmlPullParser xmlParser, ParentType parentType) {
        super(context, xmlParser, parentType);
        view = new LinearLayout(context);
    }

    @Override
    public View getView() throws IOException, XmlPullParserException {
        initBasicAttribute();
        if (layoutParams == null) return null;
        view.setLayoutParams(layoutParams);

        // add attribute of self view
        xmlParser.require(XmlPullParser.START_TAG, TagKey.NameSpace, TagView.LinearLayout);
        readGravityAttribute();
        readOrientationAttribute();
        readSumWeightAttribute();
        // add children of view
        while (xmlParser.next() != XmlPullParser.END_TAG) {
            if (xmlParser.getEventType() != XmlPullParser.START_TAG) continue;
            ((LinearLayout) view).addView(XmlParser.getLayout(xmlParser, view.getContext(), ParentType.LINEAR_LAYOUT));
        }

        //end of parsing this view
        xmlParser.require(XmlPullParser.END_TAG, TagKey.NameSpace, TagView.LinearLayout);
        return view;
    }

    @SuppressLint("RtlHardcoded")
    private void readGravityAttribute() throws XmlPullParserException {
        String gravityValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Gravity);
        int gravity = 0;
        if (gravityValue != null && !gravityValue.equals("")) {
            String[] gravityList = gravityValue.split("\\|");
            for (String temp : gravityList) {
                switch (temp) {
                    case TagValue.Right:
                        gravity += Gravity.RIGHT;
                    case TagValue.Left:
                        gravity += Gravity.LEFT;
                    case TagValue.Bottom:
                        gravity += Gravity.BOTTOM;
                    case TagValue.Top:
                        gravity += Gravity.TOP;
                    case TagValue.Center:
                        gravity += Gravity.CENTER;
                    case TagValue.CenterHorizontal:
                        gravity += Gravity.CENTER_HORIZONTAL;
                    case TagValue.CenterVertical:
                        gravity += Gravity.CENTER_VERTICAL;
                    default:
                        throw new XmlPullParserException("The value of gravity in each view must be one of the 'right', 'left', 'bottom', 'top', " +
                                "'center', 'center_horizontal', 'center_vertical'");
                }
            }
            ((LinearLayout) view).setGravity(gravity);
        }
    }

    private void readOrientationAttribute() throws XmlPullParserException {
        String orientationValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Orientation);
        if (orientationValue != null && !orientationValue.equals("")) {
            switch (orientationValue) {
                case TagValue.Vertical:
                    ((LinearLayout) view).setOrientation(LinearLayout.VERTICAL);
                    break;
                case TagValue.Horizontal:
                    ((LinearLayout) view).setOrientation(LinearLayout.HORIZONTAL);
                    break;
                default:
                    throw new XmlPullParserException("the orientation tag must be one of the vertical or horizontal");
            }
        }
    }

    private void readSumWeightAttribute() throws XmlPullParserException {
        String sumWeightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.WeightSum);
        if (sumWeightValue != null && !sumWeightValue.equals("")) {
            try {
                float sumWeight = Float.valueOf(sumWeightValue);
                ((LinearLayout) view).setWeightSum(sumWeight);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new XmlPullParserException("the value of sumWeight must be pure float");
            }
        }
    }
}
