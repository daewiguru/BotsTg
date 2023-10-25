package ru.mathmeh.urfu.bot;

import java.util.List;

/**
 *  * The Logic class handles user commands and manages notes.
 *  * This class is essentially the controller of the bot, as it receives
 *  * user input, processes it, interacts with the NoteManager to manipulate
 *  * data, and then forms an appropriate response. The user commands it can handle
 *  * include: /start, /help, /table, /add, /edit, /del and other unsupported cases.
 */
public class Logic {
    private NoteManager noteManager;

    /**
     * /**
     *      * Constructor for the Logic class.
     *      * Initializes a new NoteManager object that'll be used for managing the notes.
     *      */

    public Logic() {
        noteManager = new NoteManager();
    }

    /**
     * Handles user messages and performs actions based on the command.
     *
     * @param message The user's message.
     * @return The bot's response.
     */
    public String handleMessage(String message) {
        String[] parts = message.split(" ");
        String command = parts[0];

        switch (command) {
            case "/start":
                return "Hello, I'm a simple note bot.\n" +
                        "/help";
            case "/help":
                return "I'm a bot for managing notes. You can add, edit, delete, and view your notes.\n" +
                        "/table - view your notes\n" +
                        "/add - add a new entry\n" +
                        "/del - delete an entry (specify the entry number)\n" +
                        "/edit - edit an entry (specify the entry number)";
            case "/table":
                List<Note> notes = noteManager.getNotes();
                StringBuilder response = new StringBuilder("Your notes:\n");
                for (Note note : notes) {
                    response.append(note.getId()).append(". ").append(note.getText()).append("\n");
                }
                return response.toString();
            case "/add":
                if (parts.length >= 2) {
                    String text = message.substring(command.length() + 1);
                    noteManager.addNote(text);
                    return "Note added!";
                } else {
                    return "Please provide a text for the note.";
                }
            case "/edit":
                if (parts.length >= 2) {
                    try {
                        int id = Integer.parseInt(parts[1]);
                        String text = message.substring(command.length() + 2 + parts[1].length());
                        noteManager.editNote(id, text);
                        return "Note edited!";
                    } catch (NumberFormatException e) {
                        return "Invalid note ID.";
                    }
                } else {
                    return "Please provide a note ID and text for editing.";
                }
            case "/del":
                if (parts.length >= 2) {
                    try {
                        int id = Integer.parseInt(parts[1]);
                        noteManager.deleteNote(id);
                        return "Note deleted!";
                    } catch (NumberFormatException e) {
                        return "Invalid note ID.";
                    }
                } else {
                    return "Please provide a note ID for deletion.";
                }
            default:
                return "I don't understand the command.";
        }
    }
}