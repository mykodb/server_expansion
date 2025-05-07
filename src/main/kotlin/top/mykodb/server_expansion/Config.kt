package top.mykodb.server_expansion

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.config.ModConfigEvent
import net.neoforged.fml.event.config.ModConfigEvent.Reloading
import net.neoforged.neoforge.common.ModConfigSpec
import org.apache.commons.lang3.tuple.Pair

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
object Config {
    class Server(builder:ModConfigSpec.Builder){
        // 是否启用欢迎语
        val enableWelcome: ModConfigSpec.BooleanValue = builder
            .comment("是否启用服务器欢迎语")
            .define(listOf("welcome","enable_welcome"), true)
        // 设置 欢迎语
        val welcomeList: ModConfigSpec.ConfigValue<List<String>> = builder
            .defineListAllowEmpty(
                listOf("welcome","welcomeList"),
                listOf(
                    "欢迎 §c[player]§r 小主，小狐想你啦！"
                ),
                {""},
                { it is String}
            )

        val enableCleanup: ModConfigSpec.BooleanValue = builder
            .comment("是否启用掉落物清理")
            .define(listOf("cleanup","enable_cleanup"), true)

        val cleanupInterval: ModConfigSpec.ConfigValue<Int> = builder
            .comment("清理间隔(tick)")
            .defineInRange(listOf("cleanup","cleanup_interval"),1200,1,Int.MAX_VALUE )
        //黑名单
        val cleanupBlacklist: ModConfigSpec.ConfigValue<List<String>> = builder
            .defineListAllowEmpty(//清理垃圾
                listOf("cleanup","cleanup_blacklist"),
                listOf(
                    "minecraft:dragon_egg"
                )
                ,
                {""},
                { it is String && it.count { c -> c == ':' } == 1 && it.indexOf(':').let { idx -> idx > 0 && idx < it.length - 1 } }
            )
    }

    private var serverPair: Pair<Server, ModConfigSpec> = ModConfigSpec.Builder().configure(::Server)
    val serverSpec: ModConfigSpec = serverPair.getRight()
    val SERVER: Server = serverPair.getLeft()

    var enableWelcome: Boolean  = true
    var enableCleanup: Boolean = true
    var cleanupInterval: Int = 0
    var welcomeList: List<String> = emptyList()
    var cleanupBlacklist: Set<Item> = setOf()

    //缓存
    fun updateCache(){
        enableWelcome = SERVER.enableWelcome.get()
        cleanupInterval = SERVER.cleanupInterval.get()
        enableCleanup = SERVER.enableCleanup.get()
        welcomeList = SERVER.welcomeList.get()
        cleanupBlacklist = parseItemSet(SERVER.cleanupBlacklist.get())

    }
    private fun parseItemSet(ids: List<String>): Set<Item> = ids.mapNotNull { id ->
        id.split(":", limit = 2).takeIf { it.size == 2 }
            ?.let { (namespace, path) ->
                ResourceLocation.tryParse("$namespace:$path")
                    ?.let { BuiltInRegistries.ITEM.get(it) }
                    ?.takeIf { it != Items.AIR }
            }
    }.toSet()

    @SubscribeEvent
    fun onLoad(event: ModConfigEvent.Loading) {
        updateCache()
    }
    @SubscribeEvent
    fun onFileChange(event: Reloading) {
        updateCache()
    }
}