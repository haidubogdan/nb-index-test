
package org.netbeans.test.editor.csl;

import org.netbeans.api.lexer.Language;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.netbeans.modules.csl.api.CodeCompletionHandler;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexerFactory;
import org.netbeans.modules.parsing.spi.indexing.PathRecognizerRegistration;
import org.netbeans.test.editor.lexer.TestTokenId;
import org.netbeans.test.indexing.TestIndexer;
import org.openide.windows.TopComponent;
import org.openide.util.Lookup;

@LanguageRegistration(mimeType = "text/txt2", useMultiview = true) //NOI18N
//index all source roots only
@PathRecognizerRegistration(mimeTypes = "text/txt2", libraryPathIds = {}, binaryLibraryPathIds = {}) //NOI18N
public class TestLanguage extends DefaultLanguageConfig {
    public static final String MIME_TYPE = "text/txt2";//NOI18N
    
    @MultiViewElement.Registration(displayName = "Text2",
        iconBase = "org/netbeans/test/resources/gdl.png",
        persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
        preferredID = "txt2.source",
        mimeType = "text/txt2",
        position = 1)
    public static MultiViewEditorElement createMultiViewEditorElement(Lookup context) {
        return new MultiViewEditorElement(context);
    }
    
    public TestLanguage(){
        
    }
    
    @Override
    public Language getLexerLanguage() {
        return TestTokenId.language(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDisplayName() {
        return "text 2";
    }
    
    @Override
    public String getPreferredExtension() {
        return "txt2"; // NOI18N
    }
    
    @Override
    public CodeCompletionHandler getCompletionHandler() {
        return new TestCompletion();
    }
    
    @Override
    public EmbeddingIndexerFactory getIndexerFactory() {
        return new TestIndexer.Factory();
    }
}
