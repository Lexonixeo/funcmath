package funcmath;

import java.io.*;
import java.util.ArrayList;

public class Helper {
    public static Object read(String pathname) {
        Object ans;
        try {
            FileInputStream fis = new FileInputStream(pathname);
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
            FileOutputStream fos = new FileOutputStream(pathname);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public static ArrayList<Integer> numbersFromWords(ArrayList<String> words) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (String word : words) {
            numbers.add(Integer.parseInt(word));
        }
        return numbers;
    }

    public static int filesCount(String pathname) {
        return new File(pathname).listFiles().length;
    }

    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ignored) {}
    }

    public static <T extends Serializable> T deepClone(T o) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(o);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
            return (T) o.getClass().cast(in.readObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}