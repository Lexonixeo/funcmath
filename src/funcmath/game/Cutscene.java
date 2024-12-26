package funcmath.game;

import java.io.Serializable;
import java.util.ArrayList;

public class Cutscene implements Serializable {
    ArrayList<String> text;

    public Cutscene(ArrayList<String> text) {
        this.text = text;
    }
}
