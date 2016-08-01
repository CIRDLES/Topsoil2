package org.cirdles.topsoil.app.progress;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * Created by benjaminmuldrow on 7/27/16.
 */
public class TopsoilTableCell extends TableCell<TopsoilDataEntry, Double> {

    private TextField textField;

    public TopsoilTableCell() {
        super();

        this.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode() == KeyCode.ENTER ||
                    keyEvent.getCode() == KeyCode.TAB) {
                Double newVal = new Double(textField.getText());
                commitEdit(newVal);
                updateItem(newVal, textField.getText().isEmpty());
                keyEvent.consume();
            } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
                keyEvent.consume();
            }
        });

    }

    @Override
    public void startEdit() {
        super.startEdit();
        generateTextField();
        this.setText(null);
        this.textField.setText(getItem().toString());
        this.setGraphic(this.textField);
        this.textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        this.setText(getItem().toString());
        this.setGraphic(null);
    }

    @Override
    public void updateItem(Double item, boolean isEmpty) {
        super.updateItem(item, isEmpty);

        if (isEmpty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getItem().toString());
                    setText(null);
                    setGraphic(textField);
                }
            } else {
                setText(getItem().toString());
                setGraphic(null);
            }
        }
    }

    private void generateTextField() {
        this.textField = new TextField();
        this.textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        this.textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // if new value contains non-numerics or is empty
            if (!newValue) {
                commitEdit(new Double(textField.getText()));
            }
        });
    }

}
