本项目是大名鼎鼎的keepassdroid的安卓版。
keepass是一款单机模式的密码管理工具，有PC版，而此项目是对应的安卓版。
本项目fork自项目：[bpellin/keepassdroid](https://github.com/bpellin/keepassdroid)，并对其进行了如下修改：

* 添加指纹识别功能，当第一次输入密码并进入程序后，第二次即可使用指纹功能，面去每次都输入密码的繁琐。
* 添加移动密钥文件至apk私有空间的功能。如果你的密码库使用了密钥，通常密钥是放置在公共存储空间的，这会使你的密钥存在安全隐患。本程序可将密钥移动至程序自身的私有空间，从而保证了密钥文件的安全。
