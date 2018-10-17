package com.samiazar.library.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class XmlButton extends XmlTextView {


    public XmlButton(Context context, XmlPullParser xmlParser, ParentType parentType) {
        super(context, xmlParser, parentType);
        view = new Button(context);
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
}
