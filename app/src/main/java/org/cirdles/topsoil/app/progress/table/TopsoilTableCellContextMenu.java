package org.cirdles.topsoil.app.progress.table;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.cirdles.topsoil.app.progress.tab.TopsoilTabPane;

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
        // TODO rearrange in a more logical sense
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
            DeleteRowItemCommand deleteRowCommand = new DeleteRowItemCommand(cell);
            deleteRowCommand.execute();
            ((TopsoilTabPane) cell.getScene().lookup("#TopsoilTabPane")).getSelectedTab().addUndo(deleteRowCommand);
        });

        copyRowItem.setOnAction(action -> {
            String copyValues = "";
            TopsoilDataEntry row = cell.getDataEntry();
            for (int i = 0; i < row.getProperties().size(); i++) {
                copyValues += Double.toString(row.getProperties().get(i).get());
                if (i < row.getProperties().size() - 1) {
                    copyValues += "\t";
                }
            }
            ClipboardContent content = new ClipboardContent();
            content.putString(copyValues);
            Clipboard.getSystemClipboard().setContent(content);
        });

        clearRowItem.setOnAction(action -> {
            ClearRowItemCommand clearRowCommand = new ClearRowItemCommand(cell);
            clearRowCommand.execute();
            ((TopsoilTabPane) cell.getScene().lookup("#TopsoilTabPane")).getSelectedTab().addUndo(clearRowCommand);
        });

        deleteColumnItem.setOnAction(action -> {
            DeleteColumnItemCommand deleteColumnCommand = new DeleteColumnItemCommand(cell);
            deleteColumnCommand.execute();
            ((TopsoilTabPane) cell.getScene().lookup("#TopsoilTabPane")).getSelectedTab().addUndo(deleteColumnCommand);
        });

        copyColumnItem.setOnAction(action -> {
            String copyValues = "";
            TableColumn<TopsoilDataEntry, Double> column = cell.getTableColumn();
            for (int i = 0; i < cell.getTableView().getItems().size(); i++) {
                copyValues += Double.toString(column.getCellData(i));
                if (i < cell.getTableView().getItems().size() - 1) {
                    copyValues += "\n";
                }
            }
            ClipboardContent content = new ClipboardContent();
            content.putString(copyValues);
            Clipboard.getSystemClipboard().setContent(content);
        });

        clearColumnItem.setOnAction(action -> {
            ClearColumnItemCommand clearColumnCommand = new ClearColumnItemCommand(cell);
            clearColumnCommand.execute();
            ((TopsoilTabPane) cell.getScene().lookup("#TopsoilTabPane")).getSelectedTab().addUndo(clearColumnCommand);
        });

        copyCellItem.setOnAction(action -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(Double.toString(cell.getItem()));
            Clipboard.getSystemClipboard().setContent(content);
        });

        clearCellItem.setOnAction(action -> {
            ClearCellItemCommand clearCellCommand = new ClearCellItemCommand(cell);
            clearCellCommand.execute();
            ((TopsoilTabPane) cell.getScene().lookup("#TopsoilTabPane")).getSelectedTab().addUndo(clearCellCommand);
        });
        

        // add items to context menu
        this.getItems().addAll(
                deleteRowItem, copyRowItem, clearRowItem, new SeparatorMenuItem(),
                deleteColumnItem, copyColumnItem,
                //clearColumnItem,
                new SeparatorMenuItem(),
                copyCellItem, clearCellItem
        );
    }
}
