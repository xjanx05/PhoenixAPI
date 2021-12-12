package de.codingphoenix.phoenixapi.other;

public class StringUtils {
    public static String capitaliseString(String s) {
        String[] strings = s.split(" ");
        if (strings.length > 1) {
            StringBuilder returnable = new StringBuilder();
            for (String string : strings) {
                returnable.append(capitaliseString(string)).append(" ");
            }
            returnable.deleteCharAt(returnable.length() - 1);
            return returnable.toString();
        } else {
            return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }
    }

}
