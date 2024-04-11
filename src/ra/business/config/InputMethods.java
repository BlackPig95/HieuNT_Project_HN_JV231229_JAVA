package ra.business.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

public class InputMethods
{
    private InputMethods()
    {
    }

    private final static Scanner scanner = new Scanner(System.in);
    private static final String ERROR_ALERT = "Định dạng không hợp lệ, hoặc ngoài phạm vi! Vui lòng thử lại.";
    private static final String EMPTY_ALERT = "Trường nhập vào không thể để trống! Vui lòng thử lại.";
    private static final String DATE_FORMAT = "Vui lòng nhập đúng định dạng: dd/MM/yyyy";


    private static String getInput()
    {
        return scanner.nextLine().strip();
    }

    public static String nextLine()
    {
        while (true)
        {
            String result = scanner.nextLine();
            if (result.isBlank())
            {
                System.out.println(CONSOLECOLORS.RED + EMPTY_ALERT + CONSOLECOLORS.RESET);
                continue;
            }
            return result;
        }
    }

    public static char nextChar()
    {
        while (true)
        {
            String newChar = getInput();
            if (newChar.length() > 1)
            {
                System.out.println(CONSOLECOLORS.RED + "Vui lòng nhập chỉ duy nhất 1 ký tự" + CONSOLECOLORS.RESET);
                continue;
            }
            return newChar.charAt(0);
        }
    }

    public static boolean nextBoolean()
    {
        while (true)
        {
            String bool = nextLine();
            if (bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("false"))
                return Boolean.parseBoolean(bool);
            System.out.println(CONSOLECOLORS.RED + "Vui lòng nhập chính xác true hoặc false" + CONSOLECOLORS.RESET);
        }
    }

    public static byte nextByte()
    {
        while (true)
        {
            try
            {
                return Byte.parseByte(getInput());
            } catch (NumberFormatException errException)
            {
                System.out.println(CONSOLECOLORS.RED + ERROR_ALERT + CONSOLECOLORS.RESET);
            }
        }
    }

    public static short nextShort()
    {
        while (true)
        {
            try
            {
                return Short.parseShort(getInput());
            } catch (NumberFormatException errException)
            {
                System.out.println(CONSOLECOLORS.RED + ERROR_ALERT + CONSOLECOLORS.RESET);
            }
        }
    }

    public static int nextInt()
    {
        while (true)
        {
            try
            {
                return Integer.parseInt(getInput());
            } catch (NumberFormatException errException)
            {
                System.out.println(CONSOLECOLORS.RED + ERROR_ALERT + CONSOLECOLORS.RESET);
            }
        }
    }

    public static long nextLong()
    {
        while (true)
        {
            try
            {
                return Long.parseLong(getInput());
            } catch (NumberFormatException errException)
            {
                System.out.println(CONSOLECOLORS.RED + ERROR_ALERT + CONSOLECOLORS.RESET);
            }
        }
    }

    public static float nextFloat()
    {
        while (true)
        {
            try
            {
                return Float.parseFloat(getInput());
            } catch (NumberFormatException errException)
            {
                System.out.println(CONSOLECOLORS.RED + ERROR_ALERT + CONSOLECOLORS.RESET);
            }
        }
    }

    public static double nextDouble()
    {
        while (true)
        {
            try
            {
                return Double.parseDouble(getInput());
            } catch (NumberFormatException errException)
            {
                System.out.println(CONSOLECOLORS.RED + ERROR_ALERT + CONSOLECOLORS.RESET);
            }
        }
    }

    public static Date nextDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat(CONSTANT.DDMMYYYY);
        while (true)
        {
            try
            {
                return formatter.parse(getInput());
            } catch (ParseException errException)
            {
                System.out.println(CONSOLECOLORS.RED + DATE_FORMAT + CONSOLECOLORS.RESET);
            }
        }
    }

    public static LocalDate nextLocalDate()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CONSTANT.DDMMYYYY);
        while (true)
        {
            String input = getInput();
            try
            {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException dateTimeParseException)
            {
                System.out.println(CONSOLECOLORS.RED + DATE_FORMAT + CONSOLECOLORS.RESET);
            }
        }
    }
}
