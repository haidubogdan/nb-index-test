/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.netbeans.test.project.spi;

/**
 *
 * @author bogdan
 */
import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.support.ant.AntProjectHelper;

/** The purpose of this interface is to allow a module to provide an alternative
 * implementation of web project support on top of the standard NetBeans web
 * project metadata format. Web project implementation of AntBasedProjectType
 * will look for instances of this interface in lookup and delegate project 
 * creation to them. If no instance accepts a project the default web project
 * implementation will be used.
 *
 * @author Pavel Buzek
 */
public interface TestProjectImplementationFactory {
    /** Recognize if the project should be owned by your module 
     * or if the default implementation should be used.
     *
     * @return true if you want your {@link createProject} to be used for 
     * this project
     */
    boolean acceptProject(AntProjectHelper helper) throws IOException;
    
    /** Create your implementation of Project to completely bypass 
     * the web/project functionality.
     */
    Project createProject(AntProjectHelper helper) throws IOException;
}
