package org.netbeans.test.project;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeListener;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.classpath.GlobalPathRegistry;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.CopyOperationImplementation;
import org.netbeans.spi.project.DeleteOperationImplementation;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.openide.filesystems.FileObject;
import org.openide.util.*;
import org.openide.util.lookup.Lookups;
import org.netbeans.api.project.Sources;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.ui.*;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author bogdan
 */
public class TestProject implements Project {

    FileObject projectDir;
    ProjectState state;
    private Lookup lkp;
    private volatile ClassPath sourcePath;
    volatile ClassPathProviderImpl.PathImpl pathImpl;
    private final ThreadLocal<Boolean> projectPropertiesSave;
    AntProjectHelper helper;
    
    public TestProject(FileObject projectDir, ProjectState state) {
        this.projectDir = projectDir;
        this.state = state;
        this.projectPropertiesSave =  new ThreadLocal<Boolean>() {
            @Override
            protected Boolean initialValue() {
                return Boolean.TRUE;
            }
        };
    }

    TestProject(AntProjectHelper helper) throws IOException {
        this.projectPropertiesSave = new ThreadLocal<Boolean>() {
            @Override
            protected Boolean initialValue() {
                return Boolean.FALSE;
            }
        };
        this.helper = helper;
        
    }

    @Override
    public FileObject getProjectDirectory() {
        return projectDir;
    }

    FileObject getTextFolder(boolean create) {
        FileObject result = projectDir.getFileObject(TestProjectFactory.PROJECT_DIR);
        if (result == null && create) {
            try {
                result = projectDir.createFolder(TestProjectFactory.PROJECT_DIR);
            } catch (IOException ioe) {
                Exceptions.printStackTrace(ioe);
            }
        }
        return result;
    }

    /*
    The project type's capabilities are registered in the project's lookup:
     */
    @Override
    public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
                state,
                new ClassPathProviderImpl(this),
                new SourcesImpl(),
                new OpenHookImpl(this),
                new ActionProviderImpl(),
                new DemoDeleteOperation(),
                new DemoCopyOperation(this),
                new Info(),
                new TestProjectLogicalView(this),});
        }
        return lkp;
    }

    private ClassPath getSourceClassPath() {
        if (sourcePath == null) {
            pathImpl = new ClassPathProviderImpl.PathImpl(this);
            sourcePath = ClassPathProviderImpl.createProjectClasspath(pathImpl);
        }
        return sourcePath;
    }

    private final class ActionProviderImpl implements ActionProvider {

        private String[] supported = new String[]{
            ActionProvider.COMMAND_DELETE,
            ActionProvider.COMMAND_COPY,};

        @Override
        public String[] getSupportedActions() {
            return supported;
        }

        @Override
        public void invokeAction(String string, Lookup lookup) throws IllegalArgumentException {
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_DELETE)) {
                DefaultProjectOperations.performDefaultDeleteOperation(TestProject.this);
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_COPY)) {
                DefaultProjectOperations.performDefaultCopyOperation(TestProject.this);
            }
        }

        @Override
        public boolean isActionEnabled(String command, Lookup lookup) throws IllegalArgumentException {
            if ((command.equals(ActionProvider.COMMAND_DELETE))) {
                return true;
            } else if ((command.equals(ActionProvider.COMMAND_COPY))) {
                return true;
            } else {
                throw new IllegalArgumentException(command);
            }
        }
    }

    private class SourcesImpl implements Sources {

        public SourcesImpl() {
        }

        public SourceGroup[] getSourceGroups(String type) {
            SourceGroup[] sourceGroup = new SourceGroup[]{new SourceGroupImpl()};
            return sourceGroup;
        }

        public void addChangeListener(ChangeListener listener) {
        }

        public void removeChangeListener(ChangeListener listener) {
        }

    }

    private class SourceGroupImpl implements SourceGroup {

        public SourceGroupImpl() {
        }

        @Override
        public FileObject getRootFolder() {
            return projectDir;
        }

        @Override
        public String getName() {
            return "Sources";
        }

        @Override
        public String getDisplayName() {
            return "Sources";
        }

        @Override
        public Icon getIcon(boolean opened) {
            return null;
        }

        public boolean contains(FileObject file) {
            return FileUtil.isParentOf(projectDir, file);
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
        }

    }

    private final class DemoDeleteOperation implements DeleteOperationImplementation {

        public void notifyDeleting() throws IOException {
        }

        public void notifyDeleted() throws IOException {
        }

        public List<FileObject> getMetadataFiles() {
            List<FileObject> dataFiles = new ArrayList<FileObject>();
            return dataFiles;
        }

        public List<FileObject> getDataFiles() {
            List<FileObject> dataFiles = new ArrayList<FileObject>();
            return dataFiles;

        }
    }

    private final class DemoCopyOperation implements CopyOperationImplementation {

        private final TestProject project;
        private final FileObject projectDir;

        public DemoCopyOperation(TestProject project) {
            this.project = project;
            this.projectDir = project.getProjectDirectory();
        }

        public List<FileObject> getMetadataFiles() {
            return Collections.EMPTY_LIST;
        }

        public List<FileObject> getDataFiles() {
            return Collections.EMPTY_LIST;
        }

        public void notifyCopying() throws IOException {
        }

        public void notifyCopied(Project arg0, File arg1, String arg2) throws IOException {
        }
    }

    private final class Info implements ProjectInformation {

        @Override
        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(
                    "org/netbeans/test/resources/gdl.png"));
        }

        @Override
        public String getName() {
            return getProjectDirectory().getName();
        }

        @Override
        public String getDisplayName() {
            return getName();
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public Project getProject() {
            return TestProject.this;
        }
    }

    private static class OpenHookImpl extends ProjectOpenedHook implements PropertyChangeListener {

        private final TestProject project;
        private FileChangeListener siteRootChangesListener;

        public OpenHookImpl(TestProject project) {
            this.project = project;
        }

        @Override
        protected void projectOpened() {
            GlobalPathRegistry.getDefault().register(ClassPathProviderImpl.SOURCE_CP, new ClassPath[]{project.getSourceClassPath()});
        }

        @Override
        protected void projectClosed() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }
}
