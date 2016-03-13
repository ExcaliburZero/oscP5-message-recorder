/*
 * The MIT License
 *
 * Copyright 2016 Christopher Wells <cwellsny@nycap.rr.com>.
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package oscp5messagerecorder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import netP5.NetAddress;
import org.xml.sax.SAXException;
import oscP5.OscP5;
import oscP5.OscProperties;

/**
 * The <code>OscP5Player</code> class is used to send previously recorded oscP5
 * messages to a set port.
 *
 * @author Christopher Wells <cwellsny@nycap.rr.com>
 */
public class OscP5Player {

    private final int port;
    private final File inputFile;

    private ArrayList<String[]> messages;
    private final OscP5 server;
    private final NetAddress reciever;

    /**
     * Initializes a <code>OscP5Player</code> object with the given port and
     * input file.
     *
     * @param port The port that the OscP5Player serves to.
     * @param inputFile The message input file that the OscP5Player reads from.
     */
    public OscP5Player(int port, File inputFile) {
        this.port = port;
        this.inputFile = inputFile;
        this.messages = null;
        this.server = new OscP5(this, OscProperties.UDP);
        this.reciever = new NetAddress("0.0.0.0", this.port);
        this.importMessages();
        this.playMessages();
    }

    /**
     * Imports the messages from the input file.
     */
    private void importMessages() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MessageImportHandler messageImportHandler = new MessageImportHandler();
            saxParser.parse(this.inputFile, messageImportHandler);

            this.messages = messageImportHandler.getMessages();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the messages that were imported from the input file.
     */
    private void playMessages() {
        // Calculate the time in between each message
        int[] times = new int[this.messages.size()];
        times[0] = 0;
        for (int i = 1; i < this.messages.size(); i++) {
            times[i] = Integer.parseInt(this.messages.get(i)[1]) - Integer.parseInt(this.messages.get(i - 1)[1]);
        }

        // Play each of the messages at the correct times
        for (int i = 0; i < this.messages.size(); i++) {
            try {
                Thread.sleep(times[i]);
                this.sendMessage(this.messages.get(i));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Sends the given message through the oscP5 server.
     *
     * @param message The message to be sent.
     */
    private void sendMessage(String[] message) {
        server.send(this.reciever, message[0], Integer.parseInt(message[2]));
    }
}
