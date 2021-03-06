package threadfilereading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class FileReading
{
    public FileReading(String fileName)
    {
        filesToRead_ = new ArrayList<>();
        readers_     = new ArrayList<>();

        try (BufferedReader inputStream = new BufferedReader(new FileReader(fileName) ) )
        {
            String currentLine;

            while ((currentLine = inputStream.readLine() ) != null )
            {
                System.out.printf("Got file %s\n", currentLine);
                readers_.add(new ThreadReader(currentLine, fileLines_) );
                filesToRead_.add(currentLine);
            }
        }

        catch (IOException e)
        {
            System.out.printf("Got IOException while parsing %s : %s\n", fileName, e);
        }
    }
    
    public void reset()
    {
        fileLines_.clear();
    }
    
    public void readSerially()
    {
        long startMsec = System.nanoTime();

        for (String fileName : filesToRead_)
        {
            try (BufferedReader inputStream = new BufferedReader(new FileReader(fileName) ) )
            {
                String currentLine;

                while ((currentLine = inputStream.readLine() ) != null )
                {
                    System.out.printf("File %s. Got line %s\n", fileName, currentLine);
                    fileLines_.add(currentLine);
                }
            }

            catch (IOException e)
            {
                System.out.printf("Got IOException while parsing %s : %s\n",
                                   fileName, e);
            }
        }
        
        long finMsec = System.nanoTime();
        System.out.println("\n*** Serial reading results ***\nTotal time: " +
                (finMsec - startMsec) + " msec.");
        
        for (String line : fileLines_)
        {
            System.out.println(line);
        }
    }
    
    public void readViaThreads() throws InterruptedException
    {
        long startMsec = System.nanoTime();

        for (ThreadReader reader : readers_)
        {
            reader.start();
        }
        
        for (ThreadReader reader : readers_)
        {
            reader.join();
        }
        
        long finMsec = System.nanoTime();
        System.out.println("\n*** Parallel reading results ***\nTotal time: " +
                (finMsec - startMsec) + " msec.");
        
        for (String line : fileLines_)
        {
            System.out.println(line);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            FileReading fileReading = new FileReading("Files.txt");
            fileReading.readSerially();
            fileReading.reset();
            fileReading.readViaThreads();
        }
        
        catch (InterruptedException e)
        {
            System.out.println("Got " + e);
        }
    }
    
    private ArrayList<String> filesToRead_;
    private ArrayList<ThreadReader> readers_;
    private Set<String> fileLines_ = Collections.synchronizedSet(new HashSet<>() );
}
