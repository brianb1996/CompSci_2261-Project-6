package WebCrawler;
/*
Brian Bredahl
CMPSCI 2261 project 6
In this project, you are to create a web crawler class.

Two notes before we discuss what to do with the class:

    1) Make sure that you sleep for at least 0.5 seconds between hitting each link. This is to make sure that
       you do not ultimately DDOS any site that you wish to crawl. To get your program to sleep, check out this
       documentation from oracle: https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html
       (Links to an external site.)

    2) For the sake of not traversing advertisement sites, we will be using wikipedia links.

This class needs to do two things:

    1) Have a function that traverses 1000 links.

    2) Have a function that counts words, that is, every time you see a specific word, increment a number
       associated with that word. (Hint: Sets may be a good option for this). Note: Your function should
       count words, not html elements / attributes.

I do not necessarily care how you implement this project so long as you have a class that, at the end of your
1000 link traversal your class:
    - prints out the title of each page you've traversed
    - prints out a list of words encountered. Again, html elements / attributes should not be in this list
      (i.e. no '<p>' or '<div>'s should be in your list).

*/


import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

abstract class WebData {
    static final String baseWikipediaURL = "https://en.wikipedia.org";
    static final String wiki = "/wiki/";
    Map<String, String> traversedURLs;        // Used to hold the URLs that have been traversed
    Set<String> failedURLs;                   // Used to hold the failed URLs
    Queue<String> pendingURLs;                // Used to hold the pending URLs found
    Map<String, Integer> words;               // String is the key and Integer is the value, every time the word is found the integer will increment by one

    WebData(){
        this.traversedURLs = new TreeMap<>();
        this.failedURLs = new TreeSet<>();
        this.pendingURLs = new LinkedList<>();
        this.words = new TreeMap<>();

    }

    WebData(String searchTerm){
        this();
        this.pendingURLs.add(baseWikipediaURL + wiki + searchTerm);
    }


    //overides toString method - returns string with formatted data
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append("Page Titles:\n");
        this.traversedURLs.forEach((key, value) -> output.append("\t").append(value).append("\n"));
        output.append(String.format("%-30s%-5s %n", "\n\tWord", "Count"));
        this.words.forEach((key, value) -> output.append(String.format("%-30s%-5d %n", "\t" + key, value)));
        output.append("Number of Words Founds: ").append(this.words.size()).append("\n");
        output.append("Number of HTML Documents Traversed: ").append(this.traversedURLs.size()).append("\n");
        output.append("Number of URL's that failed: ").append(this.failedURLs.size()).append("\n");
        output.append("Number of URLs that were not used: ").append(this.pendingURLs.size()).append("\n");
        return output.toString();
    }

    /*
    This method can be used to export the data to a txt document named Web_Data.txt

    static void toTextDoc(WebData current){
    File webFile = new File("Web_Data.txt");
        try {
            PrintStream crawledData = new PrintStream(webFile);
            System.setOut(crawledData);
            System.out.println("Page Titles");
            current.traversedURLs.forEach((key, value) -> System.out.println("\t" + value));
            System.out.printf("%-30s%-5s %n", "\tWord", "Count");
            current.words.forEach((key, value) -> System.out.printf("%-30s%-5d %n", "\t" + key, value));
        }catch (IOException ex){
            System.setOut(System.out);
            System.out.println("Error: could not output to file");
        }
        System.setOut(System.out);
    }
    */
}





/*
    TODO
     1) Get user input from user
     2) Add input to main URL
     3) Traverse web page to find words on page
     4) Add words to the map words or increment the value associated with the word
     5) Add links to the pending URL queue if they don't exist in the traversed or failed sets
     6) stop once the no more URLs are in queue or 1000 links have been traversed
*/
