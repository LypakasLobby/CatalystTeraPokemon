package com.lypaka.catalystterapokemon;

import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("catalystterapokemon")
public class CatalystTeraPokemon {

    /**
     * TODO
     *
     * Add check for Mega/Dynamax/Z-Moves to prevent a Pokemon nuke
     *
     */

    public static final String MOD_ID = "catalystterapokemon";
    public static final String MOD_NAME = "CatalystTeraPokemon";
    public static final Logger logger = LogManager.getLogger("CatalystTeraPokemon");
    public static BasicConfigManager configManager;

    public CatalystTeraPokemon() throws IOException, ObjectMappingException {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/catalystterapokemon"));
        String[] files = new String[]{"catalystterapokemon.conf"};
        configManager = new BasicConfigManager(files, dir, CatalystTeraPokemon.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        ConfigGetters.load();

    }

}
