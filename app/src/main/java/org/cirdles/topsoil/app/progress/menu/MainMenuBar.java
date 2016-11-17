package org.cirdles.topsoil.app.progress.menu;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ButtonType;
import org.cirdles.topsoil.app.progress.isotope.IsotopeType;
import org.cirdles.topsoil.app.progress.tab.TopsoilTabPane;
import javafx.stage.Stage;
import org.cirdles.topsoil.app.dataset.SimpleDataset;
import org.cirdles.topsoil.app.plot.PlotWindow;
import org.cirdles.topsoil.app.plot.Variable;
import org.cirdles.topsoil.app.plot.VariableBindingDialog;
import org.cirdles.topsoil.app.progress.TopsoilRawData;
import org.cirdles.topsoil.app.progress.plot.TopsoilPlotType;
import org.cirdles.topsoil.app.progress.table.TopsoilTable;

import org.cirdles.topsoil.plot.Plot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.cirdles.topsoil.app.progress.menu.MenuItemEventHandler.*;

/**
 * Created by sbunce on 5/30/2016.
 */
public class MainMenuBar extends MenuBar {

    private MenuBar menuBar = new MenuBar();

    // Edit Menu
    private MenuItem undoItem;
    private MenuItem redoItem;

    // File Menu
    //private MenuItem newProjectItem;
    //private MenuItem saveProjectItem;
    private MenuItem openProjectItem;
    private MenuItem saveProjectAsItem;
    //private MenuItem mostRecentItem;
    //private MenuItem closeProjectItem;

    // Table Menu
    private MenuItem newTableItem;
    private MenuItem saveTableItem;
    private MenuItem exportTableItem;
    // Import >
    private MenuItem tableFromFileItem;
    private MenuItem tableFromClipboardItem;

    // Plot Menu
    private Menu plotMenu;
    private Menu generatePlotMenu;

    // Help Menu
    private MenuItem reportIssueItem;
    private MenuItem aboutItem;

    //Passed the main scene and tabbed pane
    public MainMenuBar(TopsoilTabPane tabs) {
        super();
        this.initialize(tabs);
    }

