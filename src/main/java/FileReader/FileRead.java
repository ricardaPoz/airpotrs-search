package FileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileRead
{
    private final ClassLoader loader;
    private final String path;

    public FileRead(String path)
    {
        this.path = path;
        this.loader = getClass().getClassLoader();
    }

    public void read(final LineRead obj)
    {
        try (InputStream stream = this.loader.getResourceAsStream(this.path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)))
        {
            String line;

            for (int i = 0; (line = reader.readLine()) != null; i++)
            {
                obj.onReadExecute(line, i);
            }

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void readCurrentRow(final LineRead obj, int numRow)
    {
        try (InputStream stream = this.loader.getResourceAsStream(this.path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)))
        {
            String line = reader.lines().skip(numRow).limit(1).findFirst().orElse(null);
            obj.onReadExecute(line, numRow);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void readCurrentRows(final LineRead obj, List<Integer> numRows)
    {
        try (InputStream stream = this.loader.getResourceAsStream(this.path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)))
        {
            int currentLineNum = 1;
            String line;
            int index = 0;

            while ((line = reader.readLine()) != null && index < numRows.size())
            {
                if (currentLineNum == numRows.get(index) + 1)
                {
                    obj.onReadExecute(line, currentLineNum);
                    index++;
                }
                currentLineNum++;
            }

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}