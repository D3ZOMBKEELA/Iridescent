package mod.iridescent.packets.properties;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mod.iridescent.packets.AbstractPacket;
import mod.iridescent.properties.packeted.IridescentWoodCuttingProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class WoodCuttingPropertiesPacket extends AbstractPacket {
	
	private NBTTagCompound data;
	
	public WoodCuttingPropertiesPacket() {}
	
	public WoodCuttingPropertiesPacket(EntityPlayer player) {
		data = new NBTTagCompound();
		IridescentWoodCuttingProperties.get(player).saveNBTData(data);
	}
	
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		ByteBufUtils.writeTag(buffer, data);
	}
	
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		data = ByteBufUtils.readTag(buffer);
	}
	
	public void handleClientSide(EntityPlayer player) {
		if(player != null) {
			IridescentWoodCuttingProperties.register(player);
			IridescentWoodCuttingProperties.get(player).loadNBTData(data);
		}
	}
	
	public void handleServerSide(EntityPlayer player) {
		// Never needed to be sent to the server, only recieved from
	}
}