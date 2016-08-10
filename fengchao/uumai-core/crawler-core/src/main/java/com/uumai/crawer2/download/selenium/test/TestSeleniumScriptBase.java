package com.uumai.crawer2.download.selenium.test;

import com.uumai.crawer2.download.selenium.SeleniumScriptBase;

/**
 * Created by rock on 12/9/15.
 */
public class TestSeleniumScriptBase extends SeleniumScriptBase {

    public void doaction(){
        driver.get("http://item.jd.com/1644261435.html");
    }
}
