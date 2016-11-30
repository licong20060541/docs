```
第一行、LOCAL_PATH:
LOCAL_PATH := $(call my-dir)
这个变量用于给出当前文件的路径，你必须在Android.mk文件的开头定义，这个变量不会被$(CLEAR_VARS)清除，因此每个Android.mk只需要定义一次(即使你在一个文件中定义了几个模块的情况下)；

第二行、include $(CLEAR_VARS)
CLEAR_VARS由编译系统提供，指定让GNU_MAKEFILE为你清除许多LOCAL_XXX变量，除LOCAL_PATH外，这介必要的，因为所有的编译控制都在同一个GNU MAKE执行环境中，所有变量都是全局的；

第三行、LOCAL_MODULE_TAGS 
LOCAL_MODULE_TAGS := optional
这个变量有四个选项可以选择：
1、user：指该模块只在user版本下才编译；
2、eng：指该模块只在eng版本下才编译；
3、tests：指该模块只在tests版本下才编译；
4、optional：指该模块在所有版本下都编译。

第四行、LOCAL_STATIC_JAVA_LIBRARIES
LOCAL_MODULE_TAGS := optional
LOCAL_STATIC_JAVA_LIBRARIES := \
android-common \
guava \
android-support-v13 \
android-support-v4
取 .jar库的别名，可以随便取的

LOCAL_JAVA_LIBRARIES——指定依赖的共享java类库
LOCAL_STATIC_JAVA_LIBRARIES——指定依赖的静态java类库

第五行、LOCAL_SRC_FILES
LOCAL_SRC_FILES := \
$(call all-java-files-under,src) \
src/com/android/browser/EventLogTags.logtags  – 见后文章
列举所有需要编译的源文件，宏all-java-files-under定义在build/core/definitions.mk中

第六行、LOCAL_PACKAGE_NAME 
LOCAL_PACKAGE_NAME := Browser
表示这个包的名字，一般和文件夹的名字一致

6.2 LOCAL_CERTIFICATE := platform
在 android 的API中有提供 SystemClock.setCurrentTimeMillis()函数来修改系统时间，可惜无论你怎么调用这个函数都是没用的，无论模拟器还是真 机，在logcat中总会得到"Unable to open alarm driver: Permission denied ".这个函数需要root权限或者运行与系统进程中才可以用。 
        本来以为就没有办法在应用程序这一层改系统时间了，后来在网上搜了好久，知道这个目的还是可以达到的。 
        第一个方法简单点，不过需要在Android系统源码的环境下用make来编译： 
        1. 在应用程序的AndroidManifest.xml中的manifest节点中加入android:sharedUserId="android.uid.system"这个属性。 
        2. 修改Android.mk文件，加入LOCAL_CERTIFICATE := platform这一行 
        3. 使用mm命令来编译，生成的apk就有修改系统时间的权限了。 

6.3  LOCAL_PRIVILEGED_MODULE := true
这个设置表示，SystemUI模块装入system/priv-app
如果没有这个设置，模块会装入system/app
可以在模块目录mm编译，看看会装在哪个目录
priv-app里能获得系统权限


第七行、LOCAL_PROGUARD_FLAG_FILES 
LOCAL_PROGUARD_FLAG_FILES := proguard.flags
混淆代码相关的配置


第八行、LOCAL_EMMA_COVERAGE_FILTER
LOCAL_EMMA_COVERAGE_FILTER := *,-com.android.common.*
这个暂时没能理解，如果有理解了的帮忙解答一下

第九行、LOCAL_REQUIRED_MODULES 
# We need the sound recorder for the Media Capture API.
LOCAL_REQUIRED_MODULES := SoundRecorder
配置录音方面的接口

第十行、include $(BUILD_PACKAGE)
表示当前JAVA代码build成APK

第十一行、include $(call all-makefiles-under,$(LOCAL_PATH))
表示需要build该目录下的子目录的文件，这样编译系统就会在当前目录下的子目录寻找Android.mk来编译so等其它程序
```