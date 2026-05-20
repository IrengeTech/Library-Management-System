import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagementSystem {

    static class Book {

        int id;
        String title;
        String author;
        boolean issued;

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.issued = false;
        }

        @Override
        public String toString() {

            return "ID: " + id +
                    " | Title: " + title +
                    " | Author: " + author +
                    " | Status: " + (issued ? "Issued" : "Available");
        }
    }

    static class LibraryService {

        private final ArrayList<Book> books = new ArrayList<>();

        public void addBook(Book book) {

            books.add(book);

            System.out.println("Book added successfully.");
        }

        public void viewBooks() {

            if (books.isEmpty()) {

                System.out.println("No books available.");

                return;
            }

            for (Book book : books) {

                System.out.println(book);
            }
        }

        public Book findBook(int id) {

            for (Book book : books) {

                if (book.id == id) {

                    return book;
                }
            }

            return null;
        }

        public void issueBook(int id) {

            Book book = findBook(id);

            if (book == null) {

                System.out.println("Book not found.");

                return;
            }

            if (book.issued) {

                System.out.println("Book already issued.");

                return;
            }

            book.issued = true;

            System.out.println("Book issued successfully.");
        }

        public void returnBook(int id) {

            Book book = findBook(id);

            if (book == null) {

                System.out.println("Book not found.");

                return;
            }

            if (!book.issued) {

                System.out.println("Book was not issued.");

                return;
            }

            book.issued = false;

            System.out.println("Book returned successfully.");
        }

        public void searchBook(String keyword) {

            boolean found = false;

            for (Book book : books) {

                if (book.title.toLowerCase().contains(keyword.toLowerCase())
                        || book.author.toLowerCase().contains(keyword.toLowerCase())) {

                    System.out.println(book);

                    found = true;
                }
            }

            if (!found) {

                System.out.println("No matching books found.");
            }
        }

        public void removeBook(int id) {

            Book book = findBook(id);

            if (book == null) {

                System.out.println("Book not found.");

                return;
            }

            books.remove(book);

            System.out.println("Book removed successfully.");
        }
    }

    static class InputHelper {

        private static final Scanner scanner = new Scanner(System.in);

        public static int readInt(String message) {

            while (true) {

                try {

                    System.out.print(message);

                    return Integer.parseInt(scanner.nextLine());

                } catch (NumberFormatException e) {

                    System.out.println("Invalid number. Try again.");
                }
            }
        }

        public static String readString(String message) {

            while (true) {

                System.out.print(message);

                String input = scanner.nextLine().trim();

                if (!input.isEmpty()) {

                    return input;
                }

                System.out.println("Input cannot be empty.");
            }
        }
    }

    public static void main(String[] args) {

        LibraryService library = new LibraryService();

        boolean running = true;

        while (running) {

            System.out.println("\n====== LIBRARY MANAGEMENT SYSTEM ======");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. Remove Book");
            System.out.println("7. Exit");
            System.out.println("=======================================");

            int choice = InputHelper.readInt("Choose option: ");

            switch (choice) {

                case 1:

                    int id = InputHelper.readInt("Enter Book ID: ");

                    String title = InputHelper.readString("Enter Title: ");

                    String author = InputHelper.readString("Enter Author: ");

                    library.addBook(new Book(id, title, author));

                    break;

                case 2:

                    library.viewBooks();

                    break;

                case 3:

                    int issueId = InputHelper.readInt("Enter Book ID to issue: ");

                    library.issueBook(issueId);

                    break;

                case 4:

                    int returnId = InputHelper.readInt("Enter Book ID to return: ");

                    library.returnBook(returnId);

                    break;

                case 5:

                    String keyword = InputHelper.readString("Enter keyword: ");

                    library.searchBook(keyword);

                    break;

                case 6:

                    int removeId = InputHelper.readInt("Enter Book ID to remove: ");

                    library.removeBook(removeId);

                    break;

                case 7:

                    running = false;

                    System.out.println("Exiting system...");

                    break;

                default:

                    System.out.println("Invalid option.");
            }
        }
    }
}