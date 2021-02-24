
package frc.robot.sensors;

import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import java.util.ArrayList;

import frc.robot.consoles.Logger;
import frc.robot.BotSensors;

public class Pixy {

    private static final int blockSignature = 1;

    public static int colorCounter = 0;
    public static String colorMode;

    // Pixy
    public static void initializePixy(Pixy2 pixy) {
        int result = pixy.init();
        if (result == Pixy2.PIXY_RESULT_ERROR) {
            Logger.problem("Pixy2 failed to connect!");
        }
        else if (result == Pixy2.PIXY_RESULT_TIMEOUT) {
            Logger.problem("Pixy2 timed out!");
        }
        else if (result < 0) {
            Logger.problem("Pixy2 error code: " + result);
        }
    }

    public static String detectFieldMode() {
        Pixy2CCC ccc = BotSensors.pixy.getCCC();
        int blockCount = ccc.getBlocks(true, Pixy2CCC.CCC_SIG7, 1);
        if (blockCount <= 0) {
            Logger.problem("Pixy -> detectFieldMode -> No block count");
        }

        ArrayList<Block> blocks = ccc.getBlocks();
        Block largestBlock = null;
        if (blocks == null) {
            Logger.info("Pixy -> detectFieldMode -> No blocks");
        }
        for (Block block : blocks) {
            if (block.getSignature() == blockSignature) {
                if (largestBlock == null) {
                    largestBlock = block;
                }
                else if (block.getWidth() > largestBlock.getWidth()) {
                    largestBlock = block;
                }
            }
        }
        int blockX = largestBlock.getX();
        Logger.info("Largest block x = " + blockX);
    
        if (blockX > 0){
            return "left";
        }
        else{
            return "right";
        
        }
    
    }

}
