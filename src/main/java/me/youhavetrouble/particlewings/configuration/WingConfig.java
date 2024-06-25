package me.youhavetrouble.particlewings.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import me.youhavetrouble.particlewings.configuration.settings.WingSetting;
import tigeax.customwings.util.YamlFile;
import me.youhavetrouble.particlewings.wing.WingParticle;

public class WingConfig extends YamlFile {

    private final String ID;

    private boolean hideInGUI;
    private String guiItemName;
    private Material guiItemMaterial;
    private int guiSlot, guiPage;

    private List<String> loreWhenEquipped, loreWhenUnequipped, loreWhenNoPermission, loreWhenCanBuy;

    private boolean showWhenMoving;
    private List<String> whitelistedWorlds;

    private double startVertical, startHorizontalOffset, startDistanceToPlayer, distanceBetweenParticles;
    private int wingTimer;

    private boolean wingAnimation, onlyOneSide;
    private int wingFlapSpeed, startOffset, stopOffset;

    private ArrayList<WingParticle> wingParticles;

    private int wingPrice;
    private String priceType;

    // Hasmap containing the coordinates relative to the player
    // And the assinged particle at that coordinate
    // double[] functions as double[distance from player, height]
    private HashMap<double[], WingParticle> particleCoordinates;

    public WingConfig(JavaPlugin plugin, File configFile) {
        super(plugin, configFile);

        this.ID = configFile.getName().replace(".yml", "").toLowerCase();
    }

