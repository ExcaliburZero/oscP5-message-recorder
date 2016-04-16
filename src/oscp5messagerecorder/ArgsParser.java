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

import java.util.Arrays;
import java.util.ArrayList;

/**
 * The <code>ArgsParser</code> class is used to parse the command line
 * arguments of the program.
 *
 * @author Christopher Wells {@literal <cwellsny@nycap.rr.com>}
 */
public class ArgsParser {

    /**
     * Returns an array of the flags in the parsed command.
     *
     * @param command The command to be parsed.
     * @return The flags found within the command.
     */
    public static String[] getFlags(String command) {
        if (command == null) {
            return new String[0];
        }

        ArrayList<String> parts = new ArrayList<>(Arrays.asList(command.split(" ")));
        ArrayList<String> flags = new ArrayList<>();
 
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).contains("--")) {
                flags.add(parts.get(i).substring(2));
            }
        }
 
        String[] flagsArray = flags.toArray(new String[flags.size()]);
        return flagsArray;
    }
 
    /**
     * Removes the flags from the given command.
     *
     * @param command The command to be parsed.
     * @return The command with all flags removed.
     */
    public static String removeFlags(String command) {
        if (command == null) {
            return "";
        }

        ArrayList<String> parts = new ArrayList<>(Arrays.asList(command.split(" ")));
        ArrayList<String> flags = new ArrayList<>(Arrays.asList(ArgsParser.getFlags(command)));
 
        for (String flag : flags)  {
            parts.remove("--" + flag);
        }
 
        String noFlagString = String.join(" ", parts.toArray(new String[parts.size()]));
        return noFlagString;
    }
}
