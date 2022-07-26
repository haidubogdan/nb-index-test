/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.editor.csl.module;

/**
 *
 * @author bogdan
 */
public enum PropertyCategory {

    //default, the rest
    DEFAULT, 
    
    //unknown
    UNKNOWN;
    
    private String displayName;
    private String shortDescription;
    private String longDescription;

    private PropertyCategory() {
        displayName = new StringBuilder()
                .append(name().charAt(0))
                .append(name().substring(1).toLowerCase().replace('_', ' '))
                .toString();
        
        shortDescription = new StringBuilder()
                .append("Provides styling support for ")
                .append(getDisplayName())
                .append('.')
                .toString();
        
        longDescription = shortDescription;
    }

    private PropertyCategory(String displayName, String shortDescription, String longDescription) {
        this.displayName = displayName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }
    
}
