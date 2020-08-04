package edu.umsl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class Spider extends WebData {
    Spider(String searchTerm){
        super(searchTerm);
    }

    public void releaseSpider(){
        crawl();
    }

    private void crawl(){
        final String documentBody = "mw-content-text";
        String currentURL = "";
        final int docCount = 1000;
        Document currentHTMLDoc = Jsoup.parse("");

        do{
            try {
                currentURL = this.pendingURLs.remove();
                currentHTMLDoc = Jsoup.connect(currentURL).get();
            }catch (IOException ex){
                this.failedURLs.add(currentURL);
                //break;
            }catch (NoSuchElementException ex){
                break;
            }
            this.pageTitles.add(currentHTMLDoc.title());
            runNode(currentHTMLDoc.getAllElements(), currentURL);
            this.traversedURLs.add(currentURL);
            try {
                Thread.sleep(500);
            }catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }

        }while((!this.pendingURLs.isEmpty()) && (this.traversedURLs.size() < 1000));
        System.out.println(this.traversedURLs.size() + "\t\t" + this.pendingURLs.size() + "\t\t" + this.failedURLs.size());
    }

    private void runNode(Elements currentNode, String currentURL){
        for (Element el1 : currentNode.next()) {
            if (el1.hasAttr("href") && !el1.attr("href").equals(currentURL)) {
                String URL = baseWikipediaUrl + el1.attr("href");
                if (!URL.contains("#") && URL.contains(wiki)) {
                    if (!this.traversedURLs.contains(URL) && !this.failedURLs.contains(URL) && !this.pendingURLs.contains(URL) ) {
                        this.pendingURLs.add(URL);
                    }
                }
            }
            if (el1.hasText()) {
                parseText(el1.text());
            }
        }
    }

//    private void traverseNode(Node currentNode){
//        currentNode.childNodes().forEach(node1 ->{
//            node1.siblingNodes().forEach(node2 ->{
//                node2.childNodes().forEach(node3 ->{
//                    traverseNode(node3);
//                    runNode(node3);
//                });
//            });
//        });
//
//    }

    private void parseText(String fromElement){
        String[] nodeContent = fromElement.split("[\\s+\\p{P}]");
        for(String currentWord : nodeContent){
            currentWord = currentWord.toLowerCase();
            if (currentWord.length() > 0 && currentWord.matches("[A-za-z]*\\b")) {
                int count = 1;
                if(this.words.containsKey(currentWord)){
                    count = this.words.get(currentWord);
                }
                this.words.put(currentWord, ++count);
            }
        }

    }
}
