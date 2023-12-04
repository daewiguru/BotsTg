package ru.mathmeh.urfu.bot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * The Reminder class provides functionality to set and manage reminders for the Telegram bot.
 * Reminders can be set for specific messages, notes, or categories with specified date and time.
 */
public class Reminder {
    private final ScheduledExecutorService scheduler;
    private final Map<Long, ScheduledFuture<?>> reminders;

    /**
     * Constructs a new Reminder instance with a scheduled executor service and a map for reminders.
     */
    public Reminder() {
        scheduler = Executors.newScheduledThreadPool(1);
        reminders = new ConcurrentHashMap<>();
    }

    /**
     * Sets a reminder for a specified chat with a given message and date/time.
     *
     * @param chatId    The ID of the chat where the reminder should be sent.
     * @param message   The reminder message to be sent.
     * @param dateTime  The date and time when the reminder should be triggered.
     */
    public void setReminder(long chatId, String message, LocalDateTime dateTime) {
        long delay = calculateDelay(dateTime);

        Runnable reminderTask = () -> {
            TelegramBot.getInstance().sendReminderMessage(chatId, message);
            reminders.remove(chatId);
        };

        ScheduledFuture<?> scheduledFuture = scheduler.schedule(reminderTask, delay, TimeUnit.SECONDS);
        reminders.put(chatId, scheduledFuture);
    }

    /**
     * Deletes a previously set reminder for the specified chat.
     *
     * @param chatId The ID of the chat where the reminder should be deleted.
     */
    public void deleteReminder(String name) {
        ScheduledFuture<?> scheduledFuture = reminders.get(chatId);

        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            reminders.remove(chatId);
        }
    }

    /**
     * Sets a reminder for a specified chat with information about a note and its date/time.
     *
     * @param chatId The ID of the chat where the reminder should be sent.
     * @param note   The name of the note for which the reminder is set.
     * @param date   The date and time when the reminder should be triggered.
     */
    public void set_note_reminder(long chatId, String note, String date) {
        LocalDateTime dateTime = parseDateTime(date);
        String message = "Reminder for note \"" + note + "\" set for " + formatDateTime(dateTime);
        setReminder(chatId, message, dateTime);
    }

    /**
     * Sets a reminder for a specified chat with information about a category and its date/time.
     *
     * @param chatId   The ID of the chat where the reminder should be sent.
     * @param category The name of the category for which the reminder is set.
     * @param date     The date and time when the reminder should be triggered.
     */
    public void set_category_reminder(long chatId, String category, String date) {
        LocalDateTime dateTime = parseDateTime(date);
        String message = "Reminder for category \"" + category + "\" set for " + formatDateTime(dateTime);
        setReminder(chatId, message, dateTime);
    }

    /**
     * Calculates the delay in seconds between the current date/time and the specified date/time.
     *
     * @param dateTime The target date and time.
     * @return The delay in seconds.
     */
    private long calculateDelay(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(now, dateTime).getSeconds();
    }

    /**
     * Parses a date and time represented as a string into a LocalDateTime object.
     *
     * @param date The string representation of the date and time.
     * @return The LocalDateTime object representing the parsed date and time.
     */
    private LocalDateTime parseDateTime(String date) {
        // Implement date and time parsing logic here
        // Example: return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        return LocalDateTime.now();
    }

    /**
     * Formats a LocalDateTime object into a string representation.
     *
     * @param dateTime The LocalDateTime object to be formatted.
     * @return The formatted string representation of the date and time.
     */
    private String formatDateTime(LocalDateTime dateTime) {
        // Implement date and time formatting logic here
        // Example: return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        return "";
    }

    public Map<String, List<String>> getReminders() {
        //TODO
    }
}
