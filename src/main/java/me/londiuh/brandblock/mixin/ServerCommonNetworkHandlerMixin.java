package me.londiuh.brandblock.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ServerCommonPacketListener;
import net.minecraft.network.packet.BrandCustomPayload;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import net.minecraft.text.Text;

import static me.londiuh.brandblock.BrandBlock.CONFIG;
import static me.londiuh.brandblock.BrandBlock.LOGGER;

@Mixin(ServerCommonNetworkHandler.class)
public abstract class ServerCommonNetworkHandlerMixin implements ServerCommonPacketListener {
	@Shadow
	@Final
	protected ClientConnection connection;

	@Shadow
	protected abstract GameProfile getProfile();

	@Shadow
	public abstract void disconnect(Text reason);

	@Inject(at = @At("HEAD"), method = "onCustomPayload")
	private void onCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo ci) {
		if (packet.payload() instanceof BrandCustomPayload payload && CONFIG.isBlockedBrand(payload.brand())) {
			LOGGER.info("Disconnecting {} due to use of a blocked brand", this.getProfile().getName());
			// FIXME: For some reason in 1.20.2-1.20.4 if the player is disconected during
			// the configuration phase, a generic "Disconnected" is shown, instead of the
			// disconection reason sent
			this.disconnect(CONFIG.getKickMsg());
		}
	}
}