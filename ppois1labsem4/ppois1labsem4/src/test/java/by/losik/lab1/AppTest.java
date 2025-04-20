package by.losik.lab1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testStates(){
        assertEquals(28, Arrays.stream(States.values()).count());
    }

    @Test
    public void testApp(){assertDoesNotThrow(() -> App.main(new String[]{}));}
}
