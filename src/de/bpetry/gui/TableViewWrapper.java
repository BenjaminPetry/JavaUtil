/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.gui;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Wrapper to make some functions easier when working with the table view
 *
 * @author Benjamin Petry
 */
public class TableViewWrapper<T>
{

    public interface TableCellValueExtractor<T, S>
    {

        public ObservableValue<S> extract(T value);
    }

    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    private final TableView<T> table;
    private final ObservableList<T> itemData = FXCollections.observableArrayList();
    private final FilteredList<T> filteredData;
    private final SortedList<T> sortedData;
    private final HashMap<String, TableColumn<T, ?>> nameToColumn = new HashMap<>();

    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------
    public TableViewWrapper()
    {
        this(new TableView<>());
    }

    public TableViewWrapper(TableView<T> table)
    {
        this.table = table;

        filteredData = new FilteredList<>(itemData, p -> true);
        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    //-------------------------------------------------------------------------
    ///////////////////////  Getter and Setter Methods ////////////////////////
    //-------------------------------------------------------------------------
    /**
     * Returns the table view object
     *
     * @return table view object
     */
    public TableView<T> getTable()
    {
        return table;
    }

    /**
     * Returns the tables items
     *
     * @return table items
     */
    public List<T> getItems()
    {
        return itemData;
    }

    /**
     * Adds a new column to the table. If you want to register an existing
     * column, use registerColumn() instead.
     *
     * @param <S> the type of the column's content (String, Number, etc.)
     * @param name the name of the column
     * @param value the method that extracts the cell's content. E.g.: (data) ->
     * data.getValue()
     * @return
     */
    public <S> TableColumn<T, S> addColumn(String name,
            TableCellValueExtractor<T, S> value)
    {
        TableColumn<T, S> column = new TableColumn<>(name);
        column.setCellValueFactory((data) -> value.extract(data.getValue()));
        nameToColumn.put(name, column);
        return column;
    }

    /**
     * Registers a column for this table.
     *
     * @param <S> the type of the column's content (String, Number, etc.)
     * @param column the column to register
     * @param value the method that extracts the cell's content. E.g.: (data) ->
     * data.getValue()
     */
    public <S> void registerColumn(TableColumn<T, S> column,
            TableCellValueExtractor<T, S> value)
    {
        column.setCellValueFactory((data) -> value.extract(data.getValue()));
        nameToColumn.put(column.getText(), column);
    }

    /**
     * Returns an added or registered column by name
     *
     * @param name the name of the column
     * @return the column or null of no column with the name exists
     */
    public TableColumn<T, ?> getColumnByName(String name)
    {
        if (nameToColumn.containsKey(name))
        {
            return nameToColumn.get(name);
        }
        return null;
    }

    /**
     * Adds a listener to selection-changed events
     *
     * @param listener listener to add. E.g. (obs, old, new) -> { if
     * (newSelection != null) { System.out.println("Selection:
     * "+new.toString()); } }
     */
    public void addSelectionListener(ChangeListener<? super T> listener)
    {
        table.getSelectionModel().selectedItemProperty().addListener(listener);
    }

    /**
     * Removes a listener for selection-changed events
     *
     * @param listener listener to remove
     */
    public void removeSelectionListener(ChangeListener<? super T> listener)
    {
        table.getSelectionModel().selectedItemProperty().removeListener(listener);
    }

    /**
     * Retrieves the table's disable state
     *
     * @return true -> table is disabled
     */
    public boolean isDisabled()
    {
        return table.isDisabled();
    }

    /**
     * Disables or Enables the table
     *
     * @param disable true to disable, false to enable the table
     */
    public void setDisable(boolean disable)
    {
        table.setDisable(disable);
    }

    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------
    /**
     * Filters the table list
     *
     * @param predicate filter predicate. E.g. item -> item.contains("test")
     */
    public void filter(Predicate<? super T> predicate)
    {
        filteredData.setPredicate(predicate);
    }

    /**
     * Sorts the table according to one or more columns. To reverse the sorting
     * for a column, use column.setComparator(column.getComparator.reverse())
     *
     * @param columns the columns
     */
    public void sort(TableColumn<T, ?>... columns)
    {
        table.getSortOrder().clear();
        table.getSortOrder().addAll(columns);
    }

}
