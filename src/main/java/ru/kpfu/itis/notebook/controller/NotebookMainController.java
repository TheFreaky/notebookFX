package ru.kpfu.itis.notebook.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.kpfu.itis.notebook.entity.Event;
import ru.kpfu.itis.notebook.entity.User;
import ru.kpfu.itis.notebook.model.EventModel;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class NotebookMainController implements Initializable {

    @FXML
    private TextField tName, tDescription, tFind;
    @FXML
    private DatePicker datePick;
    @FXML
    private Button bSave, bUpdate, bDelete;
    @FXML
    private TableView<Event> textView;
    @FXML
    private TableColumn<Event, String> name, description;
    @FXML
    private TableColumn<Event, Long> id;
    @FXML
    private TableColumn<Event, Date> date;

    private EventModel eventModel;
    private ObservableList<Event> observList;
    private Long key;

    private User user;

    public NotebookMainController() {
        eventModel = new EventModel();
        observList = FXCollections.observableArrayList();
    }

    public void setUser(User user) {
        this.user = user;
        eventModel.setUser(user);
        read();
    }

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
        List<Event> list = eventModel.getAll();
        showItems(list);
    }

    public void find() {
        tFind.setOnKeyReleased(event -> {
            if (tFind.getText().isEmpty() || tFind.getText() == null) {
                read();
                return;
            }

            List<Event> list = eventModel.find(tFind.getText());
            showItems(list);
        });
    }

    public void selectRow() {
        textView.setOnMouseClicked(event -> {
            int index = textView.getSelectionModel().getSelectedIndex();
            key = id.getCellData(index);
            tName.setText(name.getCellData(index));
            tDescription.setText(description.getCellData(index));
            Date indexDate = date.getCellData(index);
            if (indexDate == null) return; //ToDo: throw exception
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
            Event event = new Event(tName.getText(), tDescription.getText(), getDate(), user);
            eventModel.save(event);
            clear();
            read();
        });
    }

    public void update() {
        bUpdate.setOnAction(e -> {
            Event event = new Event(key, tName.getText(), tDescription.getText(), getDate(), user);
            eventModel.update(event);
            clear();
            read();
        });
    }

    public void delete() {
        bDelete.setOnAction(e -> {
            Event event = new Event(key, getDate());
            event.setUser(user);
            eventModel.delete(event);
            clear();
            read();
        });
    }

    private void showItems(List<Event> list) {
        textView.getItems().clear();
        observList.addAll(list);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        textView.setItems(observList);
    }

    private Date getDate() {
        return Date.from(datePick.getValue()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }
}
