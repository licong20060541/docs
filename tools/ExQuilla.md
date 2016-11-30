```
启发

    ExQuilla 31.0 破解
    注册码被用逗号分成了四个部分：
    第一部分是注册类型，EX0是免费给的试用类型，我不知道EX1、EX2是什么情况，但试了下，EX1是可以用的
    第二部分是邮件，@是免费给的60天试用的，这里要填有效的Exchange邮箱，可以在选项里Valid Emails里看到
    第三部分是license过期日期。
    第四部分是校验码，分别是前三个部分再加上356B4B5C算出来的MD5值。

实操

    依次点击 [Thunderbird Mail] -> [Tools] -> [Exquilla for Microsoft Exchange] -> [ExQuilla license options...]，打开 [Exquilla options] 界面。

    复制 [ExQuilla license] 的内容并修改。

     修改前： EX0,*@*,2015-05-09,54fc31ddcf9626f04b39db2520aec266
     修改后： EX1,hankunpeng@letv.com,2020-05-09,356B4B5C

    算出 MD5
    3.1 找一个 MD5 加密工具，比如这个
    3.2 得到 32 位的加密内容：EX1,hankunpeng@letv.com,2020-05-09,7f14b8bbd6aa00f4b11dd6a58bd8618b

    将得到的 MD5 串填进 [ExQuilla license] 里，[Close]。

```