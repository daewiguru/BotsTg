package ru.mathmeh.urfu.bot.Logic;

import ru.mathmeh.urfu.bot.Categories;
import ru.mathmeh.urfu.bot.Notes.Note;
import ru.mathmeh.urfu.bot.Notes.NoteManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements the bot logic
 * @author lendys(Yaroslav Prisepnyj)
 * @version 1.0
 */
public class Logic {
    NoteManager noteManager;
    Categories categories;

    public Logic(){
        noteManager = new NoteManager();
        categories = new Categories();
    }

    /**
     * This method realizes cross-platform logic of the bot
     * @param message text of user's message
     * @return text of bot message
     */
    public String handleMessage(String message) {
        String[] parsedCommand = parseCommand(message);
        String command = parsedCommand[0];
        String firstArgument = parsedCommand[1];
        String secondArgument = parsedCommand[2];

        switch ("/" + command) {
            case "/start":
                return "Привет! Я простой бот для записей. Вы можете создавать, управлять категориями и записями.\n" +
                        "Доступные команды: /help";
            case "/help":
                return """
                        Доступные команды:
                        /table - вывод списка записей
                        /add - добавление записи
                        /del - удаление записи (указывайте номер записи, можно посмотреть в списке)
                        /edit - изменение записи (указывайте номер записи, можно посмотреть в списке)
                        /create_category - создание категории
                        /list_categories - список категорий
                        /delete_category - удаление категории
                        /edit_category - изменение категории
                        /list - список записей в категории
                        /move - перемещение записи в категорию
                        """;

            case "/table":
                List<Note> notes = noteManager.getNotes();
                StringBuilder response = new StringBuilder("Ваши записи:\n");
                for (Note note : notes) {
                    response.append(note.getId()).append(". ").append(note.getText()).append("\n");
                }
                return response.toString();

            case "/add":
                if (!firstArgument.isEmpty()) {
                    noteManager.addNote(firstArgument);
                    return "Запись добавлена!";
                } else {
                    return "Пожалуйста, укажите запись.";
                }

            case "/edit":
                if (!firstArgument.isEmpty()) {
                    try {
                        int id = Integer.parseInt(firstArgument);
                        if (!secondArgument.isEmpty()) {
                            noteManager.editNote(id, secondArgument);
                            return "Запись изменена!";
                        } else {
                            return "Пожалуйста, укажите текст для изменения записи.";
                        }
                    } catch (NumberFormatException e) {
                        return "Неверный номер записи.";
                    }
                } else {
                    return "Пожалуйста, введите номер записи и изменения.";
                }

            case "/del":
                if (!firstArgument.isEmpty()) {
                    try {
                        int id = Integer.parseInt(firstArgument);
                        noteManager.deleteNote(id);
                        return "Запись удалена!";
                    } catch (NumberFormatException e) {
                        return "Неверный номер записи.";
                    }
                } else {
                    return "Укажите номер записи для удаления.";
                }

            case "/create_category":
                if (!firstArgument.isEmpty()) {
                    categories.createCategory(firstArgument);
                    return "Категория создана, вы можете добавлять в нее заметки.";
                } else {
                    return "Пожалуйста, укажите имя категории.";
                }

            case "/list_categories":
                return categories.listCategories();

            case "/delete_category":
                if (!firstArgument.isEmpty()) {
                    categories.deleteCategory(firstArgument);
                    return "Категория \"" + firstArgument + "\" удалена.";
                } else {
                    return "Укажите имя категории для удаления.";
                }

            case "/edit_category":
                if (!firstArgument.isEmpty() && !secondArgument.isEmpty()) {
                    categories.editCategory(firstArgument, secondArgument);
                    return "Название категории успешно изменено.";
                } else {
                    return "Пожалуйста, укажите старое и новое название категории.";
                }

            case "/list":
                if (!firstArgument.isEmpty()) {
                    StringBuilder notesList = new StringBuilder(firstArgument + ":\n");
                    notesList
                            .append(categories
                                    .getCategoryByName(firstArgument).list());
                    return notesList.toString();
                } else {
                    return "Укажите имя категории для просмотра записей.";
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
        String[] parsedCommand = new String[3];
        parsedCommand[0] = "";  // Command
        parsedCommand[1] = "";  // First argument
        parsedCommand[2] = "";  // Second argument

        String pattern =
                "^/([a-zA-Zа-яА-Я0-9_]+)\\s*([a-zA-Zа-яА-Я0-9_\\s]*)\\s*(?:to\\s*([a-zA-Zа-яА-Я0-9_\\s]+))?$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(message);

        if (matcher.find()) {
            parsedCommand[0] = matcher.group(1);
            parsedCommand[1] = matcher.group(2);
            parsedCommand[2] = matcher.group(3);
        }

        return parsedCommand;
    }
}
