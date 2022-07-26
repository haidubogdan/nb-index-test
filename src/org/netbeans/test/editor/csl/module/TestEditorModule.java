/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.editor.csl.module;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.text.Document;
import org.netbeans.modules.csl.api.CodeCompletionContext;
import org.netbeans.modules.csl.api.ColoringAttributes;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.netbeans.modules.csl.api.DeclarationFinder.DeclarationLocation;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.StructureItem;
import org.openide.filesystems.FileObject;
import org.openide.util.Pair;

/**
 *
 * @author bogdan
 */
public abstract class TestEditorModule {
    
 
    /**
     * Returns a list of css pseudo classes for the given context
     * @param context instance of {@link EditorFeatureContext}
     * @return list of css pseudo classes
     */
    public Collection<String> getPseudoClasses(EditorFeatureContext context) {
        return null;
    }
    
    /**
     * Returns a list of css pseudo elements for the given context
     * @param context instance of {@link EditorFeatureContext}
     * @return list of css pseudo elements
     */
    public Collection<String> getPseudoElements(EditorFeatureContext context) {
        return null;
    }
//    
//    public PropertySupportResolver.Factory getPropertySupportResolverFactory() {
//        return null;
//    }

    /**
     * Gets a collection of property names which are applicable in the given context.
     * 
     * <b>Rules:</b> (provider == implementor of the {@link CssEditorModule})
     * 
     * 1. provider can define new visible and invisible properties
     * 
     * 2. the property definitions can depend on property definitions provided by
     *    other provider. Care must be taken here as generally various properties
     *    can be available for different contexts. It is highly recommended to depend
     *    only on the "core css" properties as these context free properties are
     *    always available.
     * 
     * 3. provider can NOT override existing properties provided by the other providers
     *    (in fact such duplicity may happen, warning will be logged, and the first 
     *    property definition from the provider with highest priority will be used)
     * 
     *    Note: possibly if one really needs it we can add such support at least
     *          for  visible properties which no one depends on. If this was 
     *          supported for all properties the caching would be much more complicated.
     * 
     * 4. if a provider returns a property name for the given context file,
     *    it is mandatory that subsequent call to getPropertyDefinition() 
     *    must return non-null value.
     * 
     * 5. <b>the provider itself is responsible for caching of the returned property definitions!</b>
     * 
     * 6. the behavior if the context is null is undefined as this typically won't happen
     * 
     * @param file context file, may be null!
     */
    public Collection<String> getPropertyNames(FileObject file) {
        return Collections.emptyList();
    }
    
     /**
     * Gets an instance of {@link PropertyDefinition} for the give property name.
     * 
     * The module must return a non-null instance only if it also returns the property name
     * in {@link #getPropertyNames(org.openide.filesystems.FileObject)!
     * 
     * @param propertyName name of the property
     */
    public PropertyDefinition getPropertyDefinition(String propertyName) {
        return null;
    }
    
    /**
     * Returns a list of {@link HelpResolver} for the given file context.
     * 
     * @param file context file
     * @return collection of {@link HelpResolver}
     */
    public Collection<HelpResolver> getHelpResolvers(FileObject file) {
        return null;
    }

    Collection<? extends CompletionProposal> getCompletionProposals(CodeCompletionContext context) {
        return Collections.emptyList();
    }

    
    /**
     * If one wants to customize the css completion beyond the level which the PropertyDescriptor-s
     * allows, may use this method to achieve this.
     * 
     * @param context the code completion context
     * @return a list of completion proposals
     */
//    public List<CompletionProposal> getCompletionProposals(CompletionContext context) {
//        return Collections.emptyList();
//    }
      
}
