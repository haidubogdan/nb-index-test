/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.indexing;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.modules.parsing.spi.indexing.support.IndexResult;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.netbeans.test.project.ClassPathProviderImpl;
import org.openide.filesystems.FileObject;
import org.openide.util.ChangeSupport;
import org.openide.util.Exceptions;

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
                Collections.singleton(ClassPathProviderImpl.SOURCE_CP),
                Collections.<String>emptyList(),
                Collections.<String>emptyList());
        this.querySupport = QuerySupport.forRoots(TestIndexer.Factory.NAME, TestIndexer.Factory.VERSION, sourceRoots.toArray(new FileObject[]{}));
        this.changeSupport = new ChangeSupport(this);
    }
    
    public void addChangeListener(ChangeListener changeListener) {
        changeSupport.addChangeListener(changeListener);
    }
    
    public void removeChangeListener(ChangeListener changeListener) {
        changeSupport.removeChangeListener(changeListener);
    }
    
    public Map<FileObject, String> findByPrefix(String keyName, String prefix) {
        Map<FileObject, String> map = new HashMap<>();
        try {
            Collection<? extends IndexResult> results = querySupport.query(keyName, prefix, QuerySupport.Kind.PREFIX, keyName);
            for (IndexResult result : results) {
                String[] elements = result.getValues(keyName);
                for(String e : elements) {
                    String val = e;
                    map.put(result.getFile(),val);
                }

            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return map;
    }

}
