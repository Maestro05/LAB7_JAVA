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

// Базовый класс для книги
class Book extends AbstractBook implements Cloneable {
    protected int year;
    protected int copiesAvailable;

    public Book(String title, Author author, Category category, int year, int copiesAvailable) {
        super(title, author, category);
        this.year = year;
        this.copiesAvailable = copiesAvailable;
    }

    public void print() {
        System.out.println("Книга: " + title + ", Год: " + year + ", Доступных копий: " + copiesAvailable);
        author.print();
        category.print();
    }

    @Override
    public void getDetails() {
        System.out.println("Печатная книга: " + title + ", Год: " + year + ", Доступных копий: " + copiesAvailable);
    }

    @Override
    public Book clone() throws CloneNotSupportedException {
        Book clonedBook = (Book) super.clone();
        clonedBook.author = this.author.clone();
        clonedBook.category = this.category.clone();
        return clonedBook; // Мелкое клонирование
    }

    // Глубокое клонирование
    public Book deepClone() {
        try {
            Author clonedAuthor = this.author.clone();
            Category clonedCategory = this.category.clone();
            return new Book(this.title, clonedAuthor, clonedCategory, this.year, this.copiesAvailable);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

// Производный класс для электронной книги
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

    // Перегрузка метода print без вызова метода базового класса
    public void printSimple() {
        System.out.println("Электронная книга: " + title + ", Формат: " + fileFormat + ", Размер файла: " + fileSize + " MB");
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

// Интерфейс для клонирования книг
interface ClonableBook {
    Book clone();  // Метод клонирования
}

// Главный класс программы
public class Main {
    public static void main(String[] args) {
        // Создание автора и категории
        Author author = new Author("John", "Doe", "01.01.1980");
        Category category = new Category("Programming", "Books about software development");

        // Создание обычной книги
        Book originalBook = new Book("Java Programming", author, category, 2023, 10);
        originalBook.print();

        // Мелкое клонирование
        try {
            Book shallowClonedBook = originalBook.clone();
            System.out.println("\nМелкое клонирование:");
            shallowClonedBook.print();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        // Глубокое клонирование
        Book deepClonedBook = originalBook.deepClone();
        System.out.println("\nГлубокое клонирование:");
        deepClonedBook.print();

        // Создание электронной книги
        EBook eBook = new EBook("Advanced Java", author, category, 2022, 5, "EPUB", 2.5);
        System.out.println("\nЭлектронная книга (с вызовом метода базового класса):");
        eBook.print();

        // Перегрузка метода print
        System.out.println("\nЭлектронная книга (без вызова метода базового класса):");
        eBook.printSimple();

        // Демонстрация использования абстрактного класса
        AbstractBook printedBook = new Book("Printed Java", author, category, 2021, 15);
        System.out.println("\nАбстрактная книга:");
        printedBook.getDetails();
    }
}
