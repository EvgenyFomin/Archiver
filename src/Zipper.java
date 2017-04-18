import java.util.LinkedList;
import java.io.*;
import java.util.zip.*;

/**
 * Created by wolfram on 18.04.17.
 */

class Zipper {
    private String destination;
    private int compressionLevel;
    private boolean isVerbose;
    private LinkedList<String> fileList;

    protected Zipper(String destination, int compressionLevel, boolean isVerbose, LinkedList<String> fileList) {
        this.destination = destination;
        this.compressionLevel = compressionLevel;
        this.isVerbose = isVerbose;
        this.fileList = fileList;
        pack();

    }

    private void pack() {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(destination))) {
            byte[] buffer = new byte[1024];

            zipOutputStream.setLevel(compressionLevel);

            for (String o : fileList) {
                zipOutputStream.putNextEntry(new ZipEntry(o));
                try (FileInputStream fileInputStream = new FileInputStream(o)) {
                    int len;
                    while ((len = fileInputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, len);

                    }
                    zipOutputStream.closeEntry();

                }

                if (isVerbose) {
                    System.out.println("Файл " + o + " добавлен в архив");

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
