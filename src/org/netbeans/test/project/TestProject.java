package org.netbeans.test.project;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.CopyOperationImplementation;
import org.netbeans.spi.project.DeleteOperationImplementation;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.openide.filesystems.FileObject;
import org.openide.util.*;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author bogdan
 */
public class TestProject implements Project {

    private final FileObject projectDir;
    private final ProjectState state;
    private Lookup lkp;

    public TestProject(FileObject projectDir, ProjectState state) {
        this.projectDir = projectDir;
        this.state = state;
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
                state, new ActionProviderImpl(),
                new DemoDeleteOperation(),
                new DemoCopyOperation(this),
                new Info(),
                new TestProjectLogicalView(this),});
        }
        return lkp;
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
}
