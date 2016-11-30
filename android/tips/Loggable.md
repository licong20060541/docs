```
Android开发中，我们经常使用Log类来记录log信息，但是有时候我们输出的log信息太多，或者log中包含重要信息，这时候我们仅仅希望只在开发环境中才输出log，生产环境的安装包不输出日志，一个小技巧就是检测BuildConfig.DEBUG的值

if (BuildConfig.DEBUG) {
Log.v(tag, message);
}
这样我们的日志便只会在debug包下输出。

还 有一种比较常见的场景是，生产环境的安装包默认会打印日志，但是我又不想让别人能够直接从logcat中看到我的APP输出日志，这个时候就可以考虑使用 Log类提供的isLoggable (String tag, int level)函数。这个函数的作用就是检查某个tag的日志在某个日志输出级别下是否打印。默认的情况下，所有tag的日志在大于等于info的级别下都 会打印。使用这个函数，我们就可以在打印日志之前，判断我们的某个tag的日志，在某个日志级别下是否应该打印。比如

if (BuildConfig.DEBUG && Log.isLoggable("MainActivity", Log.VERBOSE)) {
Log.v("MainActivity", message);
}
这里我们在打印tag为MainAcitivity的日志之前，先判断当前的Log level是否是大于等于VERBOSE的，我们期望的是tag为MainActivity的日志仅当log level大于等于VERBOSE的时候才打印出来。
这样当我们想要查看生产环境的日志的时候，只需要在adb中设置一下某个tag的log level就可以了
adb shell setprop log.tag.<YOUR_LOG_TAG> <LEVEL>
LEVEL的值可以是VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT, 或者 SUPPRESS， SUPPRESS会禁止打印所有日志。
同时，由于只有我们自己知道我们的程序中有哪些tag，这样也可以避免别人看到我们的日志。

一个很常用的使用场景就是打印sqlite的日志，我们可以通过设置log level来让Android源码中的sqlite相关的日志打印出来，下面就是相关的命令：

adb shell setprop log.tag.SQLiteLog V
adb shell setprop log.tag.SQLiteStatements V
adb shell stop
adb shell start

查看SQLiteDebug.java可以看到就是通过isLoggable函数实现的
public static final boolean DEBUG_SQL_LOG = Log.isLoggable("SQLiteLog", Log.VERBOSE);
```