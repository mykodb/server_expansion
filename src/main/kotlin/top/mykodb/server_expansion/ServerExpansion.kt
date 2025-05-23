package top.mykodb.server_expansion

import com.mojang.logging.LogUtils
import net.minecraft.client.gui.screens.Screen
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.neoforge.client.gui.ConfigurationScreen
import net.neoforged.neoforge.client.gui.IConfigScreenFactory
import net.neoforged.neoforge.common.NeoForge
import org.slf4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import top.mykodb.server_expansion.data.DataHandler
import top.mykodb.server_expansion.event.CleanGarbage
import top.mykodb.server_expansion.event.Welcome


const val MODID: String = "server_expansion"
val LOGGER: Logger = LogUtils.getLogger()

@Mod(value = MODID)
class Mod(modBus:IEventBus,container:ModContainer){
    init {
        // register DataGeneration
        MOD_BUS.register(DataHandler())
        // register Event
        NeoForge.EVENT_BUS.register(Welcome)
        NeoForge.EVENT_BUS.register(CleanGarbage)
        // register Profiles
        container.registerConfig(ModConfig.Type.SERVER, Config.serverSpec,"$MODID/server_config.toml")
    }

}

@Mod(value = MODID, dist = [Dist.CLIENT])
class ModClient(container:ModContainer) {
    init {
        // ModConfigurationInterface
        container.registerExtensionPoint(
            IConfigScreenFactory::class.java,
            IConfigScreenFactory { mod: ModContainer, parent: Screen -> ConfigurationScreen(mod, parent) }
        )
    }
}

