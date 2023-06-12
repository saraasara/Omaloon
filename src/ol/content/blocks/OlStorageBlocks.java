package ol.content.blocks;

import mindustry.content.Items;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.meta.*;

import ol.content.*;
import ol.world.blocks.storage.OlBaseCoreBlock;

import static mindustry.type.ItemStack.*;

public class OlStorageBlocks {
    public static Block
            //cores
            landingCapsule;

    public static void load() {
        //cores
        landingCapsule = new OlBaseCoreBlock("landing-capsule"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, empty);
            canBuildMiner = true;
            minerType = OlUnitTypes.drillUnit;
            minerRequirements = ItemStack.with(OlItems.grumon, 100);

            isFirstTier = true;
            unitType = OlUnitTypes.discoverer;
            health = 650;
            itemCapacity = 450;
            size = 2;

            unitCapModifier = 3;
        }};
    }
}