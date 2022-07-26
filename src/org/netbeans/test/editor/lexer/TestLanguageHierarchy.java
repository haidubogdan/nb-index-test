/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.editor.lexer;

import java.util.Collection;
import java.util.EnumSet;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author bogdan
 */
public class TestLanguageHierarchy extends LanguageHierarchy<TestTokenId> {

    @Override
    protected Collection<TestTokenId> createTokenIds() {
        return EnumSet.allOf(TestTokenId.class);
    }

    @Override
    protected Lexer<TestTokenId> createLexer(LexerRestartInfo<TestTokenId> info) {
        return new TestLexer(info);
    }

    @Override
    protected String mimeType() {
        return "text/txt2";
    }

}
