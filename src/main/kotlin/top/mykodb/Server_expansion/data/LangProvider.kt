package top.mykodb.server_expansion.data

import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.LanguageProvider
import top.mykodb.server_expansion.MODID

object LangProvider{

    val view_garbage = "$MODID.view_garbage"
    val garbage_bags = "$MODID.garbage_bags"
    val cleanup_stats = "$MODID.cleanup_stats"
    val no_garbage = "$MODID.no_garbage"
    val is30SecondsBeforeCleanup = "$MODID.is30SecondsBeforeCleanup"
    val is30SecondsBeforeCleanupBundle = "$MODID.is30SecondsBeforeCleanupBundle"
    val isCleanupBundleTime = "$MODID.isCleanupBundleTime"
    val configuration = "$MODID.configuration"

    class EnUs(output: PackOutput): LanguageProvider(output, MODID,"en_us") {
        override fun addTranslations() {

        }
    }
    class ZhCn(output: PackOutput): LanguageProvider(output, MODID,"zh_cn") {
        override fun addTranslations() {
            add("$configuration.welcome","欢迎语")
            add("$configuration.enable_welcome","启用 欢迎语")
            add("$configuration.welcomeList","设置 欢迎语")
            add("$configuration.cleanup","掉落物清理")
            add("$configuration.enable_cleanup","启用 掉落物清理")
            add("$configuration.cleanup_interval","清理间隔")
            add("$configuration.cleanup_blacklist","清理黑名单")
            add(isCleanupBundleTime,"垃圾袋被清理了！")
            add(is30SecondsBeforeCleanupBundle,"警告:30秒后将清理垃圾袋。")
            add(is30SecondsBeforeCleanup,"警告:30秒后将清理所有掉落物!")
            add(no_garbage,"清理完成，没有垃圾哦！")
            add(cleanup_stats,"清理物品 %d 组，累计物品 %d 个；清理用时 %.2f 毫秒。")
            add(garbage_bags,"垃圾袋")
            add(view_garbage,"[查看垃圾]")
        }
    }

}