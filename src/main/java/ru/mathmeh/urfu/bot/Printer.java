package ru.mathmeh.urfu.bot;

import java.util.List;

/**
 * The Printer class provides a method to create a formatted string from a list of items.
 * It can include indices before each item if specified.
 */
public class Printer {
    /**
     * Creates a formatted string from the given list of items.
     *
     * @param items         The list of items to be formatted.
     * @param includeIndex  Specifies whether to include indices before each item.
     * @return              A formatted string representing the items.
     */
    public String makeString(List<String> items, boolean includeIndex) {
        StringBuilder result = new StringBuilder();
        int index = 1;
        for (String item : items) {
            if (includeIndex) {
                result.append(index).append(". ");
            }
            result.append(item).append("\n");
            index++;
        }
        return result.toString();
    }
}