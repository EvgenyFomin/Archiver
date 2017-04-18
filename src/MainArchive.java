/**
 * Created by wolfram on 18.04.17.
 */
public class MainArchive {
    public static void main(String[] args) {
        if ((args.length != 0) && (args[0].equals("--no-gui"))) {
            String[] s = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                s[i - 1] = args[i];

            }
            new NoGuiModel(s);

        }

        else {
            System.out.println("Реализуем GUI модель...");

        }

    }

}
