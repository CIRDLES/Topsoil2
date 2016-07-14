package org.cirdles.topsoil.app.progress;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.cirdles.topsoil.app.util.Alerter;
import org.cirdles.topsoil.app.util.ErrorAlerter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjaminmuldrow on 7/6/16.
 */
public class NewTable extends TableView<TopsoilDataEntry> {

    private final Alerter alerter = new ErrorAlerter();
    private String [] headers;

    public NewTable(List<TopsoilDataEntry> entries,
                    String [] headers,
                    IsotopeType isotopeType) {

        super();
        ObservableList<TopsoilDataEntry> data = FXCollections.observableList(entries);

        // set headers
        this.headers = getHeaders(headers, isotopeType);

        ArrayList<TableColumn> columnArrayList = new ArrayList<>();

        // make column for each header
        for (String header : this.headers) {
            TableColumn tempTableColumn = new TableColumn(header);
            tempTableColumn.setId(header);
            columnArrayList.add(tempTableColumn);
        }

        // add columns to table
        for (TableColumn tableColumn : columnArrayList) {
            tableColumn.setCellValueFactory(
                    new PropertyValueFactory<TopsoilDataEntry, Double>(tableColumn.getId())
            );
            this.getColumns().add(tableColumn);
        }

        // set rows
        this.getItems().addAll(data);

        // event listening
        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().isWhitespaceKey()) {
                this.addRow(new TopsoilDataEntry(0,0,0,0,0));
            }
        });
    }

    public void addRow(TopsoilDataEntry dataEntry) {
        this.getItems().add(dataEntry);
    }

    private String [] getHeaders(String [] headers, IsotopeType isotopeType) {

        String [] result = new String[isotopeType.getHeaders().length];

        // Use supplied headers
        if (headers != null) {
            // get headers
            int difference = isotopeType.getHeaders().length - headers.length;

            // populate non-included headers
            if (difference > 0) {
                alerter.alert("Populated " + difference + " non-included columns with 0's");

                for (int i = 0; i < headers.length; i++) {
                    result[i] = headers[i];
                }
                for (int j = headers.length; j < isotopeType.getHeaders().length; j++) {
                    result[j] = isotopeType.getHeaders()[headers.length + j - 1];
                }
            } else if (difference == 0) {
                result = headers;
            } else { // difference < 0
                alerter.alert("Only used the first " + isotopeType.getHeaders().length + "columns of file");
                for (int i = 0; i < isotopeType.getHeaders().length; i++) {
                    result[i] = headers[i];
                }
            }

        // Use default headers
        } else {
            result = isotopeType.getHeaders();
        }

        return result;
    }

}
