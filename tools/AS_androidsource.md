```
修改缓存路径 idea.properties
idea.config.path=/letv/workspace/licong/androidAll/AndroidStudioCache/config
idea.system.path=/letv/workspace/licong/androidAll/AndroidStudioCache/system
```


R文件的解析：

cp out/target/common/R/com/android/systemui/R.java src/com/android/systemui/

设置编译环境

$ cd YOUR_DIRECTORY
$ source build/envsetup.sh

生成 idegen.jar 文件

$ mmm development/tools/idegen/

不出意外的话会得到 idegen.jar 文件

$ ls -l out/host/linux-x86/framework/idegen.jar 
-rw-rw-r-- 1 han han 1.5M May 26 10:09 out/host/linux-x86/framework/idegen.jar

生成 Android Studio 工程对应文件

$ ./development/tools/idegen/idegen.sh

这里会稍微耗点时间，最终生成两个文件：

$ ls -l android.*
-rw-rw-r-- 1 han han 323K May 26 15:19 android.iml
-rw-rw-r-- 1 han han  15K May 26 15:19 android.ipr

导入 Android Studio

导入过程比较耗时，建议修改 Android.iml，将自己用不到的代码 exclude 出去。
比如我只看 frameworks 相关的东西，因此就 exclude 了其他不需要的：

      <excludeFolder url="file://$MODULE_DIR$/.repo" />
      <excludeFolder url="file://$MODULE_DIR$/abi" />
      <excludeFolder url="file://$MODULE_DIR$/android" />
      <excludeFolder url="file://$MODULE_DIR$/art" />
      <excludeFolder url="file://$MODULE_DIR$/bionic" />
      <excludeFolder url="file://$MODULE_DIR$/bootable" />
      <excludeFolder url="file://$MODULE_DIR$/build" />
      <excludeFolder url="file://$MODULE_DIR$/cts" />
      <excludeFolder url="file://$MODULE_DIR$/dalvik" />
      <excludeFolder url="file://$MODULE_DIR$/developers" />
      <excludeFolder url="file://$MODULE_DIR$/development" />
      <excludeFolder url="file://$MODULE_DIR$/device" />
      <excludeFolder url="file://$MODULE_DIR$/docs" />
      <excludeFolder url="file://$MODULE_DIR$/external" />
      <excludeFolder url="file://$MODULE_DIR$/hardware" />
      <excludeFolder url="file://$MODULE_DIR$/kernel" />
      <excludeFolder url="file://$MODULE_DIR$/libcore" />
      <excludeFolder url="file://$MODULE_DIR$/libnativehelper" />
      <excludeFolder url="file://$MODULE_DIR$/ndk" />
      <excludeFolder url="file://$MODULE_DIR$/out" />
      <excludeFolder url="file://$MODULE_DIR$/packages" />
      <excludeFolder url="file://$MODULE_DIR$/pdk" />
      <excludeFolder url="file://$MODULE_DIR$/prebuilt" />
      <excludeFolder url="file://$MODULE_DIR$/prebuilts" />
      <excludeFolder url="file://$MODULE_DIR$/sdk" />
      <excludeFolder url="file://$MODULE_DIR$/system" />
      <excludeFolder url="file://$MODULE_DIR$/tools" />
      <excludeFolder url="file://$MODULE_DIR$/vendor" />
      <excludeFolder url="file://$MODULE_DIR$/AMSS" />
      <excludeFolder url="file://$MODULE_DIR$/platform_testing" />

Android Studio 打开源码根目录下新生成的 android.ipr