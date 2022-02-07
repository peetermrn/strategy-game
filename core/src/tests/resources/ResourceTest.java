package tests.resources;
import com.gamefromscratch.resources.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {

    @Test
    public void testResourceCreation() {
        Resource resource= new Resource();
        assertEquals(100, resource.getMaxResource());
        assertEquals(100, resource.getTotalResource());
        assertFalse(resource.isEmpty());
        for (int i = 0; i < 100; i++) {
            resource.takeOne();
        }
        assertTrue(resource.isEmpty());
    }
}
