package funcmath;

import funcmath.game.Game;
import funcmath.object.MathObject;
import funcmath.utility.Helper;
import funcmath.utility.Log;

public class Main {
  public static void main(String[] args) {
    try {
      Log.getInstance().write("Игрок зашел в игру");
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
      System.out.println(
          "\n\nК сожалению, произошла необработанная ошибка. Ваша статистика сохранена, перезайдите в игру.");
      Log.getInstance().write("Игра завершена в связи с необработанной ошибкой");
    }
  }
}
