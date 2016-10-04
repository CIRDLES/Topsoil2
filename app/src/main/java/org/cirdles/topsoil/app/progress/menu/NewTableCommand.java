package org.cirdles.topsoil.app.progress.menu;

import javafx.event.EventHandler;
import org.cirdles.topsoil.app.progress.isotope.IsotopeType;
import org.cirdles.topsoil.app.progress.tab.TopsoilTab;
import org.cirdles.topsoil.app.progress.tab.TopsoilTabPane;
import org.cirdles.topsoil.app.progress.table.TopsoilDataEntry;
import org.cirdles.topsoil.app.progress.table.TopsoilTable;
import org.cirdles.topsoil.app.progress.util.Command;

/**
 * Created by Jake's PC on 10/4/2016.
 */
public class NewTableCommand implements Command {

    private TopsoilTabPane tabs;
    private TopsoilTab tab;
    private IsotopeType isotopeType;

    public NewTableCommand(TopsoilTabPane tabs, IsotopeType isotopeType) {
        this.tabs = tabs;
        this.tab = tabs.getSelectedTab();
        this.isotopeType = isotopeType;
    }

    public void execute() {
        tabs.add(new TopsoilTable(null, isotopeType, new TopsoilDataEntry[]{}));
        tab = tabs.getSelectedTab();
    }

    public void undo() {
        EventHandler handleTabDelete = this.tabs.getTabs().get(this.tabs.getTabs().size() - 1).getOnClosed();
        if (handleTabDelete != null) {
            handleTabDelete.handle(null);
        } else {
            this.tabs.getTabs().remove(this.tab);
        }
    }

    public String getActionName() {
        return "Create new table";
    }
}
