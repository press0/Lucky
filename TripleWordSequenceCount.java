
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Collectors;

class TripleWordSequenceCount {

    private static final long LIMIT = 100;

    static public void main(String args[]) {
        Map<Triple, Integer> map;

        if (args.length==0) {
            map = doStandardInput();
        } else if (Files.isRegularFile(Paths.get(args[0]))) {
            System.out.println("first arg is a file");
            map = doFiles(args);
        } else {
            map = doStrings(args);
        }

        List<Map.Entry<Triple, Integer>> list =
                map.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(LIMIT)
                        .collect(Collectors.toList());

        System.out.println(toString(list));
    }

    private static Map<Triple, Integer> doFiles(String[] args) {
        Map<Triple, Integer> map = new HashMap<>();
        Map<Triple, Integer> mapFile;

        FileInputStream inputStream;
        Scanner scanner;

        for (String string : args) {
            inputStream = null;
            scanner = null;
            try {
                inputStream = new FileInputStream(string);
                scanner = new Scanner(inputStream, "UTF-8");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    String[] strings = line.split("[\\n\\t ]");
                    mapFile = doStrings(strings);
                    mapFile.forEach((k, v) -> map.merge(k, v, Integer::sum));

                }
                if (scanner.ioException() != null) {
                    throw scanner.ioException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (scanner != null) {
                    scanner.close();
                }
            }

        }

        return map;

    }

    private static Map<Triple, Integer> doStandardInput() {
        Map<Triple, Integer> map = new HashMap<>();

        String s1;
        String s2 = null;
        String s3 = null;

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String string = scanner.next();
            s1 = s2;
            s2 = s3;
            s3 = string
                    .toLowerCase()
                    .replaceAll("[\\n\\t ]", "")
                    .replaceAll("\\p{Punct}", "");
            if (s1 == null) {
                continue;
            }
            if (!(s1.isEmpty() || s2.isEmpty() || s3.isEmpty())) {
                map.merge(Triple.of(s1, s2, s3), 1, Integer::sum);
            }

        }

        scanner.close();
        return map;
    }

    private static Map<Triple, Integer> doStrings(String[] args) {
        Map<Triple, Integer> map = new HashMap<>();
        String s1;
        String s2 = null;
        String s3 = null;

        for (String string : args) {
            s1 = s2;
            s2 = s3;
            s3 = string
                    .toLowerCase()
                    .replaceAll("[\\n\\t ]", "")
                    .replaceAll("\\p{Punct}", "");
            if (s1 == null) {
                continue;
            }
            if (!(s1.isEmpty() || s2.isEmpty() || s3.isEmpty())) {
                map.merge(Triple.of(s1, s2, s3), 1, Integer::sum);
            }
        }

        return map;
    }

    private static String toString(List<Map.Entry<Triple, Integer>> list) {
        StringJoiner joiner = new StringJoiner(", ");
        list.stream().map(entry -> entry.getValue() + " - " + entry.getKey()).forEach(joiner::add);
        return joiner.toString();
    }

}



