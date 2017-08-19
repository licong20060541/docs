1 gradle 修改为本地版本

1）gradle-wrapper.properties,,,distributionUrl=https\://services.gradle.org/distributions/gradle-3.3-all.zip


2）build.gradle,,,  
    dependencies {
        classpath 'com.android.tools.build:gradle:1.3.1'
    }


查看源码版本build/core/version_defaults.mk

#内存
ubuntu系统下apt-install安装目录  /opt/Android-studio/bin

首先，在android-studio/bin/studio.vmoptions studio64.vmoptions找到这两个文件，需要修改属性

电脑内存一般4g,以及以上，可以修改以下属性（内存占用，默认情况下，允许使用的内存较小）

-Xms2048m

-Xmx2048m

-XX:MaxPermSize=2048m

-XX:ReservedCodeCacheSize=1024m


-xmx 参数是 Java 虚拟机启动时的参数，用于限制最大堆内存。

如何确定修改生效了？
在 Settings -> Appearance 页里，打开 Show memory indicator 选项，然后主界面右下角会显示 Heap 总大小以及使用状况。
这样操作一下，就可以确认 Heap Size 修改是否生效。
改完以后记得点一下File--Invalidate caches/restart这个，才能生效，不然关掉as再开就打不开了

#快捷键
Terminator

ctrl+alt+T：新开终端

ctrl+shift+T：当前终端上新开终端窗口，叠加

Editor | Code Style | Java
Code Generation 标签
给普通 Field 添加一个’m’前缀，给 Static filed 添加一个’s’前缀

Editor | Code Style | Live Templates
我们输入 sout 后按 enter 键， Android Studio 会自动帮我们写入 System.out.println();

    File | Settings 打开设置
    选择 Editor | Code Style | Live Templates
    点击最右侧的加号并选择 Template Group
    在弹出的对话框中输入一个活动模板分组的名称，如 custom
    在左侧选中上一步中创建的 custom 分组，点击右边的加号
    选择 Live Template ，在 Abbreviation 中对输入 psh
    在 Description 中输入这个活动模板的描述
    在 Template text 中输入以下代码


    点击下方的 Define 按钮，选中 java 表示这个模板用于java代码
    点击右侧的 Edit variables
    选择 Expression 下拉框中的 className 并勾选 Skip if…

    这个操作的作用是，AS会自动将我们在上一步中用’$’符包裹的 className自动替换为当前类不含包名的类名

    点击 Apply 和 Ok 让设置生效。


Keymap到Eclipse

double shift:查找file
ctrl+shift+a:查找快捷键等

scroll bottom/top  ctrl + 上下


ctrl+shift+F：格式化，Reformat Code

ctrl+T： Surround With...

F4：查看类继承关系

F2：查看文档说明（函数使用说明）

ctrl + /：注释
ctrl + shift + /：块注释

ctrl+H:  全局搜索

ctrl+O：类方法

alt + shift + R：重命名文件

ctrl+N/alt + insert：generator选项

Ctrl+D：删除光标所在位置那行代码

Ctrl+X：剪切光标所在位置那行代码


search move line
Alt+↑：光标所在位置那行代码往上移动
Alt+↓：光标所在位置那行代码往下移动


Alt + left/Right：代码跳转


duplicate lines：
ctrl+shift+down复制一行向下

ctrl + L:行



2.代码提示列表（Eclipse中的Content Assist，Alt+/）
Keymap->Main Menu->Code->Completion->Basic

3.错误修正提示列表（Eclipse中的Quick Fix）
Keymap->Other->Show Intention Action：默认是Alt+Enter


Ctrl+G / Ctrl+Alt+Shift+G：查询变量或者函数或者类在哪里被使用或被调用，后者是前者的复杂表现，可以选择查询范围等。



Ctrl+E：查看最近打开过的文件

Ctrl+Shift+E：查看最近编辑过的文件

double Shift：全局查找，这个是全局文件查找，到文件名称层面。

Ctrl+Shift+R：快速定位到你所想打开的文件。


#ubuntu
```
ubuntu--android studio--unable to detect adb version

1. goto http://www.androiddevtools.cn/


2. download  SDK Platform-Tools version 22


3. override your sdk platform-tools dir


4. restart android studio
```
```
/dev/kvm is missing

1
sudo apt-get install qemu-kvm libvirt-bin ubuntu-vm-builder bridge-utils

2
sudo kvm-ok

INFO: /dev/kvm does not exist
HINT:   sudo modprobe kvm_intel
INFO: Your CPU supports KVM extensions
INFO: KVM (vmx) is disabled by your BIOS
HINT: Enter your BIOS setup and enable Virtualization Technology (VT),
      and then hard poweroff/poweron your system
KVM acceleration can NOT be used

Enable Virtualization Technology in BIOS

3

Affter bios setting, I used the following command

sudo modprobe kvm_intel

Now is ok
```


#genymotion使用
```
genymotion，sign in---setting，点击ADB，设置sdk位置，选择Use custom Android SDK tools，选择Android SDK的位置。


windows with VirtualBox (32/64位)
http://files2.genymotion.com/genymotion/genymotion-2.6.0/genymotion-2.6.0-vbox.exe

windows without VirtualBox (32/64位)
http://files2.genymotion.com/genymotion/genymotion-2.6.0/genymotion-2.6.0.exe

Ubuntu 14.10 and older, Debian 8
(32位)
http://files2.genymotion.com/genymotion/genymotion-2.6.0/genymotion-2.6.0-linux_x86.bin
(64位)
http://files2.genymotion.com/genymotion/genymotion-2.6.0/genymotion-2.6.0-linux_x64.bin

Ubuntu 15.04 and newer (64位)
http://files2.genymotion.com/genymotion/genymotion-2.6.0/genymotion-2.6.0-ubuntu15_x64.bin

Mac OS X 10.8 or above.
http://files2.genymotion.com/genymotion/genymotion-2.6.0/genymotion-2.6.0.dmg



电脑第一次安装genymotion，打开显示

Unable to start the virtual device.VirtualBox cannot start the virtual device............

于是打开VirtualBox，在VirtualBox启动虚拟机，显示VT-x is disabled in the bios for both all CPU modes...，这是因为bois中未开启Vt-x功能。

在bois中打开VT-x的方法：

关机，在电脑启动前按Delete键，进入到Bios界面，然后settings-->configuration-IntelVirtualTechnology设置为[ENABLED]。问题解决~~

另，我在戴尔电脑中进入Bios界面时稍有不同，操作流程为：Advanced-->CPU-->IntelVirtualTechnology设置为[ENABLED].


HP台式机--F10进入bois
```
