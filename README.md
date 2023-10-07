# S-DES算法

## 第1关：基本测试      
根据S-DES算法编写和调试程序，提供GUI解密支持用户交互。输入可以是8bit的数据和10bit的密钥，输出是8bit的密文。

GUI界面如下：

![image](https://github.com/newnameno/DES/assets/147228318/f9323f3b-d0e1-41c5-a473-079472c75af1)

输入8位明文和10位密钥后加密（翻译部分未完全显示）：

![image](https://github.com/newnameno/DES/assets/147228318/5079fece-dcbf-484e-b712-79f5bb5ddbe4)

解密后的明文正确

## 第2关：交叉测试
考虑到是算法标准，所有人在编写程序的时候需要使用相同算法流程和转换单元(P-Box、S-Box等)，以保证算法和程序在异构的系统或平台上都可以正常运行。设有A和B两组位同学(选择相同的密钥K)；则A、B组同学编写的程序对明文P进行加密得到相同的密文C；或者B组同学接收到A组程序加密的密文C，使用B组程序进行解密可得到与A相同的P。

明文是01101110，密钥为1010101010，我们小组的加密密文是：

![image](https://github.com/newnameno/DES/assets/147228318/e471bb66-e8ff-4f68-87a1-6e676e61d947)

交叉小组的加密结果是：

![image](https://github.com/newnameno/DES/assets/147228318/f9c42434-8704-4856-8163-5d7d8762a3f9)

## 第3关：扩展功能
考虑到向实用性扩展，加密算法的数据输入可以是ASII编码字符串(分组为1 Byte)，对应地输出也可以是ACII字符串(很可能是乱码)。

明文：xc 密钥：1010101010，加密后：

![image](https://github.com/newnameno/DES/assets/147228318/4d5d5882-e67c-4f02-963a-a5bfd23a203d)

## 第4关：暴力破解
假设你找到了使用相同密钥的明、密文对(一个或多个)，请尝试使用暴力破解的方法找到正确的密钥Key。在编写程序时，你也可以考虑使用多线程的方式提升破解的效率。请设定时间戳，用视频或动图展示你在多长时间内完成了暴力破解。

以明文10101010和密文00001111为例，做暴力破解：

![~(_Y{)~307LJ (X{DX76I5N](https://github.com/newnameno/DES/assets/147228318/e6c1ed2a-5410-40d1-abb4-0c98af11dbcf)

可见，该例子中存在多个密钥。

## 第5关：封闭测试
根据第4关的结果，进一步分析，对于你随机选择的一个明密文对，是不是有不止一个密钥Key？进一步扩展，对应明文空间任意给定的明文分组P_{n}，是否会出现选择不同的密钥K_{i}\ne K_{j}加密得到相同密文C_n的情况？

如上所述，存在一个明密文对映射多个密钥的情况。

关于是否任意一个明密文对都对应了不止一个密钥的情况：
我们采用穷举法，
