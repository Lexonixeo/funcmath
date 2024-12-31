package funcmath.gui.swing;

public class GFocuses extends GTextField {
  public GFocuses() {
    super(
        0,
        0,
        1,
        1,
        new TextFieldAction() {
          @Override
          public void onEnterPress() {}
        });
  }
}
