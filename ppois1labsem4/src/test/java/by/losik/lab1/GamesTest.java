package by.losik.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class GamesTest {
    @Test
    public void testGames(){
        ExercisingGames exercisingGames = new ExercisingGames();
        exercisingGames.setTime(new Date(2000,12,12));
        exercisingGames.setTask("Mop the floor");
        Assertions.assertTrue(exercisingGames.getTask().equals("Mop the floor"));
        Assertions.assertEquals(exercisingGames.getTime().getTime(), 60937390800000l);
    }
}
