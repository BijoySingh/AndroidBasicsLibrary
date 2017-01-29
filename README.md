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
- Marshmallow Permissions Support

# Installation
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AndroidBasicsLibrary-green.svg?style=true)](https://android-arsenal.com/details/1/3226)

The library is on Jcenter, so usage is really simple. Add the following dependency in your app's ```build.gradle```:
```groovy
dependencies {
    ...
    compile 'com.github.bijoysingh:android-basics:0.10.14'
    ...
}
```
You might need to also include these in case you use the corresponding dependencies
```groovy
dependencies {
    ...
    
    // For Image Downloader
    compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.4'
    
    // For internet access
    compile 'com.mcxiaoke.volley:library:1.0.17'
    
    // For Timestamp utility
    compile 'net.danlew:android.joda:2.8.1'
    
    // For basic features from Google
    compile 'com.android.support:appcompat-v7:25.0.0'
    compile 'com.android.support:support-v4:25.0.0'
    
    // For Recycler View
    compile 'com.android.support:recyclerview-v7:25.0.0'
    
    ...
}
```

## Checkout on Android Arsenal


# Basic Usage
## Internet Access
Internet access is simpler than ever. 
I have added a simple `DefaultQueryExecutor` class for convenient usage.
```java
DefaultQueryExecutor executor = new DefaultQueryExecutor.Builder(context)
    .setOnQueryListener(queryListener) // optional
    .setAuthenticationProvider(authenticationProvider) // optional
    .setTimeout(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS) // optional
    .setMaxRetries() // optional
    .setRetryBackoffMultiplier() // optional
    .build();
```
You can create a `OnQueryListener` object or have the activity making the request `implement` it.

```java
QueryParams query = QueryParams.Builder(url)
    .setCache(cacheFilename) // optional: the filename you want to save the result to as a cache
    .setQueryIdentifier(queryId) // optional
    .setAuthenticated(isAuthenticated) // optional: tells if you want to use the genAuthenticationData method of the AuthenticationProvider
    .setMethod(Request.Method.POST) // optional
    .setExtra(extraHashMap) // optional
    .addExtra(key, value) // optional
    .build();
```

Making a query is really simple too
```java
executor.get(query);
executor.send(query, data);
```

Extend the ```QueryExecutor``` class for more control on your queries and for supporting more things.
```java
public class MyQeuryExecutor extends MyQeuryExecutor {
....
}
```


## SharedPreferences storage and retrieval

Use the class and built in functions using ```save``` and ```load```.
```java
Preferences preferences = new Preferences(context);
preferences.save(KEY, your_variable);
preferences.load(KEY, your_default_variable);
```

*Optionally* Extend the ```PreferenceManager``` class.
```java
public class Preferences extends PreferenceManager {
    
    ...
    
    @Override
    public String getPreferencesFolder() {
        return "YOUR_PREFERENCE_FOLDER_NAME";
    }
}
```

### NOTE: 
If you plan to use SharedPreferences in Services due to recent changes in Android SharedPreferences this may not be your best option.
You can goolde for a solution of use the library 'net.grandcentrix.tray:tray:0.10.0'` I have personally felt it to be really good
and has a similar pattern as my library


## ImagePicker and Bitmap operations
```java
ImageManager imageManager = new ImageManager();
imageManager.showFileChooser(this);
```

Handle the response for this using ```handleResponse``` in ```onActivityResult```
```java
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    
    Bitmap bmp = imageManager.handleResponse(requestCode, resultCode, data);
    ...
    
}
```

You can perform a number of Bitmap operations
```java
ImageManager.getScaledBitmap(bitmap, scale);
ImageManager.resizeBitmap(bitmap, width, height)
ImageManager.getScaledBitmapWithHeight(bitmap, height);
ImageManager.getScaledBitmapWithWidth(bitmap, width);
```

## File read and write
To store and retreive some text, some basic support code is available. This is needed if you want to save some file/ json you receive from the server to act as cache.
```java
FileManager.write(context, filename, text_to_write);
String textRead = FileManager.read(context, filename);
```

Asynchronous write is now supported (I will be updated the way to access these functions to ease usage)
```java
FileManager.writeAsync(context, filename, text_to_write);
```

## Image Downloading
This library uses the Universal Image Loader library. To use this some basic configuration is pre-built. You can do this as follows
```java
ImageLoaderManager.displayImage(context, image_url, image_view);
```
or
```java
ImageLoaderManager loader = new ImageLoaderManager(context);
loader.displayImage(image_url, image_view);
```

You can also customize the image loader using
```java
ImageLoader imageLoader = ImageLoaderManager.getImageLoader(context);
ImageLoader imageLoader = ImageLoaderManager.getImageLoader(context, diskCacheInMB, memoryCacheInMB);
```
and use it as follows:
```java
ImageAware imageAware = new ImageViewAware(image_view, false);
imageLoader.displayImage(image_link, imageAware);
```

## Some other useful functions
These are some common useful functions. These will expand with time.
```java
DimensionManager.dpToPixels(context, dp);
DimensionManager.pixelsToDp(context, pixels);
```

## Toasts
I know toasts are pretty simple to use in Android. But I personally got pissed with typing the Toast.LENGTH_SHORT and a show() everytime.
So I built a simple wrapper around it to reduce this boilerplate code.
Using a `ToastHelper` static methods
```java
ToastHelper.show(context, R.string.your_toast_message);
ToastHelper.show(context, "your toast message");
```

Using a `ToastHelper` object
```java
ToastHelper toastHelper = new ToastHelper(context);
toastHelper.show("your toast method");
toastHelper.show(R.string.your_toast_message);
toastHelper.showLong("your toast method");
toastHelper.showLong(R.string.your_toast_message);
```

## Locale String Format Wrappers
```java
LocaleManager.toString(Character/Float/Double/Integer/Boolean variable);
LocaleManager.toString(Float/Double variable, precision);
```
This function will convert your variable to the String to these using the Locale. This functions is a wrapper around the code ```String.format```. The function will prevent Lint Warning for the same.


## Recycler View
Using Recycler View cannot be easier!
Extend the Recycler View Holder
```java
public class YourViewHolder extends RVHolder<YourItem> {
    
