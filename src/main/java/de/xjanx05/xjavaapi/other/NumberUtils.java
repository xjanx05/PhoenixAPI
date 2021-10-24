package de.xjanx05.xjavaapi.other;

public class NumberUtils {
    public static String formatTimeInteger(int duration) {
        String string = "";
        long days = 0L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        if (duration / 60 / 60 / 24 >= 1) {
            days = duration / 60 / 60 / 24;
            duration -= duration / 60 / 60 / 24 * 60 * 60 * 24;
        }
        if (duration / 60 / 60 >= 1) {
            hours = duration / 60 / 60;
            duration -= duration / 60 / 60 * 60 * 60;
        }
        if (duration / 60 >= 1) {
            minutes = duration / 60;
            duration -= duration / 60 * 60;
        }
        if (duration >= 1) {
            seconds = duration;
        }
        if (days >= 1L) {
            if (days == 1L) {
                string = days + ":";
            } else {
                string = days + ":";
            }
        }
        if (hours != 0L) {
            if (hours <= 9L) {
                string = string + "0" + hours + ":";
            } else {
                string = string + hours + ":";
            }
        }
        if (minutes <= 9L) {
            string = string + "0" + minutes + ":";
        } else {
            string = string + minutes + ":";
        }
        if (seconds <= 9L) {
            string = string + "0" + seconds;
        } else {
            string += seconds;
        }
        return string;
    }

    public static int roundUp(int num, int divisor) {
        int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
        return sign * (Math.abs(num) + Math.abs(divisor) - 1) / Math.abs(divisor);
    }
}

