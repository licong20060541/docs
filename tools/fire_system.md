#烧系统
```
adb reboot bootloader

sudo fastboot devices
sudo fastboot flash system system.img
sudo fastboot reboot


其它
sudo fastboot flash boot boot.img
lsusb
adb reboot recovery，选择bootloader，按键确定
./flash.sh
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