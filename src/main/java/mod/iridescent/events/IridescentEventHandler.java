package mod.iridescent.events;

import mod.iridescent.properties.packeted.IridescentCraftingProperties;
import mod.iridescent.properties.packeted.IridescentMiningProperties;
import mod.iridescent.properties.packeted.IridescentWoodCuttingProperties;
import mod.iridescent.proxy.ServerProxy;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent.Stop;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class IridescentEventHandler {
	
	// FML
	
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event) {
		if(event.player.capabilities.isCreativeMode) {
			return;
		} else {
			IridescentCraftingProperties properties = IridescentCraftingProperties.get(event.player);
			properties.giveExp(3);
		}
	}
	
	// Forge
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if(event.entity instanceof EntityPlayer) {
			IridescentMiningProperties.register((EntityPlayer)event.entity);
			IridescentCraftingProperties.register((EntityPlayer)event.entity);
			IridescentWoodCuttingProperties.register((EntityPlayer)event.entity);
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			NBTTagCompound data = new NBTTagCompound();
			
			IridescentMiningProperties.get((EntityPlayer)event.entity).saveNBTData(data);
			IridescentCraftingProperties.get((EntityPlayer)event.entity).saveNBTData(data);
			IridescentWoodCuttingProperties.get((EntityPlayer)event.entity).saveNBTData(data);
			
			ServerProxy.storeEntityData(event.entity.getName(), data);
			
			IridescentMiningProperties.get((EntityPlayer)event.entity).saveProxyData((EntityPlayer)event.entity);
			IridescentCraftingProperties.get((EntityPlayer)event.entity).saveProxyData((EntityPlayer)event.entity);
			IridescentWoodCuttingProperties.get((EntityPlayer)event.entity).saveProxyData((EntityPlayer)event.entity);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			IridescentMiningProperties.register((EntityPlayer)event.entity);
			IridescentCraftingProperties.register((EntityPlayer)event.entity);
			IridescentWoodCuttingProperties.register((EntityPlayer)event.entity);
			
			NBTTagCompound data = ServerProxy.getEntityData(event.entity.getName());
			
			if(data != null) {
				IridescentMiningProperties.get((EntityPlayer)event.entity).loadNBTData(data);
				IridescentCraftingProperties.get((EntityPlayer)event.entity).loadNBTData(data);
				IridescentWoodCuttingProperties.get((EntityPlayer)event.entity).loadNBTData(data);
			}
			
			IridescentMiningProperties.get((EntityPlayer)event.entity).loadProxyData((EntityPlayer)event.entity);
			IridescentCraftingProperties.get((EntityPlayer)event.entity).loadProxyData((EntityPlayer)event.entity);
			IridescentWoodCuttingProperties.get((EntityPlayer)event.entity).loadProxyData((EntityPlayer)event.entity);
		}
	}
	
	@SubscribeEvent
	public void onBlockDestroyed(BreakEvent event) {
		
		if(event.getPlayer().capabilities.isCreativeMode) {
			return; // Don't bother with the rest if they are in creative mode
		} else {
			IridescentMiningProperties miningProperties = IridescentMiningProperties.get(event.getPlayer());
			
			if(event.state.getBlock() == Blocks.coal_ore) {
				miningProperties.giveExp(1);
			}
			
			if(event.state.getBlock() == Blocks.iron_ore) {
				miningProperties.giveExp(3);
			}
			
			if(event.state.getBlock() == Blocks.gold_ore) {
				miningProperties.giveExp(2);
			}
			
			if(event.state.getBlock() == Blocks.diamond_ore) {
				miningProperties.giveExp(4);
			}
			
			if(event.state.getBlock() == Blocks.redstone_ore || event.state.getBlock() == Blocks.lit_redstone_ore) {
				miningProperties.giveExp(2);
			}
			
			if(event.state.getBlock() == Blocks.lapis_ore) {
				miningProperties.giveExp(2);
			}
			
			IridescentWoodCuttingProperties woodCuttingProperties = IridescentWoodCuttingProperties.get(event.getPlayer());
			
			if(event.state.getBlock().getMaterial() == Material.wood) {
				woodCuttingProperties.giveExp(2);
			}
		}
	}
	
	@SubscribeEvent
	public void onItemFinish(Stop event) {
		if(event.item.getItem() == Items.bow) {
			System.out.println("Bow was finished being used (An arrow was shot)");
		}
	}
	
}