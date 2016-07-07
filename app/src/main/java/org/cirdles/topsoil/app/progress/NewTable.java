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
    private TableColumn [] tableColumns;
    private String [] headers;

    public NewTable(List<TopsoilDataEntry> entries,
                    String [] headers,
                    IsotopeType isotopeType) {

        super();
        ObservableList<TopsoilDataEntry> data = FXCollections.observableList(entries);

            if (headers != null) {

                // get headers
                int difference = isotopeType.getHeaders().length - headers.length;

                // populate non-included headers
                if (difference > 0) {
                    alerter.alert("Populated " + difference + " non-included columns with 0's");

                    for (int i = 0; i < headers.length; i++) {
                        this.headers[i] = headers[i];
                    }
                    for (int j = headers.length; j < isotopeType.getHeaders().length; j++) {
                        this.headers[j] = isotopeType.getHeaders()[headers.length + j - 1];
                    }
                } else if (difference == 0) {
                    this.headers = headers;
                } else { // difference < 0
                    alerter.alert("Only used the first " + isotopeType.getHeaders().length + "columns of file");
                    for (int i = 0; i < isotopeType.getHeaders().length; i++) {
                        this.headers[i] = headers[i];
                    }
                }
            }

        // set columns
        ArrayList<TableColumn> columnArrayList = new ArrayList<>();
        for (String header : this.headers) {
            columnArrayList.add(
                    new TableColumn(header)
            );
        }
        for (TableColumn tableColumn : columnArrayList) {
            tableColumn.setCellValueFactory(
                    new PropertyValueFactory<TopsoilDataEntry, Double>(tableColumn.getId())
            );
            this.getColumns().add(tableColumn);
        }
        this.tableColumns = columnArrayList.toArray(new TableColumn[]{});

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

}
