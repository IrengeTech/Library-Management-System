import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryManagementSystemGUI extends Application {

    public static class Book {

        private int id;
        private String title;
        private String author;
        private String status;

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.status = "Available";
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getStatus() {
            return status;
        }

        public void issueBook() {
            status = "Issued";
        }

        public void returnBook() {
            status = "Available";
        }
    }

    private final ObservableList<Book> books = FXCollections.observableArrayList();

    private TableView<Book> table;

    private TextField idField;
    private TextField titleField;
    private TextField authorField;
    private TextField searchField;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Library Management System");

        table = new TableView<>();

        TableColumn<Book, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(80);

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(200);

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setPrefWidth(200);

        TableColumn<Book, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setPrefWidth(120);

        table.getColumns().addAll(idColumn, titleColumn, authorColumn, statusColumn);
        table.setItems(books);

        idField = new TextField();
        idField.setPromptText("Book ID");

        titleField = new TextField();
        titleField.setPromptText("Title");

        authorField = new TextField();
        authorField.setPromptText("Author");

        searchField = new TextField();
        searchField.setPromptText("Search by title or author");

        Button addButton = new Button("Add Book");
        Button removeButton = new Button("Remove Book");
        Button issueButton = new Button("Issue Book");
        Button returnButton = new Button("Return Book");
        Button searchButton = new Button("Search");
        Button refreshButton = new Button("Refresh");

        addButton.setOnAction(e -> addBook());
        removeButton.setOnAction(e -> removeBook());
        issueButton.setOnAction(e -> issueBook());
        returnButton.setOnAction(e -> returnBook());
        searchButton.setOnAction(e -> searchBook());
        refreshButton.setOnAction(e -> table.setItems(books));

        GridPane formLayout = new GridPane();
        formLayout.setHgap(10);
        formLayout.setVgap(10);

        formLayout.add(new Label("Book ID:"), 0, 0);
        formLayout.add(idField, 1, 0);

        formLayout.add(new Label("Title:"), 0, 1);
        formLayout.add(titleField, 1, 1);

        formLayout.add(new Label("Author:"), 0, 2);
        formLayout.add(authorField, 1, 2);

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(
                addButton,
                removeButton,
                issueButton,
                returnButton,
                searchButton,
                refreshButton
        );

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        root.getChildren().addAll(
                new Label("Library Management System"),
                formLayout,
                searchField,
                buttonLayout,
                table
        );

        Scene scene = new Scene(root, 750, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addBook() {

        try {

            String idText = idField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();

            if (idText.isEmpty() || title.isEmpty() || author.isEmpty()) {

                showAlert("Error", "All fields are required.");
                return;
            }

            int id = Integer.parseInt(idText);

            books.add(new Book(id, title, author));

            clearFields();

            showAlert("Success", "Book added successfully.");

        } catch (NumberFormatException e) {

            showAlert("Error", "Book ID must be numeric.");
        }
    }

    private void removeBook() {

        Book selectedBook = table.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {

            showAlert("Error", "Please select a book.");
            return;
        }

        books.remove(selectedBook);

        showAlert("Success", "Book removed successfully.");
    }

    private void issueBook() {

        Book selectedBook = table.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {

            showAlert("Error", "Please select a book.");
            return;
        }

        if (selectedBook.getStatus().equals("Issued")) {

            showAlert("Error", "Book is already issued.");
            return;
        }

        selectedBook.issueBook();

        table.refresh();

        showAlert("Success", "Book issued successfully.");
    }

    private void returnBook() {

        Book selectedBook = table.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {

            showAlert("Error", "Please select a book.");
            return;
        }

        if (selectedBook.getStatus().equals("Available")) {

            showAlert("Error", "Book is already available.");
            return;
        }

        selectedBook.returnBook();

        table.refresh();

        showAlert("Success", "Book returned successfully.");
    }

    private void searchBook() {

        String keyword = searchField.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {

            table.setItems(books);
            return;
        }

        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();

        for (Book book : books) {

            if (book.getTitle().toLowerCase().contains(keyword)
                    || book.getAuthor().toLowerCase().contains(keyword)) {

                filteredBooks.add(book);
            }
        }

        table.setItems(filteredBooks);
    }

    private void clearFields() {

        idField.clear();
        titleField.clear();
        authorField.clear();
    }

    private void showAlert(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void main(String[] args) {

        launch(args);
    }
}