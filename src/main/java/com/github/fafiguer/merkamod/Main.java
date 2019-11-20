package com.github.fafiguer.merkamod;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(Main.MODID)
public class Main{
    public static final String MODID = "merkamod";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public Main(){
        LOGGER.debug("No es un pan!");
    }
}
