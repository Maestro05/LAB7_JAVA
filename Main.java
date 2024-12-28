import java.util.*;

// Интерфейс для книг
interface LibraryItem {
    String getTitle();
    String getAuthor();
    int getYear();
    void print();
}

// Класс для автора
class Author {
    private String name;
    private String surname;

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}

// Класс для категории
class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Абстрактный класс для книги
abstract class Book implements LibraryItem, Comparable<Book>, Cloneable {
    protected String title;
    protected Author author;
    protected Category category;  // Теперь категория обязательна
    protected int year;
    protected boolean isAvailable;
    protected boolean isReserved;

    public Book(String title, Author author, Category category, int year) {
        this.title = title;
        this.author = author;
        this.category = category;  // Инициализация категории
        this.year = year;
        this.isAvailable = true;
        this.isReserved = false;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author.getName() + " " + author.getSurname(); }
    public int getYear() { return year; }
    public Category getCategory() { return category; }  // Метод для получения категории

    @Override
    public void print() {
        System.out.println("Книга: " + title + ", Автор: " + getAuthor() + ", Год: " + year + ", Категория: " + category.getName());
    }

    // Реализация интерфейса Comparable для сортировки
    @Override
    public int compareTo(Book other) {
        return this.title.compareTo(other.title); // Сортировка по названию
    }

    // Клонирование
    @Override
    public Book clone() throws CloneNotSupportedException {
        return (Book) super.clone();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}

// Класс для электронной книги (производный класс)
class EBook extends Book {
    private String fileFormat;
    private double fileSize;

    public EBook(String title, Author author, Category category, int year, String fileFormat, double fileSize) {
        super(title, author, category, year);
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Формат файла: " + fileFormat + ", Размер: " + fileSize + " MB");
    }
}

// Класс для библиотеки
class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    // Добавление книги в библиотеку
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Книга \"" + book.getTitle() + "\" добавлена в библиотеку.");
    }

    // Поиск книги по названию
    public Book searchByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Сортировка книг по названию
    public void sortBooks() {
        Collections.sort(books);
        System.out.println("Книги отсортированы.");
    }

    // Резервирование книги
    public boolean reserveBook(Book book) {
        if (book.isAvailable() && !book.isReserved()) {
            book.setReserved(true);
            System.out.println("Книга \"" + book.getTitle() + "\" зарезервирована.");
            return true;
        }
        System.out.println("Книга \"" + book.getTitle() + "\" недоступна для резервирования.");
        return false;
    }

    // Выдача книги
    public boolean lendBook(Book book) {
        if (book.isAvailable() && !book.isReserved()) {
            book.setAvailable(false);
            System.out.println("Книга \"" + book.getTitle() + "\" выдана.");
            return true;
        }
        System.out.println("Книга \"" + book.getTitle() + "\" недоступна для выдачи.");
        return false;
    }

    // Возврат книги
    public boolean returnBook(Book book) {
        if (!book.isAvailable()) {
            book.setAvailable(true);
            book.setReserved(false);  // Снять резерв
            System.out.println("Книга \"" + book.getTitle() + "\" возвращена в библиотеку.");
            return true;
        }
        System.out.println("Книга \"" + book.getTitle() + "\" уже в библиотеке.");
        return false;
    }

    // Вывод всех книг в библиотеке
    public void printBooks() {
        if (books.isEmpty()) {
            System.out.println("Библиотека пуста.");
        } else {
            for (Book book : books) {
                book.print();
            }
        }
    }

    // Вывод книг по категориям
    public void printBooksByCategory(String categoryName) {
        boolean found = false;
        for (Book book : books) {
            if (book.getCategory().getName().equalsIgnoreCase(categoryName)) {
                book.print();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Нет книг в категории \"" + categoryName + "\".");
        }
    }
}

// Главный класс для тестирования программы
public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // Создание авторов и категорий
        Author author1 = new Author("John", "Doe");
        Author author2 = new Author("Jane", "Smith");
        Category category1 = new Category("Programming");
        Category category2 = new Category("Fiction");

        // Создание книг
        Book book1 = new Book("Java Programming", author1, category1, 2023) {};
        Book book2 = new EBook("The Hobbit", author2, category2, 1937, "EPUB", 2.3);

        // Добавление книг в библиотеку
        library.addBook(book1);
        library.addBook(book2);

        // Меню
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Выдать книгу");
            System.out.println("3. Резервировать книгу");
            System.out.println("4. Вернуть книгу");
            System.out.println("5. Найти книгу по названию");
            System.out.println("6. Сортировать книги");
            System.out.println("7. Вывести все книги");
            System.out.println("8. Вывести книги по категории");
            System.out.println("9. Выход");
            System.out.print("Выберите действие: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера ввода

            switch (choice) {
                case 1:
                    // Добавление книги
                    System.out.print("Введите название книги: ");
                    String title = scanner.nextLine();
                    System.out.print("Введите имя автора: ");
                    String authorName = scanner.nextLine();
                    System.out.print("Введите фамилию автора: ");
                    String authorSurname = scanner.nextLine();
                    System.out.print("Введите категорию: ");
                    String category = scanner.nextLine();
                    System.out.print("Введите год издания: ");
                    int year = scanner.nextInt();
                    library.addBook(new Book(title, new Author(authorName, authorSurname), new Category(category), year) {});
                    break;
                case 2:
                    // Выдача книги
                    System.out.print("Введите название книги для выдачи: ");
                    String lendTitle = scanner.nextLine();
                    Book lendBook = library.searchByTitle(lendTitle);
                    if (lendBook != null) {
                        library.lendBook(lendBook);
                    } else {
                        System.out.println("Книга не найдена.");
                    }
                    break;
                case 3:
                    // Резервирование книги
                    System.out.print("Введите название книги для резервирования: ");
                    String reserveTitle = scanner.nextLine();
                    Book reserveBook = library.searchByTitle(reserveTitle);
                    if (reserveBook != null) {
                        library.reserveBook(reserveBook);
                    } else {
                        System.out.println("Книга не найдена.");
                    }
                    break;
                case 4:
                    // Возврат книги
                    System.out.print("Введите название книги для возврата: ");
                    String returnTitle = scanner.nextLine();
                    Book returnBook = library.searchByTitle(returnTitle);
                    if (returnBook != null) {
                        library.returnBook(returnBook);
                    } else {
                        System.out.println("Книга не найдена.");
                    }
                    break;
                case 5:
                    // Поиск книги по названию
                    System.out.print("Введите название книги для поиска: ");
                    String searchTitle = scanner.nextLine();
                    Book foundBook = library.searchByTitle(searchTitle);
                    if (foundBook != null) {
                        foundBook.print();
                    } else {
                        System.out.println("Книга не найдена.");
                    }
                    break;
                case 6:
                    // Сортировка книг
                    library.sortBooks();
                    break;
                case 7:
                    // Вывод всех книг
                    library.printBooks();
                    break;
                case 8:
                    // Вывод книг по категории
                    System.out.print("Введите категорию для поиска книг: ");
                    String categorySearch = scanner.nextLine();
                    library.printBooksByCategory(categorySearch);
                    break;
                case 9:
                    // Выход из программы
                    System.out.println("Выход из программы...");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
                    break;
            }
        } while (choice != 9);

        scanner.close();
    }
}
