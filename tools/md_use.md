#图片
```
使用相对路径插入图片:
  比如你把一个叫做1.png的图片和*.md文件放在一起，那么你就可以用这种方式插入图片：![](1.png)
  如果不想放在同一层级,那么就可以这样插入:![](foldername/1.png),
表示引用同层级一个叫做"foldername"的文件夹中的1.png图片,以此类推.
```
```
利用github存储图片，在markdown引用图片链接地址
步骤如下：
1.将markdown需要用的图片放到git仓库中，发布到github上
2.访问github仓库,smshen/MarkdownPhotos · GitHub
3.访问图片MarkdownPhotos/test.jpg at master · smshen/MarkdownPhotos · GitHub
4.点击Raw按钮--download按钮
5.拷贝链接地址https://raw.githubusercontent.com/smshen/MarkdownPhotos/master/Res/test.jpg
6.在Markdown中引用图片，![Aaron Swartz](https://raw.githubusercontent.com/smshen/MarkdownPhotos/master/Res/test.jpg)

测试：
可用链接
    https://raw.githubusercontent.com/licong20060541/docs/master/life/images/ym.jpg
github库中看到的链接：
    https://github.com/licong20060541/docs/blob/master/life/images/ym.jpg
可见是将github.com修改为了raw.githubusercontent.com，并且将blob删除了！！！

```