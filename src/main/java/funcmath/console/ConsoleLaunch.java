package funcmath.console;

import funcmath.object.MathObject;
import funcmath.utility.Helper;
import funcmath.utility.Log;

public class ConsoleLaunch {
  public static void main(String[] args) {
    try {
      Log.getInstance().write("Player has entered the game");
      Helper.generateDirectories();
      MathObject.loadMathObjects();
      Helper.clear();
      System.out.println(
          "ВНИМАНИЕ: Согласно стандартам ISO 80000-2:2019 и ГОСТ Р 54521-2011 мы принимаем число 0 как условно натуральное.");
      System.out.println(
          "Данное решение было принято в связи с возникающими проблемами у некоторых функций при отсутствии нуля.");
      System.out.println();
      Game game = Game.login();
      game.game();
    } catch (Exception e) {
      Log.getInstance().write(e);
      System.out.println("\n\nК сожалению, произошла необработанная ошибка.");
      System.out.println("Ваша статистика сохранена, вы можете перезайти в игру.");
      System.out.println(
          "Пришлите, пожалуйста, в телеграм @funcmath_bot файл "
              + Log.getInstance().getPathname()
              + ", чтобы я разобрался с данной проблемой.");
      Log.getInstance().write("The game is terminated due to an unhandled error");
    }
  }
}
