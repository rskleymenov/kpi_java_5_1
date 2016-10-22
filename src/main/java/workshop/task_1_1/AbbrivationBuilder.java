package workshop.task_1_1;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class AbbrivationBuilder {
    public static String build(List<String> list) {
        return list.stream()
                .filter(str -> isNotNull(str) && !str.isEmpty())
                .map(str -> str.substring(0, 1))
                .collect(joining());
    }

    private static boolean isNotNull(String str) {
        return str != null;
    }
}
