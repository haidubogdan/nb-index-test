/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.project;

import java.util.Collections;
import java.util.Set;
import org.netbeans.modules.parsing.spi.indexing.PathRecognizer;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author bhaidu
 */
@ServiceProvider(service = PathRecognizer.class)
public final class PathRecognizerImpl extends PathRecognizer {

    @Override
    public Set<String> getSourcePathIds() {
        return Collections.singleton(ClassPathProviderImpl.SOURCE_CP);
    }

    @Override
    public Set<String> getLibraryPathIds() {
        return null;
    }

    @Override
    public Set<String> getBinaryLibraryPathIds() {
        return null;
    }

    @Override
    public Set<String> getMimeTypes() {
        return Collections.singleton("text/txt2"); //NOI18N
    }

}
