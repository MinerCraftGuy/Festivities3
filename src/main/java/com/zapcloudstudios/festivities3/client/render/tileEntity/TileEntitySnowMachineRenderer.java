package com.zapcloudstudios.festivities3.client.render.tileEntity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.zapcloudstudios.festivities3.Festivities;
import com.zapcloudstudios.festivities3.tile.TileEntitySnowMachine;
import com.zapcloudstudios.utils.draw.BoxDrawBasic;

public class TileEntitySnowMachineRenderer extends TileEntityFestivitiesRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
	{
		Tessellator tess = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslated(d0, d1, d2);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		this.renderSnowMachine((TileEntitySnowMachine) tileentity);
		
		GL11.glPopMatrix();
	}
	
	public void renderSnowMachine(TileEntitySnowMachine tile)
	{
		Tessellator tess = Tessellator.instance;
		BoxDrawBasic draw = new BoxDrawBasic();
		draw.setTexture(this, Festivities.ID, "textures/tile/snowMachine.png", 36, 36);
		tess.startDrawingQuads();
		
		draw.cube(0, 11, 0, 16, 5, 16);
		draw.selectUV(16, 0);
		draw.drawSidesSameTexture();
		draw.selectUV(0, 16);
		draw.YDown();
		draw.selectUV(0, 0);
		draw.YUp();
		
		draw.cube(5, 3, 5, 6, 8, 6);
		draw.selectUV(30, 5);
		draw.drawSidesSameTexture();
		
		draw.cube(1, 0, 1, 14, 3, 14);
		draw.selectUV(16, 5);
		draw.YUp();
		draw.selectUV(16, 19);
		draw.YDown();
		
		if (tile.isPowered())
		{
			draw.selectUV(16, 33);
		}
		else
		{
			draw.selectUV(2, 33);
		}
		
		draw.drawSidesSameTexture();
		
		tess.draw();
	}
}
