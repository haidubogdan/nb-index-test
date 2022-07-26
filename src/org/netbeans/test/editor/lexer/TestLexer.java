
package org.netbeans.test.editor.lexer;

import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.netbeans.spi.lexer.TokenFactory;

/**
 *
 * @author bogdan
 */
public class TestLexer implements Lexer<TestTokenId> {

    private static final int EOF = LexerInput.EOF;

    // Lexer internal states - preferably small integers for more compact token storage
    private static final int INIT = 0;
    private static final int AFTER_COLON = 1;
    private static final int AFTER_NAME = 2;

    private LexerInput input;

    private TokenFactory<TestTokenId> tokenFactory;

    private int state;

    public Object state() {
		// autoconversion uses Integer.valueOf() which caches <-127,127>
        return state;
    }

    public TestLexer(LexerRestartInfo<TestTokenId> info) {
        this.input = info.input();
        this.tokenFactory = info.tokenFactory();
		this.state = (info.state() != null) ? (Integer)info.state() : INIT;
    }

    public Token<TestTokenId> nextToken() {
        int c = input.read();
        switch (state) {
            case INIT:
                return nextTokenInit(c);
            case AFTER_COLON:
                return nextTokenAfterColon(c);
            case AFTER_NAME:
                return nextTokenAfterName(c);
            default:
                throw new IllegalStateException();
        }
    }

    private Token<TestTokenId> nextTokenInit(int c) {
        switch (c) {
            case ':': // ":"
                state = AFTER_COLON;
                return token(TestTokenId.COLON);
            case '\r':
                input.consumeNewline(); // continue to '\n' handling
            case '\n':
                //state = INIT;
                return token(TestTokenId.END_OF_LINE);
            case EOF: // no chars -> finish lexing by returning null
                return null;
            default: // Name follows
                return finishName(c);
        }
    }

    private Token<TestTokenId> nextTokenAfterColon(int c) {
        switch (c) {
            case ':': // ":"
                state = AFTER_COLON;
                return token(TestTokenId.COLON);
            case '\r':
                input.consumeNewline(); // continue to '\n' handling
            case '\n':
                state = INIT;
                return token(TestTokenId.END_OF_LINE);
            case EOF: // no chars -> finish lexing by returning null
                return null;
            default:
                return finishValue(c);
        }
    }

    private Token<TestTokenId> nextTokenAfterName(int c) {
        switch (c) {
            case ':': // ":"
                state = AFTER_COLON;
                return token(TestTokenId.COLON);
            case '\r':
                input.consumeNewline(); // continue to '\n' handling
            case '\n':
                state = INIT;
                return token(TestTokenId.END_OF_LINE);
            case EOF: // no chars -> finish lexing by returning null
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    private Token<TestTokenId> finishName(int c) {
        while (true) {
            switch (c) {
                case ':':
                case '\r':
                case '\n':
                case EOF:
                    input.backup(1);
                    state = AFTER_NAME;
                    return token(TestTokenId.NAME);
            }
            c = input.read();
        }
    }

    private Token<TestTokenId> finishValue(int c) {
        while (true) {
            switch (c) {
                case '\r':
                case '\n':
                case EOF:
                    input.backup(1);
                    state = INIT;
                    return token(TestTokenId.VALUE);
            }
            c = input.read();
        }
    }

    private Token<TestTokenId> token(TestTokenId id) {
        Token<TestTokenId> t = tokenFactory.createToken(id);
        return t;
    }

    public void release() {
    }

}
