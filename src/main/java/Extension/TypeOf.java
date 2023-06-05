package Extension;

public class TypeOf
{
    public static Class<?> typeOf(String s)
    {
        if (isDouble(s))
        {
            return Double.class;
        }
        return String.class;
    }
    public static boolean isDouble(String s)
    {
        try
        {
            double d = Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;

        }
    }


    public static int compareValues(String value1, String value2)
    {
        Class<?> type1 = TypeOf.typeOf(value1);
        Class<?> type2 = TypeOf.typeOf(value2);

        if (type1 == Double.class && type2 == Double.class)
        {
            return Double.compare(Double.parseDouble(value1), Double.parseDouble(value2));
        }
        else if (type1 == String.class && type2 == String.class)
        {
            return value1.compareTo(value2);
        }

        throw new IllegalArgumentException("Incompatible value types");
    }
}

