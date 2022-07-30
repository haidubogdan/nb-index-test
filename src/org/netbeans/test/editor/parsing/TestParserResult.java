
package org.netbeans.test.editor.parsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;

/**
 *
 * @author bogdan
 */
public class TestParserResult extends ParserResult {

    public TestParserResult(Snapshot snapshot){
        super(snapshot);
    }
    
    @Override
    public List<? extends Error> getDiagnostics() {
        List<? extends Error> errors = new ArrayList<>(Collections.<Error>emptyList());
        return errors;
    }

    @Override
    protected void invalidate() {
        
    }
    
}
