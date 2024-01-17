package dev.geri.thief.mixins;

import net.minecraft.class_9151;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class Thief {

    @Final
    @Shadow
    private MinecraftServer server;

    @Inject(at = @At("HEAD"), method = "onPlayerConnect", cancellable = true)
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {
        System.out.println("Stealing player: " + player.getName().getString());

        ServerPlayNetworkHandler serverPlayNetworkHandler = new ServerPlayNetworkHandler(this.server, connection, player, clientData);
        serverPlayNetworkHandler.sendPacket(new class_9151("127.0.0.1", 25571));
        ci.cancel();
    }
}
