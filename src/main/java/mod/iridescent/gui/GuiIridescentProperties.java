package mod.iridescent.gui;

import mod.iridescent.properties.packeted.IridescentCraftingProperties;
import mod.iridescent.properties.packeted.IridescentMiningProperties;
import mod.iridescent.properties.packeted.IridescentWoodCuttingProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

public class GuiIridescentProperties extends Gui {
	
	private Minecraft mc;
	
	public GuiIridescentProperties(Minecraft minecraft) {
		this.mc = minecraft;
	}
	
	@SubscribeEvent
	public void onRenderExperienceBar(RenderGameOverlayEvent event) {
		if(event.isCancelable() || event.type != ElementType.EXPERIENCE) {
			return;
		}
		
		IridescentMiningProperties miningProperties = IridescentMiningProperties.get(this.mc.thePlayer);
		if(miningProperties == null) {
			IridescentMiningProperties.register(this.mc.thePlayer);
			return;
		}
		
		IridescentCraftingProperties craftingProperties = IridescentCraftingProperties.get(this.mc.thePlayer);
		if(craftingProperties == null) {
			IridescentCraftingProperties.register(this.mc.thePlayer);
			return;
		}
		
		IridescentWoodCuttingProperties woodCuttingProperties = IridescentWoodCuttingProperties.get(this.mc.thePlayer);
		if(woodCuttingProperties == null) {
			return;
		}
		
		int xPos = 2;
		int yPos = 2;
		
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawString(this.mc.fontRendererObj, "Mining Lvl: " + miningProperties.getSkillLevel() + "   Exp: " + miningProperties.getCurrentExp() + "/" + miningProperties.getMaxExp(), xPos, yPos, 16777215);
		yPos += 20;
		this.drawString(this.mc.fontRendererObj, "Crafting Lvl: " + craftingProperties.getSkillLevel() + "   Exp: " + craftingProperties.getCurrentExp() + "/" + craftingProperties.getMaxExp(), xPos, yPos, 16777215);
		yPos += 20;
		this.drawString(this.mc.fontRendererObj, "Wood Cutting Lvl: " + woodCuttingProperties.getSkillLevel() + "   Exp: " + woodCuttingProperties.getCurrentExp() + "/" + woodCuttingProperties.getMaxExp(), xPos, yPos, 16777215);
	}
	
}