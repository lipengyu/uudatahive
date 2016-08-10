package com.uumai.crawler2.util.htmlparse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by rock on 4/9/15.
 */
public class JsoupUtil {
    String rawText;

    String  baseUri;
    private Document doc;

        public JsoupUtil(    String rawText,String  baseUri){
            this.rawText=rawText;
            this.baseUri=baseUri;
        }

     public Document getDoc() {
        if(doc==null){
            if(baseUri!=null){
                doc = Jsoup.parse(rawText, baseUri);
            }else{
                doc = Jsoup.parse(rawText);
            }
        }
        return doc;
    }

    public Elements getElementsByTag(String tagname){
       return this.getDoc().getElementsByTag(tagname);
    }

    public  static void  main(String[] argx) throws Exception{
        File file=new File("/home/rock/kanxg/downloadfiles/amazon/B007FNCBQQ.html");
        System.out.println("file is"+ file.getName());
        StringBuffer buffer=new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        JsoupUtil jsoupUtil=new JsoupUtil(buffer.toString(), "");
        Elements links = jsoupUtil.getElementsByTag("a");
        for (Element link : links) {
            String s = link.attr("abs:href");
            if (s.startsWith("http://www.amazon.com")) {
                    System.out.println("product url:"+ s);

            }
        }
    }

}
