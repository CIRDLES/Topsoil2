package org.cirdles.topsoil.app.progress;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.cirdles.topsoil.app.progress.menu.MainButtonsBar;
import org.cirdles.topsoil.app.progress.menu.MainMenuBar;
import org.cirdles.topsoil.app.progress.menu.MenuItemEventHandler;
import org.cirdles.topsoil.app.progress.tab.TopsoilTabPane;
import org.cirdles.topsoil.app.progress.table.TopsoilTable;
import org.cirdles.topsoil.app.util.Alerter;
import org.cirdles.topsoil.app.util.ErrorAlerter;

import java.io.IOException;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(new VBox(), 750, 750);

        TopsoilTabPane tabs = new TopsoilTabPane();
        tabs.setId("TopsoilTabPane");
        MainMenuBar menuBar = new MainMenuBar(tabs);
        MenuBar mBar = menuBar.getMenuBar();
        mBar.setId("MenuBar");
        MainButtonsBar buttonBar = new MainButtonsBar(tabs);
        HBox buttons = buttonBar.getButtons();
        buttons.setId("HBox");

        // Create Scene
        ((VBox) scene.getRoot()).getChildren().addAll(
                mBar,
                buttons,
                tabs
        );

        // Display Scene
        primaryStage.setTitle("Topsoil Test");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle Keyboard Shortcuts
        scene.setOnKeyPressed(keyevent -> {

            // shortcut + T creates a new tab containing an empty table
            if (keyevent.getCode() == KeyCode.T &&
                    keyevent.isShortcutDown()) {
                TopsoilTable table = MenuItemEventHandler.handleNewTable();
                tabs.add(table);
            }
            // shortcut + I imports a new table from a file
            if (keyevent.getCode() == KeyCode.I &&
                    keyevent.isShortcutDown()) {
                try {
                    TopsoilTable table = MenuItemEventHandler.handleTableFromFile();
                    tabs.add(table);
                } catch (IOException e) {
                    Alerter alerter = new ErrorAlerter();
                    alerter.alert("File I/O Error.");
                    e.printStackTrace();
                }
            }
            // shortcut + Z undoes the last undoable action
            if (keyevent.getCode() == KeyCode.Z &&
                    keyevent.isShortcutDown()) {
                ((TopsoilTabPane) scene.lookup("#TopsoilTabPane")).getSelectedTab().undo();
            }
            // shortcut + Y redoes the last undone action
            if (keyevent.getCode() == KeyCode.Y &&
                    keyevent.isShortcutDown()) {
                ((TopsoilTabPane) scene.lookup("#TopsoilTabPane")).getSelectedTab().redo();
            }

            keyevent.consume();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
