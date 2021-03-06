package online.kingdomkeys.kingdomkeys.capability;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import online.kingdomkeys.kingdomkeys.lib.PortalData;
import online.kingdomkeys.kingdomkeys.lib.SoAState;
import online.kingdomkeys.kingdomkeys.synthesis.material.Material;
import online.kingdomkeys.kingdomkeys.util.Utils;

public interface IPlayerCapabilities {
	int getLevel();
	void setLevel(int level);
	
	int getExperience();
	void setExperience(int exp);
	void addExperience(PlayerEntity player, int exp, boolean shareXP);

	int getExperienceGiven();
	void setExperienceGiven(int exp);
	
	int getStrength();
	void setStrength(int str);
	void addStrength(int str);
	
	int getMagic();
	void setMagic(int mag);
	void addMagic(int mag);
	
	int getDefense();
	void setDefense(int def);
	void addDefense(int def);
	
	int getMaxHP();
	void setMaxHP(int hp);
	void addMaxHP(int hp);
	
	double getMP();
	void setMP(double mP);
	void addMP(double mp);
	void remMP(double cost);
	
	double getMaxMP();
	void setMaxMP(double maxMP);
	void addMaxMP(double mp);
	
	double getDP();
	void setDP(double dP);
	void addDP(double dp);
	void remDP(double cost);
	
	double getFP();
	void setFP(double fp);
	void addFP(double fp);
	void remFP(double cost);
	
	double getMaxDP();
	void setMaxDP(double dP);
		
	int getMaxAP();
	void setMaxAP(int ap);
	void addMaxAP(int ap);
	
    void levelUpStatsAndDisplayMessage(PlayerEntity player);
    void clearMessages();
	void setMessages(List<String> messages);
    List<String> getMessages();
    
	int getExpNeeded(int level, int currentExp);
	
	void setActiveDriveForm(String form);
	String getActiveDriveForm();
	
	int getAeroTicks();
	void setAeroTicks(int i);
	void remAeroTicks(int ticks);

	
	void setReflectTicks(int ticks);
	void remReflectTicks(int ticks);
	int getReflectTicks();
	void setReflectActive(boolean active);
	boolean getReflectActive();
	
	void setRecharge(boolean b);
	boolean getRecharge();
	
	void setMunny(int amount);
	int getMunny();
	
	void displayDriveFormLevelUpMessage(PlayerEntity player, String driveForm);
    void clearDFMessages();
	void setDFMessages(List<String> messages);
	List<String> getDFMessages();
	
	LinkedHashMap<String, int[]> getDriveFormMap();
	void setDriveFormMap(LinkedHashMap<String,int[]> map);

	int getDriveFormLevel(String name);
	void setDriveFormLevel(String name, int level);
    int getDriveFormExp(String drive);
    void setDriveFormExp(PlayerEntity player, String drive, int exp);
	void addDriveFormExperience(String form, ServerPlayerEntity player, int driveExpNeeded);

	//Key: drive form registry key, Value: the keychain stack
    Map<ResourceLocation, ItemStack> getEquippedKeychains();
    //Return previously equipped ItemStack, returns null if the keychain was not equipped
	ItemStack equipKeychain(ResourceLocation form, ItemStack stack);
	//Returns null if the form does not exist in the map, does not return null if the slot is empty
	ItemStack getEquippedKeychain(ResourceLocation form);
	void equipAllKeychains(Map<ResourceLocation, ItemStack> keychains, boolean force);
	boolean canEquipKeychain(ResourceLocation form, ItemStack stack);
	void setNewKeychain(ResourceLocation form, ItemStack stack);

	List<String> getMagicList();
	void setMagicList(List<String> list);
	void addMagicToList(String magic);
	void removeMagicFromList(String magic);
	
	LinkedHashMap<String, int[]> getAbilityMap();
	void setAbilityMap(LinkedHashMap<String,int[]> map);
	void addAbility(String ability, boolean notification);
	int[] getEquippedAbilityLevel(String string); 
	void addEquippedAbilityLevel(String ability, int level);
	boolean isAbilityEquipped(String string);
	void clearAbilities();

	int getAntiPoints();
	void setAntiPoints(int points);
	
	//Drive forms
	boolean getIsGliding();
	void setIsGliding(boolean b);
	
	int getAerialDodgeTicks();
	void setAerialDodgeTicks(int ticks);
	boolean hasJumpedAerialDodge();
	void setHasJumpedAerialDodge(boolean b);
	
    List<String> getPartiesInvited();
    void setPartiesInvited(List<String> list);
    void addPartiesInvited(String partyName);
    void removePartiesInvited(String partyName);
	
    List<ResourceLocation> getKnownRecipeList();
    boolean hasKnownRecipe(ResourceLocation recipe);
    void setKnownRecipeList(List<ResourceLocation> list);
	void addKnownRecipe(ResourceLocation recipe);
	void removeKnownRecipe(ResourceLocation recipe);
	void clearRecipes();
	
	TreeMap<String, Integer> getMaterialMap();
	void setMaterialMap(TreeMap<String, Integer> materialsMap);
	int getMaterialAmount(Material material);
	void addMaterial(Material material, int amount);
	void setMaterial(Material material, int amount);
	void removeMaterial(Material material, int amount);
	void clearMaterials();

	//SoA choices

	Vector3d getReturnLocation();
	void setReturnLocation(PlayerEntity playerEntity);
	void setReturnLocation(Vector3d location);
	RegistryKey<World> getReturnDimension();
	void setReturnDimension(PlayerEntity playerEntity);
	void setReturnDimension(RegistryKey<World> type);

	//The current state of the SoA
	SoAState getSoAState();
	void setSoAState(SoAState state);
	SoAState getChosen();
	void setChoice(SoAState choice);
	SoAState getSacrificed();
	void setSacrifice(SoAState sacrifice);

	//In case player leaves inbetween choices
	BlockPos getChoicePedestal();
	void setChoicePedestal(BlockPos pos);
	BlockPos getSacrificePedestal();
	void setSacrificePedestal(BlockPos pos);

	int getHearts();
	void setHearts(int hearts);
	void addHearts(int hearts);
	void removeHearts(int hearts);

	Utils.OrgMember getAlignment();
	int getAlignmentIndex();
	void setAlignment(Utils.OrgMember member);
	void setAlignment(int index);
	boolean isWeaponUnlocked(Item weapon);
	void unlockWeapon(ItemStack weapon);
	void unlockWeapon(String registryName);
	ItemStack getEquippedWeapon();
	void equipWeapon(ItemStack weapon);
	void equipWeapon(String registryName);
	Set<ItemStack> getWeaponsUnlocked();
	void setWeaponsUnlocked(Set<ItemStack> unlocks);
	int getLimitCooldownTicks();
	void setLimitCooldownTicks(int ticks);
}
