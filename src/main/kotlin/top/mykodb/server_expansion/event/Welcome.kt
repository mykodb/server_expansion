package top.mykodb.server_expansion.event

import net.minecraft.network.chat.Component
import net.minecraft.util.RandomSource
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent
import top.mykodb.server_expansion.Config.enableWelcome
import top.mykodb.server_expansion.Config.welcomeList

object Welcome {
    @SubscribeEvent
    fun onLoggedIn(event: PlayerLoggedInEvent) {
        val player = event.entity
        if (player.level().isClientSide && !enableWelcome) { return }

        val list = welcomeList
        if (list.isEmpty()){ return }
        var text: String = welcomeList[0] //默认
        if (welcomeList.size >= 2 ){
            text = welcomeList[RandomSource.create().nextInt(welcomeList.size)]
        }
        player.sendSystemMessage(
            Component.literal(
                text.replace("[player]", player.displayName!!.string)
            )
        )
    }
}