    private void initialize(TopsoilTabPane tabs) {

        // Edit Menu
        Menu editMenu = new Menu("Edit");
        undoItem = new MenuItem("Undo");
        redoItem = new MenuItem("Redo");

        undoItem.setOnAction(event -> {
            if (!tabs.isEmpty()) {
                tabs.getSelectedTab().undo();
            }
        });
        redoItem.setOnAction(event -> {
            if (!tabs.isEmpty()) {
                tabs.getSelectedTab().redo();
            }
        });

        editMenu.getItems()
                .addAll(undoItem,
                        redoItem);

        // File Menu
        Menu projectMenu = initializeProjectMenuItems(tabs);

        // Table Menu
        Menu tableMenu = new Menu("Table");
        newTableItem = new MenuItem("New Table");
        saveTableItem = new MenuItem("Save Table");
        exportTableItem = new MenuItem("Export Table");

        newTableItem.setOnAction(event -> {
            TopsoilTable table = MenuItemEventHandler.handleNewTable();
            tabs.add(table);
        });

        //Creates Submenu for Imports
        Menu importTable = new Menu("Import Table");
        tableFromFileItem = new MenuItem("From File");
        tableFromClipboardItem = new MenuItem("From Clipboard");
        importTable.getItems().addAll(
                tableFromFileItem,
                tableFromClipboardItem);

        tableMenu.getItems()
                .addAll(newTableItem,
                        saveTableItem,
                        exportTableItem,
                        importTable);

        // Plot Menu
        plotMenu = new Menu("Plot");
        generatePlotMenu = new Menu("Generate Plot");
        plotMenu.getItems().add(generatePlotMenu);

        // Help Menu
        Menu helpMenu = new Menu("Help");
        reportIssueItem = new MenuItem("Report Issue");
        aboutItem = new MenuItem("About");
        helpMenu.getItems()
                .addAll(reportIssueItem,
                        aboutItem);

        // Add menus to menuBar
        menuBar.getMenus()
                .addAll(projectMenu,
                        editMenu,
                        tableMenu,
                        plotMenu,
                        helpMenu);

        // Import Table from File
        tableFromFileItem.setOnAction(event -> {

            TopsoilTable table = null;

            // get table from selections
            try {
                table = handleTableFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // display table
            if (table != null) {
                tabs.add(table);
            } else {
                //
            }
            refreshPlotMenu(tabs);

        });

        tableFromClipboardItem.setOnAction(event -> {

            TopsoilTable table = null;

            try {
                table = handleTableFromClipboard();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (table != null) tabs.add(table);
            refreshPlotMenu(tabs);
            
        });

        // New, empty table
        newTableItem.setOnAction(event -> {

            // get new table
            TopsoilTable table = handleNewTable();
            tabs.add(table);
            refreshPlotMenu(tabs);});

        // Report Issue
        reportIssueItem.setOnAction(event -> {
            handleReportIssue();
        });

        generatePlotMenu.setOnAction(event -> {
            refreshPlotMenu(tabs);
        });

    }

    /**
     * Display plot
     * @param plotType type of plot to display
     * @param table Topsoil Table holding data to display
     */
    private void showPlot(TopsoilPlotType plotType, TopsoilTable table) {

        // variable binding dialog
        if (plotType != null) {
            List<Variable> variables = plotType.getVariables();
            SimpleDataset dataset = new SimpleDataset(table.getTitle(), new TopsoilRawData(table).getRawData());
            new VariableBindingDialog(variables, dataset).showAndWait()
                    .ifPresent(data -> {
                        Plot plot = plotType.getPlot();
                        plot.setData(data);

                        Parent plotWindow = new PlotWindow(
                                plot, plotType.getPropertiesPanel());

                        Scene scene = new Scene(plotWindow, 1200, 800);

                        Stage plotStage = new Stage();
                        plotStage.setScene(scene);
                        plotStage.show();
                    });
        }
    }

    /**
     * refreshing content of generate plot menu helper function
     * @param tabs TopsoilTabPane containing current table
     */
    public void refreshPlotMenu(TopsoilTabPane tabs) {

        // remove existing sub-menuItems
        generatePlotMenu.getItems().removeAll(generatePlotMenu.getItems());

        if (!tabs.isEmpty()) {

            // add a sub-menuItem for each applicable plot
            IsotopeType isotopeType = tabs.getSelectedTab().getTopsoilTable().getIsotopeType();
            ArrayList<MenuItem> plotItems = new ArrayList<>();
            for (TopsoilPlotType plot : isotopeType.getPlots()) {
                MenuItem plotItem = new MenuItem(plot.getName());
                plotItem.setOnAction(event1 -> {
                    showPlot(plot, tabs.getSelectedTab().getTopsoilTable());
                });
                plotItems.add(plotItem);
            }

            generatePlotMenu.getItems().addAll(plotItems);
        }
    }

    private Menu initializeProjectMenuItems(TopsoilTabPane tabs) {
        Menu projectMenu = new Menu("Project");
//        newProjectItem = new MenuItem("New Project");
//        saveProjectItem = new MenuItem("Save Project");
        saveProjectAsItem = new MenuItem("Save Project As");
        openProjectItem = new MenuItem("Open Project");
//        closeProjectItem = new MenuItem("Close Project");
//        mostRecentItem = new MenuItem("Most Recently Used");

        openProjectItem.setOnAction(event -> {
            if (!tabs.isEmpty()) {
                Alert verification = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Opening a Topsoil project will replace your current tables. Continue?",
                        ButtonType.CANCEL,
                        ButtonType.YES
                );
                verification.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        MenuItemEventHandler.handleOpenProjectFile(tabs);
                    }
                });
            } else {
                MenuItemEventHandler.handleOpenProjectFile(tabs);
            }
        });

        saveProjectAsItem.setOnAction(event -> {
            if (!tabs.isEmpty()) {
                MenuItemEventHandler.handleNewProjectFile(tabs);
            }
        });

        projectMenu.getItems()
                .addAll(
//                        newProjectItem,
//                        saveProjectItem,
                        openProjectItem,
                        saveProjectAsItem
//                        , closeProjectItem,
//                        mostRecentItem
                );

        return projectMenu;
    }

    //Returns compatible type to be added to main window
    public MenuBar getMenuBar() {
        return menuBar;
    }

}
