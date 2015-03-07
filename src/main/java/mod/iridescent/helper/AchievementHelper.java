package mod.iridescent.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementHelper {
	
	// TODO Add in capability to statically give an achievement to the player
	
	private ArrayList<AchievementPage> achievementPages = new ArrayList<AchievementPage>();
	private HashMap<String, Integer> achievementPagesLookupMap = new HashMap<String, Integer>();
	private ArrayList<Achievement> iridescentAchievements = new ArrayList<Achievement>();
	private HashMap<String, Integer> achievementsLookupMap = new HashMap<String, Integer>();
	
	/**
	 * Use this to add an achievement to the iridescent achievement page
	 * @param statName The name of the stat for minecraft statistics
	 * @param achievementName The name of the achievement
	 * @param x The column for the achievement to appear in
	 * @param y The row for the achievement to appear in
	 * @param stack The item, block, or itemstack used as an icon for the achievement
	 * @param parent The parent achievement that must be acquired before this achievement. Leave null for no parent
	 * @param independant Whether or not this is an independant achievement
	 * @param special Whether or not to have the special border
	 */
	public void addAchievement(String statName, String achievementName, int x, int y, ItemStack stack, Achievement parent, boolean independant, boolean special) {
		iridescentAchievements.add(new Achievement(statName, achievementName, x, y, stack, parent));
		
		if(independant) {
			iridescentAchievements.get(iridescentAchievements.size() - 1).setIndependent();
		}
		
		if(special) {
			iridescentAchievements.get(iridescentAchievements.size() - 1).setSpecial();
		}
		
		achievementsLookupMap.put(statName.toLowerCase(), iridescentAchievements.size());
	}
	
	public Achievement getAchievementFromName(String name) {
		return iridescentAchievements.get(achievementsLookupMap.get(name.toLowerCase()) - 1);
	}
	
	public void addAchievementPage(String pageName, String... achievements) {
		Achievement[] achievementsFromLookup = new Achievement[achievements.length];
		
		for(int i = 0; i < achievements.length; ++i) {
			achievementsFromLookup[i] = getAchievementFromName(achievements[i]);
		}
		
		achievementPages.add(new AchievementPage(pageName, achievementsFromLookup));
		
		achievementPagesLookupMap.put(pageName.toLowerCase(), achievementPages.size());
	}
	
	public AchievementPage getAchievementPage(String name) {
		return achievementPages.get(achievementPagesLookupMap.get(name.toLowerCase()) - 1);
	}
	
	/**
	 * Call this after all achievements have been added
	 */
	public void initAchievements() {
		
		for(int i = 0; i < iridescentAchievements.size(); ++i) {
			iridescentAchievements.get(i).func_180788_c();
		}
		
		for(int i = 0; i < achievementPages.size(); ++i) {
			AchievementPage.registerAchievementPage(achievementPages.get(i));
		}
	}
	
}