package opengl.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import javax.annotation.Nonnull;

public class GameLoop {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);

    private static final double TARGET_FPS = 120;
    private static final double TARGET_TIME = 1_000_000_000 / TARGET_FPS;

    public void run(@Nonnull final Function<Long, Boolean> function) {
        // We will need the last update time.
        long lastUpdateTime = System.nanoTime();

        // Simple way of calculating FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1_000_000_000);
        int frameCount = 0;

        boolean running = true;
        while (running) {
            long now = System.nanoTime();

            running = function.apply(now);

            frameCount++;

            lastUpdateTime = now;

            // Update the frame count.
            final int thisSecond = (int) (lastUpdateTime / 1_000_000_000);
            if (thisSecond > lastSecondTime) {
                LOGGER.info("FPS: {}", frameCount);
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            // Yield until it has been at least the target time between renders. This saves the CPU from hogging.
            while (now - lastUpdateTime < TARGET_TIME) {
                Thread.yield();

                try {
                    Thread.sleep(1);
                } catch (final InterruptedException interrupted) {
                    // Ignored.
                }

                now = System.nanoTime();
            }
        }
    }
}
