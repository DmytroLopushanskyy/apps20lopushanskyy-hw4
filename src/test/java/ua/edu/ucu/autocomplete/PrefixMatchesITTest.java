
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.queue.Queue;
import ua.edu.ucu.tries.RWayTrie;

/**
 *
 * @author Andrii_Rodionov and Dmytro Lopushanskyy
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abce ", "abck", "abcd", "abcdef");
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {"abce", "abck", "abcd", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abce", "abcd", "abck", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testSize() {
        assertEquals(4, pm.size());
    }

    @Test
    public void testContains() {
        assertTrue(pm.contains("abcd"));
    }

    @Test
    public void testDelete() {
        assertTrue(pm.contains("abcd"));
        pm.delete("abcd");
        assertFalse(pm.contains("abcd"));
    }

}
