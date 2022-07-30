package org.netbeans.test.project;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.spi.java.classpath.ClassPathImplementation;
import org.netbeans.spi.java.classpath.ClassPathProvider;
import org.netbeans.spi.java.classpath.FilteringPathResourceImplementation;
import org.netbeans.spi.java.classpath.PathResourceImplementation;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
import org.openide.filesystems.FileObject;

/**
 *
 * @author bhaidu
 */
public class ClassPathProviderImpl implements ClassPathProvider {

    public static final String SOURCE_CP = "classpath/txt2"; //NOI18N

    public static ClassPath createProjectClasspath(PathResourceImplementation pathResourceImplementation) {
        assert pathResourceImplementation != null;
        return ClassPathSupport.createClassPath(Collections.<PathResourceImplementation>singletonList(pathResourceImplementation));
    }

    private final TestProject project;

    public ClassPathProviderImpl(TestProject project) {
        this.project = project;
    }

    @Override
    public ClassPath findClassPath(FileObject fo, String type) {
        if (SOURCE_CP.equals(type)) {
            return ClassPathSupport.createClassPath(project.getProjectDirectory());
        }
        return null;
    }
    
    public static class PathImpl implements FilteringPathResourceImplementation {

        private final TestProject project;
        private final PropertyChangeSupport support = new PropertyChangeSupport(this);

        public PathImpl(TestProject project) {
            this.project = project;
        }

        @Override
        public boolean includes(URL url, String string) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public URL[] getRoots() {
            List<URL> result = new ArrayList<>();
            FileObject projectDir = this.project.getProjectDirectory();
            result.add(projectDir.toURL());
            for (FileObject file :projectDir.getChildren()){
                result.add(file.toURL());
            }
            return result.toArray(new URL[result.size()]);
        }
        
        @Override
        public ClassPathImplementation getContent() {
            return null;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {
            support.removePropertyChangeListener(listener);
        }
    }
}
