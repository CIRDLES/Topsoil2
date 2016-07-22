package org.cirdles.topsoil.app.progress;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.cirdles.topsoil.app.util.Alerter;
import org.cirdles.topsoil.app.util.ErrorAlerter;

/**
 * Created by benjaminmuldrow on 7/6/16.
 */
public class TopsoilTable {

    private final Alerter alerter = new ErrorAlerter();
    private String[] headers;
    private TableView<TopsoilDataEntry> table;
    private IsotopeType isotopeType;

    public TopsoilTable(String [] headers, IsotopeType isotopeType, TopsoilDataEntry... dataEntries) {

        this.table = new TableView<>();
        this.isotopeType = isotopeType;

        // populate headers
        // TODO: separate into individual function
        if (headers == null) {
            this.headers = isotopeType.getHeaders();
        } else if (headers.length < isotopeType.getHeaders().length) {
            int difference = isotopeType.getHeaders().length - headers.length;
            this.headers = new String [isotopeType.getHeaders().length];
            for (int i = 0; i < isotopeType.getHeaders().length - difference; i ++) {
                this.headers[i] = headers[i];
            }
            for (int i = isotopeType.getHeaders().length - difference; i < isotopeType.getHeaders().length; i ++) {
                this.headers[i] = isotopeType.getHeaders()[i];
            }
        }

        this.table.getColumns().addAll(createColumns(this.headers));
        this.table.getItems().addAll(dataEntries);

    }

    private TableColumn [] createColumns(String [] headers) {
        TableColumn [] result = new TableColumn[headers.length];
        for (int i = 0; i < headers.length; i ++) {
            TableColumn<TopsoilDataEntry, Double> column = new TableColumn<>(headers[i]);
            final int columnIndex = i;
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TopsoilDataEntry, Double>, ObservableValue<Double>>() {
                @Override
                public ObservableValue<Double> call(TableColumn.CellDataFeatures<TopsoilDataEntry, Double> param) {
                    return (ObservableValue) param.getValue().getProperties().get(columnIndex);
                }
            });
            result[i] = column;
        }
        return result;
    }

    public TableView getTable() {
        return this.table;
    }

}
