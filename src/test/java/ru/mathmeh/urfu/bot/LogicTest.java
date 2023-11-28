package ru.mathmeh.urfu.bot;

import org.junit.Before;
import org.junit.Test;
import ru.mathmeh.urfu.bot.Logic.Logic;

import static org.junit.Assert.*;
/**
 * This class tests logic class
 * testing all commands
 * @author lendys(Yaroslav Prisepnyj)
 *
 */

public class LogicTest {
    private Logic logic;

    @Before
    public void setUp() {
        logic = new Logic();
    }
    /**
     * This test adds a test note using the /add command and then executes the /table command.
     *  * It verifies that the response starts with "Your notes:" and contains "1. Test Note", indicating
     *  * that the list of notes is displayed correctly.
     */
    @Test
    public void testTableCommand() {
        logic.handleMessage("/add Test Note");
        String response = logic.handleMessage("/table");
        assertEquals("Вот ваши записи:\n1. Test Note\n", response);
    }
    /**
     * This test executes the /add command with the text "Test Note" and verifies that the response
     * is "Note added!". It also checks that the added note is displayed correctly in the list of notes.
     */

    @Test
    public void testAddCommand() {
        String response = logic.handleMessage("/add Test Note");
        assertEquals("Запись добавлена^_^", response);
        String tableResponse = logic.handleMessage("/table");
        assertEquals("Вот ваши записи:\n1. Test Note\n", tableResponse);
    }

    /**
     * This test executes the /add command without specifying any text and checks that the response
     * is "Please provide a text for the note.". It also ensures that no notes are added to the list.
     */
    @Test
    public void testAddCommandWithoutText() {
        String response = logic.handleMessage("/add");
        assertEquals("Пожалуйста, укажите запись.", response);
        String table = logic.handleMessage("/table");
        assertEquals("Вот ваши записи:\n", table);
    }

    /**
     * This test adds a test note using the /add command, then executes the /edit command to change
     * the content of the note. It verifies that the response is "Note edited!" and that the edited note
     * is displayed correctly in the list of notes.
     */
    @Test
    public void testEditCommand() {
        logic.handleMessage("/add Test Note");
        String response = logic.handleMessage("/edit 1 New Text");
        assertEquals("Запись изменена!", response);
        assertTrue(logic.handleMessage("/table").contains("1. New Text"));
    }
    /**
     * This test executes the /edit command with a non-integer ID and checks that the response is
     * "Invalid note ID." It also ensures that no notes are added or e
     */
    @Test
    public void testEditCommandInvalidId() {
        String response = logic.handleMessage("/edit not_an_integer New Text");
        assertEquals("Неревный номер записи.", response);
    }
    /**
     * This test adds a test note using the /add command and then executes the /del command to delete
     * the note. It verifies that the response is "Note deleted!" and that no notes are displayed
     * in the list.
     */
    @Test
    public void testDeleteCommand() {
        logic.handleMessage("/add Test Note");
        String response = logic.handleMessage("/del 1");
        assertEquals("Запись удалена!", response);
        String table = logic.handleMessage("/table");
        assertEquals("Вот ваши записи:\n", table);
    }
    /**
     * This test executes the /del command with a non-integer ID and checks that the response is
     * "Invalid note ID." It also ensures that no notes are added or deleted.
     */
    @Test
    public void testDeleteCommandInvalidId() {
        logic.handleMessage("/add Test Note");
        String response = logic.handleMessage("/del not_an_integer");
        assertEquals("Неверный номер записи.", response);
        assertTrue(logic.handleMessage("/table").contains("1. Test Note"));
    }
    @Test
    public void testListCategory(){
        logic.handleMessage("/create_category тест");
        String response = logic.handleMessage("/list_categories");
        String expected = "1. тест\n";
        assertEquals(expected, response);
    }
    /**
     * Tests the logic of the "/create_category" command. It creates a category, and then checks if the created category appears in the list of categories.
     */
    @Test
    public void testCreateCategoryCommand() {
        String add_response = logic.handleMessage("/create_category тест");
        assertEquals("Категория создана, вы можете добавлять в нее заметки.", add_response);
        String response = logic.handleMessage("/list_categories");
        String expected = "1. тест\n";
        assertEquals(expected, response);
    }

    /**
     * Tests the logic of the "/edit_category" command. It creates a category, edits its name, and then checks if the edited name appears in the list of categories.
     */

    @Test
    public void testEditCategoryCommand() {
        logic.handleMessage("/create_category тест");
        String edit_response = logic.handleMessage("/edit_category тест to тест1");
        assertEquals("Название категории успешно изменено.", edit_response);
        String response = logic.handleMessage("/list_categories");
        String expected = "1. тест1\n";
        assertEquals(expected, response);
    }

    /**
     * Tests the logic of the "/delete_category" command. It creates two categories, deletes one, and then checks if the remaining category appears in the list of categories.
     */
    @Test
    public void testDeleteCategoryCommand() {
        logic.handleMessage("/create_category тест1");
        logic.handleMessage("/create_category тест2");
        String del_response = logic.handleMessage("/delete_category тест1");
        assertEquals("Категория \"тест1\" удалена.", del_response);
        String listCategoriesResponse = logic.handleMessage("/list_categories");
        String expected = "1. тест2\n";
        assertEquals(expected, listCategoriesResponse);
    }

    /**
     * Tests the logic of the "/add" command. It creates a category, adds a note to it, and then checks if the added note appears in the list of notes for that category.
     */
    @Test
    public void testAddToCategoryCommand() {
        logic.handleMessage("/create_category тест");
        String add_response = logic.handleMessage("/added тест1 to тест");
        assertEquals("Запись добавлена в категорию!", add_response);
        String response = logic.handleMessage("/list_notes тест");
        String expected = """
                Записи в категории "тест":
                - тест1
                """;
        assertEquals(expected, response);
    }
    /**
     * Tests any unknown command. Checks if the Logic object returns the correct message when an unrecognized command is received.
     */
    @Test
    public void testUnknownCommand() {
        String response = logic.handleMessage("/unknown");
        assertEquals("Такой команды нет или она не верна. Для получения списка команд используйте /help.", response);
    }
}