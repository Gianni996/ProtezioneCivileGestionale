package View;

import javax.swing.*;

public class ArchivistaHomeView extends JPanel {

    private JPanel Intermedio0;

    private JButton listaCandidatiButton;
    private JButton volontariNonAttiviButton;
    private JButton tuttiIVolontariButton;


    public ArchivistaHomeView(){

        setVisible(true);

    }

    public JButton getCandidatiButtonButton() {
        return listaCandidatiButton;
    }

    public JButton getvolontariNonAttiviButton() {
        return volontariNonAttiviButton;
    }

    public JButton gettuttiIVolontariButton() {
        return tuttiIVolontariButton;
    }

    public JPanel getIntermedio0() {
        return Intermedio0;
    }



}