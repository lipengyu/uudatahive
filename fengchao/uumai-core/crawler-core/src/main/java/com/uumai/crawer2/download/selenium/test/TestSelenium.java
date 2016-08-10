package com.uumai.crawer2.download.selenium.test;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by rock on 5/6/15.
 */
public class TestSelenium {
    public static  void  main(String[] args){
//        System.getProperties().setProperty("webdriver.chrome.driver",
//                "/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket/shop_indexer/driver/chromedriver");

                // You may use any WebDriver implementation. Firefox is used here as an example
        WebDriver driver = new ChromeDriver();

// A "base url", used by selenium to resolve relative URLs
        String baseUrl = "http://www.oracle.com";

// Create the Selenium implementation
        Selenium selenium = new WebDriverBackedSelenium(driver, baseUrl);

// Perform actions with selenium

        selenium.open("http://www.oracle.com");


        WebElement webElement = driver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");
        System.out.print("html:"+content);

// Get the underlying WebDriver implementation back. This will refer to the
// same WebDriver instance as the "driver" variable above.
        WebDriver driverInstance = ((WebDriverBackedSelenium) selenium).getWrappedDriver();

//Finally, close the browser. Call stop on the WebDriverBackedSelenium instance
//instead of calling driver.quit(). Otherwise, the JVM will continue running after
//the browser has been closed.
        selenium.stop();
    }
}
