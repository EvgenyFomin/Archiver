import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by wolfram on 18.04.17.
 */

class GuiModel extends Component {
    boolean isVerbose = false;
    int compressionLevel = -1;
    private String destination;
    private JTextField nameField, destField;
    private JFrame frame;
    ArrayList<String> fileList = new ArrayList<>();
    ArrayList<String> filesNameList = new ArrayList<>();

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
            fileChooser.setCurrentDirectory(new File("/home/wolfram/test"));
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
                    new Errors("Данной директории не существует");
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

    // Основное окно архиватора

    private void guiMain() {
        JFrame mainframe = new JFrame("Архиватор");
        mainframe.setSize(800, 600);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setLocationRelativeTo(null);
        mainframe.setLayout(new GridBagLayout());

        JPanel pathPanel = new JPanel();
        JPanel tablePanel = new JPanel();
        JPanel menuPanel = new JPanel();
        pathPanel.setLayout(new FlowLayout());
        tablePanel.setLayout(new GridBagLayout());
        menuPanel.setLayout(new GridBagLayout());

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
        JButton plusButton = new JButton();
        JButton minusButton = new JButton();
        plusButton.setIcon(new ImageIcon("./plus.png"));
        minusButton.setIcon(new ImageIcon("./minus.png"));

        // Кнопка ИЗМЕНИТЬ

        changeButton.addActionListener(e -> {
            JFrame eosFrame = new JFrame("Ошибка");
            eosFrame.setSize(400, 150);
            eosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            eosFrame.setLocationRelativeTo(null);
            eosFrame.setLayout(new GridBagLayout());

            JLabel errorLabel = new JLabel("Завершить работу с данным архивом?");

            JButton yesButton = new JButton("Да");
            JButton noButton = new JButton("Нет");

            // Кнопка "ДА"

            yesButton.addActionListener(e1 -> {
                eosFrame.dispose();
                mainframe.dispose();
                frame.setVisible(true);
                fileList.clear();
                filesNameList.clear();

            });

            // Кнопка "Нет"

            noButton.addActionListener(e1 -> eosFrame.dispose());

            // Добавление компонент на фрейм с ошибкой

            eosFrame.add(errorLabel, new GridBagConstraints(0, 0, 3, 1, 0, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(10, 1, 1, 1), 0, 0));
            eosFrame.add(yesButton, new GridBagConstraints(0, 1, 1, 0, 0, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 70, 1, 1), 8, 0));
            eosFrame.add(noButton, new GridBagConstraints(1, 1, 1, 0, 0, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 17, 1, 1), 0, 0));

            eosFrame.setVisible(true);

        });

        FilesTableModel filesTableModel = new FilesTableModel();
        JTable filesTable = new JTable(filesTableModel);
        JScrollPane filesTableScrollPane = new JScrollPane(filesTable);
        filesTable.setPreferredSize(new Dimension(500, 400));

        // Кнопка ПЛЮС

        plusButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setCurrentDirectory(new java.io.File("/home/wolfram/test"));
            fileChooser.setApproveButtonText("Archive");
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.showOpenDialog(this);
            File[] files = fileChooser.getSelectedFiles();
            boolean isSafety = true;
            if (filesNameList.size() != 0) {
                for (int i = 0; i < files.length; i++) {
                    for (int j = 0; j < filesNameList.size(); j++) {
                        if (filesNameList.get(j).equals(files[i].getName())) {
                            isSafety = false;
                            break;

                        }

                    }

                }
            }

            if (isSafety) {
                for (int i = 0; i < files.length; i++) {
                    fileList.add(files[i].getPath());
                    filesNameList.add(files[i].getName());
                    filesTableModel.addFile(new String[]{files[i].getName(), files[i].getPath()});
                    System.out.println(files[i].getPath());

                }

                filesTableModel.fireTableDataChanged();

                new Zipper(true, destination, -1, false, fileList, filesNameList);

            } else {
                new Errors("Файлы имеют одинаковые названия");

            }


        });

        // Кнопка МИНУС


        mainframe.add(pathPanel, new GridBagConstraints(0, 0, 0, 0, 1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NORTHWEST, new Insets(0, 0, 0, 0), 0, 0));
        mainframe.add(tablePanel, new GridBagConstraints(0, 1, 0, 0, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(0, 0, 0, 0), 0, 0));
        mainframe.add(menuPanel, new GridBagConstraints(1, 1, 0, 0, 1, 1,
                GridBagConstraints.EAST, GridBagConstraints.EAST, new Insets(0, 0, 0, 100), 50, 100));

        pathPanel.add(pathLabel);
        pathPanel.add(pathField);
        pathPanel.add(changeButton);
        tablePanel.add(filesTableScrollPane);
        menuPanel.add(plusButton, new GridBagConstraints(0, 0, 1, 1, 1, 2,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0));
        menuPanel.add(minusButton, new GridBagConstraints(0, 1, 1, 1, 1, 2,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(0, 1, 0, 0), 0, 0));

        mainframe.setResizable(false);
        pathPanel.setVisible(true);
        mainframe.setVisible(true);

    }

    // Реализация таблицы для списка файлов

    public class FilesTableModel extends AbstractTableModel {
        private ArrayList<String[]> dataArrayList;

        public FilesTableModel() {
            dataArrayList = new ArrayList<>();
            for (int i = 0; i < dataArrayList.size(); i++) {
                dataArrayList.add(new String[getColumnCount()]);

            }

        }

        @Override
        public int getRowCount() {
            return dataArrayList.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Имя файла";
                case 1:
                    return "Полный путь";

            }
            return "";

        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String[] rows = dataArrayList.get(rowIndex);
            return rows[columnIndex];
        }

        public void addFile(String[] row) {
            String[] rowTable;
            rowTable = row;
            dataArrayList.add(rowTable);

        }

    }

}
