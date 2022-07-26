package org.netbeans.test.editor.csl.module;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.netbeans.modules.csl.api.CodeCompletionContext;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;

/**
 *
 * @author bogdan
 */
public class TestModuleSupport {

    public static HelpResolver getHelpResolver() {
        return new HelpResolver() {

            @Override
            public String getHelp(FileObject context, PropertyDefinition property) {
                StringBuilder sb = new StringBuilder();
                for (HelpResolver resolver : getSortedHelpResolvers(context)) {
                    String help = resolver.getHelp(context, property);
                    if (help != null) {
                        sb.append(help);
                    }
                }
                return sb.toString();
            }

            @Override
            public URL resolveLink(FileObject context, PropertyDefinition property, String link) {
                for (HelpResolver resolver : getSortedHelpResolvers(context)) {
                    URL url = resolver.resolveLink(context, property, link);
                    if (url != null) {
                        return url;
                    }
                }
                return null;
            }

            @Override
            public int getPriority() {
                return 0;
            }
        };

    }

    private static Collection<HelpResolver> getSortedHelpResolvers(FileObject file) {
        List<HelpResolver> list = new ArrayList<>();
        for (TestEditorModule module : getModules()) {
            Collection<HelpResolver> resolvers = module.getHelpResolvers(file);
            if (resolvers != null) {
                list.addAll(resolvers);
            }
        }

        Collections.sort(list, new Comparator<HelpResolver>() {

            @Override
            public int compare(HelpResolver t1, HelpResolver t2) {
                int i1 = t1.getPriority();
                int i2 = t2.getPriority();
                return Integer.valueOf(i1).compareTo(Integer.valueOf(i2));
            }
        });

        return list;
    }
    
    public static Collection<? extends TestEditorModule> getModules() {
        return Lookup.getDefault().lookupAll(TestEditorModule.class);
    }

    public static List<CompletionProposal> getCompletionProposals(CodeCompletionContext context) {
        List<CompletionProposal> all = new ArrayList<>();
        for (TestEditorModule module : getModules()) {
            all.addAll(module.getCompletionProposals(context));
        }
        return all;
    }
}
