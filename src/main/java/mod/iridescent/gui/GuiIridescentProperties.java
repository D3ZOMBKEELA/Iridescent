package mod.iridescent.gui;

import mod.iridescent.properties.IridescentMiningProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

public class GuiIridescentProperties extends Gui {
	
	private Minecraft mc;
	
	// ResourceLocation?
	
	public GuiIridescentProperties(Minecraft minecraft) {
		this.mc = minecraft;
	}
	
	@SubscribeEvent // Not sure why this is in here when you could just put it in an event handler class that tells the gui to render
	public void onRenderExperienceBar(RenderGameOverlayEvent event) {
		if(event.isCancelable() || event.type != ElementType.EXPERIENCE) {
			return;
		}
		
		IridescentMiningProperties properties = IridescentMiningProperties.get(this.mc.thePlayer);
		
		if(properties == null) {
			return;
		}
		
		int xPos = 2;
		int yPos = 2;
		
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("iridescent:textures/gui/mining.png"));
		
		this.drawTexturedModalRect(xPos, yPos, 0, 0, 51, 4);
		
		int barWidth = (int)(((float)properties.getCurrentExp() / properties.getMaxExp()) * 49); // Not sure why * 49
		this.drawTexturedModalRect(xPos, yPos + 1, 0, 4, barWidth, 2);
		this.drawString(this.mc.fontRendererObj, "Level: " + properties.getMiningLevel(), 54, 2, 16777215);
	}
	
}