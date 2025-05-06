package top.mykodb.server_expansion.data

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.data.event.GatherDataEvent
import top.mykodb.server_expansion.MODID

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
class DataHandler {
    @SubscribeEvent
    fun onGatherData(event: GatherDataEvent) {
        val generator = event.generator
        val output = event.generator.packOutput

        // 添加语言文件生成器
        generator.addProvider(event.includeClient(), LangProvider.EnUs(output))
        generator.addProvider(event.includeClient(), LangProvider.ZhCn(output))

    }
}