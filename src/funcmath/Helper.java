package funcmath;

import funcmath.exceptions.JavaException;
import funcmath.game.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Helper {
  public static Object read(String pathname) {
    Log.getInstance().write("Читается файл " + pathname);
    Object ans;
    try {
      FileInputStream fis = new FileInputStream(pathname.replace('\\', '/'));
      ObjectInputStream iis = new ObjectInputStream(fis);
      ans = iis.readObject();
      iis.close();
    } catch (IOException | ClassNotFoundException e) {
      throw new JavaException(e);
    }
    return ans;
  }

  public static void write(Object obj, String pathname) {
    Log.getInstance().write("Записывается файл " + pathname);
    try {
      File ourFile = new File(pathname.replace('\\', '/'));
      FileOutputStream fos = new FileOutputStream(ourFile);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(obj);
      oos.flush();
      oos.close();
    } catch (IOException e) {
      throw new JavaException(e);
    }

    // в линуксе может отсутствовать директория у файла, решаю через generateDirectories()
  }

  private static ArrayList<Character> stringProcessing(String str) {
    char[] first_str = str.toCharArray();
    ArrayList<Character> second_str = new ArrayList<>();
    for (char i : first_str) {
      if (i == ',' || i == '(' || i == ')') {
        second_str.add(' ');
      } else {
        second_str.add(i);
      }
    }
    ArrayList<Character> third_str = new ArrayList<>();
    char last_char = ' ';
    for (char i : second_str) {
      if (!(i == ' ' && last_char == ' ')) {
        third_str.add(i);
        last_char = i;
      }
    }
    if (!third_str.isEmpty() && third_str.get(third_str.size() - 1) == ' ') {
      third_str.remove(third_str.size() - 1);
    }
    return third_str;
  }

  public static ArrayList<String> wordsFromString(String str) {
    ArrayList<Character> our_str = Helper.stringProcessing(str);
    StringBuilder builder = new StringBuilder();
    ArrayList<String> words = new ArrayList<>();
    for (char i : our_str) {
      if (i != ' ') builder.append(i);
      else {
        words.add(builder.toString());
        builder.delete(0, builder.length());
      }
    }
    words.add(builder.toString());
    return words;
  }

  public static int filesCount(String pathname) {
    return Objects.requireNonNull(new File(pathname.replace('\\', '/')).listFiles()).length;
  }

  public static void clear() {
    Log.getInstance().write("Очистка консоли!");
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033\143");
      }
    } catch (IOException | InterruptedException ignored) {
    }
  }

  public static <T extends Serializable> T deepClone(T o) {
    try {
      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(byteOut);
      out.writeObject(o);
      out.flush();
      ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
      return Helper.cast(o.getClass().cast(in.readObject()), o);
    } catch (Exception e) {
      throw new JavaException(e);
    }
  }

  public static boolean isNotFileExists(String pathname) {
    return !(new File(pathname.replace('\\', '/'))).exists();
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

  public static void generateDirectories() {
    Log.getInstance().write("Директории генерируются...");
    new File("data/customLevels").mkdirs();
    new File("data/functions").mkdirs();
    new File("data/levels").mkdirs();
    new File("data/players").mkdirs();
    new File("data/preLevels").mkdirs();
    new File("data/locales").mkdirs();
    new File("data/objects").mkdirs();
    new File("data/images").mkdirs();
    new File("data/logs").mkdirs();
  }

  public static <T> String arrayListToString(ArrayList<T> list) {
    StringBuilder sb = new StringBuilder();
    for (T num : list) {
      sb.append(num);
      sb.append(" ");
    }
    sb.deleteCharAt(sb.lastIndexOf(" "));
    return sb.toString();
  }

  public static <T> T cast(Object obj, T resultClass) {
    //noinspection unchecked
    return (T) obj;
  }

  public static void writeLine(String line, String pathname) {
    try {
      FileWriter writer = new FileWriter(pathname, true);
      writer.write(line + "\n");
      writer.close();
    } catch (IOException e) {
      throw new JavaException(e);
    }
  }

  // Когда будет много уровней/игроков: ДОБАВИТЬ СВОЙ ХЕШ
}
