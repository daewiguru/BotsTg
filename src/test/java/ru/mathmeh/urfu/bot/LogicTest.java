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
     * Tests the logic of the "/create_category" command. It creates a category, and then checks if the created category appears in the list of categories.
     */
    @Test
    public void testCreateCategoryCommand() {
        logic.handleMessage("/create_category тест");
        String response = logic.handleMessage("/list_categories");
        assertEquals("1.тест", response);

    }
    /**
     * Tests the logic of the "/edit_category" command. It creates a category, edits its name, and then checks if the edited name appears in the list of categories.
     */

    @Test
    public void testeditCategoryCommand(){
        logic.handleMessage("/create_category тест");
        logic.handleMessage("/edit_category тест to тест1");
        String response = logic.handleMessage("/list_categories");
        assertEquals("1. тест1", response);
    }
    /**
     * Tests the logic of the "/delete_category" command. It creates two categories, deletes one, and then checks if the remaining category appears in the list of categories.
     */
    @Test
    public void testdeleteCategoryCommand(){
        logic.handleMessage("/create_category тест1");
        logic.handleMessage("/create_category тест2");
        String responce = logic.handleMessage("/delete_category тест1");
        assertEquals("1. тест2", responce);
    }
    /**
     * Tests the logic of the "/add" command. It creates a category, adds a note to it, and then checks if the added note appears in the list of notes for that category.
     */
    @Test
    public void testaddtoCategoryCommand(){
        logic.handleMessage("/create_category тест");
        logic.handleMessage("/add тест1 to тест");
        String responce = logic.handleMessage("/list_notes тест");
        assertEquals("Записи в категории \"тест\":\n" +
                "- тест1", responce);
    }

}
