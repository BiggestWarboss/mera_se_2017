package artshop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
* Создать класс Author. в нем должны быть те же поля, что и в Person,+ дополнительно:
* Массив написанных книг
 */
public class Author extends Person
{
    public Author(String finalName, String firstName, String country, String year)
    {
        super(finalName, firstName, country, year);
        books_ = new ArrayList<>();
    }

    public void addBook(Book book)
    {
        books_.add(book);
    }

    public ArrayList<Book> getBooks()
    {
        return books_;
    }

    public static Author createFromString(String data)
    {
        System.out.println(data); //TODO: remove

        String[] splitResult = data.split(DELIMITER);

        if (splitResult.length != 4) //number of fields for author
        {
            System.out.println("Incorrect data for author:\n" + data);
            return null;
        }
        
        return new Author(splitResult[0], splitResult[1], splitResult[2], splitResult[3]);
    }
    
    private ArrayList<Book> books_;
}
