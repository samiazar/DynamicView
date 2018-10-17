package com.samiazar.library.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.samiazar.library.XmlParser;
import com.samiazar.library.util.ParentType;
import com.samiazar.library.util.TagKey;
import com.samiazar.library.util.TagView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by USER
 * on 10/13/2018.
 */

public class XmlScrollView extends XmlView {


    public XmlScrollView(Context context, XmlPullParser xmlParser, ParentType parentType) {
        super(context, xmlParser, parentType);
        view = new ScrollView(context);
    }

    @Override
    public View getView() throws IOException, XmlPullParserException {
        initBasicAttribute();
        if (layoutParams == null) return null;
        view.setLayoutParams(layoutParams);

        // add attribute of self view
        xmlParser.require(XmlPullParser.START_TAG, TagKey.NameSpace, TagView.ScrollView);

        // add children of view
        while (xmlParser.next() != XmlPullParser.END_TAG) {
            if (xmlParser.getEventType() != XmlPullParser.START_TAG) continue;
            ((ScrollView) view).addView(XmlParser.getLayout(xmlParser, view.getContext(), ParentType.SCROLL_VIEW));
        }

        //end of parsing this view
        xmlParser.require(XmlPullParser.END_TAG, TagKey.NameSpace, TagView.ScrollView);
        return view;
    }
}
