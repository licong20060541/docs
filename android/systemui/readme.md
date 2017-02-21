doze目录
```
doze模式: 继承DreamService，1小时后自动断网等操作，省电

另，继承DreamService可以实现屏保

参见网盘文章

```

egg目录
```
LLandActivity和LLand
小鸟跳跃管道游戏基本一样
有时间可以研究下LLand的实现逻辑

```

keyguard目录: 锁屏相关
```
KeyguardService和KeyguardViewMediator
```


media目录
```
负责通知的铃声等
RingtonePlayer和NotificationPlayer
```

net目录
```
流量超出
NetworkOverLimitActivity，内部会弹出dialog
由NetworkPolicyManagerService发出Intent
```

power目录
```
电量相关，如弹的通知，
USB charging not supported.
Battery is low
等等
```

qs目录
```
展开栏中快捷开关
```

recents目录
```
最近任务
```

screenshot目录
```
TakeScreenshotService服务

void takeScreenshot(Runnable finisher, boolean statusBarVisible, boolean navBarVisible) {
//还有旋转的参数
}

```

settings目录

```
亮度设置相关，展开栏只剩亮度调节等
```

statusbar目录
```
状态栏展开栏
```

usb目录
```
usb相关的如通知，存储等等
```

volume目录
```
调节音量弹出的界面
```
