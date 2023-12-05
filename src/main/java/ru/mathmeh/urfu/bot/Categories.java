package ru.mathmeh.urfu.bot;

import ru.mathmeh.urfu.bot.Notes.*;

import java.util.*;

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
            throw new IllegalArgumentException(
                    "Категория " + categoryName + " уже существует.");
        }
    }

    /**
     * Edits (renames) an existing category.
     * @param oldCategoryName The current name of the category.
     * @param newCategoryName The new name for the category.
     */
    public void editCategory(String oldCategoryName, String newCategoryName) {
        if (categories.containsKey(oldCategoryName)) {
            NoteManager notes = categories.remove(oldCategoryName);
            categories.put(newCategoryName, notes);
        }
    }

    /**
     * This is a method for deleting a category
     * @param categoryName Is a name of deleting category
     */
    public void deleteCategory(String categoryName) {
        categories.remove(categoryName);
    }

    /**
     * This is a method for adding note to the category
     * @param noteText Text of adding note
     * @param categoryName Name of category, where note are added
     */
    public void addNoteToCategory(String noteText, String categoryName) {
        if (categories.containsKey(categoryName)) {
            NoteManager noteManager = categories.get(categoryName);
            noteManager.addNote(noteText);
        } else {
            throw new IllegalArgumentException(
                    "Категория " + categoryName + " не существует.");
        }
    }

    /**
     * This is a method for listing all categories
     * @return A formatted string of all categories
     */
    public String listCategories() {
        Printer printer = new Printer();
        List<String> categoryList = new ArrayList<>(categories.keySet());
        return printer.makeString(categoryList, true);
    }

    /**
     * This method shows all notes in category
     * @param categoryName Is a name of category that notes are showed
     * @return A list of notes of 1 category or empty list
     */
    public List<String> getNotesInCategory(String categoryName) {
        if (categories.containsKey(categoryName)) {
            NoteManager noteManager = categories.get(categoryName);
            List<Note> notes = noteManager.getNotes();
            List<String> noteTexts = new ArrayList<>();

            for (Note note : notes) {
                noteTexts.add(note.getText());
            }

            return noteTexts;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Checks if a category with the specified name exists.
     *
     * @param categoryName The name of the category to check for existence.
     * @return {@code true} if the category exists, {@code false} otherwise.
     */
    public boolean existCategory(String categoryName) {
        if (categories.get(categoryName) != null){
            return true;
        }
        return false;
    }
}
