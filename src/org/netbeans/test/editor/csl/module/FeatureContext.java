/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.editor.csl.module;

import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.test.editor.lexer.TestTokenId;
import org.openide.filesystems.FileObject;

/**
 *
 * @author bogdan
 */
public class FeatureContext {

    private ParserResult result;

    /**
     * @todo do not allow to instantiate
     */
    public FeatureContext(ParserResult result) {
        this.result = result;
    }
    
    /**
     * @return a parsing.api result
     */
    public ParserResult getParserResult() {
        return result;
    }

    
    /**
     * @return snapshot of the source
     */
    public Snapshot getSnapshot() {
        return result.getSnapshot();
    }
    
    /**
     * @return the parsing.api's source
     */
    public Source getSource() {
        return getSnapshot().getSource();
    }
        
    /**
     * @return the associated file object, it there's any
     */
    public FileObject getFileObject() {
        return getSource().getFileObject();
    }
    
    /**
     * @return token sequence created from the snapshot.
     */
    public TokenSequence<TestTokenId> getTokenSequence() {
        return getSnapshot().getTokenHierarchy().tokenSequence(TestTokenId.language());
    }
//    
//    /**
//     * @return an instance of {@link Model}.
//     */
//    public Model getSourceModel() {
//        return Model.getModel(result);
//    }
    
}
