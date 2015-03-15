package mod.iridescent.properties;

import mod.iridescent.Iridescent;
import mod.iridescent.packets.properties.MiningPropertiesPacket;
import mod.iridescent.proxy.ServerProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class IridescentMiningProperties implements IExtendedEntityProperties {
	
	public static final String MINING_PROP_NAME = "IridescentMining";
	
	// TODO MAKE EVERYTHING A PACKET, ALL ONLY UPDATED WHEN A BLOCK IS BROKEN THAT IS ONE OF THE
	// POSSIBLE TYPES TO GIVE MINING EXP
	
	// Ignore #4
	
//	public static final int MINING_LEVEL_ID = 20;
//	public static final int CURRENT_MINING_EXP_ID = 21;
//	public static final int MAX_MINING_EXP_ID = 22;
	
	private int miningLevel;
	private int currentExp;
	private int maxExp;
	
	private final EntityPlayer thePlayer;
	
	public IridescentMiningProperties(EntityPlayer player) {
		this.thePlayer = player;
//		int miningLevel = 1;
//		int currentLevelExp = 0;
//		int maxLevelExp = 15;
		
		miningLevel = 1;
		currentExp = 0;
		maxExp = 15;
		
//		addDataWatcherObject(MINING_LEVEL_ID, miningLevel);
//		addDataWatcherObject(CURRENT_MINING_EXP_ID, currentLevelExp);
//		addDataWatcherObject(MAX_MINING_EXP_ID, maxLevelExp);
	}
	
	private static String getSaveKey(EntityPlayer player) {
		return player.getName() + ":" + MINING_PROP_NAME;
	}
	
	public static void saveProxyData(EntityPlayer player) { 
		IridescentMiningProperties properties = get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		properties.saveNBTData(savedData);
		ServerProxy.storeEntityData(getSaveKey(player), savedData);
	}
	
	public static void loadProxyData(EntityPlayer player) {
		IridescentMiningProperties properties = get(player);
		NBTTagCompound savedData = ServerProxy.getEntityData(getSaveKey(player));
		
		if(savedData != null) {
			properties.loadNBTData(savedData);
		}
		
		sync(player);
	}
	
	public static void sync(EntityPlayer player) {
		Iridescent.packetPipeline.sendTo(new MiningPropertiesPacket(player), (EntityPlayerMP)player);
	}
	
	public static void register(EntityPlayer player) {
		player.registerExtendedProperties(MINING_PROP_NAME, new IridescentMiningProperties(player));
	}
	
	public static IridescentMiningProperties get(EntityPlayer player) {
		return (IridescentMiningProperties)player.getExtendedProperties(MINING_PROP_NAME);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		
//		properties.setInteger("Level", this.thePlayer.getDataWatcher().getWatchableObjectInt(MINING_LEVEL_ID));
//		properties.setInteger("CurrentExp", this.thePlayer.getDataWatcher().getWatchableObjectInt(CURRENT_MINING_EXP_ID));
//		properties.setInteger("MaxExp", this.thePlayer.getDataWatcher().getWatchableObjectInt(MAX_MINING_EXP_ID));
		
		properties.setInteger("Level", miningLevel);
		properties.setInteger("CurrentExp", currentExp);
		properties.setInteger("MaxExp", maxExp);
		
		compound.setTag(MINING_PROP_NAME, properties);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(MINING_PROP_NAME);
		
//		this.thePlayer.getDataWatcher().updateObject(MINING_LEVEL_ID, properties.getInteger("Level"));
//		this.thePlayer.getDataWatcher().updateObject(CURRENT_MINING_EXP_ID, properties.getInteger("CurrentExp"));
//		this.thePlayer.getDataWatcher().updateObject(MAX_MINING_EXP_ID, properties.getInteger("MaxExp"));
		
		miningLevel = properties.getInteger("Level");
		currentExp = properties.getInteger("CurrentExp");
		maxExp = properties.getInteger("MaxExp");
		
		printMiningDetails();
	}
	
	public boolean giveExp(int amount) {
		boolean levelUp = false;
		int extraExp = 0;
		
//		if(amount + getDataWatcherObject(CURRENT_MINING_EXP_ID) >= getDataWatcherObject(MAX_MINING_EXP_ID)) {
//			levelUp = true;
//			updateDataWatcherObject(MINING_LEVEL_ID, getDataWatcherObject(MINING_LEVEL_ID) + 1);
//			extraExp = amount + getDataWatcherObject(CURRENT_MINING_EXP_ID) - getDataWatcherObject(MAX_MINING_EXP_ID);
//			
//			while(extraExp >= getDataWatcherObject(MAX_MINING_EXP_ID)) {
//				updateDataWatcherObject(MINING_LEVEL_ID, getDataWatcherObject(MINING_LEVEL_ID) + 1);
//				extraExp -= getDataWatcherObject(MAX_MINING_EXP_ID);
//			}
//			
//			updateDataWatcherObject(CURRENT_MINING_EXP_ID, extraExp);
//		} else {
//			updateDataWatcherObject(CURRENT_MINING_EXP_ID, getDataWatcherObject(CURRENT_MINING_EXP_ID) + amount);
//		}
		
		if(amount + currentExp >= maxExp) {
			levelUp = true;
			miningLevel++;
			extraExp = amount + currentExp - maxExp;
			
			while(extraExp >= maxExp) {
				miningLevel++;
				extraExp -= maxExp;
			}
			
			currentExp = extraExp;
		} else {
			currentExp += amount;
		}
		
		printMiningDetails();
		sync(thePlayer);
		
		return levelUp;
	}
	
	public void increaseLevel(int levels) {
		giveExp(maxExp - currentExp);
	}
	
	public void increaseLevel() {
		increaseLevel(1);
	}
	
	public void printMiningDetails() {
		System.out.println("\tMining:");
		System.out.println("\tLevel: " + miningLevel);
		System.out.println("\tExp: " + currentExp + "/" + maxExp);
	}
	
	public int getCurrentExp() {
		return currentExp;
	}
	
	public int getMaxExp() {
		return maxExp;
	}
	
	public int getMiningLevel() {
		return miningLevel;
	}
	
	@Override
	public void init(Entity entity, World world) {
	}
	
	private void addDataWatcherObject(int id, Object object) {
		this.thePlayer.getDataWatcher().addObject(id, object);
	}
	
	private void updateDataWatcherObject(int id, Object newData) {
		this.thePlayer.getDataWatcher().updateObject(id, newData);
	}
	
	private int getDataWatcherObject(int id) {
		return this.thePlayer.getDataWatcher().getWatchableObjectInt(id);
	}
	
}