package gugunava.danil.usermanagementservice.util;

public class StringUtil {

    public static boolean isNotBlank(String s) {
        return s != null && !s.isBlank();
    }

    public static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
