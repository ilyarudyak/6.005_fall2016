package twitter;

import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
    
    public static Set<String> setToLowerCase(Set<String> usernames) {
        return usernames.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

}
