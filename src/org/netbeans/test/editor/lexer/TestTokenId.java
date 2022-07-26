
package org.netbeans.test.editor.lexer;

import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;

/**
 *
 * @author bogdan
 */
public enum TestTokenId implements TokenId {
    COMMENT(1),
    EOF(0)
    , COLON(2), END_OF_LINE(3), NAME(4), VALUE(5);
    
    private static final Language<TestTokenId> language = new TestLanguageHierarchy().language();
    
    private final int code;
    
    TestTokenId(int code) {
        this.code = code;
    }
    @Override
    public String primaryCategory() {
        return "test";
    }
    
    public static Language<TestTokenId> language() {
        return language;
    }
}
