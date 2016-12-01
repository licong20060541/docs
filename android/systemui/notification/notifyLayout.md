```

headup显示的也是状态栏展开时的一部分，当拖动时状态栏连续显示了


##
通知布局：

base  1.普通显示的通知
        <include layout="@layout/notification_template_part_line1" />

        <include layout="@layout/notification_template_part_line2" /> 两行时不显示，三行时显示

        <include layout="@layout/notification_template_part_line3" />
      2.带进度条的通知
	进度条在line2下可看到

big_text
	多行文本布局bigText().build();
	注意：合上时显示的是base布局，当拖动或展开时显示的是big_text

big_picture
	大图通知：bigPicture(bigPicture).build();
	注意：合上时显示的是base布局，当拖动或展开时显示的是big_picture
inbox:
	列表型通知:NotificationCompat.InboxStyle style
        注意：合上时显示的是base布局，当拖动或展开时显示的是inbox
big_base
	1.带按钮的通知:builder.addAction()
	注意：同上
	2.媒体通知!fake!

media相关的三个布局没有用到




















```