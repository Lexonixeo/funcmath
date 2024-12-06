package funcmath.gui.utility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ButtonAction implements ActionListener {
    public abstract void onClick();

    @Override
    public void actionPerformed(ActionEvent e) {
        onClick();
    }
}
