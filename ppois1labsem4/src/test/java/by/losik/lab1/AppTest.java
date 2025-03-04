package by.losik.lab1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
public class AppTest {
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(1 == 1);
    }

    @Test
    public void testStates(){
        assertTrue(Arrays.stream(States.values()).count() == 28);
    }
}
