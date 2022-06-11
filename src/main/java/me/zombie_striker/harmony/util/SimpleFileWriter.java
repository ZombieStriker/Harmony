package me.zombie_striker.harmony.util;

import java.io.*;

public class SimpleFileWriter {

    private final File file;
    private final FileWriter fos;

    public SimpleFileWriter(File file) {
        this.file = file;
        try {
            this.fos = new FileWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SimpleFileWriter saveFile() {
        try {
            fos.flush();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public SimpleFileWriter writeNewLine(String message) {
        try {
            fos.write(message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public SimpleFileWriter writeIndentLine(String message, int indents) {
        try {
            for (int i = 0; i < indents; i++) {
                fos.write("  ");
            }
            fos.write(message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
