package com.company;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.Arrays;
import java.net.URL;
import java.net.URLConnection;


public class DownloadPage {

    public static void main(String[] args) throws IOException {

        // Make a URL to the web page
        URL url = new URL("http://www.ndbc.noaa.gov/station_page.php?station=46225");

        // Get the input stream through URL Connection
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();

        // Once you have the Input Stream, it's just plain old Java IO stuff.

        // For this case, since you are interested in getting plain-text web page
        // I'll use a reader and output the text content to System.out.

        // For binary content, it's better to directly read the bytes from stream and write
        // to the target file.


        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        List <String> myList = new ArrayList();
        boolean lock = false;
        // read each line and write to System.out
        while ((line = br.readLine()) != null) {

            //TODO:  clean up ScrubWebsiteData
            if(ScrubWebsiteData(line, myList, lock))
            {
                myList = ParseWebsiteData(line, myList);
            }

            //TODO: Write ParseWebsiteData to get only relevant information

        }

        //myList = ParseWebsiteData(myList);

        //FileWriter fw = new FileWriter("output_data.txt");
        PrintWriter pw = new PrintWriter(new FileWriter("out.txt"));
        for (int i = 0; i < myList.size(); i++) {
            //pw.write(myList.get(i));
            pw.println(myList.get(i));
        }

        pw.close();
    }

    public static boolean ScrubWebsiteData(String line, List <String> myList, boolean lock)
    {

        //System.out.println(line);
        if(line.contains("<td>Wave Height (WVHT)") || line.contains("ft</td>")) {
            //myList.add(line);
            //System.out.println(line + " 1");
            return true;
        }
        if(line.contains("<td>Dominant Wave Period") || line.contains("sec</td>")) {
            //myList.add(line);
            //System.out.println(line + " 2");
            return true;
        }
        if(line.contains("<td>Average Period (APD)")) {
            //myList.add(line);
            //System.out.println(line+ " 3");
            return true;
        }
        if(line.contains("<td>Mean Wave Direction") || line.contains("deg true )")) {
            //myList.add(line);
            //System.out.println(line + " 4");
            return true;
        }
        if(line.contains("<td>Water Temperature") || line.contains("deg;F</td>")) {
            //myList.add(line);
            //System.out.println(line + " 5");
            return true;
        }

        if(line.contains("<td>Swell Height")) {
            //myList.add(line);
            //System.out.println(line + " 6");
            return true;
        }
        if(line.contains("<td>Swell Period")) {
            //myList.add(line);
            //System.out.println(line + " 7");
            return true;
        }

        return false;
    }

    public static List <String> ParseWebsiteData(String line, List<String> myList)
    {
        line = line.replaceAll("<td>","");
        line = line.replaceAll("</td>", "");
        line = line.replaceAll("</tr>","");

        if(line.contains("</a>")) {
            line = line.substring(line.indexOf("</a>")+4);
            }

        System.out.println(line);
        myList.add(line);

        return myList;
    }

}
