package ru.mathmeh.urfu.bot;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LogicTest {

    private Logic logic;

    @Before
    public void setUp() {
        Bot mockBot = new Bot() {
            @Override
            public void setBotToken(String token) {

            }

            @Override
            public void setBotName(String name) {

            }

            public void receiveMessage(String userId, String message) {

            }

            public void sendMessage(String userId, String message) {

            }
        };
        logic = new Logic(mockBot) {
            @Override
            public void receiveMessage(String userId, String message) {

            }

            @Override
            public void sendMessage(String userId, String message) {

            }
        };
    }

    @Test
    public void testSetBotToken() {
        String expectedToken = "testToken";
        logic.setBotToken(expectedToken);
        String actualToken = logic.getBotToken();
        assertEquals(expectedToken, actualToken); // Проверяем, что установленный и полученный токены совпадают
    }

    @Test
    public void testSetBotName() {
        String expectedName = "TestBotName";
        logic.setBotName(expectedName);
        String actualName = logic.getBotName();
        assertEquals(expectedName, actualName); // Проверяем, что установленное и полученное имя совпадают
    }
}