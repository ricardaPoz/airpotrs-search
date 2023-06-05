package CSVReader;

import FileReader.FileRead;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVReader
{
    private final String path;
    private final Map<String, ArrayList<Integer>> airportsLine;
    private final FileRead reader;

    public CSVReader(String path)
    {
        this.path = path;
        this.airportsLine = new HashMap<>();
        this.reader = new FileRead(this.path);
        this.read();
    }

    public CSVReader(URL resource)
    {
        this.path = resource.getPath();
        this.airportsLine = new HashMap<>();
        this.reader = new FileRead(this.path);
    }


    private void read()
    {
        this.reader.read((line, filePointer) ->
        {
            CSVRow row = new CSVRow(line);
            String nameAirPort = row.get(1).toLowerCase();
            this.airportsLine.computeIfAbsent(nameAirPort, k -> new ArrayList<>()).add(filePointer);
        });
    }

    public CSVRows filterByName (String startWith)
    {
        CSVRows rows = new CSVRows();

        var numRows = airportsLine.entrySet()
                .stream()
                .filter(entry ->entry.getKey().startsWith(startWith))
                .flatMap(entry -> entry.getValue().stream())
                .sorted()
                .collect(Collectors.toList());

        this.reader.readCurrentRows((line, numLine) ->
        {
            CSVRow row = new CSVRow(line);
            rows.add(row);
        }, numRows);

        return rows;
    }
}
