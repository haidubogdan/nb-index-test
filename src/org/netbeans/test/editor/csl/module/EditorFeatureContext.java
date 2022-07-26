/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.editor.csl.module;

import javax.swing.text.Document;
import org.netbeans.modules.csl.spi.ParserResult;

/**
 *
 * @author bogdan
 */
public class EditorFeatureContext extends FeatureContext {

    private int caretOffset;

    public EditorFeatureContext(ParserResult result, int caretOffset) {
        super(result);
        this.caretOffset = caretOffset;
    }
    
    /**
     * @return The editor's caret offset relative to the edited document.
     */
    public int getCaretOffset() {
        return caretOffset;
    }
    
    /**
     * @return instance of the edited document
     */
    public Document getDocument() {
        return getSnapshot().getSource().getDocument(false);
    }
}
