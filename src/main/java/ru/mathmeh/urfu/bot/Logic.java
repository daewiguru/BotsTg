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
                return "Привет я простой бот для записей, можешь записывать в меня всё что угодно,\n" +
                        "а еще удалять и редактировать записи. Ежедневник в твоем распоряжении.\n" +
                        "/help";
            case "/help":
                return """
                        /table - команда для вывода списка записей
                        /add - добавление записи
                        /del - удаление записи (указывайте номер записи, можно посмотреть в списке)
                        /edit - изменение записи (указывайте номер записи, можно посмотреть в списке)""";

            case "/table":
                List<Note> notes = noteManager.getNotes();
                StringBuilder response = new StringBuilder("Вот ваши записи:\n");
                for (Note note : notes) {
                    response.append(note.getId()).append(". ").append(note.getText()).append("\n");
                }
                return response.toString();
            case "/add":
                if (parts.length >= 2) {
                    String text = message.substring(command.length() + 1);
                    noteManager.addNote(text);
                    return "Запись добавлена^_^";
                } else {
                    return "Пожалуйста, укажите запись.";
                }
            case "/edit":
                if (parts.length >= 2) {
                    try {
                        int id = Integer.parseInt(parts[1]);
                        String text = message.substring(command.length() + 2 + parts[1].length());
                        noteManager.editNote(id, text);
                        return "Запись изменена!";
                    } catch (NumberFormatException e) {
                        return "Неревный номер записи.";
                    }
                } else {
                    return "Пожалуйста введите номер записи и изменения";
                }
            case "/del":
                if (parts.length >= 2) {
                    try {
                        int id = Integer.parseInt(parts[1]);
                        noteManager.deleteNote(id);
                        return "Запись удалена!";
                    } catch (NumberFormatException e) {
                        return "Неверный номер записи.";
                    }
                } else {
                    return "Укажите номер записи для удаления.";
                }
            default:
                return "Следуй по командам";

        }
    }
}