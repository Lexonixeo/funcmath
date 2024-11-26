package funcmath.gui;

public class MainTest {
    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        while (true) {
            frame.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
