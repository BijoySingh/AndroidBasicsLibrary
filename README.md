# Android Basics Library
This library offers some barebone code for android common to most applications.
It provides simple classes and pre-written functions for:
- Internet Access
- SharedPreferences storage and retrieval
- ImagePicker and Bitmap operations
- File read and write
- Recycler View
- Image Downloading
- Some other basic functions like dp2pixel, etc.
- Database support
- Json Parsing
- Marshmallow Permissions Support (coming soon...)

# Installation
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AndroidBasicsLibrary-green.svg?style=true)](https://android-arsenal.com/details/1/3226)

The library is on Jcenter, so usage is really simple. Add the following dependency in your app's ```build.gradle```:
```
dependencies {
    ...
    compile 'com.github.bijoysingh:android-basics:0.8.14'
    ...
}
```
You might need to also include these in case you use the corresponding dependencies
```
dependencies {
    ...
    
    // For Image Downloader
    compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.4'
    
    // For internet access
    compile 'com.mcxiaoke.volley:library:1.0.17'
    
    // For Timestamp utility
    compile 'net.danlew:android.joda:2.8.1'
    
    // For basic features from Google
    compile 'com.android.support:appcompat-v7:23.1.1'
    compile 'com.android.support:support-v4:23.1.1'
    
    // For Recycler View
    compile 'com.android.support:recyclerview-v7:23.1.1'
    
    ...
}
```

## Checkout on Android Arsenal


# Basic Usage
## Internet Access
Extend the ```AccessManager``` class. Use the ```Access``` class to send and get requests by using objects of ```AccessItem```.
```
public class Access extends AccessManager {
....
}
```

Use the class and the built in functions such as ```get``` and ```send```.
```
Access access = new Access(context);
access.get(new AccessItem(link, filename, access_type, is_authenticated));
access.send(new AccessItem(link, filename, access_type, is_authenticated), data);
```

## SharedPreferences storage and retrieval
Extend the ```PreferenceManager``` class.
```
public class Preferences extends PreferenceManager {
    
    ...
    
    @Override
    public String getPreferencesFolder() {
        return "YOUR_PREFERENCE_FOLDER_NAME";
    }
}
```

Use the class and built in functions using ```save``` and ```load```.
```
Preferences preferences = new Preferences(context);
preferences.save(KEY, your_variable);
preferences.load(KEY, your_default_variable);
```

## ImagePicker and Bitmap operations
```
ImageManager imageManager = new ImageManager();
imageManager.showFileChooser(this);
```

Handle the response for this using ```handleResponse``` in ```onActivityResult```
```
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    
    Bitmap bmp = imageManager.handleResponse(requestCode, resultCode, data);
    ...
    
}
```

You can perform a number of Bitmap operations
```
ImageManager.getScaledBitmap(bitmap, scale);
ImageManager.resizeBitmap(bitmap, width, height)
ImageManager.getScaledBitmapWithHeight(bitmap, height);
ImageManager.getScaledBitmapWithWidth(bitmap, width);
```

## File read and write
To store and retreive some text, some basic support code is available. This is needed if you want to save some file/ json you receive from the server to act as cache.
```
FileManager.write(context, filename, text_to_write);
String text_read = FileManager.read(context, filename);
```

## Image Downloading
This library uses the Universal Image Loader library. To use this some basic configuration is pre-built. You can do this as follows
```
ImageLoaderManager.displayImage(context, image_url, image_view);
```
You can also customize the image loader using
```
ImageLoader imageLoader = ImageLoaderManager.getImageLoader(context);
ImageLoader imageLoader = ImageLoaderManager.getImageLoader(context, diskCacheInMB, memoryCacheInMB);
```
and use it as follows:
```
ImageAware imageAware = new ImageViewAware(image_view, false);
imageLoader.displayImage(image_link, imageAware);
```

## Some other useful functions
These are some common useful functions. These will expand with time.
```
Functions.makeToast(context, message);
Functions.dpToPixels(context, dp)
```

## Basic Android Lint Error Fixes
```
LocaleManager.toString(Character/Float/Double/Integer/Boolean variable)
```
This function will convert your variable to the String to these using the Locale. This functions is a wrapper around the code ```String.format```. The function will prevent Lint Warning for the same.

```
ResourceManager.getColor(Context context, Integer colorId)
```
This was previously meant to handle version support. Now it is simply a wrapper around ContextCompat.getColor();

## Recycler View
Using Recycler View cannot be easier!

Extend the Recycler View Holder
```
public class YourViewHolder extends RVHolder<YourItem> {
    
    ...
    
    @Override
    public void populate(final YourItem data) {
        // Populate your view. You can set on click listeners etc.
    }
}
```

Extend the Recycler View Adapter
```
public class YourAdapter extends RVAdapter<YourItem, YourViewHolder> {
    ...    
}
```

Setup your recycler view
```
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    recyclerView.setHasFixedSize(false);
    
    layoutManager = new LinearLayoutManager(context);
    recyclerView.setLayoutManager(layoutManager);

    YourAdapter adapter = new AppAdapter(context, R.layout.your_layout_item);
    recyclerView.setAdapter(adapter);
```

A full fledged example can be seen in my [TutorialApp](https://github.com/BijoySingh/TutorialApp).

## Database Support
Adding database setup is super simple. You have to do very little work!

Just add a simple model like
```
public class YourDatabaseItem extends DatabaseModel {
    @DBColumn(primaryKey = true, autoIncrement = true)
    public Integer id;

    @DBColumn
    public String title;

    @DBColumn
    public String description;
}
```

Using ```@DBColumn``` you can add custom arguments like
```
fieldType = DBColumn.Type.INTEGER
unique = True
primaryKey = true, autoIncrement = true
fieldName = "custom_field_name"
```

You can create a custom class for your databases, or you can simply use the default database:
```
DatabaseManager db = new DatabaseManager(this, new DatabaseModel[]{new YourDatabaseItem()});

// To add an item
YourDatabaseItem your_item = ...;
db.add(your_item);

// To get all items for a custom class
List<YourDatabaseItem> items = db.get(YourDatabaseItem.class);
```
A full fledged example can be seen in my [TutorialApp](https://github.com/BijoySingh/TutorialApp).

## JSON Parsing

Making a JSON Parser is simple to work
```
public class YourItem extends JsonModel { 
    @JsonField
    public Integer integer_field;
    
    @JsonField(field = "alternate_json_field_name") 
    public String string_field;
    
    @JsonField
    public Double real_field;
    
    @JsonField(field = Type.BOOLEAN)
    public Boolean boolean_field;
    
    @JsonField(field = Type.JSON)
    public JSONObject json_field;
}
```

The method automatically detects the type of the JSON field, you can still choose to override it.
Further, it assumes that the name of the field is the JSON field, you can yet again choose to override it

You can also, quickly serialize your item into a JSON Object
```
JSONObject json = item.serialize();
```

## License
```
The MIT License (MIT)

Copyright (c) 2016 Bijoy Singh Kochar

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
