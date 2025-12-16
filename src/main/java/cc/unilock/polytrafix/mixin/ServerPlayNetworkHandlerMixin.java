package cc.unilock.polytrafix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pw.lakuna.elytra_trinket.ServerTools;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	public ServerPlayerEntity player;
	@Inject(method = "onUpdatePlayerAbilities", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/server/world/ServerWorld;)V", shift = At.Shift.AFTER), cancellable = true)
	private void onUpdatePlayerAbilities(UpdatePlayerAbilitiesC2SPacket packet, CallbackInfo ci) {
		if (this.player.getAbilities().allowFlying && this.player.getAbilities().flying != packet.isFlying()) {
			if (ServerTools.isElytraTrinketEquipped(player)) {
				this.player.getAbilities().allowFlying = false;
				this.player.getAbilities().flying = false;
				this.player.sendAbilitiesUpdate();

				this.player.startGliding();
				ci.cancel();
			}
		}
	}

	@Inject(method = "onPlayerMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"))
	private void onPlayerMove(PlayerMoveC2SPacket packet, CallbackInfo ci, @Local(ordinal = 1) boolean isMovingUpwards) {
		if (ServerTools.isElytraTrinketEquipped(this.player)) {
			if (!this.player.isClimbing() && !this.player.isGliding()) {
				if (isMovingUpwards) {
					this.player.getAbilities().allowFlying = true;
					this.player.sendAbilitiesUpdate();
				}
			}
		}
		if (this.player.isGliding() || (!(this.player.isCreative() || this.player.isSpectator()) && this.player.isOnGround())) {
			this.player.getAbilities().allowFlying = false;
			this.player.sendAbilitiesUpdate();
		}
	}
}
