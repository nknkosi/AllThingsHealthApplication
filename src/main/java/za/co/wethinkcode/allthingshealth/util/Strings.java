package za.co.wethinkcode.allthingshealth.util;

public class Strings {
    public static String capitaliseFirstLetter(String string) {
        return string.substring(0, 1)
                .toUpperCase() + string.substring(1);
    }
}
