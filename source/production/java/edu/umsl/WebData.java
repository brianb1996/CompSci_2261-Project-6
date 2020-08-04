package edu.umsl;
/*
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


import java.util.*;

abstract class WebData {
    protected static final String baseWikipediaUrl = "https://en.wikipedia.org";
    protected static final String wiki = "/wiki/";
    protected Set<String> traversedURLs;      // Used to hold the URLs that have been traversed
    protected Set<String> failedURLs;         // Used to hold the failed URLs
    protected Queue<String> pendingURLs;      // Used to hold the pending URLs found
    protected Map<String, Integer> words;// String is the key and Integer is the value, every time the word is found the integer will increment by one
    protected List<String> pageTitles;
    WebData(){
        this.traversedURLs = new TreeSet<>();
        this.failedURLs = new TreeSet<>();
        this.pendingURLs = new LinkedList<>();
        this.words = new TreeMap<>();
        this.pageTitles = new LinkedList<>();
    }
    WebData(String searchTerm){
        this();
        this.pendingURLs.add(baseWikipediaUrl + wiki + searchTerm);
    }

    static void display(WebData current){
        System.out.println("Page Titles");
        current.pageTitles.forEach(title -> System.out.println("\t" + title));
//        System.out.printf("%-30s%-5s %n","\tWord", "Count" );
//        current.words.forEach((key, value) -> System.out.printf("%-30s%-5d %n", "\t" + key, value));
    }
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
