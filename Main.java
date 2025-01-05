import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Collections;

// Базовый класс для всех элементов меню
abstract class MenuItem {
    protected String name;    // Название блюда
    protected double price;   // Цена блюда

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void display() {
        System.out.println(name + " - " + price + " руб.");
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    // Метод клонирования
    public abstract MenuItem cloneItem();
}

// Класс для главного блюда
class MainDish extends MenuItem {
    public MainDish(String name, double price) {
        super(name, price);
    }

    @Override
    public void display() {
        System.out.print("[Главное блюдо] ");
        super.display();
    }

    @Override
    public MenuItem cloneItem() {
        return new MainDish(this.name, this.price);
    }
}

// Класс для закусок
class Appetizer extends MenuItem {
    public Appetizer(String name, double price) {
        super(name, price);
    }

    @Override
    public void display() {
        System.out.print("[Закуска] ");
        super.display();
    }

    @Override
    public MenuItem cloneItem() {
        return new Appetizer(this.name, this.price);
    }
}

// Класс для напитков
class Drink extends MenuItem {
    public Drink(String name, double price) {
        super(name, price);
    }

    @Override
    public void display() {
        System.out.print("[Напиток] ");
        super.display();
    }

    @Override
    public MenuItem cloneItem() {
        return new Drink(this.name, this.price);
    }
}

// Класс для десертов
class Dessert extends MenuItem {
    public Dessert(String name, double price) {
        super(name, price);
    }

    @Override
    public void display() {
        System.out.print("[Десерт] ");
        super.display();
    }

    @Override
    public MenuItem cloneItem() {
        return new Dessert(this.name, this.price);
    }
}

// Класс для обработки заказа
class Order implements Cloneable {
    List<MenuItem> items = new ArrayList<>();  // Список заказанных позиций
    private static int orderCount = 0;  // Статическое поле для подсчета заказов
    private int orderNumber; // Номер текущего заказа

    public Order() {
        this.orderNumber = ++orderCount; // Инкрементируем счетчик заказов при создании нового объекта
    }

    public void addItem(MenuItem item) {
        items.add(item);
        sortItemsByPrice();  // Сортировка списка товаров по цене после добавления нового
    }

    // Метод для сортировки блюд по цене
    private void sortItemsByPrice() {
        Collections.sort(items, new Comparator<MenuItem>() {
            @Override
            public int compare(MenuItem item1, MenuItem item2) {
                return Double.compare(item1.getPrice(), item2.getPrice());  // Сортировка по цене
            }
        });
    }

    public void displayOrder() {
        double total = 0;
        System.out.println("\nВаш заказ #" + orderNumber + ":");
        for (MenuItem item : items) {
            item.display();
            total += item.getPrice();
        }
        System.out.println("Общая сумма: " + total + " руб.");
    }

    // Статический метод для получения количества заказов
    public static int getOrderCount() {
        return orderCount;
    }

    // Метод для очистки текущего заказа
    public void clearOrder() {
        items.clear();
    }

    // Получить номер заказа
    public int getOrderNumber() {
        return orderNumber;
    }

    // Метод для клонирования заказа (глубокое клонирование)
    public Order cloneOrder() {
        Order clonedOrder = new Order();
        for (MenuItem item : items) {
            clonedOrder.addItem(item.cloneItem());
        }
        return clonedOrder;
    }
}

public class Main {
    static List<Order> orderHistory = new ArrayList<>(); // История заказов

    // Метод для отображения главного меню
    public static void displayMenu() {
        System.out.println("\nВыберите категорию меню:");
        System.out.println("1. Главное блюдо");
        System.out.println("2. Закуски");
        System.out.println("3. Напитки");
        System.out.println("4. Десерты");
        System.out.println("5. Завершить заказ");
        System.out.println("6. Клонировать предыдущий заказ");
        System.out.println("7. Выбрать из истории заказов для клонирования");
        System.out.println("8. Поиск продукта по названию в заказе");
        System.out.println("9. Выйти из программы");
        System.out.print("Введите номер категории: ");
    }

