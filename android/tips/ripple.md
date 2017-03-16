```
    <style name="SelectableItemBackground">
        <item name="android:colorControlHighlight">@color/leeco_ripple_color</item>
        <item name="android:background">?android:attr/selectableItemBackground</item>
    </style>


<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
     android:color="@color/leeco_ripple_color">
    <item android:id="@android:id/mask"
        android:drawable="@color/leeco_ripple_color"/>
</ripple>
```