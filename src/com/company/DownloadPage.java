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

        BufferedReader br = ConnectToWebsite(url);

        List <String> myList = new ArrayList();

        // read each line and write to System.out
        myList = GetWebsiteData(br, myList);

        //Formatting website Data to uniform formatting
        myList = FormatWebsiteData(myList);

        //View data stored in myList at end of program call
        DebugLogFile(myList);

    }

    public static List<String> GetWebsiteData(BufferedReader br, List<String> myList) throws IOException
    {
        String line;

        while ((line = br.readLine()) != null) {
            //Gets all website data needed from National Buoy Data Center
            if (ScrubWebsiteData(line, myList)) {
                //Save only relevant information from Website into array;
                myList = ParseWebsiteData(line, myList);
            }

        }
        return myList;
    }

    public static boolean ScrubWebsiteData(String line, List <String> myList)
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

        return line.contains("<td>Swell Period");

    }

    public static List<String> ParseWebsiteData(String line, List<String> myList)
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

    public static List<String> FormatWebsiteData(List<String> myList)
    {
        for (int i = 0; i < 9; i+=2) {
            String tempLine = myList.get(i + 1);
            String newLine = myList.get(i) + tempLine;
            newLine = newLine.replaceAll("\t","");
            myList.set(i,newLine);
        }

        //TODO: Remove elements in list that are irrelevant.

        return myList;
    }

    public static BufferedReader ConnectToWebsite(URL url) throws IOException
    {
        BufferedReader br;
        InputStream is;
        URLConnection con;

            con = url.openConnection();

            is = con.getInputStream();


            //BufferedReader br;
            br = new BufferedReader(new InputStreamReader(is));

        return br;
    }

    public static void DebugLogFile(List<String> myList) throws IOException
    {
        PrintWriter pw = new PrintWriter(new FileWriter("out.txt"));
        for (String aMyList : myList) {
            pw.println(aMyList);
            System.out.println(aMyList);
        }

        pw.close();
    }
}
