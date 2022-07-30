package org.netbeans.test.indexing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.indexing.Context;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexer;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexerFactory;
import org.netbeans.modules.parsing.spi.indexing.Indexable;
import org.netbeans.modules.parsing.spi.indexing.support.IndexDocument;
import org.netbeans.modules.parsing.spi.indexing.support.IndexingSupport;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 *
 * @author bogdan
 */
public class TestIndexer extends EmbeddingIndexer {

    private static final Logger LOGGER = Logger.getLogger(TestIndexer.class.getSimpleName());

    @Override
    protected void index(Indexable indexable, Parser.Result result, Context context) {
        try {
            System.out.println("txt2 indexing");
            FileObject fo = result.getSnapshot().getSource().getFileObject();
            int x = 1;
            IndexingSupport support = IndexingSupport.getInstance(context);
            IndexDocument document = support.createDocument(indexable);
            document.addPair("cached", Boolean.TRUE.toString(), true, true);
            document.addPair("x_value", "search_me", true, true);
            support.addDocument(document);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static class Factory extends EmbeddingIndexerFactory {

        public static final String NAME = "txt2"; //NOI18N
        public static final int VERSION = 1;

        @Override
        public EmbeddingIndexer createIndexer(Indexable indxbl, Snapshot snpsht) {
            return new TestIndexer();
        }

        @Override
        public void filesDeleted(Iterable<? extends Indexable> deleted, Context context) {
            try {
                IndexingSupport is = IndexingSupport.getInstance(context);
                for (Indexable i : deleted) {
                    is.removeDocuments(i);
                }
            } catch (IOException ioe) {
                LOGGER.log(Level.WARNING, null, ioe);
            }
        }

        @Override
        public void filesDirty(Iterable<? extends Indexable> itrbl, Context cntxt) {
            System.out.println("txt2 files dirty");
        }

        @Override
        public String getIndexerName() {
            return NAME;
        }

        @Override
        public int getIndexVersion() {
            return VERSION;
        }
    }
}
