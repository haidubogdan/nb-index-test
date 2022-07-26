/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.netbeans.test.editor;

import java.io.IOException;
import java.util.WeakHashMap;
import org.netbeans.api.project.Project;
import org.netbeans.modules.csl.api.DataLoadersBridge;
import org.netbeans.modules.parsing.api.Source;
import javax.swing.text.Document;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.test.indexing.TestIndex;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

@NbBundle.Messages("Txt2Resolver=Txt2 Files")
@MIMEResolver.ExtensionRegistration(
        mimeType = "text/txt2",
        position = 170,
        displayName = "#Txt2Resolver",
        extension = {"txt2"}
)
public class TestProjectSupport {

    private static final WeakHashMap<Project, TestProjectSupport> INSTANCIES = new WeakHashMap<>();

    public static TestProjectSupport findFor(Source source) {
        FileObject fo = source.getFileObject();
        if (fo == null) {
            return null;
        } else {
            return findFor(fo);
        }
    }

    public static TestProjectSupport findFor(Document doc) {
        return findFor(DataLoadersBridge.getDefault().getFileObject(doc));
    }

    public static TestProjectSupport findFor(FileObject fo) {
        try {
            Project p = FileOwnerQuery.getOwner(fo);
            if (p == null) {
                return null;
            }
            synchronized (INSTANCIES) {
                TestProjectSupport instance = INSTANCIES.get(p);
                if (instance == null) {
                    instance = new TestProjectSupport(p);
                    INSTANCIES.put(p, instance);
                }
                return instance;
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        return null;
    }
    private Project project;
    private TestIndex index;

    public TestProjectSupport(Project project) throws IOException {
        this.project = project;
        this.index = TestIndex.create(project);
    }

    public TestIndex getIndex() {
        return index;
    }

    public Project getProject() {
        return project;
    }
}
