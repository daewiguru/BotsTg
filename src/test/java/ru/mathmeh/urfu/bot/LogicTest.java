package ru.mathmeh.urfu.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *class LogicTest
 * this class tests logic class
 * @author lendys(Yaroslav Prisepnyj)
 * testing commands \start and \help and retern message
 */


public class LogicTest {
    /**
     * testing commands \start
     */
    @Test
    public void testStartCommand() {
        Logic logic = new Logic();
        String result = logic.handleMessage("/start");
        Assertions.assertEquals("Hello, im new bots", result);
    }
    /**
     * testing commands \help
     */
    @Test
    public void testHelpCommand() {
        Logic logic = new Logic();
        String result = logic.handleMessage("/help");
        Assertions.assertEquals("I'm a bot that mirrors a message.", result);
    }

    /**
     * testing commands default
     */
    @Test
    public void testDefaultCase() {
        Logic logic = new Logic();
        String result = logic.handleMessage("Some other message");
        Assertions.assertEquals("Some other message", result);
    }
}