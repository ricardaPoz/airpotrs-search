package org.example;

import CSVReader.CSVRow;
import CSVReader.CSVRows;
import CSVReader.CSVReader;
import FilterParse.FilterParser;

import java.util.List;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {

        CSVReader reader = new CSVReader("airports.csv");

        Scanner scanner = new Scanner(System.in);
        String filter;
        do
        {
            try
            {
                System.out.print("Введите фильтр (или !quit для выхода): ");
                filter = scanner.nextLine().trim();

                if ("!quit".equalsIgnoreCase(filter)) {
                    break;
                }

                System.out.print("Введите начало имени аэропорта: ");
                String airportNamePrefix = scanner.nextLine().trim().toLowerCase();

                long startTime = System.currentTimeMillis();

                CSVRows rows = reader.filterByName(airportNamePrefix);
                FilterParser parser = new FilterParser(filter, rows);
                List<CSVRow> result = parser.parse();

                result.forEach(System.out::println);

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;

                System.out.println("Колличество найденных строк: " + result.size() + " Время затраченное на поиск: " + executionTime + " мс");
            }
            catch (Exception e)
            {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Пожалуйста, повторите ввод.");
            }
        }
        while (true);
    }
}
