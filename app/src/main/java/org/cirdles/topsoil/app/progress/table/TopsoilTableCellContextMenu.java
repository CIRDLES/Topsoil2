package org.cirdles.topsoil.app.progress.table;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.SeparatorMenuItem;
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

    private TopsoilTableCell cell;

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
            DeleteRowItemCommand deleteRowCommand = new DeleteRowItemCommand(this.cell);
            deleteRowCommand.execute();
            ((TopsoilTabPane) this.cell.getScene().lookup("#TopsoilTabPane")).getSelectedTab().addUndo(deleteRowCommand);
        });

        copyRowItem.setOnAction(action -> {
            String copyValues = "";
            TopsoilDataEntry row = this.cell.getDataEntry();
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
            ClearRowItemCommand clearRowCommand = new ClearRowItemCommand(this.cell);
            clearRowCommand.execute();
            ((TopsoilTabPane) this.cell.getScene().lookup("#TopsoilTabPane")).getSelectedTab().addUndo(clearRowCommand);
        });

        deleteColumnItem.setOnAction(action -> {
            DeleteColumnItemCommand deleteColumnCommand = new DeleteColumnItemCommand(this.cell);
            deleteColumnCommand.execute();
            ((TopsoilTabPane) this.cell.getScene().lookup("#TopsoilTabPane")).getSelectedTab().addUndo(deleteColumnCommand);
        });

        copyColumnItem.setOnAction(action -> {
            String copyValues = "";
            TableColumn<TopsoilDataEntry, Double> column = this.cell.getTableColumn();
            for (int i = 0; i < this.cell.getTableView().getItems().size(); i++) {
                copyValues += Double.toString(column.getCellData(i));
                if (i < this.cell.getTableView().getItems().size() - 1) {
                    copyValues += "\n";
                }
            }
            ClipboardContent content = new ClipboardContent();
            content.putString(copyValues);
            Clipboard.getSystemClipboard().setContent(content);
        });

        clearColumnItem.setOnAction(action -> {
            ClearColumnItemCommand clearColumnCommand = new ClearColumnItemCommand(this.cell);
            clearColumnCommand.execute();
            ((TopsoilTabPane) this.cell.getScene().lookup("#TopsoilTabPane")).getSelectedTab().addUndo(clearColumnCommand);
        });

        copyCellItem.setOnAction(action -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(Double.toString(this.cell.getItem()));
            Clipboard.getSystemClipboard().setContent(content);
        });

        clearCellItem.setOnAction(action -> {
            ClearCellItemCommand clearCellCommand = new ClearCellItemCommand(this.cell);
            clearCellCommand.execute();
            ((TopsoilTabPane) this.cell.getScene().lookup("#TopsoilTabPane")).getSelectedTab().addUndo(clearCellCommand);
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
