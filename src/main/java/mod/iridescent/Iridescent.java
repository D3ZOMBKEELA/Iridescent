package mod.iridescent;

import java.util.ArrayList;

import mod.iridescent.helper.AchievementHelper;
import mod.iridescent.proxy.IProxy;
import mod.iridescent.reference.ModRef;
import mod.iridescent.reference.NetworkRef;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModRef.MOD_ID, name = ModRef.MOD_NAME, version = ModRef.MOD_VERSION)
public class Iridescent {
	
	@Instance(ModRef.MOD_ID)
	public static Iridescent instance;
	
	@SidedProxy(clientSide = NetworkRef.CLIENT_PROXY, serverSide = NetworkRef.SERVER_PROXY)
	public static IProxy proxy;
	
	IridescentAchievements achievements = new IridescentAchievements();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		achievements.postInit(); // XXX Might need to move this to either preInit or init
	}
	
}