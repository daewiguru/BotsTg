package ru.mathmeh.urfu.bot.Logic;

import ru.mathmeh.urfu.bot.Categories;
import ru.mathmeh.urfu.bot.Notes.Note;
import ru.mathmeh.urfu.bot.Notes.NoteManager;
import ru.mathmeh.urfu.bot.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements the bot logic
 * @author lendys(Yaroslav Prisepnyj)
 * @version 1.0
 */
public class Logic {
    private final NoteManager noteManager;
    private final Categories categories;
    private final Printer printer;

    public Logic(){
        noteManager = new NoteManager();
        categories = new Categories();
        printer = new Printer();
    }

    /**
     * This method realizes cross-platform logic of the bot
     * @param message text of user's message
     * @return text of bot message
     */
    public String handleMessage(String message) {
        String[] pars = message.split(" ");
        String[] parsedCommand = parseCommand(message);
        String command = parsedCommand[0];
        String firstArgument = parsedCommand[1];
        String secondArgument = parsedCommand[2];

        switch (command) {
            case "start":
                return "Привет! Я простой бот для записей. Вы можете создавать, управлять категориями и записями.\n" +
                        "Доступные команды: /help";

            case "help":
                return """
                        Доступные команды:
                        /add - добавление записи
                        /edit -изменение записи
                        /del - удаление записи
                        /added <> to <> - добавление записи в категории 📩
                        /create_category - создание категории 📁
                        /list_categories - список категорий 🗂
                        /delete_category - удаление категории ❌
                        /edit_category - изменение категории ✏️
                        /list_notes - вывод категории и её содержания 📚
                        """;

            case "add":
                if (pars.length >= 2) {
                    String text = message.substring(command.length() + 1);
                    noteManager.addNote(text);
                    return "Запись добавлена^_^";
                } else {
                    return "Пожалуйста, укажите запись.";
                }

            case "edit":
                if (pars.length >= 2) {
                    int id;
                    try {
                        id = Integer.parseInt(parsedCommand[1]);
                    } catch (NumberFormatException e) {
                        return "Неверный номер записи.";
                    }

                    if (noteManager.existNote(id)) {
                        try {
                            String text = message.substring(command.length() + 2 + parsedCommand[1].length());
                            noteManager.editNote(id, text);
                            return "Запись изменена!";
                        } catch (NumberFormatException e) {
                            return "Неверный номер записи.";
                            // TODO дописать тесты
                        }
                    } else {
                        return "Записи с номером " + id + " не существует.";
                    }
                } else {
                    return "Пожалуйста введите номер записи и изменения";
                }

            case "del":
                if (pars.length >= 2) {
                    int id; // Переменная id объявлена вне блока try
                    try {
                        id = Integer.parseInt(parsedCommand[1]);
                    } catch (NumberFormatException e) {
                        return "Неверный номер записи.";
                    }

                    if (noteManager.existNote(id)) {
                        noteManager.deleteNote(id);
                        return "Запись удалена!";
                    }
                    // TODO тесты дописать
                } else {
                    return "Укажите номер записи для удаления.";
                }

            case  "table":
                List <Note> notes = noteManager.getNotes();
                StringBuilder response = new StringBuilder("Вот ваши записи:\n");
                for (Note note : notes) {
                    response.append(note.getId()).append(".").append(note.getText()).append("\n");
                }
                return response.toString();

            case "added":
                if (firstArgument.isEmpty() || secondArgument.isEmpty()) {
                    return "Пожалуйста, укажите исходную запись и категорию.";
                }

                if (!categories.existCategory(secondArgument)) {
                    return "Указанной категории не существует.";
                }

                // Дополнительная логика добавления записи в категорию
                categories.addNoteToCategory(firstArgument, secondArgument);

                return "Запись добавлена в категорию!";

            case "create_category":
                if (!firstArgument.isEmpty()) {
                    categories.createCategory(firstArgument);
                    return "Категория создана, вы можете добавлять в нее заметки.";
                } else {
                    return "Пожалуйста, укажите имя категории.";
                }

            case "list_categories":
                return categories.listCategories();

            case "delete_category":
                //TODO добавить проверку и тесты
                if (!firstArgument.isEmpty()) {
                    categories.deleteCategory(firstArgument);
                    return "Категория \"" + firstArgument + "\" удалена.";
                } else {
                    return "Укажите имя категории для удаления.";
                }

            case "edit_category":
                if (!firstArgument.isEmpty() && !secondArgument.isEmpty()) {
                    if(categories.existCategory(firstArgument)){
                        categories.editCategory(firstArgument, secondArgument);
                        return "Название категории успешно изменено.";
                    } else {
                        return "Категория, которую вы хотите изменить, не существует";
                    }

                    //TODO вывод на изменении не существующей категории
                } else {
                    return "Пожалуйста, укажите старое и новое название категории.";
                }
            case "list_notes":
                if (!firstArgument.isEmpty()) {
                    List<String> notesInCategory = categories.getNotesInCategory(firstArgument);
                    String res = "Записи в категории \"" + firstArgument + "\":\n";
                    List<String> formattedNotes = new ArrayList<>();

                    for (String note : notesInCategory) {
                        formattedNotes.add("- " + note);
                    }

                    return res + printer.makeString(formattedNotes, false);
                } else {
                    return "Пожалуйста, укажите название категории для просмотра записей.";
                }

            default:
                return "Такой команды нет или она не верна. Для получения списка команд используйте /help.";
        }
    }

    /**
     * Parses a command with arguments.
     * @param message The user's message.
     * @return An array with the command and its arguments.
     */
    private String[] parseCommand(String message) {
        String[] words = message.trim().split("\\s+");

        String[] parsedCommand = new String[3];
        parsedCommand[0] = "";  // Command
        parsedCommand[1] = "";  // First argument
        parsedCommand[2] = "";  // Second argument

        if (words.length > 0) {
            parsedCommand[0] = words[0].replace("/", "");  // Убираем "/"
        }

        if (words.length > 1) {
            parsedCommand[1] = words[1];
        }

        if (words.length > 2 && words[2].equalsIgnoreCase("to")) {
            // Если есть "to", и следующее слово не равно "to"
            if (words.length > 3) {
                parsedCommand[2] = String.join(" ", Arrays.copyOfRange(words, 3, words.length));
            }
        }
        return parsedCommand;
    }

}
