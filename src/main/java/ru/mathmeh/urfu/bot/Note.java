package ru.mathmeh.urfu.bot;

/**
 * The Note class represents a single note with a unique identifier and text content.
 */
public class Note {
    private final int id;
    private String text;

    /**
     * Constructs a new note with the given identifier and text content.
     * @param id   The unique identifier of the note.
     * @param text The text content of the note.
     */
    public Note(int id, String text) {
        this.id = id;
        this.text = text;
    }

    /**
     * Get the unique identifier of the note.
     * @return The identifier of the note.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the text content of the note.
     * @return The text content of the note.
     */
    public String getText() {
        return text;
    }
    public void setText(String newText){
        this.text = newText;
    }
}