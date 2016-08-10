package com.uumai.crawer.quartz.download.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by rock on 12/9/15.
 */
public class SeleniumActionBot {

    private WebDriver driver;
    private List<SeleniumActions> seleniumActionsList;
    public SeleniumActionBot(WebDriver driver, List<SeleniumActions> seleniumActionsList){
        this.driver=driver;
        this.seleniumActionsList=seleniumActionsList;
    }

    public  void doactions(){
        if(seleniumActionsList==null) return;
        for(SeleniumActions action:seleniumActionsList){
            doactioin(action);
        }
    }

    private void doactioin(SeleniumActions seleniumActions){
        String  command=seleniumActions.getCommand();
        String  target=seleniumActions.getTarget();
        String  value= seleniumActions.getValue();

        if("open".equalsIgnoreCase(command)){
            driver.get(command);
            return;
        }else if("sleep".equalsIgnoreCase(command)){
        	try {
				Thread.sleep(Long.parseLong(value));
				return;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }

        WebElement webElement=null;
        if(target.startsWith("id=")){
            webElement=driver.findElement(By.id(target.substring(3)));
        }else if(target.startsWith("css=")){
            webElement=driver.findElement(By.cssSelector(target.substring(4)));
//        }else if("className".equalsIgnoreCase(action.getByType())){
//            webElement=driver.findElement(By.className(action.getPath()));
        }else if(target.startsWith("link=")){
            webElement=driver.findElement(By.linkText(target.substring(5)));
        }else if(target.startsWith("name=")){
            webElement=driver.findElement(By.name(target.substring(5)));
//        }else if("partialLinkText".equalsIgnoreCase(action.getByType())){
//            webElement=driver.findElement(By.partialLinkText(action.getPath()));
//        }else if("tagName".equalsIgnoreCase(action.getByType())){
//            webElement=driver.findElement(By.tagName(action.getPath()));
        }else{
            //default is xpath
            webElement=driver.findElement(By.xpath(target));
        }


        if("clear".equalsIgnoreCase(command)){
            webElement.clear();
        }else if("sendKeys".equalsIgnoreCase(command)){
            webElement.sendKeys(value);
        }else if("click".equalsIgnoreCase(command)){
            //defalut is click
            webElement.click();
        } 
    }
}
