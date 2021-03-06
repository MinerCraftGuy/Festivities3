package com.zapcloudstudios.festivities3.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.zapcloudstudios.festivities3.Festivities;
import com.zapcloudstudios.festivities3.client.render.block.RenderBlockFireplace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFireplace extends BlockFestiveComplex
{
	public static IIcon floor;
	public static IIcon detail;
	
	public BlockFireplace(Material par2Material)
	{
		super(par2Material);
		this.setBlockBounds(1 / 16.0F, 0 / 16.0F, 1 / 16.0F, 15 / 16.0F, 5 / 16.0F, 15 / 16.0F);
		this.setRenderer(RenderBlockFireplace.class);
		this.setBlockTextureName(Festivities.ID + ":fireplace");
		this.setShouldRender3D(true);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{
		return detail;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.floor = reg.registerIcon(this.getTextureName() + "_floor");
		this.detail = reg.registerIcon(this.getTextureName() + "_detail");
	}
	
	/**
	 * A randomly called display update to be able to add particles or other
	 * items for display
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (par5Random.nextInt(24) == 0)
		{
			par1World.playSound((double) ((float) par2 + 0.5F), (double) ((float) par3 + 0.5F), (double) ((float) par4 + 0.5F), "fire.fire", 1.0F + par5Random.nextFloat(), par5Random.nextFloat() * 0.7F + 0.3F, false);
		}
		
		for (int i = 0; i < 3; ++i)
		{
			float fx = (float) par2 + par5Random.nextFloat() * 0.6F + 0.2F;
			float fy = (float) par3 + par5Random.nextFloat() * 0.5F + 0.3F;
			float fz = (float) par4 + par5Random.nextFloat() * 0.6F + 0.2F;
			par1World.spawnParticle("smoke", fx, fy, fz, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public String[] getTip(EntityPlayer player, ItemStack stack)
	{
		return new String[] { "Nice, warm, and safe" };
	}
	
	@Override
	public String[] getShiftTip(EntityPlayer player, ItemStack stack)
	{
		return null;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return super.canPlaceBlockAt(world, x, y, z) && this.canSitAt(world, x, y, z);
	}
	
	public boolean canSitAt(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y - 1, z);
		if (block.isAir(world, x, y, z))
		{
			return false;
		}
		if (block == this)
		{
			return false;
		}
		return world.doesBlockHaveSolidTopSurface(world, x, y - 1, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block change)
	{
		if (!this.canSitAt(world, x, y, z))
		{
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}
}