    public static void main(String[] args) {
        

        Scanner scanner = new Scanner(new InputStreamReader(System.in, Charset.forName("Cp866")));
        // Создание объектов для главных блюд
        MainDish[] mainDishes = {
            new MainDish("Борщ", 150),
            new MainDish("Стейк", 300),
            new MainDish("Пельмени", 180),
            new MainDish("Ризотто", 220)
        };

        // Создание объектов для закусок
        Appetizer[] appetizers = {
            new Appetizer("Салат Цезарь", 120),
            new Appetizer("Оливье", 100),
            new Appetizer("Блины с икрой", 150),
            new Appetizer("Тосты с авокадо", 110)
        };

        // Создание объектов для напитков
        Drink[] drinks = {
            new Drink("Кола", 50),
            new Drink("Минеральная вода", 40),
            new Drink("Сок апельсиновый", 70),
            new Drink("Чай черный", 60)
        };

        // Создание объектов для десертов
        Dessert[] desserts = {
            new Dessert("Торт Наполеон", 80),
            new Dessert("Мороженое", 60),
            new Dessert("Чизкейк", 120),
            new Dessert("Пирог с яблоками", 90)
        };

        boolean exit = false;
        Order currentOrder = null;

        while (!exit) {
            displayMenu();
            int category = scanner.nextInt();

            switch (category) {
                case 1: {
                    // Главное блюдо
                    displayMainDishes();
                    System.out.print("Выберите главное блюдо (1-4): ");
                    int choice = scanner.nextInt();
                    if (choice >= 1 && choice <= 4) {
                        if (currentOrder == null) currentOrder = new Order();
                        currentOrder.addItem(mainDishes[choice - 1]);
                    } else {
                        System.out.println("Некорректный выбор!");
                    }
                    break;
                }
                case 2: {
                    // Закуски
                    displayAppetizers();
                    System.out.print("Выберите закуску (1-4): ");
                    int choice = scanner.nextInt();
                    if (choice >= 1 && choice <= 4) {
                        if (currentOrder == null) currentOrder = new Order();
                        currentOrder.addItem(appetizers[choice - 1]);
                    } else {
                        System.out.println("Некорректный выбор!");
                    }
                    break;
                }
                case 3: {
                    // Напитки
                    displayDrinks();
                    System.out.print("Выберите напиток (1-4): ");
                    int choice = scanner.nextInt();
                    if (choice >= 1 && choice <= 4) {
                        if (currentOrder == null) currentOrder = new Order();
                        currentOrder.addItem(drinks[choice - 1]);
                    } else {
                        System.out.println("Некорректный выбор!");
                    }
                    break;
                }
                case 4: {
                    // Десерты
                    displayDesserts();
                    System.out.print("Выберите десерт (1-4): ");
                    int choice = scanner.nextInt();
                    if (choice >= 1 && choice <= 4) {
                        if (currentOrder == null) currentOrder = new Order();
                        currentOrder.addItem(desserts[choice - 1]);
                    } else {
                        System.out.println("Некорректный выбор!");
                    }
                    break;
                }
                case 5:
                    // Завершить заказ и перейти к следующему
                    if (currentOrder != null) {
                        currentOrder.displayOrder();
                        orderHistory.add(currentOrder);  // Добавить заказ в историю
                        currentOrder = null; // Очистить текущий заказ
                    } else {
                        System.out.println("Нет текущего заказа!");
                    }
                    break;
                case 6:
                    // Клонировать предыдущий заказ (мелкое клонирование)
                    if (!orderHistory.isEmpty()) {
                        Order lastOrder = orderHistory.get(orderHistory.size() - 1);
                        Order clonedOrder = lastOrder.cloneOrder(); // Клонирование последнего заказа
                        clonedOrder.displayOrder();
                    } else {
                        System.out.println("Нет завершенных заказов для клонирования!");
                    }
                    break;
                case 7:
                    // Выбрать из истории заказов для клонирования (глубокое клонирование)
                    if (!orderHistory.isEmpty()) {
                        System.out.println("Выберите заказ для клонирования:");
                        for (int i = 0; i < orderHistory.size(); i++) {
                            System.out.println(i + 1 + ". Заказ #" + orderHistory.get(i).getOrderNumber());
                            orderHistory.get(i).displayOrder(); // Показать полный заказ
                        }
                        int choice = scanner.nextInt();
                        if (choice >= 1 && choice <= orderHistory.size()) {
                            Order orderToClone = orderHistory.get(choice - 1);
                            Order clonedOrder = orderToClone.cloneOrder(); // Глубокое клонирование
                            clonedOrder.displayOrder();
                        } else {
                            System.out.println("Некорректный выбор!");
                        }
                    } else {
                        System.out.println("Нет заказов для клонирования!");
                    }
                    break;
                case 8:{
                    // Поиск продукта по названию в текущем заказе
                    if (currentOrder != null && !currentOrder.items.isEmpty()) {
                        System.out.print("Введите название продукта для поиска в заказе: ");
                        scanner.nextLine();  // Очищаем буфер, если был предыдущий ввод
                        String searchName = scanner.nextLine();  // Считываем полную строку для поиска
                    
                        // Поиск продукта в списке заказанных товаров
                        boolean found = false;
                        for (MenuItem item : currentOrder.items) {
                            if (item.getName().toLowerCase().contains(searchName.toLowerCase())) {
                                item.display();  // Выводим найденный продукт
                                found = true;
                            }
                        }
                        if (!found) {
                            System.out.println("Продукт не найден в текущем заказе!");
                        }
                    } else {
                        System.out.println("Нет текущего заказа или заказ пуст!");
                    }
                    break;
                }
                case 9:
                    // Выход из программы
                    exit = true;
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    break;
            }
        }

        // Вывод количества заказов после выхода из программы
        System.out.println("Всего сделано заказов: " + Order.getOrderCount());
    }

    // Методы для отображения блюд
    public static void displayMainDishes() {
        System.out.println("\nГлавные блюда:");
        System.out.println("1. Борщ - 150 руб.");
        System.out.println("2. Стейк - 300 руб.");
        System.out.println("3. Пельмени - 180 руб.");
        System.out.println("4. Ризотто - 220 руб.");
    }

    public static void displayAppetizers() {
        System.out.println("\nЗакуски:");
        System.out.println("1. Салат Цезарь - 120 руб.");
        System.out.println("2. Оливье - 100 руб.");
        System.out.println("3. Блины с икрой - 150 руб.");
        System.out.println("4. Тосты с авокадо - 110 руб.");
    }

    public static void displayDrinks() {
        System.out.println("\nНапитки:");
        System.out.println("1. Кола - 50 руб.");
        System.out.println("2. Минеральная вода - 40 руб.");
        System.out.println("3. Сок апельсиновый - 70 руб.");
        System.out.println("4. Чай черный - 60 руб.");
    }

    public static void displayDesserts() {
        System.out.println("\nДесерты:");
        System.out.println("1. Торт Наполеон - 80 руб.");
        System.out.println("2. Мороженое - 60 руб.");
        System.out.println("3. Чизкейк - 120 руб.");
        System.out.println("4. Пирог с яблоками - 90 руб.");
    }
}
