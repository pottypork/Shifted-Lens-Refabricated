package com.platypushasnohat.shifted_lens.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.sounds.SoundEvents;

import java.util.List;

public class SLMobBucketItem extends MobBucketItem {

    private final EntityType<?> fishType;

    public SLMobBucketItem(EntityType<? extends Mob> entity, Fluid fluid, Item.Properties properties) {
        super(entity, fluid, SoundEvents.BUCKET_EMPTY_FISH, properties);
        this.fishType = entity;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        ChatFormatting[] chatFormatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
        if (this.fishType == EntityType.SQUID) {
            CustomData customData = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            CompoundTag compoundtag = customData.copyTag();
            if (compoundtag.contains("BucketVariantTag", Tag.TAG_INT)) {
                int i = compoundtag.getInt("BucketVariantTag");

                String variant = "shifted_lens.squid.variant" + i;

                MutableComponent mutablecomponent = Component.translatable(variant);
                mutablecomponent.withStyle(chatFormatting);
                components.add(mutablecomponent);
            }
        }
    }
}
