import javax.swing.*;
import java.awt.*;

/**
 * Created by wolfram on 18.04.17.
 */

// Отдельный класс-шаблон для реализации ошибок с одной кнопкой

class Errors {

    private String errorMsg;
    private int windowSize;

    Errors(String errorMsg) {
        this.errorMsg = errorMsg;
        windowSize = errorMsg.length() + 300;
        error();

    }

    private void error() {
        JFrame errorFrame = new JFrame("Ошибка");
        errorFrame.setSize(windowSize, 150);
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorFrame.setLocationRelativeTo(null);
        errorFrame.setLayout(new GridBagLayout());
        errorFrame.setResizable(false);

        JLabel errorLabel = new JLabel(errorMsg);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> {
            errorFrame.setVisible(false);
            errorFrame.dispose();

        });

        errorFrame.add(errorLabel, new GridBagConstraints(0, 0, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 1, 1, 1), 0, 0));
        errorFrame.add(okButton, new GridBagConstraints(0, 1, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.SOUTH, new Insets(1, 1, 1, 1), 0, 0));

        errorFrame.setVisible(true);

    }

}
