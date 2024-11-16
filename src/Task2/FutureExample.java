package Task2;

import java.util.concurrent.*;

public class FutureExample {
        public static void main(String[] args) {
            //Створюємо ExecutorService, щоб отримати пул потоків
            ExecutorService executorService = Executors.newCachedThreadPool();
            int x = 1, y = 2;
            //Імплементуємо локальний таск за допомогою лямбда виразу (Callable це функціанальний інтерфейс)
            Callable<Integer> task = () -> {
                Thread.sleep(2000);
                return x + y;
            };
            // Розташовуємо такс у future
            Future<Integer> future = executorService.submit(task);
            try {
                System.out.println("Call future...");
                // Викликаємо метод
                int res = future.get();
                System.out.println("Result is: " + res);

                System.out.println("Done");
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                executorService.shutdown();
            }
        }


}
