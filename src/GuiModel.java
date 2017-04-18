import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;

/**
 * Created by wolfram on 18.04.17.
 */
public class GuiModel {
    boolean isVerbose = false;
    int compressionLevel = -1;
    String destination;
    JFormattedTextField nameField;
    JTextField destField;
    JFrame frame;

    public void guiDialog() {
        frame = new JFrame("Создание архива");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());


        try {
            String s = "";
            for (int i = 0; i < 100; i++) {
                s += "*";

            }
            MaskFormatter formatter = new MaskFormatter(s);
            formatter.setInvalidCharacters("/\\");
            nameField = new JFormattedTextField(formatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        destField = new JTextField();

        JButton destButton = new JButton("Обзор");
        destButton.addActionListener(new DistActionListener());
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new OkActionListener());


        JLabel dirLabel = new JLabel("Выберите директорию будущего архива");
        JLabel nameLabel = new JLabel("Имя будущего архива");


        frame.add(dirLabel, new GridBagConstraints(0, 0, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        frame.add(destField, new GridBagConstraints(0, 1, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 6));
        frame.add(destButton, new GridBagConstraints(1, 1, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        frame.add(nameLabel, new GridBagConstraints(0, 2, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        frame.add(nameField, new GridBagConstraints(0, 3, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 6));
        frame.add(okButton, new GridBagConstraints(1, 3, 1, 1, 0, 1,
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

        JTextField archivesName = new JTextField(30);
        archivesName.setText("Archive.zip");

        mainframe.add(archivesName, new GridBagConstraints(0, 0, 1, 1, 0, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
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
            if (destField.getText().equals("") && nameField.getText().equals("")) {
                destination = "Archive.zip";

            } else if (destField.getText().equals("")) {
                destination = nameField.getText();

            } else {
                File file = new File(destField.getText());

                if (!file.exists()) {
                    new Errors().isNotDirError();
                    destination = "";

                } else {
                    destination = destField.getText() + "/" + nameField.getText();
                    frame.setVisible(false);

                }

            }

            if (!destination.equals("")) guiMain();

        }
    }


}
