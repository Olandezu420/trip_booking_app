package controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Destination;
import model.VacationPackage;

import service.DestinationService;
import service.VacationPackageService;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AgencyController implements Initializable {
    @FXML
    private TextField newDestination;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField details;

    @FXML
    private DatePicker start;

    @FXML
    private DatePicker end;

    @FXML
    private TextField price;

    @FXML
    private  TextField units;

    @FXML
    private TableView<VacationPackage> packages;

    @FXML
    private TableColumn<Integer, VacationPackage> idCol;

    @FXML
    private TableColumn<String, VacationPackage> destinationCol;

    @FXML
    private TableColumn<String, VacationPackage> detailsCol;

    @FXML
    private TableColumn<String, VacationPackage> nameCol;

    @FXML
    private TableColumn<Date, VacationPackage> startCol;

    @FXML
    private TableColumn<Date, VacationPackage> endCol;

    @FXML
    private TableColumn<Integer, VacationPackage> priceCol;

    @FXML
    private TableColumn<String, VacationPackage> statusCol;

    @FXML
    private TableColumn<Integer, VacationPackage> unitsCol;

    @FXML
    private ComboBox<String> destinationBox;

    @FXML
    private Label error;

    @FXML
    private void onAddPackage() {
        Destination dest = DestinationService.findBy(destinationBox.getValue());
        VacationPackageService.addPackage(dest,
                name.getText(),
                Date.valueOf(start.getValue()),
                Date.valueOf(end.getValue()),
                Integer.parseInt(price.getText()),
                Integer.parseInt(units.getText()),
                details.getText());
        refreshTable();
    }

    @FXML
    private void onAddDestination() {
        DestinationService.addNew(newDestination.getText());
        destinationBox.getItems().add(newDestination.getText());

    }

    @FXML
    private void onEditPackage() {
        VacationPackage vp = new VacationPackage(DestinationService.findBy(destinationBox.getValue()),
                name.getText(),
                Date.valueOf(start.getValue()),
                Date.valueOf(end.getValue()),
                Integer.parseInt(price.getText()),
                Integer.parseInt(units.getText()),
                details.getText());

        vp.setId((long) Integer.parseInt(id.getText()));
        VacationPackageService.edit(vp);
        refreshTable();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshDestinations();
        loadTable();
    }

    public void refreshDestinations() {
        List<Destination> destinations = DestinationService.getAll();
        List<String> options = new ArrayList<>();
        for(Destination dest : destinations)
            options.add(dest.getName());
        ObservableList<String> opts = FXCollections.observableArrayList(options);
        destinationBox.getItems().clear();
        destinationBox.setItems(opts);
    }

    public void loadTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        unitsCol.setCellValueFactory(new PropertyValueFactory<>("units"));
        detailsCol.setCellValueFactory(new PropertyValueFactory<>("details"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        List<VacationPackage> packs = VacationPackageService.getAll();
        ObservableList<VacationPackage> opts = FXCollections.observableArrayList(packs);
        packages.setItems(opts);

        packages.setRowFactory(
                tableView -> {
                    final TableRow<VacationPackage> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem editItem = new MenuItem("Edit");
                    editItem.setOnAction(event -> {
                        VacationPackage vacationPackage = row.getItem();
                        id.setText(Long.toString(vacationPackage.getId()));
                        name.setText(vacationPackage.getName());
                        start.setValue(vacationPackage.getStart().toLocalDate());
                        end.setValue(vacationPackage.getEnd().toLocalDate());
                        details.setText(vacationPackage.getDetails());
                        destinationBox.setValue(vacationPackage.getDestination().getName());
                        price.setText(Integer.toString(vacationPackage.getPrice()));
                        units.setText(Integer.toString(vacationPackage.getUnits()));

                    });
                    MenuItem removeItem = new MenuItem("Delete");
                    removeItem.setOnAction(event -> {
                        VacationPackageService.remove(row.getItem().getId());
                        refreshTable();
                    });
                    rowMenu.getItems().addAll(editItem, removeItem);
                    row.contextMenuProperty().bind(
                            Bindings.when(row.emptyProperty())
                                    .then((ContextMenu) null)
                                    .otherwise(rowMenu));
                    return row;
                });
    }

    public void refreshTable() {
        List<VacationPackage> packs = VacationPackageService.getAll();
        ObservableList<VacationPackage> opts = FXCollections.observableArrayList(packs);
        packages.setItems(opts);
    }

    @FXML
    public void onRemoveDestination() {
        String name = destinationBox.getValue();
        DestinationService destinationService = new DestinationService();
        destinationService.delete(DestinationService.findBy(name).getId());
        error.setText(destinationService.getRemovePackagesError());
        refreshDestinations();
    }


}
