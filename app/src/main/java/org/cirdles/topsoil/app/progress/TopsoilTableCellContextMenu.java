package org.cirdles.topsoil.app.progress;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * Created by benjaminmuldrow on 8/1/16.
 */
public class TopsoilTableCellContextMenu extends ContextMenu {

    private MenuItem deleteRowItem;
    private MenuItem deleteColumnItem;
    private MenuItem copyCellItem;
    private MenuItem copyRowItem;
    private MenuItem copyColumnItem;
    private MenuItem clearCellItem;
    private MenuItem clearRowItem;
    private MenuItem clearColumnItem;

    public TopsoilTableCellContextMenu() {
        super();

        deleteRowItem = new MenuItem("Delete Row");
        copyRowItem = new MenuItem("Copy Row");
        clearRowItem = new MenuItem("Clear Row");

        deleteColumnItem = new MenuItem("Delete Column");
        copyColumnItem = new MenuItem("Copy Column");
        clearColumnItem = new MenuItem("Clear Column");

        copyCellItem = new MenuItem("Copy Cell");
        clearCellItem = new MenuItem("Clear Cell");

        this.getItems().addAll(
                deleteRowItem, copyRowItem, clearRowItem, new SeparatorMenuItem(),
                deleteColumnItem, copyColumnItem, clearColumnItem, new SeparatorMenuItem(),
                copyCellItem, clearCellItem
        );
    }
}
