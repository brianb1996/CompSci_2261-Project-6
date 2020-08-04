package edu.umsl;

import java.util.Scanner;

public class RunCrawler {
    public static void main (String [] args){
        Scanner userInput = new Scanner(System.in);
        System.out.print("Please enter a keyword to scan Wikipedia for: ");
        Spider spider = new Spider(userInput.nextLine());
        spider.releaseSpider();
        WebData.display(spider);
    }
}
