package com.samiazar.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.samiazar.library.util.ParentType;
import com.samiazar.library.util.TagKey;
import com.samiazar.library.util.TagValue;
import com.samiazar.library.util.Utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by USER
 * on 9/17/2018.
 */

public class XmlView {

    private final String TAG = getClass().getName();
    private ParentType parentType;
    XmlPullParser xmlParser;
    View view;
    ViewGroup.LayoutParams layoutParams = null;

    public XmlView(Context context, XmlPullParser xmlParser, ParentType parentType) {
        this.xmlParser = xmlParser;
        this.parentType = parentType;
        this.view = new View(context);
    }

    public void initBasicAttribute() throws IOException, XmlPullParserException {
        readWidthHeight();
        if (layoutParams == null) return;
        readLinearLayoutAttribute();
        readRelativeLayoutAttribute();
        readViewGroupAttribute();
    }

    public View getView() throws IOException, XmlPullParserException {
        initBasicAttribute();
        if (layoutParams == null) return null;
        view.setLayoutParams(layoutParams);
        return view;
    }

    private void readWidthHeight() throws XmlPullParserException {
        String widthValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.LayoutWidth);
        String heightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.LayoutHeight);
        int width, height;
        if (widthValue == null) throw new XmlPullParserException("make sure you set layout_width in all views");
        if (heightValue == null) throw new XmlPullParserException("make sure you set layout_height in all views");
        switch (widthValue) {
            case TagValue.Wrap_Content:
                width = ViewGroup.LayoutParams.WRAP_CONTENT;
                break;
            case TagValue.Match_Parent:
                width = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            default:
                width = Utils.parseDpSize(widthValue, view.getContext());
                if (width < 0) {
                    throw new XmlPullParserException("make sure you set layout_width in all views");
                }
                break;
        }

        switch (heightValue) {
            case TagValue.Wrap_Content:
                height = ViewGroup.LayoutParams.WRAP_CONTENT;
                break;
            case TagValue.Match_Parent:
                height = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            default:
                height = Utils.parseDpSize(widthValue, view.getContext());
                if (height < 0) {
                    Log.e(TAG, "make sure you set layout_height in all views");
                    throw new XmlPullParserException("make sure you set layout_height in all views");
                }
                break;
        }
        if (parentType.equals(ParentType.LINEAR_LAYOUT))
            layoutParams = new LinearLayout.LayoutParams(width, height);
        else if (parentType.equals(ParentType.RELATIVE_LAYOUT))
            layoutParams = new RelativeLayout.LayoutParams(width, height);
        else if (parentType.equals(ParentType.SCROLL_VIEW))
            layoutParams = new ScrollView.LayoutParams(width, height);
        else if (parentType.equals(ParentType.FRAME_LAYOUT))
            layoutParams = new FrameLayout.LayoutParams(width, height);
        else
            layoutParams = new ViewGroup.LayoutParams(width, height);
    }

    @SuppressLint("RtlHardcoded")
    private void readLinearLayoutAttribute() throws XmlPullParserException {
        if (!(layoutParams instanceof LinearLayout.LayoutParams)) return;

        //LayoutGravity
        String gravityValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.LayoutGravity);
        if (gravityValue != null && !gravityValue.equals("")) {
            String[] gravityList = gravityValue.split("\\|");
            for (String gravity : gravityList) {
                switch (gravity) {
                    case TagValue.Right:
                        ((LinearLayout.LayoutParams) layoutParams).gravity += Gravity.RIGHT;
                    case TagValue.Left:
                        ((LinearLayout.LayoutParams) layoutParams).gravity += Gravity.LEFT;
                    case TagValue.Bottom:
                        ((LinearLayout.LayoutParams) layoutParams).gravity += Gravity.BOTTOM;
                    case TagValue.Top:
                        ((LinearLayout.LayoutParams) layoutParams).gravity += Gravity.TOP;
                    case TagValue.Center:
                        ((LinearLayout.LayoutParams) layoutParams).gravity += Gravity.CENTER;
                    case TagValue.CenterHorizontal:
                        ((LinearLayout.LayoutParams) layoutParams).gravity += Gravity.CENTER_HORIZONTAL;
                    case TagValue.CenterVertical:
                        ((LinearLayout.LayoutParams) layoutParams).gravity += Gravity.CENTER_VERTICAL;
                    default:
                        throw new XmlPullParserException("The value of gravity in each view must be one of the 'right', 'left', 'bottom', 'top', " +
                                "'center', 'center_horizontal', 'center_vertical'");
                }
            }
        }
        //Weight
        String weightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Weight);
        if (weightValue != null && !weightValue.equals("")) {
            try {
                ((LinearLayout.LayoutParams) layoutParams).weight = Integer.valueOf(weightValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new XmlPullParserException("the value of weight in each view must be pure integer");
            }
        }
    }

    private void readRelativeLayoutAttribute() throws XmlPullParserException {
        if (!(layoutParams instanceof RelativeLayout.LayoutParams)) return;
    }

    private void readViewGroupAttribute() throws XmlPullParserException {
        //region Margin Parsing
        int marginLeft = 0, marginRight = 0, marginTop = 0, marginBottom = 0;
        String marginValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Margin);
        if (marginValue != null && !(marginValue.equals(""))) {
            marginLeft = Utils.parseDpSize(marginValue, view.getContext());
            marginRight = Utils.parseDpSize(marginValue, view.getContext());
            marginTop = Utils.parseDpSize(marginValue, view.getContext());
            marginBottom = Utils.parseDpSize(marginValue, view.getContext());
            if (marginLeft < 0 || marginRight < 0 || marginTop < 0 || marginBottom < 0)
                throw new XmlPullParserException("The value of margin in view must be pure integer");
        }
        String marginLeftValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MarginLeft);
        if (marginLeftValue != null && !(marginLeftValue.equals(""))) {
            marginLeft = Utils.parseDpSize(marginLeftValue, view.getContext());
            if (marginLeft < 0)
                throw new XmlPullParserException("The value of margin in view must be pure integer");
        }
        String marginRightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MarginRight);
        if (marginRightValue != null && !(marginRightValue.equals(""))) {
            marginRight = Utils.parseDpSize(marginRightValue, view.getContext());
            if (marginRight < 0)
                throw new XmlPullParserException("The value of margin in view must be pure integer");
        }
        String marginBottomValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MarginBottom);
        if (marginBottomValue != null && !(marginBottomValue.equals(""))) {
            marginBottom = Utils.parseDpSize(marginBottomValue, view.getContext());
            if (marginBottom < 0)
                throw new XmlPullParserException("The value of margin in view must be pure integer");
        }
        String marginTopValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MarginTop);
        if (marginTopValue != null && !(marginTopValue.equals(""))) {
            marginTop = Utils.parseDpSize(marginTopValue, view.getContext());
            if (marginTop < 0)
                throw new XmlPullParserException("The value of margin in view must be pure integer");
        }
        if (layoutParams instanceof LinearLayout.LayoutParams)
            ((LinearLayout.LayoutParams) layoutParams).setMargins(marginLeft, marginTop, marginRight, marginBottom);
        else if (layoutParams instanceof RelativeLayout.LayoutParams)
            ((RelativeLayout.LayoutParams) layoutParams).setMargins(marginLeft, marginTop, marginRight, marginBottom);
        else if (layoutParams instanceof ScrollView.LayoutParams)
            ((ScrollView.LayoutParams) layoutParams).setMargins(marginLeft, marginTop, marginRight, marginBottom);
        else if (layoutParams instanceof FrameLayout.LayoutParams)
            ((FrameLayout.LayoutParams) layoutParams).setMargins(marginLeft, marginTop, marginRight, marginBottom);
        //endregion

        //region Padding Parsing
        int paddingLeft = 0, paddingRight = 0, paddingTop = 0, paddingBottom = 0;
        String paddingValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Padding);
        if (paddingValue != null && !(paddingValue.equals(""))) {
            paddingLeft = Utils.parseDpSize(paddingValue, view.getContext());
            paddingRight = Utils.parseDpSize(paddingValue, view.getContext());
            paddingTop = Utils.parseDpSize(paddingValue, view.getContext());
            paddingBottom = Utils.parseDpSize(paddingValue, view.getContext());
            if (paddingLeft < 0 || paddingBottom < 0 || paddingTop < 0 || paddingRight < 0)
                throw new XmlPullParserException("The value of padding in view must be pure integer");
        }
        String paddingLeftValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.PaddingLeft);
        if (paddingLeftValue != null && !(paddingLeftValue.equals(""))) {
            paddingLeft = Utils.parseDpSize(paddingLeftValue, view.getContext());
            if (paddingLeft < 0)
                throw new XmlPullParserException("The value of padding in view must be pure integer");
        }
        String paddingRightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.PaddingRight);
        if (paddingRightValue != null && !(paddingRightValue.equals(""))) {
            paddingRight = Utils.parseDpSize(paddingRightValue, view.getContext());
            if (paddingRight < 0)
                throw new XmlPullParserException("The value of padding in view must be pure integer");
        }
        String paddingBottomValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.PaddingBottom);
        if (paddingBottomValue != null && !(paddingBottomValue.equals(""))) {
            paddingBottom = Utils.parseDpSize(paddingBottomValue, view.getContext());
            if (paddingBottom < 0 )
                throw new XmlPullParserException("The value of padding in view must be pure integer");
        }
        String paddingTopValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.PaddingTop);
        if (paddingTopValue != null && !(paddingTopValue.equals(""))) {
            paddingTop = Utils.parseDpSize(paddingTopValue, view.getContext());
            if (paddingTop < 0)
                throw new XmlPullParserException("The value of padding in view must be pure integer");
        }
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        //endregion

        //region Background
        String background = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Background);
        if (background!=null && !background.equals("")) {
            int resource = Utils.parseColor(background);
            if (resource < 0) {
                resource = Utils.parseResource(background);
                if (resource < 0)
                    throw new XmlPullParserException("The value of background resource in view must be color like #rgb or address resource like @drawable");
                view.setBackgroundResource(resource);
            } else {
                view.setBackgroundColor(resource);
            }

        }
        //endregion

        //region MinWidth and MinHeight
        String minWidthValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MinWidth);
        if (minWidthValue!=null && !minWidthValue.equals("")) {
            int minWidth = Utils.parseDpSize(minWidthValue, view.getContext());
            if (minWidth < 0)
                throw new XmlPullParserException("The value of min width must be dp integer like 24dp");
            view.setMinimumWidth(minWidth);
        }

        String minHeightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MinHeight);
        if (minHeightValue!=null && !minHeightValue.equals("")) {
            int minHeight = Utils.parseDpSize(minHeightValue, view.getContext());
            if (minHeight < 0)
                throw new XmlPullParserException("The value of min width must be dp integer like 24dp");
            view.setMinimumHeight(minHeight);
        }
        //endregion

        //region Tag
        String tag = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Tag);
        if (tag != null && !tag.equals("")) {
            view.setTag(tag);
        }
    }
}
