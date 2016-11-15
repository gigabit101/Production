package vswe.production.network.data;

import vswe.production.network.DataReader;
import vswe.production.network.DataWriter;
import vswe.production.network.IBitCount;
import vswe.production.network.MaxCount;
import vswe.production.tileentity.TileEntityTable;


public class DataFuel extends DataBase
{
    private static IBitCount FUEL_BIT_COUNT = new MaxCount(TileEntityTable.MAX_POWER);

    @Override
    public void save(TileEntityTable table, DataWriter dw, int id)
    {
        dw.writeData(table.getPower(), FUEL_BIT_COUNT);
    }

    @Override
    public void load(TileEntityTable table, DataReader dr, int id)
    {
        table.setPower(dr.readData(FUEL_BIT_COUNT));
    }
}
