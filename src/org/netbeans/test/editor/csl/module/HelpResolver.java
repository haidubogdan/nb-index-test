/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.editor.csl.module;

import java.net.URL;
import org.openide.filesystems.FileObject;

/**
 *
 * @author bogdan
 */
public abstract class HelpResolver {
    
    /**
     * Returns the help content in the html code form for the given property.
     */
    public abstract String getHelp(FileObject context, PropertyDefinition property);
    
    /**
     * Resolves a link (relative or absolute) from within the property help content
     */
    public abstract URL resolveLink(FileObject context, PropertyDefinition property, String link);
    
    /**
     * Return a reasonable number representing a sort priority of the help resolver.
     * Lower number means higher priority - the help content will appear higher in the 
     * documentation window if there are more help resolvers returning a content for the 
     * given source object.
     */
    public abstract int getPriority();
    
}