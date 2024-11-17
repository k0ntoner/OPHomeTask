# Individual Assignment: Blocking Requests, `Future`, and Unix Sockets in Java

## Author Information
- **Name**: Andrii Anatoliiovych Kot  
- **Group**: IPS-32  
- **University**: Taras Shevchenko National University of Kyiv  
- **Faculty**: Computer Science and Cybernetics  
- **Department**: Intelligent Software Systems  
- **Year**: 2024  

---

## Task Description
The assignment focused on studying and implementing the following:
1. **Blocking Requests**: Understanding how threads behave when blocked until an operation is completed.
2. **`Future` Mechanism**: Utilizing asynchronous operations in Java to optimize task execution.
3. **Unix Sockets**: Implementing inter-process communication (IPC) for exchanging data locally using the file system.

---

## Implementation Details

### **1. Blocking Requests**

- **Overview**: A blocking request halts the thread until the operation is completed. This simplifies programming logic but can negatively affect performance in multithreaded systems.
- **Code Example**:
    ```java
    public static void main(String[] args) {
        Resource resource = new Resource();
        MyThread thread1 = new MyThread(resource);
        MyThread thread2 = new MyThread(resource);

        thread1.start();
        thread2.start();
    }

    class MyThread extends Thread {
        private final Resource resource;

        MyThread(Resource resource) {
            this.resource = resource;
        }

        @Override
        public void run() {
            synchronized (resource) {
                System.out.println("Thread " + this.getName() + " is blocking resource.");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread " + this.getName() + " has unblocked resource.");
            }
        }
    }
    ```

---

### **2. `Future` Mechanism**

- **Overview**: The `Future` class in Java allows asynchronous task execution, enabling non-blocking operations in the main thread.
- **Key Methods**:
  - `get()`: Waits for the task result (blocking).
  - `isDone()`: Checks if the task is complete.
  - `cancel()`: Cancels the task execution.
- **Code Example**:
    ```java
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Callable<Integer> task = () -> {
            Thread.sleep(2000);
            return 1 + 2;
        };

        Future<Integer> future = executorService.submit(task);

        try {
            System.out.println("Waiting for result...");
            System.out.println("Result: " + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
    ```

---

### **3. Unix Sockets**

- **Overview**: Unix sockets enable fast and secure communication between processes on the same machine using file paths instead of IP addresses.
- **Server Code Example**:
    ```java
    public static void main(String[] args) {
        try (UnixServerSocketChannel serverChannel = UnixServerSocketChannel.open()) {
            UnixSocketAddress address = new UnixSocketAddress(new File("/tmp/unix_socket_java"));
            serverChannel.bind(address);
            System.out.println("Server is waiting for a client...");

            try (UnixSocketChannel clientChannel = serverChannel.accept()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (true) {
                    buffer.clear();
                    int bytesRead = clientChannel.read(buffer);
                    if (bytesRead == -1) break;

                    buffer.flip();
                    String message = new String(buffer.array(), 0, buffer.limit());
                    System.out.println("Received: " + message);

                    if ("End".equalsIgnoreCase(message.trim())) break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ```
- **Client Code Example**:
    ```java
    public static void main(String[] args) {
        UnixSocketAddress address = UnixSocketAddress.of("/tmp/unix_socket_java");

        try (UnixSocketChannel socketChannel = UnixSocketChannel.open(address)) {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String line;

            while (!(line = userInput.readLine()).equalsIgnoreCase("End")) {
                ByteBuffer buffer = ByteBuffer.wrap(line.getBytes());
                socketChannel.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ```

---

## Conclusion
- **Key Learnings**:
  - Blocking requests simplify implementation but may reduce performance.
  - `Future` supports efficient multithreading through asynchronous execution.
  - Unix sockets provide a fast IPC mechanism, outperforming traditional network sockets in local communication.
- **Outcome**: Server and client applications were developed to demonstrate these concepts using Java.
