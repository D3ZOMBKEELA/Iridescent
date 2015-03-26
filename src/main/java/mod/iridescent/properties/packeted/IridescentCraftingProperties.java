package mod.iridescent.properties.packeted;

import mod.iridescent.Iridescent;
import mod.iridescent.packets.properties.CraftingPropertiesPacket;
import mod.iridescent.properties.IridescentSkillProperty;
import mod.iridescent.proxy.ServerProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class IridescentCraftingProperties extends IridescentSkillProperty {
	
	public static final String SKILL_NAME = "IridescentCrafting";
	
	public IridescentCraftingProperties(EntityPlayer player) {
		super(player);
	}
	
	public void saveProxyData(EntityPlayer player) { 
		IridescentCraftingProperties properties = get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		properties.saveNBTData(savedData);
		ServerProxy.storeEntityData(getSaveKey(SKILL_NAME, player), savedData);
	}
	
	public void loadProxyData(EntityPlayer player) {
		IridescentCraftingProperties properties = get(player);
		NBTTagCompound savedData = ServerProxy.getEntityData(getSaveKey(SKILL_NAME, player));
		
		if(savedData != null) {
			properties.loadNBTData(savedData);
		}
		
		sync(player);
	}
	
	public void sync(EntityPlayer player) {
		Iridescent.packetPipeline.sendTo(new CraftingPropertiesPacket(player), player);
	}
	
	public static final void register(EntityPlayer player) {
		if(player.getExtendedProperties(SKILL_NAME) == null) {
			player.registerExtendedProperties(SKILL_NAME, new IridescentCraftingProperties(player));
		}
	}
	
	public static final IridescentCraftingProperties get(EntityPlayer player) {
		return (IridescentCraftingProperties)player.getExtendedProperties(SKILL_NAME);
	}
	
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setInteger("Level", skillLevel);
		properties.setInteger("CurrentExp", currentExp);
		properties.setInteger("MaxExp", maxExp);
		compound.setTag(SKILL_NAME, properties);
	}
	
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(SKILL_NAME);
		skillLevel = properties.getInteger("Level");
		currentExp = properties.getInteger("CurrentExp");
		maxExp = properties.getInteger("MaxExp");
	}
	
}