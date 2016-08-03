package org.cirdles.topsoil.app.progress.tab;

import javafx.css.Styleable;
import javafx.event.EventTarget;
import javafx.scene.control.TableView;
import org.cirdles.topsoil.app.progress.table.GenericTable;

/**
 * Created by benjaminmuldrow on 8/3/16.
 */
public interface GenericTab extends EventTarget, Styleable {
    TableView getTable();

    GenericTable getGenericTable();
}
