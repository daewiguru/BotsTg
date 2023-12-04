package ru.mathmeh.urfu.bot.Logic;

import ru.mathmeh.urfu.bot.Categories;
import ru.mathmeh.urfu.bot.Notes.Note;
import ru.mathmeh.urfu.bot.Notes.NoteManager;
import ru.mathmeh.urfu.bot.Printer;
import ru.mathmeh.urfu.bot.Reminder;
import ru.mathmeh.urfu.bot.TelegramBot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * This class implements the bot logic
 * @author lendys(Yaroslav Prisepnyj)
 * @version 1.0
 */
public class Logic {
    private final NoteManager noteManager;
    private final Categories categories;
    private final Printer printer;
    private final Reminder reminder;
    private final ScheduledExecutorService executorService;

    public Logic() {
        noteManager = new NoteManager();
        categories = new Categories();
        printer = new Printer();
        reminder = new Reminder();
        executorService = Executors.newScheduledThreadPool(1);
        initReminderScheduler();
    }

    private void initReminderScheduler() {
        executorService.scheduleAtFixedRate(() -> {
            Map<String, List<String>> reminders = reminder.getReminders();

            for (Map.Entry<String, List<String>> entry : reminders.entrySet()) {
                String chatId = entry.getKey();
                List<String> messages = entry.getValue();

                for (String message : messages) {
                    sendReminder(chatId, message);
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void sendReminder(String chatId, String message) {
        // Метод для отправки напоминания в чат с использованием TelegramBot
        TelegramBot.getInstance().sendMessage(Long.parseLong(chatId), message);
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
        String thirdArgument = parsedCommand[3];

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
                    try {
                        int id = Integer.parseInt(parsedCommand[1]);
                        String text = message.substring(command.length() + 2 + parsedCommand[1].length());
                        noteManager.editNote(id, text);
                        return "Запись изменена!";
                    } catch (NumberFormatException e) {
                        return "Неревный номер записи.";
                    }
                } else {
                    return "Пожалуйста введите номер записи и изменения";
                }
            case "del":
                if (pars.length >= 2) {
                    try {
                        int id = Integer.parseInt(parsedCommand[1]);
                        noteManager.deleteNote(id);
                        return "Запись удалена!";
                    } catch (NumberFormatException e) {
                        return "Неверный номер записи.";
                    }
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
                if (!firstArgument.isEmpty()) {
                    categories.addNoteToCategory(firstArgument,secondArgument);
                    return "Запись добавлена в категорию!";
                } else {
                    return "Пожалуйста, укажите запись.";
                }

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
                if (!firstArgument.isEmpty()) {
                    categories.deleteCategory(firstArgument);
                    return "Категория \"" + firstArgument + "\" удалена.";
                } else {
                    return "Укажите имя категории для удаления.";
                }

            case "edit_category":
                if (!firstArgument.isEmpty() && !secondArgument.isEmpty()) {
                    categories.editCategory(firstArgument, secondArgument);
                    return "Название категории успешно изменено.";
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
            case "time_category":
                if (!firstArgument.isEmpty() &&
                        !secondArgument.isEmpty() && !thirdArgument.isEmpty()) {
                    // Парсим дату и устанавливаем напоминание для категории
                    reminder.set_category_reminder(
                            firstArgument, secondArgument, thirdArgument);
                    return "Напоминание установлено на 12:00";
                } else {
                    return "Пожалуйста, укажите категорию и дату.";
                }
            case "time_note":
                if (!firstArgument.isEmpty() &&
                        !secondArgument.isEmpty() && !thirdArgument.isEmpty()) {
                    // Парсим дату и устанавливаем напоминание для записи
                    reminder.set_note_reminder(firstArgument, secondArgument, thirdArgument);
                    return "Напоминание установлено на 12:00";
                } else {
                    return "Пожалуйста, укажите запись и дату.";
                }
            case "delete_time_category":
                if (!firstArgument.isEmpty()) {
                    // Удаляем напоминание для категории
                    reminder.deleteReminder();
                    return "Напоминание убрано";
                } else {
                    return "Пожалуйста, укажите категорию для удаления напоминания.";
                }
            case "delete_time_note":
                if (!firstArgument.isEmpty()) {
                    // Удаляем напоминание для записи
                    reminder.deleteReminder();
                    return "Напоминание убрано";
                } else {
                    return "Пожалуйста, укажите запись для удаления напоминания.";
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

        String[] parsedCommand = new String[4];
        parsedCommand[0] = "";  // Command
        parsedCommand[1] = "";  // First argument
        parsedCommand[2] = "";  // Second argument
        parsedCommand[3] = "";  // Date

        if (words.length > 0) {
            parsedCommand[0] = words[0].replace("/", "");  // Убираем "/"
        }

        if (words.length > 1) {
            parsedCommand[1] = words[1];
        }

        if (words.length > 2 && words[2].equalsIgnoreCase("to")) {
            // Если есть "to", и следующее слово не равно "to"
            if (words.length > 3) {
                parsedCommand[2] = words[3];
            }

            // Проверяем, есть ли еще слово (дата)
            if (words.length > 4) {
                parsedCommand[3] = String
                        .join(" ", Arrays.copyOfRange(words, 4, words.length));
            }
        }
        return parsedCommand;
    }

    public String getCurrentChatId() {
        // Замените этот код на логику, которая возвращает текущий chatId
        return "ваш_chat_id";
    }

}

