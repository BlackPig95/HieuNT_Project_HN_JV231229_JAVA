package ra.business.config;

import java.io.*;
import java.util.List;

public class IOFile
{
    public static final String USER_PATH = "src/ra/business/data/userdata.txt";
    public static final String MOVIE_PATH = "src/ra/business/data/moviedata.txt";
    public static final String MOVIE_CATEGORY_PATH = "src/ra/business/data/categorydata.txt";
    public static final String SHOW_TIME_PATH = "src/ra/business/data/showtimedata.txt";
    public static final String ROOM_PATH = "src/ra/business/data/roomdata.txt";
    public static final String SNACK_PATH = "src/ra/business/data/snackdata.txt";
    public static final String USER_LOGIN = "src/ra/business/data/userlogindata.txt";

    public static <T extends Serializable> List<T> readFromFile(String path)
    {
        InputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try
        {
            fileInputStream = new FileInputStream(path);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (List<T>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                if (fileInputStream != null)
                {
                    fileInputStream.close();
                }
                if (objectInputStream != null)
                {
                    objectInputStream.close();
                }
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    //Overload sử dụng param File thay vì String
    public static <T extends Serializable> List<T> readFromFile(File file)
    {
        InputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try
        {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (List<T>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                if (fileInputStream != null)
                {
                    fileInputStream.close();
                }
                if (objectInputStream != null)
                {
                    objectInputStream.close();
                }
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T extends Serializable> void writeToFile(String path, List<T> tList)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(path);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tList);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                if (fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null)
                {
                    objectOutputStream.close();
                }
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    //Overload sử dụng param File thay vì string
    public static <T extends Serializable> void writeToFile(File file, List<T> tList)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tList);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                if (fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null)
                {
                    objectOutputStream.close();
                }
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    //Dùng cho việc đọc một object duy nhất thay vì là một list
    public static <T extends Serializable> void writeObject(File file, T objectToWrite)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(objectToWrite);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                if (fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null)
                {
                    objectOutputStream.close();
                }
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    //Overload
    public static <T extends Serializable> void writeObject(String path, T objectToWrite)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(path);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(objectToWrite);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                if (fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null)
                {
                    objectOutputStream.close();
                }
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    //Dùng cho việc đọc 1 object duy nhất thay vì 1 list
    public static <T extends Serializable> T readObject(File file)
    {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try
        {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                if (fileInputStream != null)
                {
                    fileInputStream.close();
                }
                if (objectInputStream != null)
                {
                    objectInputStream.close();
                }
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T extends Serializable> T readObject(String path)
    {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try
        {
            fileInputStream = new FileInputStream(path);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                if (fileInputStream != null)
                {
                    fileInputStream.close();
                }
                if (objectInputStream != null)
                {
                    objectInputStream.close();
                }
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
