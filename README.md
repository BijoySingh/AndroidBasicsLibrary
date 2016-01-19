# BasicAndroidProject
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

I am still figuring out how to add this to jcenter, till then please use it this way:
- Download the aar from the aar/ directory.
- Add this into your libs folder and add the following code in your build.gradle
```
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
  compile(name:'starterpack', ext:'aar')
}
```

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
ImageLoader imageLoader = Functions.getImageLoader(context);
ImageAware imageAware = new ImageViewAware(image_view, false);
imageLoader.displayImage(image_link, imageAware);
```

## Some other useful functions
These are some common useful functions. These will expand with time.
```
Functions.makeToast(context, message);
Functions.dpToPixels(context, dp)
```

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

# License
The MIT License (MIT)

Copyright (c) 2015 Bijoy Singh

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
