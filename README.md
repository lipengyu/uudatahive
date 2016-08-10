# uudatahive
uudatahive is a distributed crawler  system
蜂巢爬虫系统 是一套只需要定义XPath，就可实现爬取网站,APP的系统, 支持多种解析方式（XPath,正则表达式），多种下载方式（HttpClient库, PhantomJs, Selenium）,多种输出方式（Excel，MongoDB）。 可不做任何修改发布到Yarn,Docker,Mesos系统中实现分布式。

# 爬虫构架
构架一套基于JAVA的分布式爬虫系统，可单机运行， 可不做任何修改发布到Yarn,Docker,Mesos实现分布式。

# 框架特点
1. 可单机运行， 只需要定义简单的2-3行code，即可完成爬虫；

2. 可提交UUData分布式爬虫云, 直接支持分布式; (部分代码整理中，开源plan)

3. 下载方式： 支持java标准库, HttpClient库, PhantomJs, Selenium, MockDownload, FileDownload, ShellDownload;

4. 解析方式：  支持HTML XPath,  Json XPath, 正则表达式， 自定义扩展解析库

5. 保存方式：  支持本地文件输出: txt, Excel 格式， MongoDB输出（分布式）


# 最简单的爬虫：

爬取百度搜索某个关键字的结果数量
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
		BaiduSearch master = new BaiduSearch();
		master.init();
		master.start();
	}
}
```
运行输出：
```
{"result":"百度为您找到相关结果约15,100,000个"}
```


# 下载安装
