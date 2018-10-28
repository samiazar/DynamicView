package com.samiazar.library;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.samiazar.library.util.ParentType;
import com.samiazar.library.util.TagView;
import com.samiazar.library.view.XmlButton;
import com.samiazar.library.view.XmlEditText;
import com.samiazar.library.view.XmlFrameLayout;
import com.samiazar.library.view.XmlImageView;
import com.samiazar.library.view.XmlLinearLayout;
import com.samiazar.library.view.XmlRelativeLayout;
import com.samiazar.library.view.XmlScrollView;
import com.samiazar.library.view.XmlTextView;
import com.samiazar.library.view.XmlView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER
 * on 9/4/2018.
 */

@WorkerThread
public class XmlParser {

    private String xml = "";
    private Context context;
    private final String TAG = getClass().getName();

    public XmlParser(String xml, Context context) {
        this.xml = xml;
        this.context = context;
    }

    public List<View> parse() throws IOException, XmlPullParserException {
        List<View> views = new ArrayList<>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));

        int eventType = parser.getEventType();
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                views.add(getLayout(parser, context, ParentType.FRAME_LAYOUT));
            }
            eventType = parser.next();
        }
        return views;
    }

    public static View getLayout(XmlPullParser parser, Context context, ParentType parentType) throws IOException, XmlPullParserException {
        switch (parser.getName()) {
            case TagView.View:
                return new XmlView(context, parser, parentType).getView();
            case TagView.RelativeLayout:
                return new XmlRelativeLayout(context, parser, parentType).getView();
            case TagView.LinearLayout:
                return new XmlLinearLayout(context, parser, parentType).getView();
            case TagView.ScrollView:
                return new XmlScrollView(context, parser, parentType).getView();
            case TagView.FrameLayout:
                return new XmlFrameLayout(context, parser, parentType).getView();
            case TagView.TextView:
                return new XmlTextView(context, parser, parentType).getView();
            case TagView.Button:
                return new XmlButton(context, parser, parentType).getView();
            case TagView.EditText:
                return new XmlEditText(context, parser, parentType).getView();
            case TagView.ImageView:
                return new XmlImageView(context, parser, parentType).getView();
            default:
                return null;
        }
    }


}
