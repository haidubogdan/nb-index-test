package org.netbeans.test.editor.csl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.modules.csl.api.CodeCompletionContext;
import org.netbeans.modules.csl.api.CodeCompletionHandler;
import org.netbeans.modules.csl.api.CodeCompletionResult;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ParameterInfo;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.test.editor.TestProjectSupport;
import org.netbeans.test.editor.csl.module.HelpResolver;
import org.netbeans.test.editor.csl.module.PropertyDefinition;
import org.netbeans.test.editor.csl.module.TestModuleSupport;
import org.openide.filesystems.FileObject;

/**
 *
 * @author bogdan
 */
public class TestCompletion implements CodeCompletionHandler {

    @Override
    public CodeCompletionResult complete(CodeCompletionContext completionContext) {
        FileObject current = completionContext.getParserResult().getSnapshot().getSource().getFileObject();
        TestProjectSupport support = TestProjectSupport.findFor(current);
        List<CompletionProposal> cssModulesCompletionProposals = TestModuleSupport.getCompletionProposals(completionContext);
          
        Map<FileObject, String> s1 = support.getIndex().findByPrefix("x_value", "");
        Map<FileObject, String> s2 = support.getIndex().findByPrefix("search_me", "");
        Map<FileObject, String> s3 = support.getIndex().findByPrefix("cached", "");
        int x = 3;

        return null;
    }

    @Override
    public String document(ParserResult info, ElementHandle element) {
        FileObject fileObject = info.getSnapshot().getSource().getFileObject();
        HelpResolver resolver = TestModuleSupport.getHelpResolver();
        if (resolver != null) {
            if (element instanceof TestPropertyElement) {
                TestPropertyElement e = (TestPropertyElement) element;
                PropertyDefinition property = e.getPropertyDescriptor();
                return resolver.getHelp(fileObject, property);
            }
        }
        return null;
    }

    @Override
    public ElementHandle resolveLink(String string, ElementHandle eh) {
        return null;
    }

    @Override
    public String getPrefix(ParserResult pr, int i, boolean bln) {
        return "tttest";
    }

    @Override
    public QueryType getAutoQuery(JTextComponent jtc, String string) {
        return QueryType.COMPLETION;
    }

    @Override
    public String resolveTemplateVariable(String string, ParserResult pr, int i, String string1, Map map) {
        return null;
    }

    @Override
    public Set<String> getApplicableTemplates(Document dcmnt, int i, int i1) {
        return Collections.emptySet();
    }

    @Override
    public ParameterInfo parameters(ParserResult pr, int i, CompletionProposal cp) {
        return ParameterInfo.NONE;
    }

}
