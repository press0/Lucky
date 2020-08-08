
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
        if (args.length == 0) {
            System.out.println("args required");
            System.exit(-1);
        }
        //is the first arg a path that exists ?
        Path path = Paths.get(args[0]);
        boolean pathExists = Files.isRegularFile(path);
        System.out.println("is first arg a file: " + pathExists);

        Map<Triple, Integer> map;

        if (pathExists) {
            map = doScanFiles(args);
        } else {
            map = doStream(args);
        }

        List<Map.Entry<Triple, Integer>> list =
                map.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(LIMIT)
                        .collect(Collectors.toList());

        System.out.println(toString(list));


    }

    private static String toString(List<Map.Entry<Triple, Integer>> list) {
        StringJoiner joiner = new StringJoiner(", ");
        list.stream().map(entry -> entry.getValue() + " - " + entry.getKey()).forEach(joiner::add);
        return joiner.toString();
    }


    private static Map<Triple, Integer> doScanFiles(String[] args) {
        Map<Triple, Integer> map = new HashMap<>();
        Map<Triple, Integer> mapFile;

        FileInputStream inputStream;
        Scanner sc;

        for (String string : args) {
            inputStream = null;
            sc = null;
            try {
                inputStream = new FileInputStream(string);
                sc = new Scanner(inputStream, "UTF-8");
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();

                    String[] strings = line.split("[\\n\\t ]");
                    mapFile = doStream(strings);
                    mapFile.forEach((k, v) -> map.merge(k, v, Integer::sum));

                }
                if (sc.ioException() != null) {
                    throw sc.ioException();
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
                if (sc != null) {
                    sc.close();
                }
            }

        }

        return map;

    }

    private static Map<Triple, Integer> doStream(String[] args) {
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

}



