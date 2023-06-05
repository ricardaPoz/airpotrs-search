package FilterParse;

import CSVReader.CSVRow;
import CSVReader.CSVRows;
import Extension.TypeOf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FilterParser
{
    private static final String EQUAL_OPERATOR = "=";
    private static final String NOT_EQUAL_OPERATOR = "<>";
    private static final String GREATER_THAN_OPERATOR = ">";
    private static final String LESS_THAN_OPERATOR = "<";

    private final String[] tokens;
    private int currentTokenIndex;
    private CSVRows csvRows;

    public FilterParser(String filter, CSVRows csvRows)
    {
        this.tokens = tokenize(filter);
        this.currentTokenIndex = 0;
        this.csvRows = csvRows;
    }

    public List<CSVRow> parse()
    {
        if (this.tokens.length == 0)
        {
            return csvRows.stream()
                    .sorted((o1, o2) -> o1.get(1).compareToIgnoreCase(o2.get(1)))
                    .collect(Collectors.toList());
        }

        Node filterTree = parseExpression();
        return evaluateNode(filterTree).stream()
                .sorted((o1, o2) -> o1.get(1).compareToIgnoreCase(o2.get(1)))
                .collect(Collectors.toList());
    }

    private Node parseExpression()
    {
        Node leftOperand = parseTerm();

        while (currentTokenIndex < tokens.length)
        {
            String operator = tokens[currentTokenIndex];
            if (operator.equals(")"))
            {
                break;
            }
            currentTokenIndex++;

            Node rightOperand = parseTerm();
            leftOperand = new Node(operator, leftOperand, rightOperand);
        }

        return leftOperand;
    }

    private Node parseTerm()
    {
        String token = tokens[currentTokenIndex];
        currentTokenIndex++;

        if (token.equals("("))
        {
            Node expression = parseExpression();
            if (!tokens[currentTokenIndex].equals(")"))
            {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            currentTokenIndex++;
            return expression;
        } else
        {
            return new Node(token);
        }
    }

    private List<CSVRow> evaluateNode(Node node)
    {
        if (node.isLeaf())
        {
            return evaluateCondition(node.getValue());
        }

        List<CSVRow> leftResult = evaluateNode(node.getLeft());
        List<CSVRow> rightResult = evaluateNode(node.getRight());

        if (node.getValue().equals("&"))
        {
            return intersect(leftResult, rightResult);
        } else if (node.getValue().equals("|"))
        {
            return union(leftResult, rightResult);
        }

        throw new IllegalArgumentException("Invalid operator: " + node.getValue());
    }

    private List<CSVRow> evaluateCondition(String condition)
    {
        String[] parts;

        if (condition.contains(EQUAL_OPERATOR))
        {
            parts = condition.split(EQUAL_OPERATOR);
            return equal(parts[0].trim(), parts[1].trim());
        } else if (condition.contains(NOT_EQUAL_OPERATOR))
        {
            parts = condition.split(NOT_EQUAL_OPERATOR);
            return notEqual(parts[0].trim(), parts[1].trim());
        } else if (condition.contains(GREATER_THAN_OPERATOR))
        {
            parts = condition.split(GREATER_THAN_OPERATOR);
            return greater(parts[0].trim(), parts[1].trim());
        } else if (condition.contains(LESS_THAN_OPERATOR))
        {
            parts = condition.split(LESS_THAN_OPERATOR);
            return less(parts[0].trim(), parts[1].trim());
        } else
        {
            throw new IllegalArgumentException("Invalid condition: " + condition);
        }
    }

    private List<CSVRow> equal(String column, String value)
    {
        int columnIndex = getColumnNumber(column) - 1;
        String clearValue = value.replaceAll("[\"’'`]", "").toLowerCase();
        return csvRows.filter(csvRow -> csvRow.get(columnIndex).toLowerCase().equals(clearValue)).collect(Collectors.toList());
    }

    private List<CSVRow> notEqual(String column, String value)
    {
        int columnIndex = getColumnNumber(column) - 1;
        String clearValue = value.replaceAll("[\"’'`]", "").toLowerCase();
        return csvRows.filter(csvRow -> !csvRow.get(columnIndex).toLowerCase().equals(clearValue)).collect(Collectors.toList());
    }

    private List<CSVRow> greater(String column, String value)
    {
        int columnIndex = getColumnNumber(column) - 1;
        String clearValue = value.replaceAll("[\"’'`]", "").toLowerCase();

        return csvRows.filter(csvRow -> TypeOf.compareValues(csvRow.get(columnIndex).toLowerCase(), clearValue) > 0).collect(Collectors.toList());
    }

    private List<CSVRow> less(String column, String value)
    {
        int columnIndex = getColumnNumber(column) - 1;
        String clearValue = value.replaceAll("[\"’'`]", "").toLowerCase();

        return csvRows.filter(csvRow -> TypeOf.compareValues(csvRow.get(columnIndex).toLowerCase(), clearValue) < 0).collect(Collectors.toList());
    }

    private int getColumnNumber(String column)
    {
        int res = 0;
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(column);
        while (matcher.find())
        {
            res = Integer.parseInt(matcher.group(0));
        }
        return res;
    }

    private String[] tokenize(String filter)
    {
        String[] tokens = filter.split("\\s+|(?<=\\()|(?=\\()|(?<=\\))|(?=\\))");
        List<String> tokenList = new ArrayList<>();

        for (String token : tokens)
        {
            if (!token.isEmpty())
            {
                tokenList.add(token.trim());
            }
        }

        return tokenList.toArray(new String[0]);
    }


    private static List<CSVRow> intersect(List<CSVRow> list1, List<CSVRow> list2)
    {
        List<CSVRow> intersection = new ArrayList<>(list1);
        intersection.retainAll(list2);
        return intersection;
    }

    private static List<CSVRow> union(List<CSVRow> list1, List<CSVRow> list2)
    {
        List<CSVRow> union = new ArrayList<>(list1);
        union.addAll(list2);
        return union;
    }
}
