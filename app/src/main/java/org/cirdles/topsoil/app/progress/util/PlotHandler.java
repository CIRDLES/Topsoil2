package org.cirdles.topsoil.app.progress.util;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.cirdles.topsoil.app.plot.PlotType;
import org.cirdles.topsoil.app.plot.UraniumLeadPlotType;
import org.cirdles.topsoil.app.plot.standard.ScatterPlotPropertiesPanel;
import org.cirdles.topsoil.app.progress.TopsoilRawData;
import org.cirdles.topsoil.app.progress.table.TopsoilTable;
import org.cirdles.topsoil.plot.Plot;
import org.cirdles.topsoil.plot.scatter.ScatterPlot;

import java.util.List;
import java.util.Map;

/**
 * Created by benjaminmuldrow on 8/8/16.
 */
public class PlotHandler {

    public static void handleGetPlot(TopsoilTable table, PlotType type) {

        TopsoilRawData rawData = new TopsoilRawData(table);

        if (type.equals(UraniumLeadPlotType.SCATTER_PLOT)) {
            Plot plot = type.newInstance();
            type.newPropertiesPanel(plot);
        }

        else if (type.equals(UraniumLeadPlotType.UNCERTAINTY_ELLIPSE_PLOT)) {
            //TODO Debug
            System.out.print("uncertain.");
            Plot plot = type.newInstance();
            type.newPropertiesPanel(plot);
        }
    }

    public static void handleNewPlot(Plot plot) {
        if (plot.equals(new ScatterPlot())) {
            Scene scene = new Scene(new VBox(), 600, 600);
            ((VBox)scene.getRoot()).getChildren().addAll(plot.displayAsNode(), new ScatterPlotPropertiesPanel(plot));

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }
}
