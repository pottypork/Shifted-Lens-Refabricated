package com.platypushasnohat.shifted_lens.events;

import com.platypushasnohat.shifted_lens.ShiftedLensConfig;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;

public class CommonEvents {

    public static void init() {
        UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (player.isSpectator()) {
                return InteractionResult.PASS;
            }

            ItemStack stack = player.getItemInHand(hand);

            if (stack.is(Items.BUCKET) && ((ShiftedLensConfig.MILKABLE_SQUIDS.get() && entity instanceof Squid) || ShiftedLensConfig.MILKABLE_EVERYTHING.get())) {
                player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                ItemStack result = ItemUtils.createFilledResult(stack, player, Items.MILK_BUCKET.getDefaultInstance());
                player.setItemInHand(hand, result);
                return InteractionResult.sidedSuccess(level.isClientSide());
            }

            return InteractionResult.PASS;
        });
    }
}
