#无响应
```
ctrl+alt+F1--F7
tty7为图形界面

进入tty1,执行
ps -t tty7
将xorg杀死
sudo kill xxx

```

#linux
```
带空格的文件如AndroidManifest _copy.xml~
删除
rm AndroidManifest\ _copy.xml~

在linux中查找包含某个特定内容的文件
cd <your dictionery>
grep -isnr "http" *

只在.ini文件中查找文件内容包含“http”

grep -nr "http" *.ini

nri, nrw

-i, --ignore-case         忽略大小写

-s, --no-messages         不显示错误信息

-n, --line-number         输出的同时打印行号

-r, --recursive           like --directories=recurse

选项与参数：
-a ：将 binary 文件以 text 文件的方式搜寻数据
-c ：计算找到 '搜寻字符串' 的次数
-i ：忽略大小写的不同，所以大小写视为相同
-n ：顺便输出行号
-v ：反向选择，亦即显示出没有 '搜寻字符串' 内容的那一行！
--color=auto ：可以将找到的关键词部分加上颜色的显示喔！


1，在某个路径下查文件。

在/etc下查找“*.log”的文件

find /etc -name “*.log”

2，扩展，列出某个路径下所有文件，包括子目录。

find /etc -name “*”

3，在某个路径下查找所有包含“hello abcserver”字符串的文件。

find /etc -name “*” | xargs grep “hello abcserver”

或者find /etc -name “*” | xargs grep “hello abcserver” > ./cqtest.txt


rm mv等命令对大量文件操作是报错 -bash: /bin/rm: Argument list too long
也可用xargs 解决
删除当前目录下所有.cpp文件
find . -name "*.c" | xargs rm -rf


sed删除替换等

awk编程


find -name *.xml | xargs grep "whatToFind"

find . -name "*.c" | xargs grep 'efg'

xargs展开find获得的结果，使其作为grep的参数

find . -name "*.xml" | xargs sed -i 's/com.android.systemui/com.leeco.rsd.systemui/g'


tee - 重定向输出到多个文件
比如 ls >a.txt，这时我们就不能看到输出了，如果我们既想把输出保存到文件中，
又想在屏幕上看到输出内容，就可以使用tee命令了
格式：tee file
输出到标准输出的同时，保存到文件file中。如果文件不存在，则创建；如果已经存在，则覆盖之

```