#烧系统
```

licong10@licong10-HP-ProDesk-480-G3-MT:~/leshi/minervar/cesRSD$ find out -name fastboot
out/host/linux-x86/obj/EXECUTABLES/fastboot_intermediates/fastboot
out/host/linux-x86/bin/fastboot

adb shell--dmesg

adb reboot bootloader

sudo fastboot devices
sudo fastboot flash system system.img
sudo fastboot flash userdata userdata.img
sudo fastboot reboot

sudo adb kill-server: when show no permission


adb shell getprop
其它
make framework
make services
sudo fastboot flash boot boot.img
lsusb
adb reboot recovery，选择bootloader，按键确定
./flash.sh
make update-api -j16
make SystemUI
mm编译模块
repo start aaa .
git commit --amend
repo abandon aaa
repo rebase

私有资源，在symbols.xml中添加 <java-symbol type="dimen" name="notification_height" />
共有资源，在public.xml中添加 <public type="dimen" name="notification_height" id="0x01050007" />
```

```
ps ax | grep mtp
adb root
adb remount
which adb
adb kill-server
adb start-server
dumpsys notification
am start -n com.android.browser/com.android.browser.BrowserActivity
am startservice com.android.systemui/.SystemUIService
monkey调试
adb shell monkey -p eui.auto.letvfeedback -s 686127382 -v --pct-touch 30 --throttle 50 50000 --ignore-crashes --monitor-native-crashes
```

#repo
```
repo init -u xxxxx
repo sync
source build/envsetup.sh
lunch    选择xx
make -j8
```



#屏幕参数

```

adb shell wm density

adb shell dumpsys window displays

adb shell wm size

adb shell getprop | grep lcd_density

显示矩形的当前值
adb shell dumpsys window
显示SurfaceFlinger的状态
adb shell dumpsys SurfaceFlinger



drawable                 H*W             Density
drawable-ldpi          320*240          120   (0.75)
drawable-mdpi        480*320          160   (1)
drawable-hdpi         800*480           240   (1.5)
drawable-xhdpi       1280*720         320   (2)
drawable-nodpi         (把一些不能被拉伸的图片放在 drawable-nodpi 中，此图片将不会被放大，以原大小显示)
drawable-nodpi-1024×600
drawable-nodpi-1280×800
drawable-nodpi-800×480
```
