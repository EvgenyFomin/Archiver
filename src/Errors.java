import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by wolfram on 18.04.17.
 */
public class Errors {
//    public static void main(String[] args) {
//        isNotDirError();
//
//    }

    public void isNotDirError() {
        JFrame errorFrame = new JFrame("Ошибка");
        errorFrame.setSize(300, 150);
        errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        errorFrame.setLocationRelativeTo(null);
        errorFrame.setLayout(new GridBagLayout());

        JLabel errorLabel = new JLabel("Данной директории не существует");

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorFrame.setVisible(false);
                errorFrame.dispose();

            }
        });

        errorFrame.add(errorLabel, new GridBagConstraints(0, 0, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        errorFrame.add(okButton, new GridBagConstraints(0, 1, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));

        errorFrame.setVisible(true);


    }

}
