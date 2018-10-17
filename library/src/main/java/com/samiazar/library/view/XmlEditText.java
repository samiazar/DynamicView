package com.samiazar.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class XmlEditText extends XmlTextView {


    public XmlEditText(Context context, XmlPullParser xmlParser, ParentType parentType) {
        super(context, xmlParser, parentType);
        view = new EditText(context);
    }

    @Override
    public View getView() throws IOException, XmlPullParserException {
        initBasicAttribute();
        if (layoutParams == null) return null;
        view.setLayoutParams(layoutParams);

        // add attribute of self view
        xmlParser.require(XmlPullParser.START_TAG, TagKey.NameSpace, TagView.EditText);
        initTextAttribute();

        xmlParser.next();
        //end of parsing this view
        xmlParser.require(XmlPullParser.END_TAG, TagKey.NameSpace, TagView.EditText);
        return view;
    }
}
