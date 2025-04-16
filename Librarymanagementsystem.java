import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

class Book implements Comparable<Book> {
    String title;
    String author;
    int id;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public int compareTo(Book other) {
        return this.title.compareTo(other.title);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Author: " + author;
    }
}

class Library {
    private TreeMap<Integer, Book> bookStorage = new TreeMap<>(); // TreeMap for sorted order
    private Queue<Integer> borrowQueue = new LinkedList<>(); // Queue for book borrowing requests

    public void addBook(int id, String title, String author) {
        bookStorage.put(id, new Book(id, title, author));
    }

    public void deleteBook(int id) {
        bookStorage.remove(id);
    }

    public Book searchBookByTitle(String title) {
        List<Book> books = new ArrayList<>(bookStorage.values());
        Collections.sort(books); // <- this line is necessary
        int index = Collections.binarySearch(books, new Book(0, title, ""));
        return index >= 0 ? books.get(index) : null;
    }

    public void requestBorrow(int bookId) {
        if (bookStorage.containsKey(bookId)) {
            borrowQueue.add(bookId);
            System.out.println("Book is added to request borrow with id:  " + bookId);
        } else {
            System.out.println("Book not found.");
        }
    }

    public void processBorrowRequest() {
        if (!borrowQueue.isEmpty()) {
            int bookId = borrowQueue.poll();
            System.out.println("Processing borrow request for: " + bookStorage.get(bookId));
        } else {
            System.out.println("No borrow requests.");
        }
    }

    public void displayBooks() {
        for (Book book : bookStorage.values()) {
            System.out.println(book);
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        int choice;

        do {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Search Book by Title");
            System.out.println("4. Request Borrow");
            System.out.println("5. Process Borrow Requests");
            System.out.println("6. Display All Books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
            case 1:
                System.out.print("Enter Book ID: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter Book Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter Book Author: ");
                String author = scanner.nextLine();
                library.addBook(id, title, author);
                break;
            case 2:
                System.out.print("Enter Book ID to delete: ");
                int deleteId = scanner.nextInt();
                library.deleteBook(deleteId);
                break;
            case 3:
                System.out.print("Enter Book Title to search: ");
                String searchTitle = scanner.nextLine();
                Book foundBook = library.searchBookByTitle(searchTitle);
                System.out.println(foundBook != null ? foundBook : "Book not found");
                break;
            case 4:
                System.out.print("Enter Book ID to request borrow: ");
                int borrowId = scanner.nextInt();
                library.requestBorrow(borrowId);
                break;
            case 5:
                library.processBorrowRequest();
                break;
            case 6:
                library.displayBooks();
                break;
            case 7:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 7);
        scanner.close();
    }
}
