import com.codecool.events.App;

public class Main {

    public static void main(String[] args) {
        try {
            App.run();
            App.getApp().dispatchRoutes();
        } catch (Exception e) {
            System.err.println("Error: " + e.toString());
            System.exit(0);
        }

        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing db connection...");
            App.getApp().closeConnection();
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                System.err.println("Error: " + e.toString());
            }
        }));
    }
}