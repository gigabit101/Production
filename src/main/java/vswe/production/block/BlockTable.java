package vswe.production.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import vswe.production.StevesProduction;
import vswe.production.creativetab.CreativeTabProduction;
import vswe.production.tileentity.TileEntityTable;

import javax.annotation.Nullable;

public class BlockTable extends BlockContainer
{
    protected BlockTable()
    {
        super(Material.ROCK);
        setCreativeTab(CreativeTabProduction.getTab());
        setHardness(3.5F);
//        setRegistryName("worktable");
        setUnlocalizedName("worktable");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityTable();
    }
//
//    @SideOnly(Side.CLIENT)
//    private IIcon[] icons;
//
//
//    @SideOnly(Side.CLIENT)
//    @Override
//    public void registerBlockIcons(IIconRegister register) {
//        icons = new IIcon[]  {
//                register.registerIcon("production:bot"),
//                register.registerIcon("production:top"),
//                register.registerIcon("production:front"),
//                register.registerIcon("production:back"),
//                register.registerIcon("production:left"),
//                register.registerIcon("production:right"),
//        };
//    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public IIcon getIcon(int side, int meta) {
//        return getIconFromSideAndMeta(side, 2);
//    }
//
//    @SideOnly(Side.CLIENT)
//    public IIcon getIconFromSideAndMeta(int side, int meta) {
//        return icons[getSideFromSideAndMeta(side, meta)];
//    }
//
//    @Override
//    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
//        return getIconFromSideAndMeta(side, world.getBlockMetadata(x, y, z));
//    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    public static int getSideFromSideAndMeta(int side, int meta)
    {
        if (side <= 1)
        {
            return side;
        } else
        {
            int index = SIDES_INDICES[side - 2] - meta;
            if (index < 0)
            {
                index += SIDES.length;
            }
            return SIDES[index] + 2;
        }
    }

    public static int getSideFromSideAndMetaReversed(int side, int meta)
    {
        if (side <= 1)
        {
            return side;
        } else
        {
            int index = SIDES_INDICES[side - 2] + meta;
            index %= SIDES.length;

            return SIDES[index] + 2;
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            FMLNetworkHandler.openGui(player, StevesProduction.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    private static final int[] SIDES_INDICES = {0, 2, 3, 1};
    private static final int[] SIDES = {0, 3, 1, 2};

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        int rotation = MathHelper.floor_double((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
//        world.setBlockState(pos, rotation, 2);
    }

//    @Override
//    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack item) {
//        int rotation = MathHelper.floor_double((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
//        world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
//    }


    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof IInventory)
        {
            IInventory inventory = (IInventory) te;
            for (int i = 0; i < inventory.getSizeInventory(); ++i)
            {
                ItemStack item = inventory.getStackInSlot(i);

                if (item != null)
                {
                    float offsetX = world.rand.nextFloat() * 0.8F + 0.1F;
                    float offsetY = world.rand.nextFloat() * 0.8F + 0.1F;
                    float offsetZ = world.rand.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world, pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ, item.copy());
                    entityItem.motionX = world.rand.nextGaussian() * 0.05F;
                    entityItem.motionY = world.rand.nextGaussian() * 0.05F + 0.2F;
                    entityItem.motionZ = world.rand.nextGaussian() * 0.05F;

                    world.spawnEntityInWorld(entityItem);
                }
            }
        }

        super.breakBlock(world, pos, state);
    }
}
