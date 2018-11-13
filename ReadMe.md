## Dynamic View
This library helps you to implement an activity page with string XML. Assume an activity that can be changing to whatever you want just in runtime. For example, you can call an API to the server and get new string then with the utility of this library you can create an android page with the server response.

**index**

 - [First step](https://github.com/samiazar/DynamicView#first-step)
 - [View](https://github.com/samiazar/DynamicView#view)
 - [RelativeLayout](https://github.com/samiazar/DynamicView#relativelayout)
 - [LinearLayout](https://github.com/samiazar/DynamicView#linearlayout)
 - [FrameLayout ScrollView](https://github.com/samiazar/DynamicView#FrameLayout-ScrollView)
 - [TextView (EditText, Button)](https://github.com/samiazar/DynamicView#TextView-%28EditText,-Button%29)
 - [ImageView](https://github.com/samiazar/DynamicView#imageview)
 - [onClick](https://github.com/samiazar/DynamicView#onclick)
## First step
First of all you must add [jar](https://github.com/samiazar/DynamicView/blob/master/library/dynamicView-library.jar) file into project (sorry I have not enough time to adding the project into mavenRepository).
 In activity that you want shows custom xml you must add a ViewGroup in root and then do like this:
 
    try {  
        List<View> views = new XmlParser(xmlString, context).parse();  
        if (views != null)  // if XML, not parseable the views can be null
          for (View view : views)  
             layout.addView(view);
    } catch (IOException e) {  
        e.printStackTrace();  
    } catch (XmlPullParserException e) {  
        e.printStackTrace(); //this section occurs when a part of XML is wrong
    } 
the class of XmlParser get a string that must be valid XML and context then *parse()* method do all the works.
## View
the list of tags in any View that you can be used and library parse that correctly.
 - (**required**) android:layout_width = [wrap_content / match_parent/ (int)dp]
 - (**required**) android:layout_height = [wrap_content / match_parent/ (int)dp] 
 - android:id = [a string that start with @+/id]
 - android:layout_margin = [(int)dp]
 - android:layout_marginLeft = [(int)dp]
 - android:layout_marginRight = [(int)dp]
 - android:layout_marginBottom = [(int)dp]
 - android:layout_marginTop = [(int)dp]
 - android:padding = [(int)dp]
 - android:paddingLeft = [(int)dp]
 - android:paddingRight = [(int)dp]
 - android:paddingBottom = [(int)dp]
 - android:paddingTop = [(int)dp]
 - android:background = [weblink of image / HexColor(#RRGGBB) / resource(@drawable/)]
 - android:foreground = [weblink of image / HexColor(#RRGGBB) / resource(@drawable/)]
 - android:minWidth = [(int)dp]
 - android:minHeight = [(int)dp]
 - android:tag = [String]

 If parent of view is LinearLayout you can use these tags too.
 
 - android:layout_gravity = [right / left / bottom / top/ center / center_horizontal / center_vertical]
 - android:layout_weight = [Integer]
 
 If parent of view is RelativeLayout you can use these tags too.
 
 - android:layout_alignBaseline = [a string that start with @+id/ or @id/]
- android:layout_alignBottom = [a string that start with @+id/ or @id/]
- android:layout_alignLeft = [a string that start with @+id/ or @id/]
- android:layout_alignRight = [a string that start with @+id/ or @id/]
- android:layout_alignTop = [a string that start with @+id/ or @id/]
- android:layout_above = [a string that start with @+id/ or @id/]
- android:layout_below = [a string that start with @+id/ or @id/]
- android:layout_toLeftOf = [a string that start with @+id/ or @id/]
- android:layout_toRightOf = [a string that start with @+id/ or @id/]
- android:layout_alignParentBottom = [boolean]
- android:layout_alignParentLeft = [boolean]
- android:layout_alignParentRight = [boolean]
- android:layout_alignParentTop = [boolean]
- android:layout_centerHorizontal = [boolean]
- android:layout_centerInParent = [boolean]
- android:layout_centerVertical = [boolean]

## RelativeLayout
Everything you need to use in RelativeLayout tag availabel in [View](https://github.com/samiazar/DynamicView#view) section.
## LinearLayout
the list of tags in LinearLayout that you can be used and library parse that correctly.

 - android:gravity = [right / left / bottom / top/ center / center_horizontal / center_vertical]
 - android:orientation = [ vertical / horizontal]
 - android:weightSum = [Float]

also other tags that you need to use in LinearLayout tag availabel in [View](https://github.com/samiazar/DynamicView#view) section.
## FrameLayout ScrollView
Everything you need to use in FrameLayout and ScrollView availabel in [View](https://github.com/samiazar/DynamicView#view) section.
## TextView (EditText, Button)
the list of tags in TextView and other child classes like editText and Button that you can be used and library parse that correctly.

 - android:text = [String]
 - android:hint = [String]
 - android:textColor = [Hex Color (#RRGGBB)]
 - android:textColorHint = [Hex Color (#RRGGBB)]
 - android:textSize = [(int)dp]
 - android:textStyle = [bold / normal / italic]
 - android:maxLines = [Integer]
 - android:inputType = [text / phone / number / date / textPassword / time / 
 - android:gravity = [right / left / bottom / top/ center / center_horizontal / center_vertical]
 - android:ellipsize = [end / start / middle / marquee / none]
 - android:cursorVisible = [Boolean[
 - android:textAllCaps = [Boolean[
 - android:autoLink = [all / map / web / phone / email / none]

also other tags that you need to use in TextView tag availabel in [View](https://github.com/samiazar/DynamicView#view) section.
## ImageView

the list of tags in ImageView that you can be used and library parse that correctly.

 - android:src = [weblink of image / HexColor(#RRGGBB) / resource(@drawable/)]
 - android:adjustViewBounds = [Boolean]
 - android:cropToPadding = [Boolean]
 - android:scaleType = [center / centerCrop / fitXY / centerInside / fitCenter / fitEnd / fitStart / matrix]
 - android:tint = [Hex Color (#RRGGBB)]
 - android:tintMode = [ add/ multiply/ screen/ src_atop/ src_in / src_over]

also other tags that you need to use in ImageView tag availabel in [View](https://github.com/samiazar/DynamicView#view) section.

## onClick
In each view you can define onClick event.

if you want to on perform click goes to another activity you must define "onClick" tag and "activity" tag as below: 
- android:onClick = [StartActivity]
- android:activity = [(the complete name of activity with package name)]

or if you want to on perfom click call an API as a fire and forget you must define "onClick" tag and "url" tag and "method" tag and "body" tag and also "header" tag as below:
- android:onClick = [CallApi]
- android:url = [(the url of api)]
- android:method = [GET/POST/DELETE/PUT]
- android:body = [(a json text that you want put into body of call)]
- android:header = [(a json text that each key is header key and each value is header value)]

and if you want to on perfon click call an API and get new XML to show to user, You must define "onClick" tag and "url" tagas below (remember the response of api must be pure xml):

- android:onClick = [NewXMl]
- android:url = [(the url of api)]

