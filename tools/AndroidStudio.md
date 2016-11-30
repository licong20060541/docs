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
