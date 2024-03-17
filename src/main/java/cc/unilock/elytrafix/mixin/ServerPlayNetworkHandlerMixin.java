package cc.unilock.elytrafix.mixin;

import cc.unilock.elytrafix.event.UpdatePlayerAbilitiesCallback;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	public ServerPlayerEntity player;

	@Inject(method = "onUpdatePlayerAbilities", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/server/world/ServerWorld;)V", shift = At.Shift.AFTER), cancellable = true)
	private void onUpdatePlayerAbilities(UpdatePlayerAbilitiesC2SPacket packet, CallbackInfo ci) {
		ci.cancel();
		if (this.player.getAbilities().allowFlying && this.player.getAbilities().flying != packet.isFlying()) {
			if (UpdatePlayerAbilitiesCallback.EVENT.invoker().update(this.player)) {
				this.player.getAbilities().flying = packet.isFlying();
			} else {
				this.player.sendAbilitiesUpdate();
			}
		}
	}
}
