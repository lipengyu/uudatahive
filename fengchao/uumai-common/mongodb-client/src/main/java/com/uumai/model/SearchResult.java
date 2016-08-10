package com.uumai.model;

/**
 * Created with IntelliJ IDEA.
 * User: rock
 * Date: 1/14/15
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult {

    private String searchtext;
    private int currentpage;
    private long count;
    private int limit;
    private int endpagenum;

    private String pagehtml;

    public static void main(String[] arg)
    {
        SearchResult result=new SearchResult ();

        result.setSearchtext("new balance");
        result.setCurrentpage(1);
        result.setCount(101);
        result.setLimit(10);
        System.out.println("end page:"+result.getEndpagenum());
        System.out.println("pagehtml:"+result.getPagehtml());

    }

    /**
     *
     * @return
     *
     * <tr>
    <td colspan="11" class="ePage">
    <table width="100%" border="0" class="none">
    <tr>
    <td class="left_noborder">
    </td>
    <td class="right_noborder">
    <em>|&lt;</em>

    <em>&lt;&lt;</em>

    <span>1</span>
    <a href="/top/amazon--2.shtml">2</a>
    <a href="/top/amazon--3.shtml">3</a>
    <a href="/top/amazon--4.shtml">4</a>
    <a href="/top/amazon--5.shtml">5</a>
    <a href="/top/amazon--6.shtml">6</a>

    <a href="/top/amazon--6.shtml">&gt;&gt;</a>

    <a href="/top/amazon--200.shtml">&gt;|</a>
    <em>&nbsp;</em>
    </td>
    </tr>
    </table>
    </td>
    </tr>
     */

    public String getPagehtml() {
        if(pagehtml==null){
              StringBuffer sb=new StringBuffer();
              sb.append("<tr>\n" +
                      "      <td colspan=\"11\" class=\"ePage\">\n" +
                      "        <table width=\"100%\" border=\"0\" class=\"none\">\n" +
                      "        <tr>\n" +
                      "          <td class=\"left_noborder\">\n" +
                      "          </td>\n" +
                      "          <td class=\"right_noborder\">");

            if(currentpage==1){
                sb.append(" <em>|&lt;</em>\n" +
                        "            \n" +
                        "            <em>&lt;&lt;</em>");
            }else{
                //TODO add link for first 2 page

            }

            for(int i=-2;i<=2;i++){
                if(currentpage+i<1) continue;
                if(currentpage+i>getEndpagenum()) break;
                if(i==0){
                    sb.append("<span>"+currentpage+"</span>");
                }else{
                    sb.append("<a href=\"javascript:gotoPage('"+(currentpage+i)+"') \">"+(currentpage+i)+"</a>");
                }

            }


            if(currentpage==getEndpagenum()){
                    //TODO add link for last page
            }else{
                sb.append("");
            }
            sb.append("<em>&nbsp;</em>\n" +
                    "          </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "            </td>\n" +
                    "        </tr>");

            pagehtml=sb.toString();
        }
        return pagehtml;
    }

    public void setPagehtml(String pagehtml) {
        this.pagehtml = pagehtml;
    }

    public String getSearchtext() {
        return searchtext;
    }

    public void setSearchtext(String searchtext) {
        this.searchtext = searchtext;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getEndpagenum() {
        if(endpagenum==0){
            double p1=Math.floor(new Double(count/limit).doubleValue());
            if(count % limit==0){
                endpagenum=new Double(p1).intValue();
            } else{
                endpagenum=new Double(p1).intValue()+1;
            }
        }
        return endpagenum;
    }

    public void setEndpagenum(int endpagenum) {
        this.endpagenum = endpagenum;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
