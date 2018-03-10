# Android Basics Library
This library offers some barebone code for android common to most applications.
It provides simple classes and pre-written functions for:
- Internet Access
- SharedPreferences / DataStore storage and retrieval
- File read and write
- Recycler View
- Some other basic functions like dp2pixel, etc.
- Marshmallow Permissions Support
- Simple Profiler
- Parallel Execution
- Text Utilities

# Installation
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AndroidBasicsLibrary-green.svg?style=true)](https://android-arsenal.com/details/1/3226)

The library is on Jcenter, so usage is really simple. Add the following dependency in your app's ```build.gradle```:
```groovy
dependencies {
    ...
    compile 'com.github.bijoysingh:android-basics:4.0.0'
    ...
}

// compile 'com.github.bijoysingh:android-basics:3.1.2' for Android Support Lib v26
// compile 'com.github.bijoysingh:android-basics:2.1.0' last stable version
    
```
You might need to also include these in case you use the corresponding dependencies
```groovy
dependencies {
    ...

    // For internet access
    compile 'com.mcxiaoke.volley:library:1.0.17'
    
    // For Timestamp utility
    compile 'net.danlew:android.joda:2.8.1'
    
    ...
}
```

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

Use the class and built in functions using ```put``` and ```get```.
```java
PreferenceManager preferences = new PreferenceManager(context);
preferences.put(KEY, your_variable);
preferences.get(KEY, your_default_variable);
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
You can go for a solution of use the library `'net.grandcentrix.tray:tray:0.11.1'` But I have recently seen that it has bugs like 
deletion during updates. Try the new class `DataStore` described next:

## Async safe Store
Use this class for saving / retrieving content using ```put``` and ```get```. You can get the content from services / main system alike. The access will be fast and will be the same.
```java
Store storage = Store.get(context);
    
storage.put(KEY, your_variable);
storage.get(KEY, your_default_variable);
```

Note: If you were using the older DataStore class, you can migrate to the Store API using:
dataStore.migrateToStore(store);

- Store offers no context solution vs DataStore
- Compressed storage
- It also allows you to have multiple smaller Stores, with names, so you can segregate your store based on use cases.  `Store.get(context, NAME)`
- Further it allows for a VersionedStore, which allows for you to specify a version with the Store. And migrations are handled gracefully, using a Migration object
 `VersionedStore.get(context, NAME, version)` or `VersionedStore.get(context, NAME, version, migration)` 


## Image operations
This library used to support Image operations, but they are being removed as  
directly using Picasso and Fresco for these is the best idea. 

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
### Simple Recycler View
This is a recycler view with a simple one kind of view item.
Extend the Recycler View Holder -> This is the holder for the view contents of one item.
We will show you how to use this for a simple item
```java

/**
 * @example
 * View Item inside a layout R.layout.content_item
 *  ------------------------------------
 * |   TextView                         |
 * |   id -> content                    |
 *  ------------------------------------
 */
public class YourViewHolder extends RecyclerViewHolder<YourItem> {

    /**
     * @example
     * TextView content;
     */

    public YourViewHolder(Context context, View itemView) {
      super(context, itemView);
      /**
       * @example
       * content = (TextView) itemView.findViewById(R.id.content);
       */
    }
    
    @Override
    public void populate(YourItem data, Bundle bundle) {
        // Populate your view. You can set on click listeners etc.
        /**
         * @example
         * content.setText(data.getContent());
         */
    }
}
```

Extend the Recycler View Adapter, this is the controller to your recycler view.
Most of the basic functions have already been done for you.
```java
public class YourAdapter extends RecyclerViewAdapter<YourItem, YourViewHolder> {

  /**
   * The recycler view adapter constructor
   *
   * @param context the application/activity context
   */
  public YourAdapter(Context context) {
    super(context, R.layout.your_layout_file, YourViewHolder.class);
  }
}
```

```java
// Using this adapter is easy
YourAdapter yourAdapter = new YourAdapter(context);

// You can do a lot from this adapter. This will take your list of items
yourAdapter.setItems(items);
yourAdapter.addItems(items);
yourAdapter.addItem(item);
yourAdapter.addItem(item, position);
yourAdapter.removeItem(item);
yourAdapter.removeItem(position);
yourAdapter.clearItems();

// You can get the default layout manager as well from the adapter.
// A layout manager controls the way your recycler view is rendered.
// This is basically like a simple list - LinearLayoutManager or
// a grid layout - GridLayoutManager
recyclerView.setLayoutManager(yourAdapter.getLinearLayoutManager())
recyclerView.setLayoutManager(yourAdapter.getGridLayoutManager(columns))
```

Setup your recycler view
You could either do the following
```java
    recyclerView = new RecyclerViewBuilder(context)
        .setView(rootView, R.id.recycler_view)
        .setView(activity, R.id.recycler_view) // or use this
        .setRecyclerView(recyclerView) // or use this
        .setAdapter(yourAdapter)
        .setOnScrollListener(onScrollListener) // optional
        .setLayoutManager(layoutManager) // set the layout manager
        .build();
```
or the usual way will also work of course.

### Multi View Recycler View
A common use case for recycler views is to use it with multiple view holders / views.
This involves some common setup which has been taken care for you here.
```java
public class YourAdapter extends MultiRecyclerViewAdapter<YourItem> {
  public YourAdapter(
    Context context,
    List<MultiRecyclerViewControllerItem<YourItem>> items) {
    super(context, items);
  }

  @Override
  public int getItemViewType(int position) {
    // Return an int value indicating your view type for the given position
  }
}
```

```java
// To set this up, you need to create this list of MultiRecyclerViewControllerItem items
// Each of these items maps, view type to some common properties like:
MultiRecyclerViewControllerItem<YourItem> item = new MultiRecyclerViewControllerItem.Builder<YourItem>()
    .viewType(VIEW_TYPE) // the view type for this view holder
    .spanSize(VIEW_SPAN) // optional for grid view: the number of columns the view spans
    .layoutFile(R.layout.your_view_item) // the view item layout for this view type
    .holderClass(YourRecyclerViewHolder.class) // the class of the holder
    .build();

// You can create a list of these items for each view, and set it to the adapter constructor.
YourAdapter adapter = new YourAdapter(context, items);

// Optional for grid views: using this handles the span size properties for you.
recyclerView.setLayoutManager(yourAdapter.getGridLayoutManager());
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
```

```java
// Simply run something in the background
SimpleThreadExecutor.execute(runnable);

// Or get more control
SimpleThreadExecutor executor = new SimpleThreadExecutor();
executor.addRunnable(runnable1)
    .addRunnable(runnable2)
    .execute();
```

Async Tasks have an issue that they only work synchronously. This is generally a problem for
doing more things. Introducing MultiAsyncTask for this usecase
```java
MultiAsyncTask.execute(this, new MultiAsyncTask.Task<Result>() {
  @Override
  public Result run() {
    return doHeavyOperation();
  }

  @Override
  public void handle(Result result) {
    doUiOperation(result);
  }
});
```

```java
List<String> names = ...;
Parallel<String, Integer> parallel = new Parallel();
parallel.setListener(new ParallelExecutionListener<String, Integer>() {
    
});

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

## Intent Utils
Some common actions around intents
To share a text to other applications on the device
```java
new IntentUtils.ShareBuilder(context)
  .setSubject(subjectString)
  .setText(textString)
  .setChooserText("Share using...") // Optional
  .share();
```

```java
IntentUtils.openAppPlayStore(context);

IntentUtils.openAppPlayStore(context, packageName);
```

## Text Utils
Some common actions which need to be done with Text is now part of the library

```java
// To copy text to a clipboard
TextUtils.copyToClipboard(context, textToCopy);

// To check if a string is null or empty
TextUtils.isNullOrEmpty(text);

// Check if two nullable strings are equal
TextUtils.areEqual(string1, string2);
TextUtils.areEqualNullIsEmpty(string1, string2);
```

## Bottom Sheet Fragment 
There is a lot of boiler plate code using the bottom sheet fragment. This code allows for using that
The XML for the activity MUST be surrounded by a `CoordinatorLayout`
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout 
  xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="match_parent"
  android:layout_height="match_parent">
  ...
</android.support.design.widget.CoordinatorLayout>
```


You also need a layout file for the bottom sheet. Let's say it's `layout_bottom_sheet.xml` 
```java
public class BottomSheet extends SimpleBottomSheetFragment {
  @Override
  public int getLayout() {
    return R.layout.layout_bottom_sheet;
  }

  @Override
  public void setupView(View contentView) {
    // find views inside the layout_bottom_sheet.xml
  }
}
```

## View Pager Activity and Fragment
There is a lot of boiler plate code which needs to be done for view pagers. This will save that for you
```java
public class YourActivity extends ViewPagerActivity {

  @Override
  protected Fragment getPageFragment(int position) { ... }

  @Override
  protected void onPageChanged(int position) { ... }

  @Override
  protected int getPagesCount() { ... }

  @Override
  protected int getViewPagerResourceId() {
    return R.id.pager;
  }
}
```

```java
public class YourFragment extends SimpleFragment {
  @Override
  protected int getLayoutId() {
    return R.layout.your_fragment_layout;
  }

  @Override
  protected void onCreateView() {
    // Simply use like a activity
    TextView yourTextView = (TextView) findViewById(R.id.your_textview);
  }
}
```

## Random Helper
Sometimes you need to generate secure random numbers like IDs etc which are alphanumeric etc.
```
// Get a alpha numeric random string of length 16
RandomHelper.getRandom();

// Get a alpha numeric random string of a fixed length
RandomHelper.getRandomString(stringLength);

// Get a Big Integer which has upto a fixed approx length (this is because we use nearest power of 2)
RandomHelper.getRandomInteger(approxMaxIntegerLength);
```

## Database Support
Removing this support too as Reflection is not very efficient or reliable. Try using google's Room library.

## JSON Parsing
Removing this support too as Reflection is not very efficient or reliable.

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