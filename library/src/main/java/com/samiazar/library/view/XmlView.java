package com.samiazar.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.samiazar.library.util.ImageDownloader;
import com.samiazar.library.util.ParentType;
import com.samiazar.library.util.TagKey;
import com.samiazar.library.util.TagValue;
import com.samiazar.library.util.ParsingUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.ref.WeakReference;

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
        readId();
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

    private void readId() throws XmlPullParserException {
        String idValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Id);
        if (idValue != null && !idValue.equals("")) {
            int id = ParsingUtil.parseId(idValue);
            if (id < 0 ) throw new XmlPullParserException("make sure define id for views in right way");
            view.setId(id);
        }
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
                width = ParsingUtil.parseDpSize(widthValue, view.getContext());
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
                height = ParsingUtil.parseDpSize(heightValue, view.getContext());
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

        //region LayoutGravity
        String gravityValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.LayoutGravity);
        if (gravityValue != null && !gravityValue.equals("")) {
            String[] gravityList = gravityValue.split("\\|");
            int gravity = 0;
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
            ((LinearLayout.LayoutParams) layoutParams).gravity = gravity;
        }
        //endregion

        //region Weight
        String weightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Weight);
        if (weightValue != null && !weightValue.equals("")) {
            try {
                ((LinearLayout.LayoutParams) layoutParams).weight = Integer.valueOf(weightValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new XmlPullParserException("the value of weight in each view must be pure integer");
            }
        }
        //endregion
    }

    private void readRelativeLayoutAttribute() throws XmlPullParserException {
        if (!(layoutParams instanceof RelativeLayout.LayoutParams)) return;

        String aboveValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Above);
        if (aboveValue!=null && !aboveValue.equals("")) {
            int targetViewId = ParsingUtil.parseId(aboveValue);
            if (targetViewId < 0) throw new XmlPullParserException("make sure define id of target view for above attribute is right");
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ABOVE, targetViewId);
        }

        String belowValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Below);
        if (belowValue!=null && !belowValue.equals("")) {
            int targetViewId = ParsingUtil.parseId(belowValue);
            if (targetViewId < 0) throw new XmlPullParserException("make sure define id of target view for below attribute is right");
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.BELOW, targetViewId);
        }

        String rightOfValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.ToRightOf);
        if (rightOfValue!=null && !rightOfValue.equals("")) {
            int targetViewId = ParsingUtil.parseId(rightOfValue);
            if (targetViewId < 0) throw new XmlPullParserException("make sure define id of target view for toRightOf attribute is right");
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.RIGHT_OF, targetViewId);
        }

        String leftOfValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.ToLeftOf);
        if (leftOfValue!=null && !leftOfValue.equals("")) {
            int targetViewId = ParsingUtil.parseId(leftOfValue);
            if (targetViewId < 0) throw new XmlPullParserException("make sure define id of target view for toLeftOf attribute is right");
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.LEFT_OF, targetViewId);
        }

        String alignTopValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AlignTop);
        if (alignTopValue!=null && !alignTopValue.equals("")) {
            int targetViewId = ParsingUtil.parseId(alignTopValue);
            if (targetViewId < 0) throw new XmlPullParserException("make sure define id of target view for alignTop attribute is right");
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_TOP, targetViewId);
        }

        String alignBottomValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AlignBttom);
        if (alignBottomValue!=null && !alignBottomValue.equals("")) {
            int targetViewId = ParsingUtil.parseId(alignBottomValue);
            if (targetViewId < 0) throw new XmlPullParserException("make sure define id of target view for alignBottom attribute is right");
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_BOTTOM, targetViewId);
        }

        String alignBaselineValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AlignBaseline);
        if (alignBaselineValue!=null && !alignBaselineValue.equals("")) {
            int targetViewId = ParsingUtil.parseId(alignBaselineValue);
            if (targetViewId < 0) throw new XmlPullParserException("make sure define id of target view for alignBaseline attribute is right");
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_BASELINE, targetViewId);
        }

        String alignLeftValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AlignLeft);
        if (alignLeftValue!=null && !alignLeftValue.equals("")) {
            int targetViewId = ParsingUtil.parseId(alignLeftValue);
            if (targetViewId < 0) throw new XmlPullParserException("make sure define id of target view for alignLeft attribute is right");
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_LEFT, targetViewId);
        }

        String alignRightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AlignRight);
        if (alignRightValue!=null && !alignRightValue.equals("")) {
            int targetViewId = ParsingUtil.parseId(alignRightValue);
            if (targetViewId < 0) throw new XmlPullParserException("make sure define id of target view for alignRight attribute is right");
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_RIGHT, targetViewId);
        }

        String alignParentTopValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AlignParentTop);
        if (alignParentTopValue!=null && !alignParentTopValue.equals("")) {
            try {
                boolean alignParentTop = Boolean.valueOf(alignParentTopValue);
                if (alignParentTop)
                    ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_PARENT_TOP);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("make sure define boolean for alignParentTop attribute is right");
            }
        }

        String alignParentBottomValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AlignParentBottom);
        if (alignParentBottomValue!=null && !alignParentBottomValue.equals("")) {
            try {
                boolean alignParentBottom = Boolean.valueOf(alignParentBottomValue);
                if (alignParentBottom)
                    ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("make sure define boolean for alignParentBottom attribute is right");
            }
        }

        String alignParentRightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AlignParentRight);
        if (alignParentRightValue!=null && !alignParentRightValue.equals("")) {
            try {
                boolean alignParentRight = Boolean.valueOf(alignParentRightValue);
                if (alignParentRight)
                    ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("make sure define boolean for alignParentRight attribute is right");
            }
        }

        String alignParentLeftValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.AlignParentLeft);
        if (alignParentLeftValue!=null && !alignParentLeftValue.equals("")) {
            try {
                boolean alignParentLeft = Boolean.valueOf(alignParentLeftValue);
                if (alignParentLeft)
                    ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("make sure define boolean for alignParentLeft attribute is right");
            }
        }

        String centerInParentValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.CenterInParent);
        if (centerInParentValue!=null && !centerInParentValue.equals("")) {
            try {
                boolean centerInParent = Boolean.valueOf(centerInParentValue);
                if (centerInParent)
                    ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("make sure define boolean for centerInParent attribute is right");
            }
        }

        String centerHorizontalValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.CenterHorizontal);
        if (centerHorizontalValue!=null && !centerHorizontalValue.equals("")) {
            try {
                boolean centerHorizontal = Boolean.valueOf(centerHorizontalValue);
                if (centerHorizontal)
                    ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("make sure define boolean for centerHorizontal attribute is right");
            }
        }

        String centerVerticalValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.CenterVertical);
        if (centerVerticalValue!=null && !centerVerticalValue.equals("")) {
            try {
                boolean centerVertical = Boolean.valueOf(centerVerticalValue);
                if (centerVertical)
                    ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException("make sure define boolean for centerVertical attribute is right");
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void readViewGroupAttribute() throws XmlPullParserException {
        //region Margin Parsing
        int marginLeft = 0, marginRight = 0, marginTop = 0, marginBottom = 0;
        String marginValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Margin);
        if (marginValue != null && !(marginValue.equals(""))) {
            marginLeft = ParsingUtil.parseDpSize(marginValue, view.getContext());
            marginRight = ParsingUtil.parseDpSize(marginValue, view.getContext());
            marginTop = ParsingUtil.parseDpSize(marginValue, view.getContext());
            marginBottom = ParsingUtil.parseDpSize(marginValue, view.getContext());
            if (marginLeft < 0 || marginRight < 0 || marginTop < 0 || marginBottom < 0)
                throw new XmlPullParserException("The value of margin in view must be pure integer");
        }
        String marginLeftValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MarginLeft);
        if (marginLeftValue != null && !(marginLeftValue.equals(""))) {
            marginLeft = ParsingUtil.parseDpSize(marginLeftValue, view.getContext());
            if (marginLeft < 0)
                throw new XmlPullParserException("The value of margin in view must be pure integer");
        }
        String marginRightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MarginRight);
        if (marginRightValue != null && !(marginRightValue.equals(""))) {
            marginRight = ParsingUtil.parseDpSize(marginRightValue, view.getContext());
            if (marginRight < 0)
                throw new XmlPullParserException("The value of margin in view must be pure integer");
        }
        String marginBottomValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MarginBottom);
        if (marginBottomValue != null && !(marginBottomValue.equals(""))) {
            marginBottom = ParsingUtil.parseDpSize(marginBottomValue, view.getContext());
            if (marginBottom < 0)
                throw new XmlPullParserException("The value of margin in view must be pure integer");
        }
        String marginTopValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MarginTop);
        if (marginTopValue != null && !(marginTopValue.equals(""))) {
            marginTop = ParsingUtil.parseDpSize(marginTopValue, view.getContext());
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
            paddingLeft = ParsingUtil.parseDpSize(paddingValue, view.getContext());
            paddingRight = ParsingUtil.parseDpSize(paddingValue, view.getContext());
            paddingTop = ParsingUtil.parseDpSize(paddingValue, view.getContext());
            paddingBottom = ParsingUtil.parseDpSize(paddingValue, view.getContext());
            if (paddingLeft < 0 || paddingBottom < 0 || paddingTop < 0 || paddingRight < 0)
                throw new XmlPullParserException("The value of padding in view must be pure integer");
        }
        String paddingLeftValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.PaddingLeft);
        if (paddingLeftValue != null && !(paddingLeftValue.equals(""))) {
            paddingLeft = ParsingUtil.parseDpSize(paddingLeftValue, view.getContext());
            if (paddingLeft < 0)
                throw new XmlPullParserException("The value of padding in view must be pure integer");
        }
        String paddingRightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.PaddingRight);
        if (paddingRightValue != null && !(paddingRightValue.equals(""))) {
            paddingRight = ParsingUtil.parseDpSize(paddingRightValue, view.getContext());
            if (paddingRight < 0)
                throw new XmlPullParserException("The value of padding in view must be pure integer");
        }
        String paddingBottomValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.PaddingBottom);
        if (paddingBottomValue != null && !(paddingBottomValue.equals(""))) {
            paddingBottom = ParsingUtil.parseDpSize(paddingBottomValue, view.getContext());
            if (paddingBottom < 0 )
                throw new XmlPullParserException("The value of padding in view must be pure integer");
        }
        String paddingTopValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.PaddingTop);
        if (paddingTopValue != null && !(paddingTopValue.equals(""))) {
            paddingTop = ParsingUtil.parseDpSize(paddingTopValue, view.getContext());
            if (paddingTop < 0)
                throw new XmlPullParserException("The value of padding in view must be pure integer");
        }
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        //endregion

        //region Background
        String background = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Background);
        if (background!=null && !background.equals("")) {
            String link = ParsingUtil.parseLink(background);
            if (!link.equals("")) {
                new ImageDownloader(view) {
                    @Override
                    public void onLoadSource(WeakReference<View> viewWeakReference, Bitmap bitmap) {
                        viewWeakReference.get().setBackground(new BitmapDrawable(viewWeakReference.get().getContext().getResources(), bitmap));
                    }
                }.execute(link);
            } else {
                int resource = ParsingUtil.parseColor(background);
                if (resource == 0) {
                    resource = ParsingUtil.parseResource(background, view.getContext());
                    if (resource == 0)
                        throw new XmlPullParserException("The value of background resource in view must be color like #rgb or address resource like @drawable");
                    view.setBackgroundResource(resource);
                } else {
                    view.setBackgroundColor(resource);
                }
            }
        }
        //endregion

        //region Foreground
        String foreground = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.Foreground);
        if (foreground!=null && !foreground.equals("") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String link = ParsingUtil.parseLink(foreground);
            if (!link.equals("")) {
                new ImageDownloader(view) {
                    @Override
                    public void onLoadSource(WeakReference<View> viewWeakReference, Bitmap bitmap) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            viewWeakReference.get().setForeground(new BitmapDrawable(viewWeakReference.get().getContext().getResources(), bitmap));
                        }
                    }
                }.execute(link);
            } else {
                int resource = ParsingUtil.parseColor(foreground);
                if (resource == 0) {
                    resource = ParsingUtil.parseResource(foreground, view.getContext());
                    if (resource == 0)
                        throw new XmlPullParserException("The value of foreground resource in view must be color like #rgb or address resource like @drawable");
                    view.setForeground(ContextCompat.getDrawable(view.getContext(), resource));
                } else {
                    view.setForeground(new ColorDrawable(resource));
                }
            }
        }
        //endregion

        //region MinWidth and MinHeight
        String minWidthValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MinWidth);
        if (minWidthValue!=null && !minWidthValue.equals("")) {
            int minWidth = ParsingUtil.parseDpSize(minWidthValue, view.getContext());
            if (minWidth < 0)
                throw new XmlPullParserException("The value of min width must be dp integer like 24dp");
            view.setMinimumWidth(minWidth);
        }

        String minHeightValue = xmlParser.getAttributeValue(TagKey.AndroidNameSpace, TagKey.MinHeight);
        if (minHeightValue!=null && !minHeightValue.equals("")) {
            int minHeight = ParsingUtil.parseDpSize(minHeightValue, view.getContext());
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
        //endregion
    }
}
