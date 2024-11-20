package funcmath;

import funcmath.game.Game;

public class Main {
  public static void main(String[] args) {
    Helper.generateDirectories();
    Helper.loadMathObjects();
    Helper.clear();
    System.out.println(
        "ВНИМАНИЕ: Согласно стандартам ISO 80000-2:2019 и ГОСТ Р 54521-2011 мы принимаем число 0 как условно натуральное.");
    System.out.println(
        "Данное решение было принято в связи с возникающими проблемами у некоторых функций при отсутствии нуля.");
    System.out.println();
    Game game = Game.login();
    game.game();
  }
}
