/*
 * Copyright (c) 2016 Christopher Wells <cwellsny@nycap.rr.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package oscp5messagerecorder; 

import junitparams.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * The <code>ArgsParserTest</code> class is used to test the ArgsParser class.
 */
@RunWith(JUnitParamsRunner.class)
public class ArgsParserTest {

    /**
     * Tests of the removeFlags method, of class ArgsParser.
     */
    @Test
    @Parameters(method = "testRemoveFlagsParameters")
    public void testRemoveFlags(String input, String expectedOutput) {
        String actualOutput = ArgsParser.removeFlags(input);
        assertEquals(expectedOutput, actualOutput);
    }

    private Object[] testRemoveFlagsParameters() {
        return new Object[]{
            new Object[]{"record 5000 test.xml /muse/acc --test", "record 5000 test.xml /muse/acc"},
            new Object[]{"record 5000 test.xml /muse/acc", "record 5000 test.xml /muse/acc"},
            new Object[]{"record --xml 5000 --750 test.xml /muse/acc --test", "record 5000 test.xml /muse/acc"},
            new Object[]{"--xml --750 --test", ""},
            new Object[]{null, ""},
        };
    }

    /**
     * Tests of getFlags method, of class ArgsParser.
     */
    @Test
    @Parameters(method = "testGetFlagsParameters")
    public void testGetFlags(String input, String[] expectedOutput) {
        String[] actualOutput = ArgsParser.getFlags(input);
        assertEquals(expectedOutput, actualOutput);
    }

    private Object[] testGetFlagsParameters() {
        return new Object[]{
            new Object[]{"record 5000 test.xml /muse/acc --test", new String[]{"test"}},
            new Object[]{"record 5000 test.xml /muse/acc", new String[]{}},
            new Object[]{"record --xml 5000 test.xml /muse/acc", new String[]{"xml"}},
            new Object[]{"record --xml 5000 --verbose test.xml /muse/acc", new String[]{"xml", "verbose"}},
            new Object[]{"record --xml 5000 --verbose test.xml --testing /muse/acc", new String[]{"xml", "verbose", "testing"}},
            new Object[]{null, new String[0]},
        };
    }
}
