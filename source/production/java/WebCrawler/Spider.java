package WebCrawler;
import WebCrawler.WebData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.NoSuchElementException;

//Spider is used to crawl the web and extends WebData to access the Sets and Maps used to Store all the data found while crawling
public class Spider extends WebData {
    public Spider(String searchTerm){
        super(searchTerm);
    }

    //releaseSpider is used to access the crawl method so that all of crawls data is kept private
    public void releaseSpider(){
        crawl();
    }

    //crawl is used to retrieves the HTML documents associated with URLs and calls findLinks and parseText to collect the data for the HTML documents
    private void crawl(){
        String currentURL = "";                                                         //Stores current URL to the current HTML document
        Document currentHTMLDoc;                                                        //used to store the current HTML document
        do{
            try {
                currentURL = this.pendingURLs.remove();                                 // URLs are retrieved from the pendingURLs LinkedList implemented as a quue
                currentHTMLDoc = Jsoup.connect(currentURL).get();
                this.traversedURLs.put(currentURL, currentHTMLDoc.title());
                findLinks(currentHTMLDoc.getElementsByAttribute("href"), currentURL);
                parseText(currentHTMLDoc.body().text());
                Thread.sleep(500);
            } catch (IOException ex) {
                this.failedURLs.add(currentURL);
            } catch (NoSuchElementException ex) {
                break;
            }catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }while((!this.pendingURLs.isEmpty()) && (this.traversedURLs.size() < 1000));
        if(this.traversedURLs.size() < 1000){
            System.out.println("Only " + this.traversedURLs.size() + " links were searched");
        }
    }

    //findLinks method takes elements as argument and checks the href attribute for vaild URLs and partial URL's to search
    private void findLinks(Elements linkElements, String currentURL){
        String url;                                                                 //Used to hold the url found in a href attribute
        for (Element el1 : linkElements) {
            String href = el1.attr("href");                               // creates string to hold the value of the href attribute
            if (href.contains(wiki) && !href.equals(currentURL)){                   // if the href attribute has the wiki string and doesnt equal the current URL
                if(href.contains("https://")){                                      // if the URL is a full URL
                    if(href.contains("en.")) {                                      // if the URL contains en. to show that the link is in english
                        url = href;
                    }else{
                        continue;                                                   // if the URl doesnt have en. it will not be added to the list to be searched
                    }
                }else{                                                              // other wise if the url is an incomplete link
                    url = baseWikipediaURL + href;
                }
                if (!this.traversedURLs.containsKey(url) && !this.failedURLs.contains(url) && !this.pendingURLs.contains(url)) { //if the Url doesnt exist in the set, map, or queue
                    this.pendingURLs.add(url);
                }
            }
        }
    }

    //method used to parse the text into seperate words and add the words to the map words
    private void parseText(String fromElement){
        String[] documentContent = fromElement.split("[\\s+\\p{P}]"); // regular expression to split the string into separate words
        for(String currentWord : documentContent){
            currentWord = currentWord.toLowerCase();
            if (currentWord.length() > 0 && currentWord.matches("[A-za-z]*\\b")) { //regular expression to get letters only
                int count = 0;                                                           // count is used to set the value of the map, count is set to zero so the prefix increment operation will set the map value to one
                if(this.words.containsKey(currentWord)){                                 // if the words map contains the key already
                    count = this.words.get(currentWord);
                }
                this.words.put(currentWord, ++count);
            }
        }
    }

}
