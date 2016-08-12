# uudatahive（蜂巢爬虫系统）
蜂巢爬虫系统是一套只需要定义XPath，就可实现爬取网站,APP的系统, 支持多种解析方式（XPath,正则表达式），多种下载方式（HttpClient库, PhantomJs, Selenium）,多种输出方式（Excel，MongoDB）。 可不做任何修改发布到Yarn,Docker,Mesos系统中实现分布式。

# 爬虫构架
构架一套基于JAVA的分布式爬虫系统，可单机运行， 可不做任何修改发布到Yarn,Docker,Mesos实现分布式。

# 框架特点
1. 可单机运行， 只需要定义简单的2-3行code，即可完成爬虫；

2. 可提交UUData分布式爬虫云, 直接支持分布式; (部分代码整理中，开源plan)

3. 下载方式： 支持java标准库, HttpClient库, PhantomJs, Selenium, MockDownload, FileDownload, ShellDownload;

4. 解析方式：  支持HTML XPath,  Json XPath, 正则表达式， 自定义扩展解析库

5. 保存方式：  支持本地文件输出: txt, Excel 格式， MongoDB输出（分布式）


# 最简单的爬虫：

爬取百度搜索某个关键字（java）的结果数量
```
public class BaiduSearch extends QuartzLocalDebugAppMaster {
	@Override
	public void dobusiness() throws Exception {
		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();     
		tasker.setUrl("http://www.baidu.com/s?wd=java");              //定义URL
		tasker.addXpath("result", "//div[@class='nums']/text()");     //定义结果的XPath
		putDistributeTask(tasker);                                     //执行
	}
	public static void main(String[] args) throws Exception {
	   new BaiduSearch().init().start();
	}
}
```
运行输出：
```
{"result":"百度为您找到相关结果约15,100,000个"}
```

# 单机爬虫原理

* AppMaster 创建一个tasker，定义url,Xpath, Download Type, result Format,提交tasker给Worker；

* Worker 拿到任务，初始化，从DownloadFactory构建下载器，下载， 解析，输出；

![image](https://raw.githubusercontent.com/kanxg/uudatahive/master/doc/uumai_fengchao.png)


# 下载安装
```
PreCondition:   安装了JDK8, Maven, Git
git clone https://github.com/kanxg/uudatahive
cd uudatahive
./bin/buildcore.sh             // 编译core
./bin/buildapp.sh   example    //编译 demo项目
```

运行 Demo 例子
```
cd build/fengchao
./uumai.sh com.uumai.crawer.quartz.gupiao.baidu.BaiduSearch
```

# 开发新的爬虫
```
cd crawler-website/crawler-example/
mvn eclipse:eclipse  or mvn idea:idea   生成项目
在eclipse or IntellJ 中 import项目
定义一个类继承自QuartzLocalDebugAppMaster，  添加 dobusiness() 方法
定义爬虫内容
调试
```
# 开发手册

[[开发手册]](https://kanxg.gitbooks.io/uudatahive/content/)

包含已经解析的Xpath，共享

[[Xpath参考]](https://kanxg.gitbooks.io/fegnchao_xpath/content/)


# 分布式爬虫原理

 * AppMaster 创建tasker，序列化，提交到任务池。

 * AdminAppMaster 提交工作池到分布式系统， 工作池启动，从任务池拿取任务，反序列化tasker， 构建worker，运行任务；

 ![image](https://raw.githubusercontent.com/kanxg/uudatahive/master/doc/uumai_distributed.png)

 # 提交分布式系统

 1. 当前系统支持5种分布式：

  a)  YARN,  Hadoop 分布式系统

  b)  Docker 虚拟化分布式

  c） Mesos 分布式系统 （未完成）

  d） Apache Storm (旧的系统，已弃用)

  e） Standalone  (旧的系统，已弃用)


2.  如何0代码修改，提交分布式系统？

# 问题解决以及爬虫定义分享

* 官方QQ群：uudata蜂巢系统交流群 117354543  
