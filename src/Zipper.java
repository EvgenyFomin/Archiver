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

    }

}
