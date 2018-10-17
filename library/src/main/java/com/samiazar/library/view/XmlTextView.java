package com.samiazar.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.samiazar.library.XmlParser;
import com.samiazar.library.util.ParentType;
import com.samiazar.library.util.TagKey;
import com.samiazar.library.util.TagValue;
import com.samiazar.library.util.TagView;
import com.samiazar.library.util.Utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by USER
 * on 10/13/2018.
 */

public class XmlTextView extends XmlView {


    public XmlTextView(Context context, XmlPullParser xmlParser, ParentType parentType) {
        super(context, xmlParser, parentType);
        view = new TextView(context);
    }

    @Override
    public View getView() throws IOException, XmlPullParserException {
        initBasicAttribute();
        if (layoutParams == null) return null;
        view.setLayoutParams(layoutParams);

        // add attribute of self view
        initTextAttribute();

        xmlParser.next();
        //end of parsing this view
        return view;
    }

    void initTextAttribute() throws XmlPullParserException {
        readAutoLinkAttribute();
        readCapitalizeAttribute();
        readCursorVisibleAttribute();
        readEllipsizeAttribute();
        readGravityAttribute();
        readInputTypeAttribute();
        readMaxLinesAttribute();
        readTextAttribute();
    }

    private void readAutoLinkAttribute() throws XmlPullParserException {
        String autoLink = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AutoLink);
        if (autoLink != null && !autoLink.equals("")) {
            switch (autoLink) {
                case TagValue.All:
                    Linkify.addLinks(((TextView) view), Linkify.ALL);
                    break;
                case TagValue.Map:
                    Linkify.addLinks(((TextView) view), Linkify.MAP_ADDRESSES);
                    break;
                case TagValue.Web:
                    Linkify.addLinks(((TextView) view), Linkify.WEB_URLS);
                    break;
                case TagValue.Phone:
                    Linkify.addLinks(((TextView) view), Linkify.PHONE_NUMBERS);
                    break;
                case TagValue.Email:
                    Linkify.addLinks(((TextView) view), Linkify.EMAIL_ADDRESSES);
                    break;
                case TagValue.None:
                    return;
                default:
                    throw new XmlPullParserException("the attribute autoLink not correct");
            }
            ((TextView) view).setLinksClickable(true);
        }
    }

    private void readCapitalizeAttribute() throws XmlPullParserException {
        String allCapsValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.TextAllCaps);
        if (allCapsValue != null && !allCapsValue.equals("")) {
            try {
                boolean allCaps = Boolean.valueOf(allCapsValue );
                ((TextView) view).setAllCaps(allCaps);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("the value of textAllCaps must be boolean");
            }
        }
    }

    private void readCursorVisibleAttribute() throws XmlPullParserException {
        String cursorVisibleValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.CursorVisible);
        if (cursorVisibleValue != null && !cursorVisibleValue.equals("")) {
            try {
                boolean cursorVisible = Boolean.valueOf(cursorVisibleValue);
                ((TextView) view).setCursorVisible(cursorVisible);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("the value of cursorVisible must be boolean");
            }
        }
    }

    private void readEllipsizeAttribute() throws XmlPullParserException {
        String ellipsizeValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Ellipsize);
        if (ellipsizeValue != null && !ellipsizeValue.equals("")) {
            switch (ellipsizeValue) {
                case TagValue.End:
                    ((TextView) view).setEllipsize(TextUtils.TruncateAt.END);
                    break;
                case TagValue.Start:
                    ((TextView) view).setEllipsize(TextUtils.TruncateAt.START);
                    break;
                case TagValue.Middle:
                    ((TextView) view).setEllipsize(TextUtils.TruncateAt.MIDDLE);
                    break;
                case TagValue.Marquee:
                    ((TextView) view).setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    break;
                case TagValue.None:
                    break;
                default:
                    throw new XmlPullParserException("the value of ellipsize must be one of the end, start, marquee, middle, none");
            }
        }
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
                        break;
                    case TagValue.Left:
                        gravity += Gravity.LEFT;
                        break;
                    case TagValue.Bottom:
                        gravity += Gravity.BOTTOM;
                        break;
                    case TagValue.Top:
                        gravity += Gravity.TOP;
                        break;
                    case TagValue.Center:
                        gravity += Gravity.CENTER;
                        break;
                    case TagValue.CenterHorizontal:
                        gravity += Gravity.CENTER_HORIZONTAL;
                        break;
                    case TagValue.CenterVertical:
                        gravity += Gravity.CENTER_VERTICAL;
                        break;
                    default:
                        throw new XmlPullParserException("The value of gravity in each view must be one of the 'right', 'left', 'bottom', 'top', " +
                                "'center', 'center_horizontal', 'center_vertical'");
                }
            }
            ((TextView) view).setGravity(gravity);
        }
    }

    private void readInputTypeAttribute() throws XmlPullParserException {
        String inputTypeValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.InputType);
        if (inputTypeValue != null && !inputTypeValue.equals("")) {
            switch (inputTypeValue) {
                case TagValue.Text:
                    ((TextView) view).setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case TagValue.Phone:
                    ((TextView) view).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
                    break;
                case TagValue.TextPassword:
                    ((TextView) view).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
                case TagValue.Date:
                    ((TextView) view).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_DATETIME_VARIATION_DATE);
                    break;
                case TagValue.Time:
                    ((TextView) view).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_DATETIME_VARIATION_TIME);
                    break;
                case TagValue.Number:
                    ((TextView) view).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    break;
                    default:
                        throw new XmlPullParserException("the value of InputType must be one of the text, phone, password, date, time, number");
            }
        }
    }

    private void readMaxLinesAttribute() throws XmlPullParserException {
        String maxLineValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MaxLines);
        if (maxLineValue != null && !maxLineValue.equals("")) {
            try {
                int maxLines = Integer.valueOf(maxLineValue);
                ((TextView) view).setMaxLines(maxLines);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new XmlPullParserException("the value of maxLines must be pure Integer");
            }
        }
    }

    private void readTextAttribute() throws XmlPullParserException {
        String text = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Text);
        if (text != null && !text.equals("")) {
            ((TextView) view).setText(text);
        }

        String hint = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Hint);
        if (hint != null && !hint.equals("")) {
            ((TextView) view).setHint(hint);
        }

        String textColorValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.TextColor);
        if (textColorValue != null && !textColorValue.equals("")) {
            int color = Utils.parseColor(textColorValue);
            if (color == 0)
                throw new XmlPullParserException("The attribute text color define in wrong way");
            ((TextView) view).setTextColor(color);
        }

        String textHintColorValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.TextColorHint);
        if (textHintColorValue != null && !textHintColorValue.equals("")) {
            int color = Utils.parseColor(textHintColorValue);
            if (color == 0)
                throw new XmlPullParserException("The attribute text color define in wrong way");
            ((TextView) view).setHintTextColor(color);
        }

        String textSizeValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.TextSize);
        if (textSizeValue != null && !textSizeValue.equals("")) {
            int textSize = Utils.parseDpSize(textSizeValue, view.getContext());
            if (textSize < 0)
                throw new XmlPullParserException("the text size must be dp size");
            ((TextView) view).setTextSize(textSize);
        }

        String textStyleValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.TextStyle);
        if (textStyleValue != null && !textStyleValue.equals("")) {
            switch (textStyleValue) {
                case TagValue.Bold:
                    ((TextView) view).setTypeface(((TextView) view).getTypeface(), Typeface.BOLD);
                    break;
                case TagValue.Normal:
                    ((TextView) view).setTypeface(((TextView) view).getTypeface(), Typeface.NORMAL);
                    break;
                case TagValue.Italic:
                    ((TextView) view).setTypeface(((TextView) view).getTypeface(), Typeface.ITALIC);
                    break;
                default:
                    throw new XmlPullParserException("the text style must be one of the italic, bold, normal");
            }
        }
    }


}
