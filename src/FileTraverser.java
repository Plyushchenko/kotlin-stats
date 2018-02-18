import java.io.File;
import java.util.ArrayList;
import java.util.List;

class FileTraverser {
    static List<Integer> go(File f) {
        List<Integer> result = initResult();
        File[] files = f.listFiles();
        if (files == null) {
            return result;
        }
        for (File file: files) {
            if (file.isDirectory()) {
                List<Integer> recursiveResult = go(file);
                update(result, recursiveResult);
            } else {
                if (isKtFile(file)) {
                    increment(result, 0);
                }
                increment(result, 1);
            }
        }
        return result;
    }

    private static void increment(List<Integer> result, int i) {
        result.set(i, result.get(i) + 1);
    }

    private static void update(List<Integer> result, List<Integer> recursiveResult) {
        result.set(0, result.get(0) + recursiveResult.get(0));
        result.set(1, result.get(1) + recursiveResult.get(1));
    }

    private static List<Integer> initResult() {
        List<Integer> result = new ArrayList<>();
        result.add(0);
        result.add(0);
        return result;
    }

    private static boolean isKtFile(File file) {
        String name = file.getName();
        return name.endsWith(".kt");
    }
}
