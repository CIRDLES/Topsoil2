package org.cirdles.topsoil.app.progress;

import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import org.cirdles.topsoil.app.util.ErrorAlerter;

import java.io.IOException;

import static org.cirdles.topsoil.app.progress.MenuItemEventHandler.handleNewTable;
import static org.cirdles.topsoil.app.progress.MenuItemEventHandler.handleTableFromFile;

/**
 * Created by sbunce on 5/30/2016.
 */

public class MainMenuBar extends MenuBar {

    private MenuBar menuBar = new MenuBar();

    // Project Menu
    private MenuItem newProjectItem;
    private MenuItem saveProjectItem;
    private MenuItem saveProjectAsItem;
    private MenuItem openProjectItem;
    private MenuItem mostRecentItem;
    private MenuItem closeProjectItem;

    // Table Menu
    private MenuItem newTableItem;
    private MenuItem saveTableItem;
    private MenuItem saveTableAsItem;
    // Import >
    private MenuItem tableFromFileItem;
    private MenuItem tableFromClipboardItem;
    // Isotope System >
    private MenuItem uraniumLeadSystemItem;
    private MenuItem uraniumThoriumSystemItem;

    // Help Menu
    private MenuItem reportIssueItem;
    private MenuItem aboutItem;

    // Scene
    private Scene scene;

    public MainMenuBar(Scene scene) {
        super();
        this.scene = scene;
        this.initialize();
    }

    public void initialize() {
        // Project Menu
        Menu projectMenu = new Menu("Project");
        newProjectItem = new MenuItem("New Project");
        saveProjectItem = new MenuItem("Save Project");
        saveProjectAsItem = new MenuItem("Save Project As");
        openProjectItem = new MenuItem("Open Project");
        mostRecentItem = new MenuItem("Most Recently Used");
        closeProjectItem = new MenuItem("Close Project");
        projectMenu.getItems()
                .addAll(newProjectItem,
                        saveProjectItem,
                        saveProjectAsItem,
                        openProjectItem,
                        mostRecentItem,
                        closeProjectItem);

        // Table Menu
        Menu tableMenu = new Menu("Table");
        newTableItem = new MenuItem("New Table");
        saveTableItem = new MenuItem("Save Table");
        saveTableAsItem = new MenuItem("Save Table As");

        newTableItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                IsotopeSelectionWindow iso = new IsotopeSelectionWindow();
            }
        });
        //Saves the currently opened table
        MenuItem saveTable = new MenuItem("Save Table");
        //Saves the currently opened table as a specified file
        MenuItem saveTableAs = new MenuItem("Save Table As");

        //Creates Submenu for Imports
        Menu importTable = new Menu("Import Table");
        tableFromFileItem = new MenuItem("From File");
        tableFromClipboardItem = new MenuItem("From Clipboard");
        importTable.getItems().addAll(
                tableFromFileItem,
                tableFromClipboardItem);

        //Creates Submenu for Isotype system selection
        Menu isoSystem = new Menu("Set Isotope System");
        uraniumLeadSystemItem = new MenuItem("UPb");
        uraniumThoriumSystemItem = new MenuItem("UTh");
        isoSystem.getItems().addAll(
                uraniumLeadSystemItem,
                uraniumThoriumSystemItem);
        tableMenu.getItems()
                .addAll(newTableItem,
                        saveTableItem,
                        saveTableAsItem,
                        importTable,
                        isoSystem);

        // Plot Menu
        Menu plotMenu = new Menu("Plot");

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
                        tableMenu,
                        plotMenu,
                        helpMenu);

        // Import Table from File
        tableFromFileItem.setOnAction(event -> {

            TopsoilTable table = null;

            try {
                table = handleTableFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // display table
            if (table != null) {
                ((VBox) scene.getRoot()).getChildren().addAll(table);
            } else {
                ErrorAlerter alerter = new ErrorAlerter();
                alerter.alert("Invalid Table");
            }

        });

        // New, empty table
        newTableItem.setOnAction(event -> {

            // get new table
            TopsoilTable table = handleNewTable();

            // display new table
            ((VBox) scene.getRoot()).getChildren().addAll(table);
        });
    }

    //Returns compatible type to be added to main window
    public MenuBar getMenuBar() {
        return menuBar;
    }
}
