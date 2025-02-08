import java.util.*;
import java.util.concurrent.*;

class Book {
   private String title;
   private String author;
   private String ISBN;
   
   public Book(String title, String author, String ISBN) {
       this.title = title;
       this.author = author;
       this.ISBN = ISBN;
   }
   
   public String getTitle() { return title; }
   public String getAuthor() { return author; }
   public String getISBN() { return ISBN; }
}

class User {
   private String name;
   private String userID;
   private List<Book> borrowedBooks = new ArrayList<>();
   
   public User(String name, String userID) {
       this.name = name;
       this.userID = userID;
   }
   
   public String getName() { return name; }
   public String getUserID() { return userID; }
   public List<Book> getBorrowedBooks() { return borrowedBooks; }
}

interface ILibrary {
   void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException;
   void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;
   void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;
   Book searchBook(String title);
}

abstract class LibrarySystem implements ILibrary {
   protected List<Book> books = new ArrayList<>();
   protected List<User> users = new ArrayList<>();
   
   public abstract void addBook(Book book);
   public abstract void addUser(User user);
}

class LibraryManager extends LibrarySystem {
   private final Map<String, User> userMap = new ConcurrentHashMap<>();
   private final Map<String, Book> bookMap = new ConcurrentHashMap<>();
   
   public synchronized void addBook(Book book) {
       books.add(book);
       bookMap.put(book.getISBN(), book);
   }
   
   public synchronized void addUser(User user) {
       users.add(user);
       userMap.put(user.getUserID(), user);
   }
   
   public synchronized void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException {
       User user = userMap.get(userID);
       if (user == null) throw new UserNotFoundException("User not found");
       Book book = bookMap.get(ISBN);
       if (book == null) throw new BookNotFoundException("Book not found");
       if (user.getBorrowedBooks().size() >= 5) throw new MaxBooksAllowedException("Maximum books limit reached");
       user.getBorrowedBooks().add(book);
   }
   
   public synchronized void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
       User user = userMap.get(userID);
       if (user == null) throw new UserNotFoundException("User not found");
       Book book = bookMap.get(ISBN);
       if (book == null) throw new BookNotFoundException("Book not found");
       user.getBorrowedBooks().remove(book);
   }
   
   public synchronized void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
       if (!bookMap.containsKey(ISBN)) throw new BookNotFoundException("Book not found");
       if (!userMap.containsKey(userID)) throw new UserNotFoundException("User not found");
   }
   
   public Book searchBook(String title) {
       return books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
   }
}

class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) { super(message); }
}

class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) { super(message); }
}

class MaxBooksAllowedException extends Exception {
    public MaxBooksAllowedException(String message) { super(message); }
}

public class LibraryManagementMain {
   public static void main(String[] args) {
       LibraryManager libManager = new LibraryManager();
       libManager.addBook(new Book("Java Programming", "Author A", "1234"));
       libManager.addUser(new User("Alice", "U001"));
       
       ExecutorService executor = Executors.newFixedThreadPool(3);
       executor.execute(() -> {
           try { 
               libManager.borrowBook("1234", "U001");
               System.out.println("Book borrowed successfully");
           } catch (Exception e) { 
               System.out.println(e.getMessage());
           }
       });
       executor.execute(() -> {
           try { 
               libManager.returnBook("1234", "U001"); 
               System.out.println("Book returned successfully");
           } catch (Exception e) { 
               System.out.println(e.getMessage());
           }
       });
       executor.shutdown();
   }
}
