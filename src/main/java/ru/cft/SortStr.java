package ru.cft;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class SortStr implements SortType<String> {

    public void start(CommandArgs file) {
        SortStr sortStr = new SortStr();
        List<String> list = new ArrayList<>();
        for (String s : file.get2().get(3)) {
            List<String> temp = fileReader(s);
            if (!isValid(temp)) {
                System.out.println("ОШИБКА: в файле " + s + " обнаружен пробел");
            }

            list.addAll(temp);
            sortStr.mergeSortType(list, file.get(0, 0));
        }

        sortStr.fileWriter(list, file.get(2, 0));
    }

    public List<String> fileReader(String file) {
        List<String> list = new ArrayList<>();
        if (file != null) {
            try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                list = in.lines().collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private boolean isValid(List<String> list) {
        for (String s : list) {
            if (s.equals("\s")) {
                return false;
            }
        }
        return true;
    }

    private void fileWriter(List<String> file, String name) {
        if (file != null) {
            try (PrintWriter pw = new PrintWriter(
                    new BufferedOutputStream(
                            new FileOutputStream("cftdir\\" + name)))) {
               file.forEach(pw::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static boolean check(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i + 1).compareTo(list.get(i)) < 0) {
                return false;
            }
        }
        return true;
    }


    private void mergeSortType(List<String> list, String sequence) {

        int l = list.size();

        if (l < 2) {
            return;
        }
        int mid = l / 2;
        int leftIndex = 0;
        int rightIndex = 0;

        List<String> left = new ArrayList<>(mid);
        List<String> right = new ArrayList<>(l - mid);

        for (int i = 0; i < mid; i++) {
            left.add(list.get(i));
        }

        for (int i = mid; i < l; i++) {
            right.add(list.get(i));
        }

        if (sequence.equals("-d")) {
            mergeSortType(left, sequence);
            mergeSortType(right, sequence);
            mergeTypeDescend(list, right, left);
        } else {
            mergeSortType(left, sequence);
            mergeSortType(right, sequence);
            mergeTypeAscend(list, left, right);
        }
    }

    private void mergeTypeAscend(List<String> total, List<String> left, List<String> right) {
        total.clear();
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (right.get(rightIndex).compareTo(left.get(leftIndex)) >= 0) {
                total.add(left.get(leftIndex));
                leftIndex++;
            } else {
                total.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        while (leftIndex < left.size()) {
            total.add(left.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < right.size()) {
            total.add(right.get(rightIndex));
            rightIndex++;
        }
    }

    private void mergeTypeDescend(List<String> total, List<String> left, List<String> right) {
        total.clear();
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (right.get(rightIndex).compareTo(left.get(leftIndex)) < 0) {
                total.add(left.get(leftIndex));
                leftIndex++;
            } else {
                total.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        while (leftIndex < left.size()) {
            total.add(left.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < right.size()) {
            total.add(right.get(rightIndex));
            rightIndex++;
        }
    }
}
