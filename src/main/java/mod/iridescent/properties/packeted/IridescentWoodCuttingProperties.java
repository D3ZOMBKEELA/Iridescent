package mod.iridescent.properties.packeted;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import mod.iridescent.Iridescent;
import mod.iridescent.packets.properties.WoodCuttingPropertiesPacket;
import mod.iridescent.properties.IridescentSkillProperty;
import mod.iridescent.proxy.ServerProxy;

public class IridescentWoodCuttingProperties extends IridescentSkillProperty {
	
	public static final String SKILL_NAME = "IridescentWoodCutting";
	
	public IridescentWoodCuttingProperties(EntityPlayer player) {
		super(player);
	}
	
	public void saveProxyData(EntityPlayer player) { 
		IridescentWoodCuttingProperties properties = get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		properties.saveNBTData(savedData);
		ServerProxy.storeEntityData(getSaveKey(SKILL_NAME, player), savedData);
	}
	
	public void loadProxyData(EntityPlayer player) {
		IridescentWoodCuttingProperties properties = get(player);
		NBTTagCompound savedData = ServerProxy.getEntityData(getSaveKey(SKILL_NAME, player));
		
		if(savedData != null) {
			properties.loadNBTData(savedData);
		}
		
		sync(player);
	}
	
	public void sync(EntityPlayer player) {
		Iridescent.packetPipeline.sendTo(new WoodCuttingPropertiesPacket(player), player);
	}
	
	public static final void register(EntityPlayer player) {
		if(player.getExtendedProperties(SKILL_NAME) == null) {
			player.registerExtendedProperties(SKILL_NAME, new IridescentWoodCuttingProperties(player));
		}
	}
	
	public static final IridescentWoodCuttingProperties get(EntityPlayer player) {
		return (IridescentWoodCuttingProperties)player.getExtendedProperties(SKILL_NAME);
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