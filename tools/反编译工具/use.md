`java -jar AXMLPrinter2.jar  AndroidManifest.xml  > newxml.xml`

apktool

     作用：资源文件获取，可以提取出图片文件和布局文件进行使用查看
     输入以下命令：apktool.bat d -f  test.apk  test
     apktool.bat   d  -f    [apk文件 ]   [输出文件夹]）
     反编译完的文件重新打包成apk，那你可以：输入apktool.bat   b    test（你编译出来文件夹）便可

dex2jar

     作用：将apk反编译成java源码（classes.dex转化成jar文件）
     dex2jar   classes.dex

jd-gui

     作用：查看APK中classes.dex转化成出的jar文件，即源码文件打开工具jd-gui
     打开工具jd-gui,选择目录下的文件