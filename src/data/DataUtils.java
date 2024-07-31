package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataUtils
{
    public static List<String> getCodes(String path)
    {
        List<String> codes = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(path)))
        {
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null)
            {
                String codigo = line.split(";")[0];
                codes.add(codigo);
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return codes;
    }

    public static void logProgress(String path, String text)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path, true)))
        {
            writer.write(text + "\n");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
