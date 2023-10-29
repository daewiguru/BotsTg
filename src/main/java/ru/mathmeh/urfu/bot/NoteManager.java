package ru.mathmeh.urfu.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * The NoteManager class manages a collection of notes, providing methods for adding,
 * editing, and deleting notes.
 */
public class NoteManager {
    private List<Note> notes;
    private int nextId;

    /**
     * Constructs a new NoteManager with an empty list of notes and initializes
     * the next available note identifier.
     */
    public NoteManager() {
        notes = new ArrayList<>();
        nextId = 1;
    }

    /**
     * Get the list of all stored notes.
     * @return A list of all notes.
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Adds a new note with the specified text content to the list of notes.
     * @param text The text content of the new note.
     */
    public void addNote(String text) {
        Note note = new Note(nextId, text);
        notes.add(note);
        nextId++;
    }

    /**
     * Edits the text content of a note with the specified identifier.
     * @param id   The identifier of the note to be edited.
     * @param newText The new text content for the note.
     */
    public void editNote(int id, String newText) {
        for (Note note : notes) {
            if (note.getId() == id) {
                note.text = newText;
                return;
            }
        }
    }

    /**
     * Deletes a note with the specified identifier.
     * @param id The identifier of the note to be deleted.
     */
    public void deleteNote(int id) {
        notes.removeIf(note -> note.getId() == id);
    }
}
