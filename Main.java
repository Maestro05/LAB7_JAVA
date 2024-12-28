import java.util.*;

// Классы Author и Category (исходные классы)
class Author implements Cloneable {
    private String name;
    private String surname;
    private String birthdate;

    public Author(String name, String surname, String birthdate) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void print() {
        System.out.println("Автор: " + name + " " + surname + ", Дата рождения: " + birthdate);
    }

    @Override
    public Author clone() throws CloneNotSupportedException {
        return (Author) super.clone(); // Мелкое клонирование
    }
}

// Класс для категории книги
class Category implements Cloneable {
    private String name;
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void print() {
        System.out.println("Категория: " + name + ", Описание: " + description);
    }

    @Override
    public Category clone() throws CloneNotSupportedException {
        return (Category) super.clone(); // Мелкое клонирование
    }
}

// Статус книги
enum BookStatus {
    AVAILABLE, CHECKED_OUT, RESERVED
}

// Абстрактный класс для книги
abstract class AbstractBook {
    protected String title;
    protected Author author;
    protected Category category;

    public AbstractBook(String title, Author author, Category category) {
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public abstract void getDetails();  // Каждая книга должна предоставить детали
}

// Класс книги (печатной книги)
class Book extends AbstractBook implements Cloneable {
    protected int year;
    protected int copiesAvailable;
    protected BookStatus status;  // Статус книги

    public Book(String title, Author author, Category category, int year, int copiesAvailable) {
        super(title, author, category);
        this.year = year;
        this.copiesAvailable = copiesAvailable;
        this.status = BookStatus.AVAILABLE;  // По умолчанию книга доступна
    }

    public void print() {
        System.out.println("Книга: " + title + ", Год: " + year + ", Доступных копий: " + copiesAvailable + ", Статус: " + status);
        author.print();
        category.print();
    }

    @Override
    public void getDetails() {
        System.out.println("Печатная книга: " + title + ", Год: " + year + ", Доступных копий: " + copiesAvailable + ", Статус: " + status);
    }

    @Override
    public Book clone() throws CloneNotSupportedException {
        Book clonedBook = (Book) super.clone();
        clonedBook.author = this.author.clone();
        clonedBook.category = this.category.clone();
        return clonedBook; // Мелкое клонирование
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public BookStatus getStatus() {
        return status;
    }

    public int getYear() {
        return year;
    }

    public void decreaseCopies() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    public void increaseCopies() {
        copiesAvailable++;
    }
}

// Класс для электронной книги
class EBook extends Book {
    private String fileFormat;
    private double fileSize; // Размер файла в мегабайтах

    public EBook(String title, Author author, Category category, int year, int copiesAvailable, String fileFormat, double fileSize) {
        super(title, author, category, year, copiesAvailable);
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;
    }

    @Override
    public void print() {
        super.print(); // Вызов метода базового класса
        System.out.println("Формат файла: " + fileFormat + ", Размер файла: " + fileSize + " MB");
    }

    @Override
    public void getDetails() {
        System.out.println("Электронная книга: " + title + ", Формат: " + fileFormat + ", Размер файла: " + fileSize + " MB");
    }

    @Override
    public EBook clone() throws CloneNotSupportedException {
        EBook clonedEBook = (EBook) super.clone();
        return clonedEBook; // Мелкое клонирование
    }
}

// Менеджер библиотеки
class LibraryManager {
    private List<Book> booksList;
    private Map<String, Book> booksMap;  // По названию книги

    public LibraryManager() {
        booksList = new ArrayList<>();
        booksMap = new HashMap<>();
    }

    // Добавление книги
    public void addBook(Book book) {
        booksList.add(book);
        booksMap.put(book.title, book);
    }

    // Поиск книги по названию
    public Book searchBookByTitle(String title) {
        return booksMap.get(title);
    }

    // Сортировка книг по году
    public void sortBooksByYear() {
        booksList.sort(Comparator.comparingInt(Book::getYear));
    }

    // Вывод всех книг
    public void printAllBooks() {
        if (booksList.isEmpty()) {
            System.out.println("В библиотеке нет книг.");
        } else {
            for (Book book : booksList) {
                book.print();
            }
        }
    }

