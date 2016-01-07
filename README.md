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
- Quick database support (coming soon)

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
