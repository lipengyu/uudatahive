package com.uumai.crawer2.download.selenium.test;

import com.uumai.crawer.util.UumaiProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by rock on 12/4/15.
 */
public class TestPhantomJSDriver {

    static{
        System.getProperties().setProperty("phantomjs.binary.path",
                UumaiProperties.getUUmaiHome() + "/driver/phantomjs");
    }
    public static void  main(String[] args){
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
         DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
        desiredCapabilities.setCapability("loadImages",false);
        desiredCapabilities.setJavascriptEnabled(true);

        PhantomJSDriver driver = new PhantomJSDriver(desiredCapabilities);
        // And now use this to visit Google
        driver.get("file:///home/rock/kanxg/knowledges/bigdata/hadoop/zookeeper/ZooKeeper%E5%8E%9F%E7%90%86%E5%8F%8A%E4%BD%BF%E7%94%A8%20%20%20%E5%B0%8F%E6%AD%A6%E5%93%A5%E7%9A%84%E5%8D%9A%E5%AE%A2%20-%20%E5%B7%A6%E6%89%8B%E7%A8%8B%E5%BA%8F%E5%8F%B3%E6%89%8B%E8%AF%97.html");

        // Find the text input element by its name
//        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
//        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
//        element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        WebElement webElement = driver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");
        System.out.print("html:"+content);
        driver.close();
        driver.quit();
    }
}
