package ru.cft;

public class Sort {

    public static void launch(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("not enough initial parameters");
        }
        CommandArgs names = new CommandArgs();
        CommandArgs com = names.of(args);
        SortType sortType;
        if (com.get(1, 0).equals("-i")) {
            sortType = new SortInt();
        } else {
            sortType = new SortStr();
        }
        sortType.start(com);
        System.out.println("Программа выполнена");
    }

    public static void main(String[] args) {
        launch(args);
    }



}
