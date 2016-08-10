package com.uumai.crawer2;

import com.uumai.crawer2.CookieManager.CrawlerCookie;
import com.uumai.crawler.selector.Html;
import com.uumai.crawler.selector.Json;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rock on 12/23/15.
 */
public class CrawlerResult {

    private String url;
    private int returncode;

    private String rawText;
    private Html html;
//    private String  baseUri;
    private Json json;

//    private Document doc;

    private List<CrawlerCookie> cookies;


    public void addNewCookies(List<CrawlerCookie> newcookies){
        if(cookies==null){
            cookies=new ArrayList<CrawlerCookie>();
        }
        for(CrawlerCookie newcrawlerCookie:newcookies){
            this.cookies.add(newcrawlerCookie);
        }
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

//    public Document getDoc() {
//        if(doc==null){
//            if(baseUri!=null){
//                doc = Jsoup.parse(rawText, baseUri);
//            }else{
//                doc = Jsoup.parse(rawText);
//            }
//        }
//        return doc;
//    }

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

//    public String getBaseUri() {
//        if(baseUri==null){
//            try {
////                System.out.println("url:"+this.url);
//                URL url = new URL(this.url);
//                String path = url.getFile().substring(0, url.getFile().lastIndexOf('/'));
//                String base = url.getProtocol() + "://" + url.getHost() + path;
//                baseUri=base;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return baseUri;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHtml(Html html) {
        this.html = html;
    }

    public void setJson(Json json) {
        this.json = json;
    }



    public List<CrawlerCookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<CrawlerCookie> cookies) {
        this.cookies = cookies;
    }

//    public void setBaseUri(String baseUri) {
//        this.baseUri = baseUri;
//    }
//
//    public void setDoc(Document doc) {
//        this.doc = doc;
//    }

    public Html getHtml() {
        if (html == null) {
            html = new Html(rawText);
        }
        return html;
    }


    /**
     * get json content of page
     *
     * @return json
     * @since 0.5.0
     */
    public Json getJson() {
        if (json == null) {
            json = new Json(rawText);
        }
        return json;
    }
}
