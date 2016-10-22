package workshop.task_2_1;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by olenasyrota on 6/28/16.
 */
public class DeveloperService {

    public static List<String> getLanguages(List<Developer> team) {
        return team.stream()
                .flatMap(languages -> languages.getLanguages().stream())
                .collect(toList());
    }


}
