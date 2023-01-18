package ru.cft;

import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class CommandArgs {
    public static void main(String[] args) {
        CommandArgs ca = new CommandArgs();
        System.out.println(ca.of(args).get2());

    }

    private final List<List<String>> values = new ArrayList<>();

    public String get(int index1, int index2) {
        return values.get(index1).get(index2);
    }
    public List<List<String>> get2() {
        return values;
    }

    public CommandArgs of(String[] args) {
        CommandArgs names = new CommandArgs();
        isValid(args);
        names.parse(args);
        return names;
    }

    public void parse(String[] args) {

        if (args[0].equals("-a") || args[0].equals("-d")) {
            values.add(0, List.of(args[0]));
            values.add(1, List.of(args[1]));
            values.add(2, List.of(args[2]));
            List<String> internal = new ArrayList<>();
            for (int i = 3; i < args.length; i++) {
                internal.add(args[i]);
            }
            values.add(3, internal);
        } else {
            values.add(0, List.of("-a"));
            values.add(1, List.of(args[0]));
            values.add(2, List.of(args[1]));
            List<String> internal = new ArrayList<>();
            for (int i = 2; i < args.length; i++) {
                internal.add(args[i]);
            }
            values.add(3, internal);
        }

    }

    private static void isValid(String[] args) {
        if (!(args[0].equals("-a")
                || args[0].equals("-d")
                || args[0].equals("-s")
                || args[0].equals("-i")
                || args[1].equals("-i")
                || args[1].equals("-s")
                || args[1].endsWith(".txt")
                )) {
            throw new IllegalArgumentException("введен неверный аргумент");
        }
        for (int i = 2; i < args.length; i++) {
            if (!args[i].endsWith(".txt")) {
                throw new IllegalArgumentException("введен неверный формат файлов");
            }
        }
        for (int i = args.length - 1; i > 2; i--) {
            if (args[i].endsWith(".txt") && !args[i - 2].endsWith(".txt")) {
                File path = Paths.get(args[i]).toFile();
                if (!path.isFile() && !path.exists()) {
                    throw new IllegalArgumentException(String.format("%s - файла не существует либо указан не верно", path));
                }
            }
        }
    }

}
