package gettingtophilosophy;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import cis3089.mybinarysearchtree.MyBinarySearchTree;
import  org.jsoup.select.Selector;
//import cis3089.mybinarysearchtree.Node; 

/**
 *
 * @author bdddo
 */
public class GettingToPhilosophy
{
    // These are used to limit the number of requests to Wikipedia per second.
    // They are not needed for the learning exercise, but are need for the assignment.
    private static long lastRequestTime = -1;
    private final static long MIN_INTERVAL = 1000;
    
    // this Gets the MyBinarySearchTree class and creates a vaualble.
    // it gets binary seack treee to keep track of visited pages to avoid infintate loops.
    private static final MyBinarySearchTree<String> visitedPages = new MyBinarySearchTree<>();
    
    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        // URL to connect to. this is is the starting point for the path to philosophy
        //String url = "/wiki/Java_(programming_language)";
        //String url = "/wiki/Terrain";
        String url = "/wiki/Genre";
        //String url = "/wiki/Duluth,_Minnesota";
        //String url = "/wiki/College_of_St._Scholastica";
        //String url = "/wiki/Chicken";
        //String url = "/wiki/River";
       //String url = "/wiki/Geometry";
       // String url = "/wiki/Coffee";
        //String url = "/wiki/Txorierri_line"; //-> Contains parentheses.
       // String url = "/wiki/2023_NCAA_Division_I_baseball_season";
        
        
        // this is the target URal we want to reach
        String target = "/wiki/Philosophy";
        int counter = 1;
        
        System.out.println("Getting to Philosophy");
        System.out.println("---------------------");
        System.out.println(counter + ". " + url);
        counter++;
        
        // This code will add the starting URL to the visited pages tree.
        visitedPages.add(url);
        
        // this loops the targe page untial (/wiki/philosophy) is reached.
        while (!url.equals(target))
        {
            // this will get the first valid hyperlink on the page.
            url = getFirstHyperlink(url);
            
            // this will check if the page has already been visited to avoid loops. and then if so will stop the program.
            if (visitedPages.containsValue(url))
            {
                System.out.println("A loop has been detected! We cannot reach the philosphy page.");
                break;
            }
            
            // this will decative if there is no valid hyperlink found and will stop the program.
            if (url.isEmpty())
            {
                System.out.println("No valid hyperlink found, stopping.");
                break;
            }
            
            // this will print the next URal and add it to the visited pages.
            System.out.println(counter + ". " + url);           
            counter++;
            visitedPages.add(url);
            
            // this calls a method to limited the rate to avoid overwhelwing wikipedia.
            sleepIfNeeded();
        }
    }
    
    // this method will get tyhe first valid hyperlink on the given wikipedia page.
    private static String getFirstHyperlink(String url) throws IOException
    {
        // this will establish a connection to the wikipedia page
        Connection connection = Jsoup.connect("https://en.wikipedia.org" + url);
        Document document = connection.get();
        
        //this will select th emain content area of the page where the aticle text is located.
        Element content = document.select("div[id=mw-content-text]").first();        
        Elements paragraphs = content.getElementsByTag("p");
        
        // this for loop will iterate through each parapgraph to find the first valid hyperlink.
        for (Element paragraph : paragraphs)
        {            
            for (Node child : paragraph.childNodes())
            {
                // this will check if the child node is a hyperlink and is not within parentheses.
               if (child.nodeName().equals("a") && !isInParentheses(child))
               //if (child.nodeName().equals("a"))
                {
                    Element link = (Element) child;
                    String href = link.attr("href");
                    
                    // this will check if the link is a valid wikipedia link.
                    if (isValidLink(href))
                    {
                        if (!url.equals(href))
                        {
                            // this will return the first valid hyperlink that is foud.
                              return href;
                        }
                      
                    }
                    
                }
            }
        }
        
        // This method call is for testing purposes. It will print out the 
        // hyperlinks in this node and will recursively call itself on the
        // node's children and the children's children, etc.
        // Comment this out once you have things working.
       //printHyperlinks(content);
        
       // this will return an empty string if not valid hyperlink is found.
        return "";
    }
    
    // this method is to check if a hyperlink is valid for traversal.
    private static boolean isValidLink(String href)
    {
        return href.startsWith("/wiki/") && !href.contains(":");
    }
    

    /**
     * this method is to detemrine if a given node is whith parenttheses.
     * @param node
     * @return
     */
    public static boolean isInParentheses(Node node)
    {
        if (node.hasParent())
        {
            // this will get the HTML of the parent node. And find the postion of the first opening parenthesis. Find the positon of the first closing parenthesis. and lastly find the position of the node within the parent.
            String parentHtml = node.parent().outerHtml();
            int openIndex = parentHtml.indexOf('(');
            int closeIndex = parentHtml.indexOf(')');
            int nodeIndex = parentHtml.indexOf(node.outerHtml());
            
            // this will check if the node is located between an opening and a closing parenthesis.
            return openIndex != -1 && closeIndex != -1 && nodeIndex > openIndex && nodeIndex < closeIndex;
        }
        
        // this will return false if the node has no parent or is not within parentheses.
        return false;
    }
    
    private static void printHyperlinks(Node node)
    {
        if (node.nodeName().equals("a"))
        {
            // If so, print it out.
            System.out.println("Hyperlink found: " + node);
            
            // Sometimes it can be helpful to examine the parent node too.
            // You may want to uncomment this occasionally.
            //System.out.println("Hyperlink parent: " + node.parent());
            //System.out.println();
        }
        
        // Go through the children nodes and find their hyperlinks too.       
        for (Node child: node.childNodes()) {
            printHyperlinks(child);
        }
    }
    
    /**
     * Rate limits by waiting at least the minimum interval between requests.
     * Not needed for the learning exercise.  Need for the assignment.
     */
    private static void sleepIfNeeded() {
        if (lastRequestTime != -1) {
            long currentTime = System.currentTimeMillis();
            long nextRequestTime = lastRequestTime + MIN_INTERVAL;
            
            if (currentTime < nextRequestTime) {
                try {
                        //System.out.println("Sleeping until " + nextRequestTime);
                        Thread.sleep(nextRequestTime - currentTime);
                } catch (InterruptedException e) {
                        System.err.println("Warning: sleep interrupted in fetchWikipedia.");
                }
            }
        }
        
        lastRequestTime = System.currentTimeMillis();
    }
}
