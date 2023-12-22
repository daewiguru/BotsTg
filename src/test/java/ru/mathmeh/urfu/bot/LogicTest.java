package ru.mathmeh.urfu.bot;

import org.junit.Before;
import org.junit.Test;
import ru.mathmeh.urfu.bot.Logic.Logic;

import static org.junit.Assert.*;

/**
 * This class tests the Logic class, covering various commands and scenarios.
 * @author lendys (Yaroslav Prisepnyj)
 */
public class LogicTest {
    private Logic logic;

    @Before
    public void setUp() {
        logic = new Logic();
    }

    /**
     * This test adds a test note using the /add command and then executes the /table command.
     * It verifies that the response starts with "Your notes:" and contains "1. Test Note", indicating
     * that the list of notes is displayed correctly.
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
        assertEquals("Неверный номер записи.", response);
    }

    /**
     * Tests the logic of the "/edit_category" command when trying to edit a non-existent category.
     * It executes the "/edit_category" command with a non-existent category, and checks if the response indicates that the category does not exist.
     */
    @Test
    public void testEditUnexistCategoryCommand() {
        String response = logic.handleMessage("/edit_category nonExistentCategory to newCategory");
        assertEquals("Категория, которую вы хотите изменить, не существует", response);
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

    /**
     * Tests the logic of the "/list_categories" command.
     * It creates a category, executes the "/list_categories" command, and checks if the response contains the expected category name.
     */
    @Test
    public void testListCategory() {
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
     * We look at the filling of the category, so the addition has been checked; all that remains is to
     * check the contents of the category; in this test we will not check the addition,
     * but will check the emptiness
     */
    @Test
    public void testListNotes() {
        logic.handleMessage("/create_category тест");
        String categoryResponse = logic.handleMessage("/list_notes тест");
        String expected = """
                Записи в категории \"тест\":\n""";
        assertEquals(expected, categoryResponse);
    }

    /**
     * Tests the logic of the "/add" command. It creates a category, adds a note to it,
     * and then checks if the added note appears in the list of notes for that category.
     * We also check the completion of entries in the category,
     * this is an additional check "list_notes"
     */
    @Test
    public void testAddToUnexistCategoryCommand() {
        // Verify that there are still no categories present
        String listCategoriesResponseAfter = logic.handleMessage("/list_categories");
        assertEquals("", listCategoriesResponseAfter);

        String response = logic.handleMessage("/added test1 to nonExistentCategory");
        assertEquals("Указанной категории не существует.", response);

    }

    /**
     * Tests the logic of the "/added" command. It creates a category, adds a note to it,
     * and then checks if the added note appears in the list of notes for that category.
     * We also check the completion of entries in the category,
     * this is an additional check "list_notes"
     */
    @Test
    public void testAddToCategoryCommand() {
        logic.handleMessage("/create_category testCategory");
        String response = logic.handleMessage("/added test1 to testCategory");
        assertEquals("Запись добавлена в категорию!", response);
        String listNotesResponse = logic.handleMessage("/list_notes testCategory");
        String expected = """
            Записи в категории "testCategory":
            - test1
            """;
        assertEquals(expected, listNotesResponse);
    }

    /**
     * Tests the logic of the "/added" command when the target category is not specified.
     * It checks if the Logic object returns the correct message when the second category is not provided.
     */
    @Test
    public void testAddedCommandWithoutCategory() {
        String response = logic.handleMessage("/added test1 to");
        assertEquals("Пожалуйста, укажите исходную запись и категорию.", response);
    }

    /**
     * Tests any unknown command. Checks if the Logic object returns the correct message when an unrecognized command is received.
     */
    @Test
    public void testUnknownCommand() {
        String response = logic.handleMessage("/unknown");
        assertEquals("Такой команды нет или она не верна. Для получения списка команд используйте /help.", response);
    }

    /**
     * Tests the logic of the "/edit" command with a non-existent note ID.
     */
    @Test
    public void testEditUnexistNoteCommand() {
        // Verify that there are no notes present
        String tableResponse = logic.handleMessage("/table");
        assertEquals("Вот ваши записи:\n", tableResponse);

        String response = logic.handleMessage("/edit 999 New Text");
        assertEquals("Записи с номером 999 не существует.", response);
    }

    /**
     * Tests the logic of non-command message
     */
    @Test
    public void testInvalidCommand() {
        String response = logic.handleMessage("Some random text");
        assertEquals("Такой команды нет или она не верна. Для получения списка команд используйте /help.", response);
    }
}
