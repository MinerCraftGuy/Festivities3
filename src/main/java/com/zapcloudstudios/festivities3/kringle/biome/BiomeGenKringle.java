package com.zapcloudstudios.festivities3.kringle.biome;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BiomeGenKringle extends BiomeGenBase
{
	public static BiomeGenKringle kringlePlains;
	public static BiomeGenKringle kringleIce;
	public static BiomeGenKringle kringleMountains;
	public static BiomeGenKringle kringleChristmasForest;
	public static BiomeGenKringle kringleForest;
	public static BiomeGenKringle kringlePeaks;
	
	public float candy;
	public float plant;
	
	public static List<BiomeGenKringle> kringleBiomes = new ArrayList<BiomeGenKringle>();
	
	public static void registerBiomes(int base)
	{
		kringlePlains = (BiomeGenKringle) new BiomeGenKringlePlains(base + 1, 0.75F, 0.3F).setBiomeName("Plains").setHeight(new Height(0.1F, 0.3F));
		kringleIce = (BiomeGenKringle) new BiomeGenKringleIce(base + 2, 0.1F, 0.2F).setBiomeName("Ice").setHeight(new Height(0.05F, 0.1F));
		kringleMountains = (BiomeGenKringle) new BiomeGenKringleMountains(base + 3, 0.4F, 0.7F).setBiomeName("Mountains").setHeight(new Height(0.3F, 0.9F));
		kringleChristmasForest = (BiomeGenKringle) new BiomeGenKringleChristmasForest(base + 4, 0.6F, 0.7F).setBiomeName("Christmas Forest").setHeight(new Height(0.3F, 0.6F));
		kringleForest = (BiomeGenKringle) new BiomeGenKringleForest(base + 5, 0.2F, 0.9F).setBiomeName("Forest").setHeight(new Height(0.4F, 0.6F));
		kringlePeaks = (BiomeGenKringle) new BiomeGenKringlePeaks(base + 6, 0.2F, 0.4F).setBiomeName("Peaks").setHeight(new Height(0.1F, 1.3F));
	}
	
	public Block topBlock;
	public Block fillerBlock;
	
	public BiomeGenKringle(int id, float candy, float plant)
	{
		super(id);
		this.candy = candy;
		this.plant = plant;
		this.setEnableSnow();
		this.setColor(0xFF0511);
		this.setTemperatureRainfall(0.0F, 0.5F);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		
		this.topBlock = Blocks.snow;
		this.fillerBlock = Blocks.snow;
		
		this.kringleBiomes.add(this);
	}
	
	public KringleDecorator getDecorator()
	{
		return (KringleDecorator) this.theBiomeDecorator;
	}
	
	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return new KringleDecorator();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * takes temperature, returns color
	 */
	public int getSkyColorByTemp(float temp)
	{
		float s = temp * -0.5F;
		float v = temp * 0.2F - 0.1F;
		return Color.getHSBColor(0.6F, 0.8F + s, 0.27F + v).getRGB();
	}
	
	/**
	 * Returns true if the biome have snowfall instead a normal rain.
	 */
	@Override
	public boolean getEnableSnow()
	{
		return true;
	}
	
	/**
	 * Return true if the biome supports lightning bolt spawn, either by have
	 * the bolts enabled and have rain enabled.
	 */
	@Override
	public boolean canSpawnLightningBolt()
	{
		return false;
	}
	
	/**
	 * Checks to see if the rainfall level of the biome is extremely high
	 */
	@Override
	public boolean isHighHumidity()
	{
		return false;
	}
	
	@Override
	public void decorate(World world, Random rand, int chunkX, int chunkZ)
	{
		super.decorate(world, rand, chunkX, chunkZ);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int x, int y, int z)
	{
		return Color.getHSBColor(0.55F, 0.1F, 1.0F).getRGB();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int x, int y, int z)
	{
		return super.getBiomeFoliageColor(x, y, z);
	}
	
	@Override
	public BiomeDecorator getModdedBiomeDecorator(BiomeDecorator original)
	{
		return original;
	}
	
	@Override
	public int getWaterColorMultiplier()
	{
		return super.getWaterColorMultiplier();
	}
	
	@Override
	public BiomeGenBase setBiomeName(String name)
	{
		return super.setBiomeName("Kringle " + name);
	}
	
	public static BiomeGenKringle getBiome(float plant, float candy)
	{
		plant /= 2;
		candy /= 2;
		float dist = 999.9F;
		BiomeGenKringle biome = null;
		for (int i = 0; i < kringleBiomes.size(); i++)
		{
			BiomeGenKringle b = kringleBiomes.get(i);
			float ca = b.candy - candy;
			float pl = b.plant - plant;
			float d = ca * ca + pl * pl;
			if (d < dist)
			{
				dist = d;
				biome = b;
			}
		}
		return biome;
	}
}
