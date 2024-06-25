package me.youhavetrouble.particlewings.configuration.settings;

import org.bukkit.Material;

import me.youhavetrouble.particlewings.ParticleWings;
import me.youhavetrouble.particlewings.configuration.Config;

public enum ConfigSetting implements SettingInterface {

    WING_VIEW_DISTANCE("wingViewDistance", SettingType.INT), 
    WING_MAX_PITCH("wingMaxPitch", SettingType.INT),
    INVISIBILITY_POTION_HIDES_WING("invisibilityPotionHidesWing", SettingType.BOOLEAN),

    WING_SELECT_MENU_NAME("wingSelectMenu.name", SettingType.STRING),
    WING_SELECT_MENU_SIZE("wingSelectMenu.size", SettingType.GUISIZE),
    WING_SELECT_MENU_PAGES("wingSelectMenu.pages", SettingType.INT),

    REMOVE_WING_ITEM_NAME("wingSelectMenu.removeWingItem.name", SettingType.STRING),
    REMOVE_WING_ITEM_MATERIAL("wingSelectMenu.removeWingItem.material", SettingType.MATERIAL),
    REMOVE_WING_ITEM_SLOT("wingSelectMenu.removeWingItem.slot", SettingType.GUISLOT),

    HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_NAME("wingSelectMenu.hideWingsToggleItem.nameON", SettingType.STRING),
    HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_MATERIAL("wingSelectMenu.hideWingsToggleItem.materialON", SettingType.MATERIAL),
    HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_NAME("wingSelectMenu.hideWingsToggleItem.nameOFF", SettingType.STRING),
    HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_MATERIAL("wingSelectMenu.hideWingsToggleItem.materialOFF", SettingType.MATERIAL),
    HIDE_OTHER_WINGS_TOGGLE_SLOT("wingSelectMenu.hideWingsToggleItem.slot", SettingType.GUISLOT),

    SHOW_WING_TOGGLE_ON_ITEM_NAME("wingSelectMenu.showWingToggleItem.nameON", SettingType.STRING),
    SHOW_WING_TOGGLE_ON_ITEM_MATERIAL("wingSelectMenu.showWingToggleItem.materialON", SettingType.MATERIAL),
    SHOW_WING_TOGGLE_OFF_ITEM_NAME("wingSelectMenu.showWingToggleItem.nameOFF", SettingType.STRING),
    SHOW_WING_TOGGLE_OFF_ITEM_MATERIAL("wingSelectMenu.showWingToggleItem.materialOFF", SettingType.MATERIAL),
    SHOW_WING_TOGGLE_SLOT("wingSelectMenu.showWingToggleItem.slot", SettingType.GUISLOT),

    NAGIVATION_ITEM_NEXT_NAME("wingSelectMenu.navigationItem.next.name", SettingType.STRING),
    NAGIVATION_ITEM_NEXT_MATERIAL("wingSelectMenu.navigationItem.next.material", SettingType.MATERIAL),
    NAGIVATION_ITEM_NEXT_SLOT("wingSelectMenu.navigationItem.next.slot", SettingType.GUISLOT),
    NAGIVATION_ITEM_PREVIOUS_NAME("wingSelectMenu.navigationItem.previous.name", SettingType.STRING),
    NAGIVATION_ITEM_PREVIOUS_MATERIAL("wingSelectMenu.navigationItem.previous.material", SettingType.MATERIAL),
    NAGIVATION_ITEM_PREVIOUS_SLOT("wingSelectMenu.navigationItem.previous.slot", SettingType.GUISLOT),

    FILTER_ITEM_ENABLE("wingSelectMenu.filterItem.enable", SettingType.BOOLEAN),
    FILTER_ITEM_SLOT("wingSelectMenu.filterItem.slot", SettingType.GUISLOT),

    FILTER_ITEM_NO_FILTER_NAME("wingSelectMenu.filterItem.noFilter.name", SettingType.STRING),
    FILTER_ITEM_NO_FILTER_MATERIAL("wingSelectMenu.filterItem.noFilter.material", SettingType.MATERIAL),
    FILTER_ITEM_NO_FILTER_LORE("wingSelectMenu.filterItem.noFilter.lore", SettingType.STRINGLIST),

    FILTER_ITEM_OWNED_WINGS_NAME("wingSelectMenu.filterItem.ownedWings.name", SettingType.STRING),
    FILTER_ITEM_OWNED_WINGS_MATERIAL("wingSelectMenu.filterItem.ownedWings.material", SettingType.MATERIAL),
    FILTER_ITEM_OWNED_WINGS_LORE("wingSelectMenu.filterItem.ownedWings.lore", SettingType.STRINGLIST),

