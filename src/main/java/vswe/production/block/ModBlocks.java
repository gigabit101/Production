package vswe.production.block;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import vswe.production.creativetab.CreativeTabProduction;
import vswe.production.item.Upgrade;
import vswe.production.tileentity.TileEntityTable;

public final class ModBlocks {
    public static BlockTable table;

    private static final String UNLOCALIZED_NAME = "production_table";
    private static final String LOCALIZED_NAME = "Production Table";

    private static final String PLANKS = "plankWood";
    private static final String COBBLE = "cobblestone";

    public static void init() {
        table = new BlockTable();
        GameRegistry.registerBlock(table, UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileEntityTable.class, UNLOCALIZED_NAME);

        CreativeTabProduction.getTab().init(table);

        GameRegistry.addRecipe(new ShapedOreRecipe(table, "PPP", "CUC", "CCC", 'P', PLANKS, 'C', COBBLE, 'U', Upgrade.BLANK.getItemStack()));
    }

    private ModBlocks(){}
}
