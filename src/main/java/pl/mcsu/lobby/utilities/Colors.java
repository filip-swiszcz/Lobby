package pl.mcsu.lobby.utilities;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Colors {

    public static ChatColor light_red = ChatColor.of("#fc5c65");
    public static ChatColor red = ChatColor.of("#eb3b5a");
    public static ChatColor light_green = ChatColor.of("#26de81");
    public static ChatColor green = ChatColor.of("#20bf6b");
    public static ChatColor light_blue = ChatColor.of("#4b7bec");
    public static ChatColor blue = ChatColor.of("#3867d6");
    public static ChatColor light_purple = ChatColor.of("#a55eea");
    public static ChatColor purple = ChatColor.of("#8854d0");
    public static ChatColor light_gray = ChatColor.of("#d1d8e0");
    public static ChatColor gray = ChatColor.of("#a5b1c2");

    public static String convert(String text) {
        if (text == null) return null;
        final Pattern pattern = Pattern.compile("#([A-Fa-f0-9]{6})");
        Matcher matcher = pattern.matcher(text);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, ChatColor.of("#" + matcher.group(1)).toString());
        }
        return matcher.appendTail(stringBuffer).toString();
    }

    public static String gradient(String text, Color from, Color to, boolean bold) {
        final double[] red = linear(from.getRed(), to.getRed(), text.length());
        final double[] green = linear(from.getGreen(), to.getGreen(), text.length());
        final double[] blue = linear(from.getBlue(), to.getBlue(), text.length());
        final StringBuilder builder = new StringBuilder();
        if (bold) {
            for (int i = 0; i < text.length(); i++) {
                builder.append(ChatColor.of(new Color(
                                (int) Math.round(red[i]),
                                (int) Math.round(green[i]),
                                (int) Math.round(blue[i]))))
                        .append("&l")
                        .append(text.charAt(i));
            }
            return ChatColor.translateAlternateColorCodes('&', builder.toString());
        }
        for (int i = 0; i < text.length(); i++) {
            builder.append(ChatColor.of(new Color(
                            (int) Math.round(red[i]),
                            (int) Math.round(green[i]),
                            (int) Math.round(blue[i]))))
                    .append(text.charAt(i));
        }
        return builder.toString();
    }

    public static double[] linear(double from, double to, int max) {
        final double[] result = new double[max];
        for (int i = 0; i < max; i++) {
            result[i] = from + i * ((to - from) / (max - 1));
        }
        return result;
    }

}
