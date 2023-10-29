package ru.mathmeh.urfu.bot;

import java.util.List;

/**
 * This class implements the bot logic
 * @author lendys(Yaroslav Prisepnyj)
 * @version 1.0
 */
public class Logic {
    NoteManager noteManager;

    public Logic(){
        noteManager = new NoteManager();
    }

    /**
     * This method realize cross-platform logic of bots
     * @param message text of user`s message
     * @return tet of bot message
     */

    public String handleMessage(String message) {
        String[] parts = message.split(" ");
        String command = parts[0];

        switch (command) {
            case "/start":
                return "Hello, I'm a simple note bot.\n" +
                        "/help";
            case "/help":
                return """
                        I'm a bot for managing notes. You can add, edit, delete, and view your notes.
                        /table - view your notes
                        /add - add a new entry
                        /del - delete an entry (specify the entry number)
                        /edit - edit an entry (specify the entry number)""";

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