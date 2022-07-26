
package org.netbeans.test.project;

import java.awt.Image;
import javax.swing.Action;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author bogdan
 */
public class TestProjectLogicalView implements LogicalViewProvider{
    private final TestProject project;

    public TestProjectLogicalView(TestProject project) {
        this.project = project;
    }

    @Override
    public Node createLogicalView() {
        try {
            FileObject text = project.getTextFolder(true);

            DataFolder textDataObject = DataFolder.findFolder(text);

            Node realTextFolderNode = textDataObject.getNodeDelegate();

            return new TextNode(realTextFolderNode, project);

        } catch (DataObjectNotFoundException donfe) {
            Exceptions.printStackTrace(donfe);
            //read-only filesystem or something evil happened*
            return new AbstractNode(Children.LEAF);
        }
    }

    private static final class TextNode extends FilterNode {

        final TestProject project;

        public TextNode(Node node, TestProject project) throws DataObjectNotFoundException {
            super(node, new FilterNode.Children(node),
                    //NewAction and friends want the original Node's lookup.
                    //Make a merge of both*
                    new ProxyLookup(new Lookup[]{Lookups.singleton(project),
                        node.getLookup()
                    }));
            this.project = project;
        }

        @Override
        public Action[] getActions(boolean arg0) {
            Action[] nodeActions = new Action[7];
            nodeActions[0] = CommonProjectActions.newFileAction();
            nodeActions[1] = CommonProjectActions.copyProjectAction();
            nodeActions[2] = CommonProjectActions.deleteProjectAction();
            nodeActions[5] = CommonProjectActions.setAsMainProjectAction();
            nodeActions[6] = CommonProjectActions.closeProjectAction();
            return nodeActions;
        }

        @Override
        public Image getIcon(int type) {
            return ImageUtilities.loadImage("org/netbeans/test/resources/gdl.png");
        }

        @Override
        public Image getOpenedIcon(int type) {
            return getIcon(type);
        }

        @Override
        public String getDisplayName() {
            return project.getProjectDirectory().getName();
        }

    }

    @Override
    public Node findPath(Node root, Object target) {
        //leave unimplemented for now
        return null;
    }
}
