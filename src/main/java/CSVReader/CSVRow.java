package CSVReader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVRow
{
    private static final String PATTERN = "(?<=^|,)([^,]*)(?=,|$)";
    private final String row;
    private final ArrayList<String> elements;

    public CSVRow(String row)
    {
        this.elements = new ArrayList<>();
        this.row = row;
        this.parse();
    }

    private void parse()
    {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(this.row);

        while (matcher.find())
        {
            var element = matcher.group(0).replace("\"", "");
            elements.add(element);
        }
    }

    public String get(int index)
    {
        return elements.get(index);
    }

    public String getRow()
    {
        return this.row;
    }

    @Override
    public String toString()
    {
        return String.format("\"%s\"[%s]", elements.get(1), row);
    }
}