    // Поиск книги по автору
    public void searchBooksByAuthor(String authorName) {
        boolean found = false;
        for (Book book : booksList) {
            if (book.author.getName().equalsIgnoreCase(authorName)) {
                book.print();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Книги автора \"" + authorName + "\" не найдены.");
        }
    }

    // Выдача книги
    public void checkoutBook(String title) {
        Book book = searchBookByTitle(title);
        if (book != null) {
            if (book.getStatus() == BookStatus.AVAILABLE) {
                book.setStatus(BookStatus.CHECKED_OUT);
                book.decreaseCopies();
                System.out.println("Книга \"" + title + "\" успешно выдана.");
            } else {
                System.out.println("Книга \"" + title + "\" недоступна для выдачи.");
            }
        } else {
            System.out.println("Книга с таким названием не найдена.");
        }
    }

    // Возврат книги
    public void returnBook(String title) {
        Book book = searchBookByTitle(title);
        if (book != null) {
            if (book.getStatus() == BookStatus.CHECKED_OUT) {
                book.setStatus(BookStatus.AVAILABLE);
                book.increaseCopies();
                System.out.println("Книга \"" + title + "\" успешно возвращена.");
            } else {
                System.out.println("Книга \"" + title + "\" не была выдана.");
            }
        } else {
            System.out.println("Книга с таким названием не найдена.");
        }
    }

    // Резервация книги
    public void reserveBook(String title) {
        Book book = searchBookByTitle(title);
        if (book != null) {
            if (book.getStatus() == BookStatus.AVAILABLE) {
                book.setStatus(BookStatus.RESERVED);
                System.out.println("Книга \"" + title + "\" успешно зарезервирована.");
            } else {
                System.out.println("Книга \"" + title + "\" недоступна для резервации.");
            }
        } else {
            System.out.println("Книга с таким названием не найдена.");
        }
    }
}

// Главный класс программы
public class Main {
    public static void main(String[] args) {
        // Создание автора и категории
        Author author = new Author("John", "Doe", "01.01.1980");
        Category category = new Category("Programming", "Books about software development");

        // Создание книг
        Book originalBook = new Book("Java Programming", author, category, 2023, 10);
        EBook eBook = new EBook("Advanced Java", author, category, 2022, 5, "EPUB", 2.5);
        Book anotherBook = new Book("Java for Beginners", author, category, 2021, 12);
        EBook ebook2 = new EBook("Mastering Java", author, category, 2023, 7, "PDF", 1.8);

        // Создание менеджера библиотеки
        LibraryManager libraryManager = new LibraryManager();

        // Добавление книг в библиотеку
        libraryManager.addBook(originalBook);
        libraryManager.addBook(eBook);
        libraryManager.addBook(anotherBook);
        libraryManager.addBook(ebook2);

        // Пример взаимодействия с библиотекой
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nМеню:");
            System.out.println("1. Показать все книги");
            System.out.println("2. Поиск книги по названию");
            System.out.println("3. Выдать книгу");
            System.out.println("4. Вернуть книгу");
            System.out.println("5. Резервировать книгу");
            System.out.println("6. Сортировка книг по году");
            System.out.println("7. Выход");

            System.out.print("Выберите действие: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Чтобы захватить оставшийся символ новой строки

            switch (choice) {
                case 1:
                    libraryManager.printAllBooks();
                    break;
                case 2:
                    System.out.print("Введите название книги для поиска: ");
                    String searchTitle = scanner.nextLine();
                    Book foundBook = libraryManager.searchBookByTitle(searchTitle);
                    if (foundBook != null) {
                        foundBook.print();
                    } else {
                        System.out.println("Книга с таким названием не найдена.");
                    }
                    break;
                case 3:
                    System.out.print("Введите название книги для выдачи: ");
                    String checkoutTitle = scanner.nextLine();
                    libraryManager.checkoutBook(checkoutTitle);
                    break;
                case 4:
                    System.out.print("Введите название книги для возврата: ");
                    String returnTitle = scanner.nextLine();
                    libraryManager.returnBook(returnTitle);
                    break;
                case 5:
                    System.out.print("Введите название книги для резервации: ");
                    String reserveTitle = scanner.nextLine();
                    libraryManager.reserveBook(reserveTitle);
                    break;
                case 6:
                    libraryManager.sortBooksByYear();
                    System.out.println("Книги отсортированы по году.");
                    break;
                case 7:
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        } while (choice != 7);

        scanner.close();
    }
}