    ...
    
    @Override
    public void populate(YourItem data, Bundle bundle) {
        // Populate your view. You can set on click listeners etc.
    }
}
```

Extend the Recycler View Adapter
```java
public class YourAdapter extends RVAdapter<YourItem, YourViewHolder> {
    ...    
}
```

Setup your recycler view
You could either do the following
```java
    recyclerView = new RVBuilder(context)
        .setView(rootView, R.id.recycler_view)  
        .setAdapter(yourAdapter)
        .setOnScrollListener(onScrollListener) // optional
        .build();
```
or the usual way will also work of course.

A full fledged example can be seen in my [TutorialApp](https://github.com/BijoySingh/TutorialApp). The class ```RVAdapter``` is well documented to understand the other helper functions.

## TimestampItem
Another common action you need to do is convert your timestamp string to time. And also convert it to the write timezone.
```java
String timestamp = "...." // your timestamp string
TimestampItem item = new TimestampItem.Builder(timestamp)
    .setTimezone(hours, minutes) // optional
    .setDeviceTimezone() // optional
    .setTimeFormat("hh:mm aa") // optional
    .setDateFormat("dd MMMM yyyy") // optional
    .setDateTimeFormat("hh:mm aa, dd MMMM yyyy") // optional
    .buil()
    
item.getTime(); // the time string
item.getDate(); // the date string
item.getDateTime() // the date and time string
item.getCompressedDateTime(); // the compressed date and time
```

## DateFormatter
If you have to repeatted format your Dates here is a simple wrapper on your code
```java
// Get Today's formatted date
DateFormatter.getToday();
DateFormatter.getToday(format);

// Get any Date's formatted date
DateFormatter.getDate(date);
DateFormatter.getDate("dd mmmm yyyy", date);
DateFormatter.getDate(date, locale);
DateFormatter.getDate("hh:MM a, dd mmmm yyyy", date, locale);

// Some default formats exist (a lot more exist)
Formats.DD_MM_YYYY.getFormat()
Formats.HH_MM_A.getFormat();
Formats.HH_MM_DD_MM_YYYY.getFormat();
Formats.HH_MM_A_DD_MMMM_YYYY.getFormat();
```

## Threading and Async
Hate writting a lot boilerplate code to use Executors or AsyncTasks, we got you covered
```java
// Want to run something in background and handle it in UI thread
SimpleAsyncTask<String> task = new SimpleAsyncTask<>() { ... }
task.execute();

// Want to run something huge, with high priority, but in background

// Simply run something in the background
SimpleThreadExecutor.execute(runnable);

// Or get more control
SimpleThreadExecutor executor = new SimpleThreadExecutor();
executor.addRunnable(runnable1)
    .addRunnable(runnable2)
    .execute();
```

## PermissionManager
Handling your permissions for Marshmallow made simpler, and cleaner
```java
// Could be more than one permissions here
String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

// Initialise the manager object, with required permissions
PermissionManager manager = new PermissionManager(context, permissions);

/*
 * Or set them as you need them
 * PermissionManager manager = new PermissionManager(context);
 * manager.setPermissions(permissions);
 */
```

Now checking for permission is really simple
```java
manager.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
```

And requesting for permissions too
```java

// Using an access code fixed in the library
manager.requestPermissions();

// Using a custom access code, for more control
manager.requestPermissions(SOME_REQUEST_CODE);
```

It will automatically detect which permissions are already allowed, and will request the missing permissions.
To handle a response, the procedure is same as that in the usual case. You override the ```onRequestPermissionsResult``` listener.

## Database Support
Adding database setup is super simple. You have to do very little work!

Just add a simple model like
```java
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
```java
fieldType = DBColumn.Type.INTEGER
unique = True
primaryKey = true, autoIncrement = true
fieldName = "custom_field_name"
```

You can create a custom class for your databases, or you can simply use the default database:
```java
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
```java
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
```java
JSONObject json = item.serialize();
```

## License
```
Copyright 2016 Bijoy Singh Kochar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```

## Reference Licensing
### Volley
```
Apache 2.0 License
Copyright (C) 2014,2015,2016 Xiaoke Zhang
Copyright (C) 2011 The Android Open Source Project
```

### Joda 
```
Apache 2.0 License
https://github.com/dlew/joda-time-android/blob/master/LICENSE
```

### Universal Image Loader
```
Apache 2.0 License
Copyright 2011-2015 Sergey Tarasevich
```
```
