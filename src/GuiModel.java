import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by wolfram on 18.04.17.
 */

class GuiModel extends Component {
    private int compressionLevel = -1;
    private String destination;
    private JTextField nameField, destField;
    private JFrame dialogFrame;
    private ArrayList<String> fileList = new ArrayList<>();
    private ArrayList<String> filesNameList = new ArrayList<>();

    // Диалоговое окно создания пути для архива

    void guiDialog() {
        dialogFrame = new JFrame("Создание архива");
        dialogFrame.setSize(500, 300);
        dialogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialogFrame.setLocationRelativeTo(null);
        dialogFrame.setLayout(new GridBagLayout());

        nameField = new JTextField(30);
        destField = new JTextField();
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

        // Добавление кнопок и AL

        JButton destButton = new JButton("Обзор");
        JButton okButton = new JButton("Ok");

        destButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            try {
                fileChooser.setDialogTitle("Выбор директории");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.showOpenDialog(this);
                destination = fileChooser.getSelectedFile().getAbsolutePath();
                destField.setText(destination);

            } catch (NullPointerException E) {

            }


        });


        okButton.addActionListener(e -> {
            // Всевозможные проверки на директорию архива

            if (destField.getText().equals("") && nameField.getText().equals("")) {
                destination = "Archive.zip";

            } else if (destField.getText().equals("")) {
                destination = nameField.getText();

            } else {
                File directory = new File(destField.getText());

                if (!directory.exists()) {
                    new Errors("Данной директории не существует");
                    destination = "";

                } else {
                    destination = (nameField.getText().equals("")) ?
                            destField.getText() + "/" + "Archive.zip" : destField.getText() + "/" + nameField.getText();

                }

            }

            if (!destination.equals("")) {
                // Проверка на существование данного архива

                if (new File(destination).exists()) {
                    JFrame changeDialogFrame = new JFrame();
                    changeDialogFrame.setSize(400, 150);
                    changeDialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    changeDialogFrame.setLocationRelativeTo(null);
                    changeDialogFrame.setLayout(new GridBagLayout());
                    changeDialogFrame.setResizable(false);

                    JLabel errorLabel = new JLabel("Данный файл уже существует. Заменить?");

                    JButton yesButton = new JButton("Да");
                    JButton noButton = new JButton("Нет");

                    // Кнопка "ДА"

                    yesButton.addActionListener(e1 -> {
                        changeDialogFrame.dispose();
                        dialogFrame.setVisible(false);
                        guiMain();

                    });

                    // Кнопка "Нет"

                    noButton.addActionListener(e1 -> changeDialogFrame.dispose());

                    // Добавление компонент на фрейм

                    changeDialogFrame.add(errorLabel, new GridBagConstraints(0, 0, 3, 1, 0, 1,
                            GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(10, 1, 1, 1), 0, 0));
                    changeDialogFrame.add(yesButton, new GridBagConstraints(0, 1, 1, 0, 0, 1,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 70, 1, 1), 8, 0));
                    changeDialogFrame.add(noButton, new GridBagConstraints(1, 1, 1, 0, 0, 1,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 17, 1, 1), 0, 0));

                    changeDialogFrame.setVisible(true);

                } else {
                    dialogFrame.setVisible(false);
                    guiMain();

                }

            }

        });


        JLabel dirLabel = new JLabel("Выберите директорию будущего архива");
        JLabel nameLabel = new JLabel("Имя будущего архива");

        // Добавляем компоненты на dialogFrame


        dialogFrame.add(dirLabel, new GridBagConstraints(0, 0, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        dialogFrame.add(destField, new GridBagConstraints(0, 1, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 6));
        dialogFrame.add(destButton, new GridBagConstraints(1, 1, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        dialogFrame.add(nameLabel, new GridBagConstraints(0, 2, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        dialogFrame.add(nameField, new GridBagConstraints(0, 3, 1, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 6));
        dialogFrame.add(okButton, new GridBagConstraints(1, 3, 1, 1, 0, 1,
                GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        dialogFrame.pack();
        dialogFrame.setResizable(false);
        dialogFrame.setVisible(true);

    }

    // Основное окно архиватора

    private void guiMain() {
        JFrame mainframe = new JFrame("Архиватор");
        mainframe.setSize(800, 600);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setLocationRelativeTo(null);
        mainframe.setLayout(new GridBagLayout());

        // Бьем архиватор на 3 панели - путь до архива, таблица с файлами и меню

        JPanel pathPanel = new JPanel();
        JPanel tablePanel = new JPanel();
        JPanel menuPanel = new JPanel();
        pathPanel.setLayout(new FlowLayout());
        tablePanel.setLayout(new GridBagLayout());
        menuPanel.setLayout(new GridBagLayout());

        JLabel pathLabel = new JLabel("Путь до архива: ");
        JLabel compressionLevelLabel = new JLabel("Уровень компрессии: ");
        JTextField pathField = new JTextField(20);
        pathField.setText(destination);

        // Запрещаем изменять JTextField. Только если по кнопке :)

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

        JComboBox<String> compressLevelComboBox =
                new JComboBox<>(new String[]{"Default", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});

        compressLevelComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                compressionLevel = compressLevelComboBox.getSelectedIndex() - 1;

            }

        });

        // Кнопка ИЗМЕНИТЬ

        changeButton.addActionListener(e -> {
            JFrame endOfSessionFrame = new JFrame();
            endOfSessionFrame.setSize(400, 150);
            endOfSessionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            endOfSessionFrame.setLocationRelativeTo(null);
            endOfSessionFrame.setLayout(new GridBagLayout());
            endOfSessionFrame.setResizable(false);

            JLabel errorLabel = new JLabel("Завершить работу с данным архивом?");

            JButton yesButton = new JButton("Да");
            JButton noButton = new JButton("Нет");

            // Кнопка "ДА"

            yesButton.addActionListener(e1 -> {
                endOfSessionFrame.dispose();
                mainframe.dispose();
                dialogFrame.setVisible(true);
                fileList.clear();
                filesNameList.clear();

            });

            // Кнопка "Нет"

            noButton.addActionListener(e1 -> endOfSessionFrame.dispose());

            // Добавление компонент на фрейм

            endOfSessionFrame.add(errorLabel, new GridBagConstraints(0, 0, 3, 1, 0, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(10, 1, 1, 1), 0, 0));
            endOfSessionFrame.add(yesButton, new GridBagConstraints(0, 1, 1, 0, 0, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 70, 1, 1), 8, 0));
            endOfSessionFrame.add(noButton, new GridBagConstraints(1, 1, 1, 0, 0, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 17, 1, 1), 0, 0));

            endOfSessionFrame.setVisible(true);

        });

        // Таблица

        FilesTableModel filesTableModel = new FilesTableModel();
        JTable filesTable = new JTable(filesTableModel);
        JScrollPane filesTableScrollPane = new JScrollPane(filesTable);
        filesTableScrollPane.setPreferredSize(new Dimension(450, 400));

        // Кнопка ПЛЮС

        plusButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setApproveButtonText("Archive");
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.showOpenDialog(this);
            File[] files = fileChooser.getSelectedFiles();

            // Запрет на повторные названия файлов

            boolean isSafety = true;
            if (files.length != 0) {
                if (filesNameList.size() != 0) {
                    for (File file : files) {
                        for (String aFilesNameList : filesNameList) {
                            if (aFilesNameList.equals(file.getName())) {
                                isSafety = false;
                                break;

                            }

                        }

                    }
                }

                if (isSafety) {
                    for (File file : files) {
                        fileList.add(file.getPath());
                        filesNameList.add(file.getName());
                        filesTableModel.addFile(new String[]{file.getName(), file.getPath()});

                    }

                    filesTableModel.fireTableDataChanged();

                    // Создаю отдельный поток на архивацию и прогресс бар. Без отдельного потока медленно работает.

                    Zipper zipper = new Zipper(true, destination, compressionLevel, false, fileList, filesNameList);
                    Thread thread = new Thread(zipper);
                    thread.start();


                } else {
                    new Errors("Файлы имеют одинаковые названия");

                }
            }


        });


        // Кнопка МИНУС. Реализация: удаление файла из рахива = перезапись нужных файлов в архив

        minusButton.addActionListener(e -> {

            // Получаем выделенные строки и удаляем из таблицы в обратном порядке

            int[] sel = filesTable.getSelectedRows();
            for (int i = sel.length - 1; i > -1; i--) {
                filesTableModel.removeRow(sel[i]);

            }

            Zipper zipper = new Zipper(true, destination, compressionLevel, false, fileList, filesNameList);
            Thread thread = new Thread(zipper);
            thread.start();

        });

        // Добавление компонент

        mainframe.add(pathPanel, new GridBagConstraints(0, 0, 0, 0, 1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NORTHWEST, new Insets(0, 0, 0, 0), 0, 0));
        mainframe.add(tablePanel, new GridBagConstraints(0, 1, 0, 0, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(0, 5, 0, 0), 0, 0));
        mainframe.add(menuPanel, new GridBagConstraints(1, 1, 0, 0, 1, 1,
                GridBagConstraints.EAST, GridBagConstraints.EAST, new Insets(0, 0, 0, 30), 80, 100));


        pathPanel.add(pathLabel);
        pathPanel.add(pathField);
        pathPanel.add(changeButton);
        tablePanel.add(filesTableScrollPane);
        menuPanel.add(plusButton, new GridBagConstraints(0, 0, 1, 1, 1, 2,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(0, 100, 0, 0), 0, 0));
        menuPanel.add(minusButton, new GridBagConstraints(0, 1, 1, 1, 1, 2,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(0, 100, 0, 0), 0, 0));
        menuPanel.add(compressionLevelLabel, new GridBagConstraints(0, 2, 1, 1, 0, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(5, 0, 0, 0), 0, 0));
        menuPanel.add(compressLevelComboBox, new GridBagConstraints(1, 2, 1, 1, 0, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(0, -50, 0, 20), 0, 0));

        mainframe.setResizable(false);
        pathPanel.setVisible(true);
        mainframe.setVisible(true);

    }

    // Реализация таблицы для списка файлов

    private class FilesTableModel extends AbstractTableModel {
        private ArrayList<String[]> dataArrayList;

        FilesTableModel() {
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

        void addFile(String[] row) {
            dataArrayList.add(row);

        }

        void removeRow(int row) {
            dataArrayList.remove(row);
            filesNameList.remove(row);
            fileList.remove(row);
            fireTableDataChanged();

        }

    }

}