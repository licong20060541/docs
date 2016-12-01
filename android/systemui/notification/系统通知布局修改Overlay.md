```
    1.  
    add id change  
    4e857f4ef0357e05806819d0488a73a12208fe8f  
      
        core/res/res/values/symbols.xml  
            <java-symbol type="id" name="notification_main_column" />  
          
    --------- core/res/res/layout/notification_template_material_base.xml ---------  
      
         <LinearLayout  
    +        android:id="@+id/notification_main_column"  
             android:layout_width="match_parent"  
             android:layout_height="match_parent"  
             android:layout_gravity="top"  
      
    ------Notification.java  
        remoteViews.setViewLayoutMarginEndDimen(R.id.notification_main_column, endMargin);  
      
    ------SystemUI  
        View mainColumn = mView.findViewById(com.android.internal.R.id.notification_main_column);  
      
      
      
      
    2.  
    add attribute  
    c0fac72d3c84b6a1a1c82abfa9a74957b11fadc6  
      
    ----------------------- core/res/res/values/symbols.xml -----------------------  
    -  <java-symbol type="dimen" name="notification_header_height" />  
    +  <java-symbol type="dimen" name="notification_content_margin_bottom" />  
      
      
      
    ------------------------ core/res/res/values/dimens.xml ------------------------  
         <!-- height of the content margin on the bottom -->  
    -    <dimen name="notification_content_margin_bottom">13dp</dimen>  
    -  
    -    <!-- height of notification header view if present -->  
    -    <dimen name="notification_header_height">32dp</dimen>  
    +    <dimen name="notification_content_margin_bottom">16dp</dimen>  
      
      
      
    ------------------- core/java/android/app/Notification.java -------------------  
    public class Notification implements Parcelable  
                 int i=0;  
                 final float density = mBuilder.mContext.getResources().getDisplayMetrics().density;  
                 int topPadding = (int) (5 * density);  
    -            int bottomPadding = (int) (13 * density);  
    +            int bottomPadding = mBuilder.mContext.getResources().getDimensionPixelSize(  
    +                    com.android.internal.R.dimen.notification_content_margin_bottom);  
                 boolean first = true;  
      
      
    ----SystemUI  
            mCollapsedBottompadding = getResources().getDimensionPixelSize(  
                    com.android.internal.R.dimen.notification_content_margin_bottom);  
      
      
      
    3.  
    change layout  
    c848c3a1b4e3b365fb42ad7e418f90def054ed5f  
      
      
    ----------------------- core/res/res/values/symbols.xml -----------------------  
    -  <java-symbol type="id" name="profile_badge_large_template" />  
    -  <java-symbol type="id" name="profile_badge_line3" />  
    +  <java-symbol type="id" name="profile_badge" />  
      
      
      
    -------------- core/res/res/layout/notification_template_text.xml --------------  
    +<?xml version="1.0" encoding="utf-8"?>  
    +<!--  
    +  ~ Copyright (C) 2016 The Android Open Source Project  
    +  ~  
    +  ~ Licensed under the Apache License, Version 2.0 (the "License");  
    +  ~ you may not use this file except in compliance with the License.  
    +  ~ You may obtain a copy of the License at  
    +  ~  
    +  ~      http://www.apache.org/licenses/LICENSE-2.0  
    +  ~  
    +  ~ Unless required by applicable law or agreed to in writing, software  
    +  ~ distributed under the License is distributed on an "AS IS" BASIS,  
    +  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
    +  ~ See the License for the specific language governing permissions and  
    +  ~ limitations under the License  
    +  -->  
    +<TextView xmlns:android="http://schemas.android.com/apk/res/android"  
    +    android:id="@+id/text"  
    +    android:layout_width="match_parent"  
    +    android:layout_height="wrap_content"  
    +    android:layout_gravity="top"  
    +    android:layout_marginTop="1.5dp"  
    +    android:ellipsize="marquee"  
    +    android:fadingEdge="horizontal"  
    +    android:gravity="top"  
    +    android:singleLine="true"  
    +    android:textAppearance="@style/TextAppearance.Material.Notification"  
    +    />  
      
      
      
    ------- core/res/res/layout/notification_template_material_big_base.xml -------  
                 <include layout="@layout/notification_template_part_line1" />  
    -            <include layout="@layout/notification_template_part_line3" />  
    +            <include layout="@layout/notification_template_text" />  
             </LinearLayout>  
      
      
      
      
    ------------- core/res/res/layout/notification_template_header.xml -------------  
    +    <ImageView android:id="@+id/profile_badge"  
    +        android:layout_width="@dimen/notification_badge_size"  
    +        android:layout_height="@dimen/notification_badge_size"  
    +        android:layout_gravity="center"  
    +        android:layout_marginStart="4dp"  
    +        android:scaleType="fitCenter"  
    +        android:visibility="gone"  
    +        android:contentDescription="@string/notification_work_profile_content_description"  
    +        />  
     </NotificationHeaderView>  
      
      
      
    ------------------- core/java/android/app/Notification.java -------------------  
      
    +                contentView.setImageViewBitmap(R.id.profile_badge, profileBadge);  
    +                contentView.setViewVisibility(R.id.profile_badge, View.VISIBLE);  
      
      
      
      
    -------------- core/java/android/view/NotificationHeaderView.java --------------  
      
    +        mProfileBadge = findViewById(com.android.internal.R.id.profile_badge);  
```


#overlay覆盖系统
```
    vendor/qcom/overlay/common/frameworks/base/core/res/res下  
        如values/dimens文件修改属性    <dimen name="status_bar_icon_size">34dp</dimen>  
            layout等等  系统通知布局修改 and Overlay 
      
     vendor/qcom/overlay/common/frameworks/base/packages/SettingsProvider/res下  
        如values/defaults文件修改属性  <integer name="def_screen_off_timeout">-1</integer>  
```

```
厂商framework  
  
vendor/qcom/frameworks/sdk/res/values/  
    类似系统qcom_symbols.xml和colors等等  
vendor/qcom/frameworks/sdk/webview等 
```

```
    <?xml version="1.0" encoding="utf-8"?>    
    <resources>    
        <public type="string" name="string3" id="0x7f040001" />    
    </resources>    
  
        这个public.xml用来告诉Android资源打包工具aapt，将类型为string的资源string3的ID固定为0x7f040001。  
为什么需要将某一个资源项的ID固定下来呢？一般来说，当我们将自己定义的资源导出来给第三方应用程序使用时，  
为了保证以后修改这些导出资源时，仍然保证第三方应用程序的兼容性，就需要给那些导出资源一个固定的资源ID。  
```