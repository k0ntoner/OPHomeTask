package Task1;

public class MyThread extends Thread {
    private Resource resource;
    public MyThread(Resource resource) {
        this.resource = resource;
    }
    @Override
    public void run() {
        try{
            System.out.println("Thread: "+this.getName()+", Start");
            // Блокуємо обʼєкт
            synchronized(resource){
                System.out.println("Thread: "+this.getName()+", block resource");
                sleep(5000);
            }
            System.out.println("Thread: "+this.getName()+", Unblock resource");
        }
        catch (InterruptedException e){
            System.out.println(e);
        }
    }
}
