package com.uumai.crawer2.download.selenium;

import java.io.Serializable;

/**
 * Created by rock on 12/9/15.
 */
public class SeleniumScriptBase implements Serializable {

    protected UumaiSeleniumWebDriver driver;

    public void doaction(){

    }

    public UumaiSeleniumWebDriver getDriver() {
        return driver;
    }

    public void setDriver(UumaiSeleniumWebDriver driver) {
        this.driver = driver;
    }
}
