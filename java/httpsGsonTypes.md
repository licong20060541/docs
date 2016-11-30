#https
HTTP之下加入了SSL (Secure Socket Layer)，安全的基础就靠这个SSL了。SSL位于TCP/IP和HTTP协议之间。

HTTPS在传输数据之前需要客户端（浏览器）与服务端（网站）之间进行一次握手，在握手过程中将确立双方加密传输数据的密码信息。
握手过程的简单描述如下：
1.浏览器将自己支持的一套加密算法、HASH算法发送给网站。
2.网站从中选出一组加密算法与HASH算法，并将自己的身份信息以证书的形式发回给浏览器。证书里面包含了网站地址，加密公钥，
3.以及证书的颁发机构等信息。
3.浏览器获得网站证书之后，开始验证证书的合法性，如果证书信任，则生成一串随机数字作为通讯过程中对称加密的秘钥。
4.然后取出证书中的公钥，将这串数字以及HASH的结果进行加密，然后发给网站。
4.网站接收浏览器发来的数据之后，通过私钥进行解密，然后HASH校验，如果一致，则使用浏览器发来的数字串使加密一段握手消息发给浏览器。
5.浏览器解密，并HASH校验，没有问题，则握手结束。接下来的传输过程将由之前浏览器生成的随机密码并利用对称加密算法进行加密。
握手过程中如果有任何错误，都会使加密连接断开，从而阻止了隐私信息的传输。

#GsonTypes

```
       static Type getSuperclassTypeParameter(Class<?> subclass)
        {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class)
            {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
// use
                    callback.mType = getSuperclassTypeParameter(getClass());
                    if (callback.mType == String.class)
                    {
                        sendSuccessResultCallback(string, callback);
                    } else
                    {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }
```