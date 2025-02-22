package funcmath.utility;

import funcmath.game.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Helper {
  public static Object read(String pathname) {
    Logger.write("Reading file " + pathname);
    Object ans;
    try {
      FileInputStream fis = new FileInputStream(pathname.replace('\\', '/'));
      ObjectInputStream iis = new ObjectInputStream(fis);
      ans = iis.readObject();
      iis.close();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return ans;
  }

  public static void write(Object obj, String pathname) {
    Logger.write("Writing file " + pathname);
    try {
      File ourFile = new File(pathname.replace('\\', '/'));
      FileOutputStream fos = new FileOutputStream(ourFile);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(obj);
      oos.flush();
      oos.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // в линуксе может отсутствовать директория у файла, решаю через generateDirectories()
  }

  public static <T extends Serializable> T deepClone(T o) {
    try {
      return (T) Serializer.deserialize(Serializer.serialize(o));
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static String getLastMessage(Throwable e) {
    Throwable ee = Helper.deepClone(e);
    while (ee.getMessage() == null) {
      try {
        ee = ee.getCause();
      } catch (Exception ex) {
        return e.toString();
      }
    }
    return ee.getMessage();
  }

  public static <T extends Number> int[] toIntArray(ArrayList<T> al) {
    int size = al.size();
    int[] ans = new int[size];
    for (int i = 0; i < size; i++) {
      ans[i] = al.get(i).intValue();
    }
    return ans;
  }

  public static ArrayList<String> getFileNames(String pathname) {
    ArrayList<String> answer = new ArrayList<>();
    File folder = new File(pathname.replace('\\', '/'));
    File[] listOfFiles = folder.listFiles();
    if (listOfFiles == null) {
      return answer;
    }

    for (File file : listOfFiles) {
      if (file.isFile()) {
        answer.add(file.getName());
      }
    }
    return answer;
  }

  public static boolean isNotFileExists(String pathname) {
    return !(new File(pathname.replace('\\', '/'))).exists();
  }

  public static <T extends Number> byte[] toByteArray(ArrayList<T> al) {
    int size = al.size();
    byte[] ans = new byte[size];
    for (int i = 0; i < size; i++) {
      ans[i] = al.get(i).byteValue();
    }
    return ans;
  }

  public static <T> String collectionToString(Collection<T> list) {
    StringBuilder sb = new StringBuilder();
    for (T num : list) {
      sb.append(num);
      sb.append(" ");
    }
    sb.deleteCharAt(sb.lastIndexOf(" "));
    return sb.toString();
  }
}
