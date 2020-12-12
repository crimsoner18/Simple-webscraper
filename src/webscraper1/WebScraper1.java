package webscraper1;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.net.URLEncoder;
import java.util.List;

public class WebScraper1 {

    public static void main(String[] args) {
        String searchQuery = "Filipino"; //Search Query

        WebClient client = new WebClient();  //Start the htmlunit web client
        client.getOptions().setCssEnabled(false);  
        client.getOptions().setJavaScriptEnabled(false);  
        
        try {  
            String searchUrl = "https://newyork.craigslist.org/search/sss?sort=rel&query=" 
                            + URLEncoder.encode(searchQuery, "UTF-8"); //Web URL + Search URL
            HtmlPage page = client.getPage(searchUrl);
            
            List<HtmlElement> items = page.getByXPath("//li[@class='result-row']") ;
            if(items.isEmpty()){
                System.out.println("No items found !");
            }
            else{
                for(HtmlElement htmlItem : items){
                    HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//a[@class='result-title hdrlnk']"));
                    HtmlElement spanPrice = ((HtmlElement) htmlItem.getFirstByXPath(".//a/span[@class='result-price']")) ;
                    HtmlElement spanTitle = ((HtmlElement) htmlItem.getFirstByXPath(".//a[@class='result-title hdrlnk']")) ;
                    String spanLink = itemAnchor.getAttribute("href");

                    String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText() ;
                    String itemName = spanTitle == null ? "N/A" : spanTitle.asText() ;
                    String itemUrl = spanLink == null ? "" : spanLink ;

                    System.out.println( String.format("Name : %s \nUrl : %s \nPrice : %s", itemName, itemUrl, itemPrice));
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
