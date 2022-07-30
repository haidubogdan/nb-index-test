/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.project;

import java.io.IOException;
import java.util.Collection;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.support.ant.AntBasedProjectRegistration;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.test.project.spi.TestProjectImplementationFactory;
import org.openide.util.Lookup;

/**
 *
 * @author bogdan
 */
public final class TestProjectType {

    public static final String TYPE = "org.netbeans.test.project";
    private static final String PROJECT_CONFIGURATION_NAME = "data";
    public static final String PROJECT_CONFIGURATION_NAMESPACE = "http://www.netbeans.org/ns/test-project/3";
    private static final String PRIVATE_CONFIGURATION_NAME = "data";
    private static final String PRIVATE_CONFIGURATION_NAMESPACE = "http://www.netbeans.org/ns/test-project-private/1";

    private static final String[] PROJECT_CONFIGURATION_NAMESPACE_LIST
            = {"http://www.netbeans.org/ns/test-project/1",
                "http://www.netbeans.org/ns/test-project/2",
                "http://www.netbeans.org/ns/test-project/3"};
    
    private TestProjectType() {}
    
    @AntBasedProjectRegistration(
        iconResource="org/netbeans/test/project/ui/resources/gdl.png", // NOI18N
        type=TYPE,
        sharedName=PROJECT_CONFIGURATION_NAME,
        sharedNamespace=PROJECT_CONFIGURATION_NAMESPACE,
        privateName=PRIVATE_CONFIGURATION_NAME,
        privateNamespace=PRIVATE_CONFIGURATION_NAMESPACE
    )
    public static Project createProject(AntProjectHelper helper) throws IOException {
        for(TestProjectImplementationFactory factory : getProjectFactories()) {
            if (factory.acceptProject(helper)) {
                //delegate project completely to another implementation
                return factory.createProject(helper);
            }
        }
        return new TestProject(helper);
    }
    
    private static Collection<? extends TestProjectImplementationFactory> getProjectFactories() {
        return Lookup.getDefault().lookupAll(TestProjectImplementationFactory.class);
    }

    public static String[] getConfigurationNamespaceList() {
        return PROJECT_CONFIGURATION_NAMESPACE_LIST.clone();
    }
}
