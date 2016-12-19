文字颜色设为#708284，背景颜色设为#07242E


Android5.x编译需要openjdk1.7版本，可以通过源直接安装

Android 5.1 用到的jdk不再是Oracle 的 jdk ，而是开源的 openjdk，在ubuntu安装好后，使用如下命令安装jdk：
$sudo apt-get install openjdk-7-jdk  

 

$ sudo apt-get install openjdk-7-jre openjdk-7-jdk

切换java版本
$ sudo update-alternatives --config java 


配置路径

使用命令 sudo gedit  /etc/profile 打开 /etc/profile 文件，然后在文件末尾添加

#set Java environment
#/usr/lib/jvm/Java-7-openjdk-amd64
#export JAVA_HOME=/usr/local/java/jdk1.7.0_79
#export JRE_HOME=/usr/local/java/jdk1.7.0_79/jre

export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
export JRE_HOME=/usr/lib/jvm/java-7-openjdk-amd64/jre
export CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH
export PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$JAVA_HOME:$PATH


输入命令 sudo source /etc/profile 使其生效，然后再使用命令 java -version，如果查看到 java 版本信息，即表示成功安装了。

但是，此时只有执行过 sudo source /etc/profile 命令的终端所在的线程才可以使用 Java 环境变量，

而其他线程则还不可以。重启之后则都可以了


=======================================



As a workaround, you can install OpenJDK 8 from aPPA repository:

1. Open terminal from the Dash or by pressing Ctrl+Alt+T. When it opens, run the command below to add PPA:

sudo add-apt-repository ppa:openjdk-r/ppa

OpenJDK 8 PPA

Type in user password when it asks and hit Enter to continue.

2. After that, update system package cache and install OpenJDK 8:

sudo apt-get update 

// error---++sudo apt-get -o Acquire::http::proxy="http://127.0.0.1:8087/" update

sudo apt-get install openjdk-8-jdk

3. If you have more than one Java versions installed on your system. Run below command set the default Java:

sudo update-alternatives --config java

Type in a number to select a Java version.

set default java version

And set default Java Compiler by running:

sudo update-alternatives --config javac

4. Finally check out current Java version by running:

java -version

It outputs something like this:

openjdk version "1.8.0_01-internal"
OpenJDK Runtime Environment (build 1.8.0_01-internal-b04)
OpenJDK 64-Bit Server VM (build 25.40-b08, mixed mode)
