import java.io.*;
import java.util.List;
import java.util.zip.*;

/**
 * Created by wolfram on 18.04.17.
 */

class Zipper {
    private String destination;
    private int compressionLevel;
    private boolean isVerbose;
    private List<String> fileList, filesNameList;
    private boolean isGui;

    protected Zipper(boolean isGui, String destination, int compressionLevel,
                     boolean isVerbose, List<String> fileList, List<String> filesNameList) {
        this.destination = destination;
        this.compressionLevel = compressionLevel;
        this.isVerbose = isVerbose;
        this.fileList = fileList;
        this.isGui = isGui;
        this.filesNameList = filesNameList;
        pack();

    }

    private void pack() {
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

            }

        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден");
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода");
        } catch (IllegalArgumentException e) {
            System.err.println("Неверный уровень компрессии");

        }

    }

}
