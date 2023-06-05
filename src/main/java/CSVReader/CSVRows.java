package CSVReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CSVRows
{
    private ArrayList<CSVRow> csvRows;
    public CSVRows()
    {
        this.csvRows = new ArrayList<>();
    }

    public int size()
    {
        return this.csvRows.size();
    }

    public CSVRow get(int index)
    {
        return this.csvRows.get(index);
    }

    public void add(CSVRow value)
    {
        this.csvRows.add(value);
    }

    public void addAll(Collection<CSVRow> value)
    {
        this.csvRows.addAll(value);
    }

    public Stream<CSVRow> stream()
    {
        return this.csvRows.stream();
    }

    public Stream<CSVRow> filter(Predicate<CSVRow> predicate)
    {
        return csvRows.stream().filter(predicate);
    }
}
