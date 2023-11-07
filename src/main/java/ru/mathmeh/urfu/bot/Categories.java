package ru.mathmeh.urfu.bot;


import ru.mathmeh.urfu.bot.Notes.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The Categories class manages a collection of categories, each containing a list of notes.
 */
public class Categories {
    /**
     * A HashMap where keys are category names and values are NoteManagers.
     */
    private final Map<String, NoteManager> categories;

    /**
     * Constructor method for Categories.
     */
    public Categories() {
        categories = new HashMap<>();
    }

    /**
     * Creates a new category with the specified name.
     * @param categoryName The name of the new category.
     */
    public void createCategory(String categoryName) {
        if (!categories.containsKey(categoryName)) {
            categories.put(categoryName, new NoteManager());
        } else {
            throw new IllegalArgumentException
                    ("Категория " + categoryName + " уже существует.");
        }
    }

    /**
     * Edits (renames) an existing category.
     * @param oldCategoryName The current name of the category.
     * @param newCategoryName The new name for the category.
     */
    public void editCategory(String oldCategoryName, String newCategoryName) {
        if (categories.containsKey(oldCategoryName)) {
            NoteManager temp = categories.get(oldCategoryName);
            categories.remove(oldCategoryName);
            categories.put(newCategoryName, temp);
        } else {
            throw new IllegalArgumentException
                    ("Старая категория - " + oldCategoryName + ", не найдена.");
        }
    }

    /**
     * Lists all available categories.
     * @return A list of category names.
     */
    public String listCategories() {
        StringBuilder result = new StringBuilder();
        int index = 1;
        for (String category : categories.keySet()) {
            result.append(index).append(". ").append(category).append("\n");
            index++;
        }
        return result.toString();
    }

    /**
     * Deletes an empty category.
     * @param deletingCategory The name of the category to be deleted.
     */
    public void deleteCategory(String deletingCategory) {
        if (categories.containsKey(deletingCategory) &&
                categories.get(deletingCategory).getNotes().isEmpty()) {
            categories.remove(deletingCategory);
        } else {
            throw new IllegalArgumentException
                    ("Категория " + deletingCategory + " не найдена или не пуста.");
        }
    }

    /**
     * Displays a table of all categories and their notes.
     * @return A formatted table of categories and notes.
     */
    public String table() {
        StringBuilder table = new StringBuilder();
        for (Map.Entry<String, NoteManager> entry : categories.entrySet()) {
            String categoryName = entry.getKey();
            NoteManager noteManager = entry.getValue();
            table
                    .append(categoryName)
                    .append(":\n")
                    .append("\t")
                    .append(noteManager.list());

        }
        return table.toString();
    }
}
