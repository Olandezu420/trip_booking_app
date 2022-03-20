package controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Destination;
import model.UserHolder;
import model.VacationPackage;
import service.DestinationService;
import service.UserProfileService;
import service.VacationPackageService;
import java.net.URL;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class UserViewController implements Initializable {
    @FXML
    private DatePicker date;

    @FXML
    private TextField price;

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
    private ComboBox<String> filterBy;

    @FXML
    private TableView<VacationPackage> my_bookings;

    @FXML
    private TableColumn<String, VacationPackage> destinationCol1;

    @FXML
    private TableColumn<String, VacationPackage> nameCol1;

    @FXML
    private Label error;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Destination> destinations = DestinationService.getAll();
        List<String> options = new ArrayList<>();
        String[] options1 = new String[]{"Price", "Destination", "Period"};
        List<String> options2 = Arrays.asList(options1);
         for(Destination dest : destinations)
            options.add(dest.getName());
        ObservableList<String> opts = FXCollections.observableArrayList(options);
        ObservableList<String> opts2 = FXCollections.observableArrayList(options2);
        destinationBox.getItems().clear();
        destinationBox.setItems(opts);
        filterBy.getItems().clear();
        filterBy.setItems(opts2);
        loadTables();
    }

    private ObservableList<VacationPackage> opts = FXCollections.observableArrayList();

    public void loadTables() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        unitsCol.setCellValueFactory(new PropertyValueFactory<>("units"));
        detailsCol.setCellValueFactory(new PropertyValueFactory<>("details"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        destinationCol1.setCellValueFactory(new PropertyValueFactory<>("destination"));
        nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));

        refreshTable1();
        refreshTable2();

        packages.setRowFactory(
                tableView -> {
                    final TableRow<VacationPackage> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem editItem = new MenuItem("Book");
                    editItem.setOnAction(event -> {
                        UserProfileService userProfileService = new UserProfileService();
                        userProfileService.bookPackage((UserHolder
                                .getInstance()
                                .getUser()
                                .getUserProfile()
                                .getId()), row.getItem().getId());
                        error.setText(userProfileService.getPackageAlreadyAddedError());
                        refreshTable2();
                        refreshTable1();
                    });


                    rowMenu.getItems().addAll(editItem);
                    row.contextMenuProperty().bind(
                            Bindings.when(row.emptyProperty())
                                    .then((ContextMenu) null)
                                    .otherwise(rowMenu));
                    return row;
                });
    }

    public void refreshTable1() {
        List<VacationPackage> packs = VacationPackageService.getAll();
        packs = packs.stream().filter(vp -> vp.getUnits() != 0).collect(Collectors.toList());
        this.opts = FXCollections.observableArrayList(packs);
        packages.setItems(opts);
    }

    public void refreshTable2() {
        List<VacationPackage> packs = UserProfileService
                .getBookings(UserHolder
                            .getInstance()
                            .getUser()
                            .getUserProfile()
                            .getId());
        ObservableList<VacationPackage> opts = FXCollections.observableArrayList(packs);
        my_bookings.setItems(opts);
    }

    @FXML
    public void onFilter() {
        switch(filterBy.getValue()) {
            case "Price":
                filterByPrice();
                break;
            case "Period":
                filterByPeriod();
                break;
            case "Destination":
                filterByDestination();
                break;
        }
    }

    private void filterByPrice() {
        packages.setItems(FXCollections.observableArrayList(opts.stream().filter(vacationPackage -> vacationPackage.getPrice() < Integer.parseInt(price.getText())).collect(Collectors.toList())));
    }

    private void filterByPeriod() {
        packages.setItems(FXCollections.observableArrayList(opts.stream().filter(vacationPackage -> vacationPackage.getStart().toLocalDate().compareTo(date.getValue()) < 0).collect(Collectors.toList())));
    }

    private void filterByDestination() {
        packages.setItems(FXCollections.observableArrayList(opts.stream().filter(vacationPackage -> Objects.equals(vacationPackage.getDestination().getName(), destinationBox.getValue())).collect(Collectors.toList())));
    }
}
