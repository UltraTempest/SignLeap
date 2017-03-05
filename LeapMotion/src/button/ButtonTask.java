package button;

import java.util.TimerTask;

public class ButtonTask extends TimerTask {    
    private static ButtonTask instance = new ButtonTask();

    public ButtonTask() {}    
    public static ButtonTask getInstance() {
        return instance;
    }

    @Override
    public void run() {
        System.out.println("In run method");
    }
}