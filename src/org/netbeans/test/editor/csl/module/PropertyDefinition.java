/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.editor.csl.module;

import org.openide.filesystems.FileObject;

/**
 *
 * @author bogdan
 */
public class PropertyDefinition {

    private String name, grammar;
    private ITestModule cssModule;
    private PropertyCategory propertyCategory;

    /**
     * Creates an instance of PropertyDefinition with the PropertyCategory.OTHER
     * property category.
     *
     * @param name name of the property
     * @param valueGrammar grammar of the property value
     */
    public PropertyDefinition(String name, String valueGrammar) {
        this(name, valueGrammar, null);
    }

    /**
     * Creates an instance of PropertyDefinition with the PropertyCategory.OTHER
     * property category.
     *
     * @param name name of the property
     * @param valueGrammar grammar of the property value
     * @param module ITestModule serving this property definition
     */
    public PropertyDefinition(String name, String valueGrammar, ITestModule module) {
        this(name, valueGrammar, PropertyCategory.DEFAULT, module);
    }

    /**
     * Creates an instance of PropertyDefinition.
     *
     * @param name name of the property
     * @param valueGrammar grammar of the property value
     * @param module ITestModule serving this property definition
     * @param propertyCategory category of the property
     */
    public PropertyDefinition(String name, String valueGrammar, PropertyCategory propertyCategory, ITestModule module) {
        this.name = name;
        this.grammar = valueGrammar;
        this.propertyCategory = propertyCategory;
        this.cssModule = module;
    }

    /**
     * Gets the property value grammar.
     */
    public String getGrammar() {
        return grammar;
    }

    /**
     * Gets the {@link ITestModule} serving this property definition.
     *
     * @return instance of {@link ITestModule}. May be null.
     */
    public ITestModule getITestModule() {
        return cssModule;
    }

    /**
     * Gets the property category this property definition belongs to
     *
     */
    public PropertyCategory getPropertyCategory() {
        return propertyCategory;
    }

    /**
     * @return The property name.
     */
    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PropertyDefinition other = (PropertyDefinition) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}