    FILTER_ITEM_UNOWNED_WINGS_NAME("wingSelectMenu.filterItem.unownedWings.name", SettingType.STRING),
    FILTER_ITEM_UNOWNED_WINGS_MATERIAL("wingSelectMenu.filterItem.unownedWings.material", SettingType.MATERIAL),
    FILTER_ITEM_UNOWNED_WINGS_LORE("wingSelectMenu.filterItem.unownedWings.lore", SettingType.STRINGLIST);

    public final String path;
    private final SettingType settingType;

    ConfigSetting(final String path, final SettingType settingType) {
        this.path = path;
        this.settingType = settingType;
    }

    public SettingType getSettingType() {
        return settingType;
    }

    public void setValue(Object value) {
        if (value instanceof Material) {
            value = value.toString();
        }

        Config config = ParticleWings.getInstance().getConfig();
        config.set(this.path, value);
        config.save();
        ParticleWings.getInstance().reload();
    }

    public Object getCurrentValue() {

        Config config = ParticleWings.getInstance().getConfig();

        return switch (this) {
            case WING_VIEW_DISTANCE -> config.getWingViewDistance();
            case WING_MAX_PITCH -> config.getWingMaxPitch();
            case INVISIBILITY_POTION_HIDES_WING -> config.getInvisibilityPotionHidesWing();
            case WING_SELECT_MENU_NAME -> config.getWingSelectMenuName();
            case WING_SELECT_MENU_SIZE -> config.getWingSelectMenuSize();
            case WING_SELECT_MENU_PAGES -> config.getWingSelectMenuPages();
            case REMOVE_WING_ITEM_NAME -> config.getRemoveWingItemName();
            case REMOVE_WING_ITEM_MATERIAL -> config.getRemoveWingItemMaterial();
            case REMOVE_WING_ITEM_SLOT -> config.getRemoveWingSlot();
            case HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_NAME -> config.getHideOtherWingsToggleONItemName();
            case HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_MATERIAL -> config.getHideOtherWingsToggleONItemMaterial();
            case HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_NAME -> config.getHideOtherWingsToggleOFFItemName();
            case HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_MATERIAL -> config.getHideOtherWingsToggleOFFItemMaterial();
            case HIDE_OTHER_WINGS_TOGGLE_SLOT -> config.getHideOtherWingsToggleSlot();
            case SHOW_WING_TOGGLE_ON_ITEM_NAME -> config.getShowWingToggleONItemName();
            case SHOW_WING_TOGGLE_ON_ITEM_MATERIAL -> config.getShowWingToggleONItemMaterial();
            case SHOW_WING_TOGGLE_OFF_ITEM_NAME -> config.getShowWingToggleOFFItemName();
            case SHOW_WING_TOGGLE_OFF_ITEM_MATERIAL -> config.getShowWingToggleOFFItemMaterial();
            case SHOW_WING_TOGGLE_SLOT -> config.getShowWingToggleSlot();
            case NAGIVATION_ITEM_NEXT_NAME -> config.getNavigationNextItemName();
            case NAGIVATION_ITEM_NEXT_MATERIAL -> config.getNavigationNextItemMaterial();
            case NAGIVATION_ITEM_NEXT_SLOT -> config.getNavigationNextSlot();
            case NAGIVATION_ITEM_PREVIOUS_NAME -> config.getNavigationPreviousItemName();
            case NAGIVATION_ITEM_PREVIOUS_MATERIAL -> config.getNavigationPreviousItemMaterial();
            case NAGIVATION_ITEM_PREVIOUS_SLOT -> config.getNavigationPreviousSlot();
            case FILTER_ITEM_ENABLE -> config.getFilterItemEnable();
            case FILTER_ITEM_SLOT -> config.getFilterSlot();
            case FILTER_ITEM_NO_FILTER_NAME -> config.getFilterNoneItemName();
            case FILTER_ITEM_NO_FILTER_MATERIAL -> config.getFilterNoneItemMaterial();
            case FILTER_ITEM_NO_FILTER_LORE -> config.getFilterNoneItemLore();
            case FILTER_ITEM_OWNED_WINGS_NAME -> config.getFilterOwnedItemName();
            case FILTER_ITEM_OWNED_WINGS_MATERIAL -> config.getFilterOwnedItemMaterial();
            case FILTER_ITEM_OWNED_WINGS_LORE -> config.getFilterOwnedItemLore();
            case FILTER_ITEM_UNOWNED_WINGS_NAME -> config.getFilterUnownedItemName();
            case FILTER_ITEM_UNOWNED_WINGS_MATERIAL -> config.getFilterUnownedItemMaterial();
            case FILTER_ITEM_UNOWNED_WINGS_LORE -> config.getFilterUnownedItemLore();
        };
    }

}