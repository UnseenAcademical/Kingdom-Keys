package online.kingdomkeys.kingdomkeys.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import online.kingdomkeys.kingdomkeys.datagen.init.*;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new Recipes(generator));
        generator.addProvider(new ItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(new BlockModels(generator, event.getExistingFileHelper()));
        generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
        generator.addProvider(new KeybladeStats(generator, event.getExistingFileHelper()));
        generator.addProvider(new LootTables(generator));
        generator.addProvider(new SynthesisRecipe(generator, event.getExistingFileHelper()));
    }
}
