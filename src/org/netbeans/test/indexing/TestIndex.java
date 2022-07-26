/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.indexing;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.netbeans.api.project.Project;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.openide.filesystems.FileObject;
import org.openide.util.ChangeSupport;

/**
 *
 * @author bogdan
 */
public class TestIndex {

    public static TestIndex create(Project project) throws IOException  {
        return new TestIndex(project);
    }
    private final QuerySupport querySupport;
    private final Collection<FileObject> sourceRoots;
    private final ChangeSupport changeSupport;

    private TestIndex(Project project) throws IOException {
        //QuerySupport now refreshes the roots indexes so it can held until the source roots are valid
        sourceRoots = QuerySupport.findRoots(project,
                null /* all source roots */,
                Collections.<String>emptyList(),
                Collections.<String>emptyList());
        this.querySupport = QuerySupport.forRoots(TestIndexer.Factory.NAME, TestIndexer.Factory.VERSION, sourceRoots.toArray(new FileObject[]{}));
        this.changeSupport = new ChangeSupport(this);
    }

}
