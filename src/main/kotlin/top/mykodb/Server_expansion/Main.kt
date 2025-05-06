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
import top.mykodb.server_expansion.event.EventHandler


const val MODID: String = "server_expansion"
val LOGGER: Logger = LogUtils.getLogger()

@Mod(value = MODID)
class Mod(modBus:IEventBus,container:ModContainer){
    init {
        // 注册 数据生成
        MOD_BUS.register(DataHandler())
        // 注册 事件
        NeoForge.EVENT_BUS.register(EventHandler)
        NeoForge.EVENT_BUS.register(CleanGarbage)
        // 注册 配置文件
        container.registerConfig(ModConfig.Type.SERVER, Config.serverSpec)
    }
}

@Mod(value = MODID, dist = [Dist.CLIENT])
class ModClient(container:ModContainer) {
    init {

        // 模组配置界面
        container.registerExtensionPoint(
            IConfigScreenFactory::class.java,
            IConfigScreenFactory { mod: ModContainer, parent: Screen -> ConfigurationScreen(mod, parent) }
        )
    }
}
