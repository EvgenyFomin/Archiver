import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.zip.*;

/**
 * Created by wolfram on 18.04.17.
 */

class Zipper implements Runnable {
    private String destination;
    private int compressionLevel;
    private boolean isVerbose;
    private List<String> fileList, filesNameList;
    private boolean isGui;
    private int n = 0;

    Zipper(boolean isGui, String destination, int compressionLevel,
           boolean isVerbose, List<String> fileList, List<String> filesNameList) {
        this.destination = destination;
        this.compressionLevel = compressionLevel;
        this.isVerbose = isVerbose;
        this.fileList = fileList;
        this.isGui = isGui;
        this.filesNameList = filesNameList;

    }

    @Override
    public void run() {
        ProgressBar progressBar = new ProgressBar();
        Thread thread = new Thread(progressBar);
        thread.start();
        pack();

    }

    void pack() {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(destination))) {
            byte[] buffer = new byte[1024];

            zipOutputStream.setLevel(compressionLevel);

            for (int i = 0; i < fileList.size(); i++) {
                if (isGui) {
                    zipOutputStream.putNextEntry(new ZipEntry(filesNameList.get(i)));

                } else {
                    zipOutputStream.putNextEntry(new ZipEntry(fileList.get(i)));

                }

                try (FileInputStream fileInputStream = new FileInputStream(fileList.get(i))) {
                    int len;
                    while ((len = fileInputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, len);

                    }
                    zipOutputStream.closeEntry();

                }

                if (isVerbose) {
                    System.out.println("Файл " + fileList.get(i) + " добавлен в архив");

                }

                n++;

            }

        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден");
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода");
        } catch (IllegalArgumentException e) {
            System.err.println("Неверный уровень компрессии");

        }

    }

    private class ProgressBar implements Runnable {
        @Override
        public void run() {
            setProgressBar();

        }

        void setProgressBar() {
            JFrame frame = new JFrame("Процесс архивации/удаления");
            frame.setSize(300, 150);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setLayout(new GridBagLayout());

            JProgressBar progressBar = new JProgressBar();
            progressBar.setMinimum(0);
            progressBar.setMaximum(fileList.size());
            progressBar.setStringPainted(true);

            frame.setVisible(true);
            frame.add(progressBar);

            while (n < fileList.size()) {
                progressBar.setValue(n);

            }

            frame.dispose();

        }

    }

}