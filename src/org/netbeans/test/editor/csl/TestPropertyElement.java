
package org.netbeans.test.editor.csl;

import org.netbeans.test.editor.csl.module.PropertyDefinition;

/**
 *
 * @author bogdan
 */
public class TestPropertyElement extends TestElement {

    private PropertyDefinition propertyDescriptor;
    
    public TestPropertyElement(PropertyDefinition propertyDescriptor) {
        super(propertyDescriptor.getName());
        this.propertyDescriptor = propertyDescriptor;
    }
    
    public PropertyDefinition getPropertyDescriptor() {
        return this.propertyDescriptor;
    }
}