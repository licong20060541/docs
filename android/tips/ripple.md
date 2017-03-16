```
法1，设置style
    <style name="SelectableItemTheme">
        <item name="android:colorControlHighlight">@color/leeco_ripple_color</item>
    </style>

    <style name="SelectableItemBackground">
        <item name="android:theme">@style/SelectableItemTheme</item>
        <item name="android:background">?android:attr/selectableItemBackground</item>
    </style>

法2，设置背景
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
     android:color="@color/leeco_ripple_color">
    <item android:id="@android:id/mask"
        android:drawable="@color/leeco_ripple_color"/>
</ripple>
```