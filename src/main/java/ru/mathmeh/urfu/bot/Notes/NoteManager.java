package ru.mathmeh.urfu.bot.Notes;

import ru.mathmeh.urfu.bot.Notes.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The NoteManager class manages a collection of notes, providing methods for adding,
 * editing, and deleting notes.
 */
public class NoteManager {
    private final Map<Integer, Note> notes;
    private int nextId;

    /**
     * Constructs a new NoteManager with an empty list of notes and initializes
     * the next available note identifier.
     */
    public NoteManager() {
        notes = new HashMap<>();
        nextId = 1;
    }

    /**
     * Get the list of all stored notes.
     * @return A list of all notes.
     */
    public List<Note> getNotes() {
        return new ArrayList<>(notes.values());
    }

    /**
     * Adds a new note with the specified text content to the list of notes.
     * @param text The text content of the new note.
     */
    public void addNote(String text) {
        Note note = new Note(nextId, text);
        notes.put(nextId, note);
        nextId++;
    }

    /**
     * Edits the text content of a note with the specified identifier.
     * @param id   The identifier of the note to be edited.
     * @param newText The new text content for the note.
     */
    public void editNote(int id, String newText){
        if(notes.containsKey(id)){
            Note note = notes.get(id);
            note.setText(newText);
        }
    }

    /**
     * Deletes a note with the specified identifier.
     * @param id The identifier of the note to be deleted.
     */
    public void deleteNote(int id) {
        notes.remove(id);
    }
    //TODO javadoc
    public String list() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, Note> entry : notes.entrySet()) {
            int key = entry.getKey();
            Note value = entry.getValue();
            result.append(key).append(". ").append(value.getText()).append("\n");
        }
        return result.toString();
    }
}

