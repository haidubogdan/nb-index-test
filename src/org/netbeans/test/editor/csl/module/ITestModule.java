/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.editor.csl.module;

/**
 *
 * @author bogdan
 */
public interface ITestModule {

    public String getName();

    /**
     * display name of the module - may be presented to users
     */
    public String getDisplayName();

    /**
     * link to the css3 module specification.
     */
    public String getSpecificationURL();
}
