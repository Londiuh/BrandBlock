package me.londiuh.brandblock.mixin;

import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import static me.londiuh.brandblock.BrandBlock.CONFIG;
import static me.londiuh.brandblock.BrandBlock.LOGGER;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
	@Shadow public ClientConnection connection;
	@Shadow public ServerPlayerEntity player;

	@Inject(method = "onCustomPayload", at = @At("HEAD"))
	public void onCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo ci) {
		if (packet.getChannel().equals(CustomPayloadC2SPacket.BRAND) && CONFIG.isBlockedBrand(packet.getData().readString())) {
			LOGGER.info("[BrandBlock] Kicked {}[{}] because is using a blocked brand", player.getEntityName(), connection.getAddress());
			connection.send(new DisconnectS2CPacket(CONFIG.getKickMsg()));
			connection.disconnect(CONFIG.getKickMsg());
		}
	}
}
