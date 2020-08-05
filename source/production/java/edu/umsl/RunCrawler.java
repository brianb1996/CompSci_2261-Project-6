package edu.umsl;

import WebCrawler.Spider;

import java.util.Scanner;

//class used simply to run the program
public class RunCrawler {
    public static void main (String [] args){
        Scanner userInput = new Scanner(System.in);
        System.out.print("Please enter a keyword to scan Wikipedia for: ");
        Spider spider = new Spider(userInput.nextLine());
        spider.releaseSpider();
        System.out.println(spider.toString());
    }
}
