package org.cirdles.topsoil.app.progress.menu;

import javafx.scene.control.TableView;
import org.cirdles.topsoil.app.progress.tab.TopsoilTabPane;
import org.cirdles.topsoil.app.progress.table.TopsoilDataEntry;
import org.cirdles.topsoil.app.progress.util.Command;

public class ClearTableCommand implements Command {

    private TableView<TopsoilDataEntry> tableView;
    private TopsoilDataEntry[] rows;

    public ClearTableCommand(TableView<TopsoilDataEntry> tableView) {
        this.tableView = tableView;
        this.rows = new TopsoilDataEntry[this.tableView.getItems().size()];
        for (int i = 0; i < this.tableView.getItems().size(); i++) {
            this.rows[i] = this.tableView.getItems().get(i);
        }
    }

    public void execute() {
        ((TopsoilTabPane) this.tableView.getScene().lookup("#TopsoilTabPane"))
                .getSelectedTab().getTopsoilTable().clear();
        this.tableView.getItems().add(TopsoilDataEntry.newEmptyDataEntry(this.tableView));
    }

    public void undo() {
        this.tableView.getItems().setAll(this.rows);
    }

    public String getActionName() {
        return "Clear table";
    }

}
