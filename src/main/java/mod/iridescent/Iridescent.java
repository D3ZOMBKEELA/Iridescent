package mod.iridescent;

import mod.iridescent.events.IridescentEventHandler;
import mod.iridescent.packets.PacketPipeline;
import mod.iridescent.proxy.IProxy;
import mod.iridescent.reference.ModRef;
import mod.iridescent.reference.NetworkRef;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModRef.MOD_ID, name = ModRef.MOD_NAME, version = ModRef.MOD_VERSION)
public class Iridescent {
	
	@Instance(ModRef.MOD_ID)
	public static Iridescent instance;
	
	@SidedProxy(clientSide = NetworkRef.CLIENT_PROXY, serverSide = NetworkRef.SERVER_PROXY)
	public static IProxy proxy;
	
	public static final IridescentAchievements achievements = new IridescentAchievements();
	
	public static final PacketPipeline packetPipeline = new PacketPipeline();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		packetPipeline.initialise();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		packetPipeline.postInitialise();
		
		achievements.postInit(); // XXX Might need to move this to either preInit or init
		MinecraftForge.EVENT_BUS.register(new IridescentEventHandler());
		proxy.registerRenderers();
	}
	
	/**
	 * TODO:
	 *  - Add base blocks and items
	 * 
	 *  - Add new inventory screens
	 * 
	 *  - Add extended properties for the player
	 *  
	 *  	General (HUD):				ID:
	 *  	 - Health					 - 20
	 *  	 - Mana						 - 21
	 *  	 - Armor					 - 22
	 *  
	 *  	Nutrition (HUD):
	 *  	 - Hunger					 - 23
	 *  	 - Saturation				 - 24
	 *  	 - Thirst					 - 25
	 *  
	 *  	Diet (Daily):				(Possibly)
	 *  	 - Calories					 - 26
	 *  	 - Fat						 - 27
	 *  	 - Fibre					 - 28
	 *  	 - Sugar					 - 29
	 *  	 - Protein					 - 30
	 *  	 - Calcium					 - 31
	 *  	 - (Possibly more?)
	 *  
	 *  	Skills:						Status:
	 *  	 - Melee					 - Incomplete
	 *  	 - Archery					 - Incomplete
	 *  	 - Magic					 - Incomplete
	 *  	 - Defense					 - Incomplete
	 *  	 - Crafting					 - Incomplete
	 *  	 - Mining					 - Complete
	 *  	 - Smithing					 - Incomplete
	 *  	 - Fishing					 - Incomplete
	 *  	 - Cooking					 - Incomplete
	 *  	 - Firemaking				 - Incomplete
	 *  	 - Wood cutting				 - Incomplete
	 *  	 - Agility					 - Incomplete
	 *  	 - Alchemy					 - Incomplete
	 *  
	 *  	 - Farming					 - Incomplete
	 */
	
}