package mod.iridescent.events;

import mod.iridescent.properties.IridescentMiningProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IridescentEventHandler {
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if(event.entity instanceof EntityPlayer && IridescentMiningProperties.get((EntityPlayer)event.entity) == null) {
			IridescentMiningProperties.register((EntityPlayer)event.entity);
		}
	}
	
	@SubscribeEvent
	public void onBlockDestroyed(BlockEvent.BreakEvent event) {
		
		if(event.getPlayer().capabilities.isCreativeMode) {
			return; // Don't bother with the rest if they are in creative mode
		} else {
			IridescentMiningProperties properties = IridescentMiningProperties.get(event.getPlayer());
			
			if(event.state.getBlock() == Blocks.coal_ore) {
				properties.giveExp(1);
			}
			
			if(event.state.getBlock() == Blocks.iron_ore) {
				properties.giveExp(3);
			}
			
			if(event.state.getBlock() == Blocks.gold_ore) {
				properties.giveExp(2);
			}
			
			if(event.state.getBlock() == Blocks.diamond_ore) {
				properties.giveExp(4);
			}
			
			if(event.state.getBlock() == Blocks.redstone_ore || event.state.getBlock() == Blocks.lit_redstone_ore) {
				properties.giveExp(2);
			}
			
			if(event.state.getBlock() == Blocks.lapis_ore) {
				properties.giveExp(2);
			}
		}
	}
	
}