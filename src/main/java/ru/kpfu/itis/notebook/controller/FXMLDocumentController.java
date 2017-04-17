package ru.kpfu.itis.notebook.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.kpfu.itis.notebook.entity.Event;
import ru.kpfu.itis.notebook.model.EventService;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField tName, tDescription, tFind;
    @FXML
    private DatePicker datePick;
    @FXML
    private Button bSave, bUpdate, bDelete;
    @FXML
    private TableView<Event> textView;
    @FXML
    private TableColumn<Event, String> id, name, description;
    @FXML
    private TableColumn<Event, Date> date;

    private EventService eventService = new EventService();
    private ObservableList<Event> observList = FXCollections.observableArrayList();
    private String key;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        read();
        find();
        save();
        update();
        delete();
        selectRow();
    }

    public void read() {
        textView.getItems().clear();
        List<Event> list = eventService.getAll();
        observList.addAll(list);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        textView.setItems(observList);
    }

    public void find() {
        tFind.setOnKeyReleased(event -> {
            textView.getItems().clear();

            if (tFind.getText().isEmpty() || tFind.getText() == null) {
                read();
                return;
            }

            List<Event> list = eventService.find(tFind.getText());
            observList.addAll(list);

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));

            textView.setItems(observList);
        });
    }

    public void selectRow() {
        textView.setOnMouseClicked(event -> {
            int index = textView.getSelectionModel().getSelectedIndex();
            key = String.valueOf(id.getCellData(index));
            tName.setText(name.getCellData(index));
            tDescription.setText(description.getCellData(index));
            Date indexDate = date.getCellData(index);
            if (indexDate == null) return;
            LocalDate localDate = indexDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePick.setValue(localDate);
        });
    }

    public void clear() {
        key = null;
        tName.clear();
        tDescription.clear();
        datePick.setValue(null);
    }

    public void save() {
        bSave.setOnAction(e -> {
            Event event = new Event();
            event.setName(tName.getText());
            event.setDescription(tDescription.getText());
            Date date = Date.from(datePick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            event.setDate(date);
            eventService.save(event);
            clear();
            read();
        });
    }

    public void update() {
        bUpdate.setOnAction(e -> {
            Event event = new Event();
            event.setId(Long.parseLong(key));
            event.setName(tName.getText());
            event.setDescription(tDescription.getText());
            Date date = Date.from(datePick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            event.setDate(date);
            eventService.update(event);
            clear();
            read();
        });
    }

    public void delete() {
        bDelete.setOnAction(e -> {
            Event event = new Event();
            event.setId(Long.parseLong(key));
            Date date = Date.from(datePick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            event.setDate(date);
            eventService.delete(event);
            clear();
            read();
        });
    }
}