    @Override
    protected void updateFile() {
        try {
            load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initDataFromFile() {
        wingParticles = createWingParticles(getConfigurationSection("wing.particles"));
        updateDataFromFile();
    }

    @Override
    protected void updateDataFromFile() {

        showWhenMoving = getBoolean(WingSetting.SHOW_WHEN_MOVING.path, false);
        whitelistedWorlds = parseWhitelistedWorlds(getStringList(WingSetting.WHITELISTED_WORLDS.path).toString());

        wingPrice = getInt(WingSetting.WHITELISTED_WORLDS.path, -1);
        priceType = getString(WingSetting.ECONOMY_PRICE.path, "economy");

        hideInGUI = getBoolean(WingSetting.MENU_ITEM_HIDE_IN_MENU.path, false);

        guiItemName = getColorString(WingSetting.MENU_ITEM_NAME.path);
        guiItemMaterial = Material.valueOf(getString(WingSetting.MENU_ITEM_MATERIAL.path));
        guiSlot = getInt(WingSetting.MENU_ITEM_SLOT.path);
        guiPage = getInt(WingSetting.MENU_ITEM_PAGE.path);

        loreWhenEquipped = getColorStringList(WingSetting.MENU_ITEM_LORE_WHEN_EQUIPPED.path);
        loreWhenUnequipped = getColorStringList(WingSetting.MENU_ITEM_LORE_WHEN_UNEQUIPPED.path);
        loreWhenNoPermission = getColorStringList(WingSetting.MENU_ITEM_LORE_WHEN_NO_PERMISSION.path);
        loreWhenCanBuy = getColorStringList(WingSetting.MENU_ITEM_LORE_WHEN_CAN_BUY.path);

        startVertical = getDouble(WingSetting.WING_START_VERTICAL.path, 0);
        startHorizontalOffset = getDouble(WingSetting.WING_START_HORIZONTAL_OFFSET.path, 0);
        startDistanceToPlayer = getDouble(WingSetting.WING_START_DISTANCE_TO_PLAYER.path, 0);
        distanceBetweenParticles = getDouble(WingSetting.WING_DISTANCE_BETWEEN_PARTICLES.path, 0.1);
        wingTimer = getInt(WingSetting.WING_TIMER.path, 10);

        wingAnimation = getBoolean(WingSetting.WING_FLAP_ANIMATION.path, false);
        wingFlapSpeed = getInt(WingSetting.WING_WING_FLAP_SPEED.path, 0);
        startOffset = getInt(WingSetting.WING_START_OFFSET.path, 30);
        stopOffset = getInt(WingSetting.WING_STOP_OFFSET.path, 70);

        onlyOneSide = getBoolean(WingSetting.WING_ONLY_ONLY_SIDE.path, false);

        particleCoordinates = parseParticleCoordinates(getConfigurationSection("wing.particleLayout"));

    }

    public void reload() {
        super.update();

        for (WingParticle wingParticle : wingParticles) {
            wingParticle.reload();
        }

    }

    public String getID() {
        return ID;
    }

    public boolean isHiddenInGUI() {
        return hideInGUI;
    }

    public int getPrice() {
        return wingPrice;
    }

    public String getGuiItemName() {
        return guiItemName;
    }

    public Material getGuiItemMaterial() {
        return guiItemMaterial;
    }

    public int getGuiSlot() {
        return guiSlot;
    }

    public int getGuiPage() {
        return guiPage;
    }

    public List<String> getLoreWhenEquipped() {
        return loreWhenEquipped;
    }

    public List<String> getLoreWhenUnequipped() {
        return loreWhenUnequipped;
    }

    public List<String> getLoreWhenNoPermission() {
        return loreWhenNoPermission;
    }

    public List<String> getloreWhenCanBuy() {
        List<String> lore = new ArrayList<>();

        for (String string : loreWhenCanBuy) {
            string = string.replace("{PRICE}", wingPrice + "");
        }
        return lore;
    }

    public List<String> getWhitelistedWorlds() {
        return this.whitelistedWorlds;
    }

    public String getWhitelistedWorldsString() {
        return getWhitelistedWorlds().toString().replace("[", "").replace("]", "");
    }

    public boolean getShowWhenMoving() {
        return this.showWhenMoving;
    }

    public double getStartVertical() {
        return startVertical;
    }

	public Double getStartHorizontalOffset() {
		return startHorizontalOffset;
	}

    public Double getStartDistanceToPlayer() {
        return startDistanceToPlayer;
    }

    public double getDistanceBetweenParticles() {
        return distanceBetweenParticles;
    }

    public int getWingTimer() {
        return wingTimer;
    }

    public boolean getWingAnimation() {
        return wingAnimation;
    }

    public int getWingFlapSpeed() {
        return wingFlapSpeed;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getStopOffset() {
        return stopOffset;
    }

    public boolean getOnlyOneSide() {
        return onlyOneSide;
    }

    public ArrayList<WingParticle> getWingParticles() {
        return wingParticles;
    }

    public WingParticle getWingParticleByID(String ID) {
        for (WingParticle wingParticle : wingParticles) {
            if (wingParticle.getID().equals(ID)) {
                return wingParticle;
            }
        }
        return null;
    }


    public String getPriceType() {
        return priceType;
    }

    public HashMap<double[], WingParticle> getParticleCoordinates() {
        return particleCoordinates;
    }

    // Util

    private List<String> parseWhitelistedWorlds(String list) {
        return Arrays.asList(list.replace("]", "").replace("[", "").split(", "));
    }

    // TODO Refractor

    // Turn the data gotten from the config.yml into a HashMap containing the
    // relative coordinates and the assinged wingParticle
    private HashMap<double[], WingParticle> parseParticleCoordinates(ConfigurationSection particleLayout) {

        HashMap<double[], WingParticle> particleCoordinates = new HashMap<>();
        Set<String> rows = particleLayout.getKeys(false);
        double distance;
        double height = startVertical + (rows.size() * distanceBetweenParticles); // Highest vertical point of the wing

        // Go through all the horizontal rows
        for (String rowNumber : rows) {

            height = height - distanceBetweenParticles;
            distance = startDistanceToPlayer;

            String[] particleLine = particleLayout.getString(rowNumber).split(",");

            // Go though each "particle" on the row
            for (String particleID : particleLine) {

                // "-" means there should be no particle at the coordinate
                if (particleID.equals("-")) {
                    distance = distance + distanceBetweenParticles;
                    continue;
                }

                double[] coordinates = new double[2];
                coordinates[0] = distance;
                coordinates[1] = height;

                particleCoordinates.put(coordinates, getWingParticleByID(particleID));

                distance = distance + distanceBetweenParticles;

            }
        }
        return particleCoordinates;
    }

    // Turn the data gotten from the config.yml into all the WingParticles of a wing
    private ArrayList<WingParticle> createWingParticles(ConfigurationSection wingParticlesConfig) {

        ArrayList<WingParticle> particles = new ArrayList<>();

        // Loop throught all the wing particles of the wing
        for (String key : wingParticlesConfig.getKeys(false)) {

            WingParticle wingParticle = new WingParticle(this, key);

            particles.add(wingParticle);
        }

        return particles;

    }
}
