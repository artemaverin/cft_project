package ru.cft;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SortInt implements SortType<Integer> {

    public void start(CommandArgs file) {
        SortInt sortInt = new SortInt();
        SortStr ss = new SortStr();
        List<Integer> list = new ArrayList<>();
        for (String s : file.get2().get(3)) {
            List<String> temp = ss.fileReader(s);
            if (!isValid(temp)) {
                System.out.println("ОШИБКА: в файле " + s + " обнаружен пробел");
            }
            list.addAll(fileReader(s));
            sortInt.mergeSortType(list, file.get(0, 0));
        }

        sortInt.fileWriter(list, file.get(2, 0));
    }

    private List<Integer> fileReader(String file) {
        List<Integer> list = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        if (file != null) {
            try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                temp = in.lines().toList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (String s:temp) {
            list.add(Integer.valueOf(s));
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

    private void fileWriter(List<Integer> file, String name) {
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

    private void mergeSortType(List<Integer> list, String sequence) {

        int l = list.size();

        if (l < 2) {
            return;
        }
        int mid = l / 2;

        List<Integer> left = new ArrayList<>(mid);
        List<Integer> right = new ArrayList<>(l - mid);

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

    private void mergeTypeAscend(List<Integer> total, List<Integer> left, List<Integer> right) {
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

    private void mergeTypeDescend(List<Integer> total, List<Integer> left, List<Integer> right) {
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
