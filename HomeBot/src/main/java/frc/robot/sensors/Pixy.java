package frc.robot.sensors;

import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.Pixy2Video;
import io.github.pseudoresonance.pixy2api.Pixy2Video.RGB;
import edu.wpi.first.wpilibj.DriverStation;

import java.util.ArrayList;

import frc.robot.consoles.Logger;
import frc.robot.BotSensors;

public class Pixy {

    private static String detectedColor;
    private static RGB rgb;
    private static final int blockSignature = 1;

    public static int colorCounter = 0;
    public static String colorMode;

    public static String detectColor() {
        Pixy2CCC ccc = BotSensors.pixy.getCCC();
        int blockCount = ccc.getBlocks(true, Pixy2CCC.CCC_SIG7, 1);
        if (blockCount <= 0) {
            Logger.problem("Pixy -> detectColor -> No block count");
        }

        ArrayList<Block> blocks = ccc.getBlocks();
        Block largestBlock = null;
        if (blocks == null) {
            Logger.info("Pixy -> detectColor -> No blocks");
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
        // Pixy2Video video = BotSensors.pixy.getVideo();
    }
}