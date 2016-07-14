package org.cirdles.topsoil.app.progress;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by benjaminmuldrow on 7/13/16.
 */
public class NewDataEntry {

    ObservableList<DoubleProperty> properties;
    List<String> propertyNames;

    /**
     *
     * @param collection
     */
    public NewDataEntry(Collection<DoubleProperty> collection) {
        ArrayList<DoubleProperty> propertiesArrayList = new ArrayList<>(collection);
        this.properties = FXCollections.observableArrayList(propertiesArrayList);

        propertyNames = new ArrayList<>();

        for (DoubleProperty property : properties) {
            propertyNames.add(property.getName());
        }
    }

}
