package ru.mathmeh.urfu.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *class LogicTest
 * @author lendys(Yaroslav Prisepnyj)
 * testing commands \start and \help
 */


public class LogicTest {
    /**
     * method testStartCommand
     * testing commands \start
     */
    @Test
    public void testStartCommand() {
        Logic logic = new Logic();
        String result = logic.messageHandler("/start");
        Assertions.assertEquals("Hello, im new bots", result);
    }
    /**
     * method testHelpCommand
     * testing commands \help
     */
    @Test
    public void testHelpCommand() {
        Logic logic = new Logic();
        String result = logic.messageHandler("/help");
        Assertions.assertEquals("I'm a bot that mirrors a message.", result);
    }

    /**
     * method testDefaultCase
     * testing commands default
     */
    @Test
    public void testDefaultCase() {
        Logic logic = new Logic();
        String result = logic.messageHandler("Some other message");
        Assertions.assertEquals("Some other message", result);
>>>>>>> c1948a1 (add logic and logic test)
    }
}