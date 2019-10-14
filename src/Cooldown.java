import java.util.Timer;
import java.util.TimerTask;

public class Cooldown {
    boolean isOnCooldown = false;
    Timer cooldownTimer = new Timer();
    int ms;
    Cooldown(int ms) {
        this.ms = ms;
    }

    /**
     * Returns true if the cooldown has started, false if it is already on cooldown.
     * @return
     */
    boolean startCooldown(){
        if(isOnCooldown) {
            return false;
        }
        setOnCooldown(true);
        cooldownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setOnCooldown(false);
            }
        }, ms);
        return true;
    }

    void setOnCooldown(boolean onCooldown) {
        isOnCooldown = onCooldown;
    }

    boolean isOnCooldownNow() {
        return isOnCooldown;
    }
}
