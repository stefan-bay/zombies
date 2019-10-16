import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a cooldown for an event that should be delayed.
 */
public class Cooldown {
    // True if this cooldown is on cooldown
    boolean isOnCooldown = false;
    // The timer to reopen this cooldown.
    Timer cooldownTimer = new Timer();
    // The delay between uses of this cooldown.
    int ms;

    /**
     * Creates a cooldown.
     * @param ms the delay on this cooldown.
     */
    Cooldown(int ms) {
        this.ms = ms;
    }

    /**
     * try to start the cooldown and return the result.
     * @return true if the cooldown has started, false if it is already on cooldown.
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

    /**
     * Sets this cooldown state.
     * @param onCooldown true if this object should be on cooldown.
     */
    void setOnCooldown(boolean onCooldown) {
        isOnCooldown = onCooldown;
    }
}
