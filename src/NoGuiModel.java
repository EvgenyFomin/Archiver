import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wolfram on 18.04.17.
 */

public class NoGuiModel {
    private String[] args;

    public NoGuiModel(String[] args) {
        this.args = new String[args.length];
        System.arraycopy(args, 0, this.args, 0, args.length);
        parser();

    }

    private void parser() {
        int positionOfDest = -1;
        int positionOfCompressionLevel = -1;
        int compressionLevel = -1;
        boolean isVerbose = false;


        // Независимо от того, как введена строка, если присутствует ключ --help, то выводим справку

        for (String o : args) {
            if (o.equals("--help")) {
                help();
                System.exit(0);

            }

        }

        // Ищем позицию ключа -o для определения конечного пути

        for (int i = 0; i < args.length; i++) {
            if ((args[i].equals("-o")) && (i != args.length - 1)) {
                positionOfDest = i;
                break;

            }

        }

        if (positionOfDest == -1) {
            System.out.println("Отсутствует конечный путь. Для справки используйте ключ --help");
            System.exit(0);

        }

        // Проверка на уровень компрессии

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-q")) {
                positionOfCompressionLevel = i;

            }

        }

        if (positionOfCompressionLevel == args.length - 1) {
            positionOfCompressionLevel = -1;

        }

        if (positionOfCompressionLevel != -1) {
            try {
                compressionLevel = Integer.parseInt(args[positionOfCompressionLevel + 1]);

            } catch (NumberFormatException e) {
                System.out.println("Невозможный уровень компрессии. Будет установлено значение по умолчанию.");

            }

        }

        // Проверка на существование ключа -v

        if (args[0].equals("-v")) {
            isVerbose = true;

        }

        // Список файлов

        ArrayList<String> fileList = new ArrayList<>();

        int min = ((positionOfCompressionLevel < positionOfDest) && (positionOfCompressionLevel != -1)) ?
                positionOfCompressionLevel : positionOfDest; /* Данная переменная определяет что ближе - -q или -o
                                                          Это позволяет создавать --no-gui запросы с разным порядком ключей */

        // Добавляем в filelist все файлы

        fileList.addAll(Arrays.asList(args).subList(Boolean.compare(isVerbose, true) + 1, min));

        // Запускаем архивацию

        Zipper zipper = new Zipper(false, args[positionOfDest + 1], compressionLevel, isVerbose, fileList, null);
        zipper.pack();

    }

    private void help() {
        System.out.println("Справка по использованию архиватора:\n" +
                "Использование: java -jar archiver.jar --no-gui [-v] [FILE]... [-q 123456789] [-o DEST]\n" +
                "Ключи:\n" +
                "  -v\t\tverbose mode\n" +
                "  -q\t\tуровень компрессии [-1..9]\n" +
                "  -o\t\tпуть до архива. Обязательный параметр\n" +
                "  --help\tсправка");

    }

}
