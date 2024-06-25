package me.youhavetrouble.particlewings.configuration.settings;

import org.bukkit.Material;

import me.youhavetrouble.particlewings.ParticleWings;
import me.youhavetrouble.particlewings.configuration.WingConfig;
import me.youhavetrouble.particlewings.wing.Wing;

public enum WingSetting implements SettingInterface {

    SHOW_WHEN_MOVING("showWhenMoving", SettingType.BOOLEAN),
    WHITELISTED_WORLDS("whitelistedWorlds", SettingType.STRINGLIST),

    ECONOMY_PRICE_TYPE("economy.priceType", SettingType.STRING), 
    ECONOMY_PRICE("economy.price", SettingType.INT),

    MENU_ITEM_HIDE_IN_MENU("menuItem.hideInMenu", SettingType.BOOLEAN),

    MENU_ITEM_NAME("menuItem.name", SettingType.STRING),
    MENU_ITEM_MATERIAL("menuItem.material", SettingType.MATERIAL),
    MENU_ITEM_SLOT("menuItem.slot", SettingType.GUISLOT),
    MENU_ITEM_PAGE("menuItem.page", SettingType.INT),

    MENU_ITEM_LORE_WHEN_EQUIPPED("menuItem.loreWhenEquipped", SettingType.STRINGLIST),
    MENU_ITEM_LORE_WHEN_UNEQUIPPED("menuItem.loreWhenUnequipped", SettingType.STRINGLIST),
    MENU_ITEM_LORE_WHEN_NO_PERMISSION("menuItem.loreWhenNoPermission", SettingType.STRINGLIST),
    MENU_ITEM_LORE_WHEN_CAN_BUY("menuItem.loreWhenCanBuy", SettingType.STRINGLIST),

    WING_START_VERTICAL("wing.startVertical", SettingType.DOUBLE),
    WING_START_HORIZONTAL_OFFSET("wing.startHorizontalOffset", SettingType.DOUBLE),
    WING_START_DISTANCE_TO_PLAYER("wing.startDistanceToPlayer", SettingType.DOUBLE),
    WING_DISTANCE_BETWEEN_PARTICLES("wing.distanceBetweenParticles", SettingType.DOUBLE),
    WING_TIMER("wing.timer", SettingType.INT),

    WING_FLAP_ANIMATION("wing.flapAnimation", SettingType.BOOLEAN),
    WING_WING_FLAP_SPEED("wing.flapSpeed", SettingType.INT), 
    WING_START_OFFSET("wing.startOffset", SettingType.INT),
    WING_STOP_OFFSET("wing.stopOffset", SettingType.INT), 
    WING_ONLY_ONLY_SIDE("wing.onlyOneSide", SettingType.BOOLEAN);

    public final String path;
    private final SettingType settingType;

    WingSetting(final String path, final SettingType settingType) {
        this.path = path;
        this.settingType = settingType;
    }

    public SettingType getSettingType() {
        return settingType;
    }

    public void setValue(Object value, Wing wing) {
        if (value instanceof Material) {
            value = value.toString();
        }

        wing.getConfig().set(this.path, value);
        wing.getConfig().save();
        ParticleWings.getInstance().reload();
    }

    public Object getCurrentValue(Wing wing) {

        WingConfig wingConfig = wing.getConfig();

        return switch (this) {
            case SHOW_WHEN_MOVING -> wingConfig.getShowWhenMoving();
            case WHITELISTED_WORLDS -> wingConfig.getWhitelistedWorlds();
            case ECONOMY_PRICE_TYPE -> wingConfig.getPriceType();
            case ECONOMY_PRICE -> wingConfig.getPrice();
            case MENU_ITEM_HIDE_IN_MENU -> wingConfig.isHiddenInGUI();
            case MENU_ITEM_NAME -> wingConfig.getGuiItemName();
            case MENU_ITEM_MATERIAL -> wingConfig.getGuiItemMaterial();
            case MENU_ITEM_SLOT -> wingConfig.getGuiSlot();
            case MENU_ITEM_PAGE -> wingConfig.getGuiPage();
            case MENU_ITEM_LORE_WHEN_EQUIPPED -> wingConfig.getLoreWhenEquipped();
            case MENU_ITEM_LORE_WHEN_UNEQUIPPED -> wingConfig.getLoreWhenUnequipped();
            case MENU_ITEM_LORE_WHEN_NO_PERMISSION -> wingConfig.getLoreWhenNoPermission();
            case MENU_ITEM_LORE_WHEN_CAN_BUY -> wingConfig.getloreWhenCanBuy();
            case WING_START_VERTICAL -> wingConfig.getStartVertical();
            case WING_START_HORIZONTAL_OFFSET -> wingConfig.getStartHorizontalOffset();
            case WING_START_DISTANCE_TO_PLAYER -> wingConfig.getStartDistanceToPlayer();
            case WING_DISTANCE_BETWEEN_PARTICLES -> wingConfig.getDistanceBetweenParticles();
            case WING_TIMER -> wingConfig.getWingTimer();
            case WING_FLAP_ANIMATION -> wingConfig.getWingAnimation();
            case WING_WING_FLAP_SPEED -> wingConfig.getWingFlapSpeed();
            case WING_START_OFFSET -> wingConfig.getStartOffset();
            case WING_STOP_OFFSET -> wingConfig.getStopOffset();
            case WING_ONLY_ONLY_SIDE -> wingConfig.getOnlyOneSide();
        };
    }

}
