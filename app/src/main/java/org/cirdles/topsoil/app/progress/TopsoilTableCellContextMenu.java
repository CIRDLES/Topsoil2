package org.cirdles.topsoil.app.progress;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;

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

    private TableCell cell;

    public TopsoilTableCellContextMenu(TopsoilTableCell cell) {
        super();
        this.cell = cell;

        // initialize menu items
        deleteRowItem = new MenuItem("Delete Row");
        copyRowItem = new MenuItem("Copy Row");
        clearRowItem = new MenuItem("Clear Row");

        deleteColumnItem = new MenuItem("Delete Column");
        copyColumnItem = new MenuItem("Copy Column");
        clearColumnItem = new MenuItem("Clear Column");

        copyCellItem = new MenuItem("Copy Cell");
        clearCellItem = new MenuItem("Clear Cell");

        // add actions
        deleteRowItem.setOnAction(action -> {
            cell.getTableView().getItems().remove(cell.getIndex());
        });

        // add items to context menu
        this.getItems().addAll(
                deleteRowItem, copyRowItem, clearRowItem, new SeparatorMenuItem(),
                deleteColumnItem, copyColumnItem, clearColumnItem, new SeparatorMenuItem(),
                copyCellItem, clearCellItem
        );
    }
}
