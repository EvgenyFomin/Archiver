import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by wolfram on 18.04.17.
 */

class GuiModel {
    boolean isVerbose = false;
    int compressionLevel = -1;
    private String destination;
    private JTextField nameField, destField;
    private JFrame frame;

    // Диалоговое окно создания пути для архива

    void guiDialog() {
        frame = new JFrame("Создание архива");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        nameField = new JTextField(30);
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == '/' || c == '\\' || c == ' ') {
                    e.consume();

                }

            }

        });


        destField = new JTextField();

        JButton destButton = new JButton("Обзор");
        JButton okButton = new JButton("Ok");
        destButton.addActionListener(e -> {
            JButton open = new JButton();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./"));
            fileChooser.setDialogTitle("Выбор директории");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showOpenDialog(open);
            destination = fileChooser.getSelectedFile().getAbsolutePath();
            destField.setText(destination);

        });
        okButton.addActionListener(e -> {
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
                    destination = (nameField.getText().equals("")) ?
                            destField.getText() + "/" + "Archive.zip" : destField.getText() + "/" + nameField.getText();
                    frame.setVisible(false);

                }

            }

            if (!destination.equals("")) {
                guiMain();

            }

        });


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

    private void guiMain() {
        JFrame mainframe = new JFrame("Архиватор");
        mainframe.setSize(800, 600);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setLocationRelativeTo(null);
        mainframe.setLayout(new GridBagLayout());

        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new FlowLayout());

        JLabel pathLabel = new JLabel("Путь до архива: ");
        JTextField pathField = new JTextField(20);
        pathField.setText(destination);
        pathField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                e.consume();

            }

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                e.consume();

            }
        });

        JButton changeButton = new JButton("Изменить");
        changeButton.addActionListener(e -> {
            JFrame eosFrame = new JFrame("Ошибка");
            eosFrame.setSize(400, 150);
            eosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            eosFrame.setLocationRelativeTo(null);
            eosFrame.setLayout(new GridBagLayout());

            JLabel errorLabel = new JLabel("Завершить работу с данным архивом?");

            JButton yesButton = new JButton("Да");
            JButton noButton = new JButton("Нет");
            yesButton.addActionListener(e1 -> {
                eosFrame.dispose();
                mainframe.dispose();
                frame.setVisible(true);

            });

            noButton.addActionListener(e1 -> eosFrame.dispose());

            eosFrame.add(errorLabel, new GridBagConstraints(0, 0, 3, 1, 0, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(10, 1, 1, 1), 0, 0));
            eosFrame.add(yesButton, new GridBagConstraints(0, 1, 1, 0, 0, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 70, 1, 1), 8, 0));
            eosFrame.add(noButton, new GridBagConstraints(1, 1, 1, 0, 0, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 17, 1, 1), 0, 0));

            eosFrame.setVisible(true);

        });

        mainframe.add(pathPanel, new GridBagConstraints(0, 0, 0, 0, 1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NORTHWEST, new Insets(0, 0, 0, 0), 0, 0));
        pathPanel.add(pathLabel);
        pathPanel.add(pathField);
        pathPanel.add(changeButton);

        mainframe.setResizable(false);
        pathPanel.setVisible(true);
        mainframe.setVisible(true);

    }

}
