import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by wolfram on 18.04.17.
 */
public class GuiModel {
    boolean isVerbose = false;
    int compressionLevel = -1;
    String destination;
    JTextField destField;
    JFrame frame;

    public void guiDialog() {
        frame = new JFrame("Создание архива");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        destField = new JTextField(30);

        JButton destButton = new JButton("Обзор");
        destButton.addActionListener(new DistActionListener());
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new OkActionListener());

        JLabel label = new JLabel("Выберите директорию будущего архива");


        frame.add(label, new GridBagConstraints(0, 0, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        frame.add(destField, new GridBagConstraints(0, 1, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 6));
        frame.add(destButton, new GridBagConstraints(1, 1, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        frame.add(okButton, new GridBagConstraints(1, 2, 1, 1, 0, 1,
                GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public void guiMain() {
        JFrame mainframe = new JFrame("Архиватор");
        mainframe.setSize(800, 600);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setLocationRelativeTo(null);
        mainframe.setLayout(new GridBagLayout());

        JTextField archivesName = new JTextField("Archive.zip");

        mainframe.setVisible(true);

    }

    private class DistActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton open = new JButton();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("./"));
            fileChooser.setDialogTitle("Выбор директории");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showOpenDialog(open);
            destination = fileChooser.getSelectedFile().getAbsolutePath();
            destField.setText(destination);

        }
    }

    private class OkActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                destination = destField.getText();
                File file = new File(destination);

                if (!file.exists()) {
                    new Errors().isNotDirError();

                } else {
                    frame.setVisible(false);
                    guiMain();

                }

            } catch (NullPointerException E) {

            }

        }
    }


}
