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
            NoteManager notes = categories.remove(oldCategoryName);
            categories.put(newCategoryName, notes);
        }
    }

    public void deleteCategory(String categoryName) {
        categories.remove(categoryName);
    }

    public void addNoteToCategory(String noteText, String categoryName) {
        if (categories.containsKey(categoryName)) {
            NoteManager noteManager = categories.get(categoryName);
            noteManager.addNote(noteText);
        } else {
            throw new IllegalArgumentException("Категория " + categoryName + " не существует.");
        }
    }

    public String listCategories() {
        StringBuilder result = new StringBuilder();
        int index = 1;
        for (String category : categories.keySet()) {
            result.append(index).append(". ").append(category).append("\n");
            index++;
        }
        return result.toString();
    }

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
            return Collections.emptyList(); // Возвращаем пустой список, если категория не найдена
        }
    }
}
