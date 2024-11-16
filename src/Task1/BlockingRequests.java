package Task1;

public class BlockingRequests {
    public static void main(String[] args){
        //Створюємо довільний обʼєкт, який буде блокуватися
        Resource resource = new Resource();
        //Розміщуємо обʼєкт в двух потоках
        MyThread myThread1 = new MyThread(resource);
        MyThread myThread2 = new MyThread(resource);
        //Запускаємо
        myThread1.start();
        myThread2.start();
    }
}
