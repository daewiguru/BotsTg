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

        assertTrue(response.startsWith("Вот ваши записи:"));
        assertTrue(response.contains("1. Test Note"));
    }
    /**
     * This test executes the /add command with the text "Test Note" and verifies that the response
     * is "Note added!". It also checks that the added note is displayed correctly in the list of notes.
     */
    @Test
    public void testAddCommand() {
        String response = logic.handleMessage("/add Test Note");
        assertEquals("Запись добавлена^_^", response);
        assertTrue(logic.handleMessage("/table").contains("1. Test Note"));

    }
    /**
     * This test executes the /add command without specifying any text and checks that the response
     * is "Please provide a text for the note.". It also ensures that no notes are added to the list.
     */
    @Test
    public void testAddCommandWithoutText() {
        String response = logic.handleMessage("/add");
        assertEquals("Пожалуйста, укажите запись.", response);
        assertTrue(logic.handleMessage("/table").contains(" "));
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
        assertTrue(logic.handleMessage("/table").contains(" "));
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
    /**
     * Tests any unknown command. Checks if the Logic object returns the correct message when an unrecognized command is received.
     */
    @Test
    public void testUnknownCommand() {
        String response = logic.handleMessage("/unknown");
        assertEquals("Следуй по командам", response);

    }
    @Test
    public void testCreateCategoryCommand() {
        String response = logic.handleMessage("/create_category NewCategory");
        assertEquals("Категория создана, вы можете добавлять в нее заметки.", response);
    }

    @Test
    public void testListCategoriesCommand() {
        logic.handleMessage("/create_category Category1");
        logic.handleMessage("/create_category Category2");
        String response = logic.handleMessage("/list_categories");
        assertTrue(response.contains("Category1"));
        assertTrue(response.contains("Category2"));
    }

    @Test
    public void testDeleteCategoryCommand() {
        logic.handleMessage("/create_category CategoryToDelete");
        String response = logic.handleMessage("/delete_category CategoryToDelete");
        assertEquals("Категория \"CategoryToDelete\" удалена.", response);
    }

    @Test
    public void testEditCategoryCommand() {
        logic.handleMessage("/create_category OldCategoryName");
        String response = logic.handleMessage("/edit_category OldCategoryName NewCategoryName");
        assertEquals("Название категории успешно изменено.", response);
    }
}
