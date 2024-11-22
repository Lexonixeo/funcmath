package funcmath;

import funcmath.object.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Helper {
  public static Object read(String pathname) {
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

  public static ArrayList<Integer> integersFromWords(ArrayList<String> words) {
    ArrayList<Integer> numbers = new ArrayList<>();
    for (String word : words) {
      numbers.add(Integer.parseInt(word));
    }
    return numbers;
  }

  public static ArrayList<Double> doublesFromWords(ArrayList<String> words) {
    ArrayList<Double> numbers = new ArrayList<>();
    for (String word : words) {
      numbers.add(Double.parseDouble(word));
    }
    return numbers;
  }

  public static int filesCount(String pathname) {
    return Objects.requireNonNull(new File(pathname.replace('\\', '/')).listFiles()).length;
  }

  public static void clear() {
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
      throw new RuntimeException(e);
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

  public static ArrayList<Integer> getLevelList(ArrayList<String> fileNames) {
    ArrayList<Integer> answer = new ArrayList<>();
    for (String fileName : fileNames) {
      answer.add(Integer.parseInt(fileName.substring(5, fileName.length() - 4)));
    }
    answer.sort(Comparator.naturalOrder());
    return answer;
  }

  public static void generateDirectories() {
    new File("data/customLevels").mkdirs();
    new File("data/functions").mkdirs();
    new File("data/levels").mkdirs();
    new File("data/players").mkdirs();
    new File("data/preLevels").mkdirs();
    new File("data/locales").mkdirs();
    new File("data/objects").mkdirs();
  }

  public static String arrayListToString(ArrayList<MathObject> nums) {
    StringBuilder sb = new StringBuilder();
    for (MathObject num : nums) {
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

  public static void loadMathObjects() {
    MathObject.loadMathObject(new FNatural());
    MathObject.loadMathObject(new FInteger());
    MathObject.loadMathObject(new FRational());
    MathObject.loadMathObject(new FReal());
    MathObject.loadMathObject(new FComplex());
    MathObject.loadMathObject(new FUnknown());
  }

  // Когда будет много уровней/игроков: ДОБАВИТЬ СВОЙ ХЕШ
}
