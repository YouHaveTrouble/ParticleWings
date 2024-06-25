package tigeax.customwings.util;

import org.bukkit.Material;

/*
 * List of all the minecraft particles
 * Assigned are the material and name that are shows when the particle is referenced in a GUI
 */

public enum ParticleItem {

	// 1.13
	BARRIER("BARRIER", "Barrier"),
	BLOCK_CRACK("CRACKED_STONE_BRICKS", "Block Crack"),
	BLOCK_DUST("GLOWSTONE_DUST", "Block Dust"),
	BUBBLE_COLUMN_UP("WATER_BUCKET", "Bubble Column Up"),
	BUBBLE_POP("WATER_BUCKET", "Bubble Pop"),
	CLOUD("FEATHER", "Cloud"),
	CRIT("WOODEN_SWORD", "Crit"),
	CRIT_MAGIC("DIAMOND_SWORD", "Crit Magic"),
	CURRENT_DOWN("WATER_BUCKET", "Current Down"),
	DAMAGE_INDICATOR("GOLDEN_SWORD", "Damage Indicator"),
	DOLPHIN("WATER_BUCKET", "Dolphin"),
	DRAGON_BREATH("DRAGON_HEAD", "Dragon Breath"),
	DRIP_LAVA("LAVA_BUCKET", "Drip Lava"),
	DRIP_WATER("WATER_BUCKET", "Drip Water"),
	ENCHANTMENT_TABLE("ENCHANTING_TABLE", "Enchantment Table"),
	END_ROD("END_ROD", "End Rod"),
	EXPLOSION_HUGE("TNT", "Explosion Huge"),
	EXPLOSION_LARGE("TNT", "Explosion Large"),
	EXPLOSION_NORMAL("TNT", "Explosion Normal"),
	FALLING_DUST("SAND", "Falling Dust"),
	FIREWORKS_SPARK("FIREWORK_ROCKET", "Fireworks Spark"),
	FLAME("BLAZE_POWDER", "Flame"),
	HEART("ROSE_BUSH", "Heart"),
	ITEM_CRACK("ITEM_FRAME", "Item Crack"),
	LAVA("LAVA_BUCKET", "Lava"),
	//MOB_APPEARANCE("ELDER_GUARDIAN_SPAWN_EGG", "Mob Appearance"),
	NAUTILUS("CONDUIT", "Nautilus"),
	NOTE("NOTE_BLOCK", "Note"),
	PORTAL("OBSIDIAN", "Portal"),
	REDSTONE("REDSTONE", "Redstone"),
	SLIME("SLIME_BALL", "Slime"),
	SMOKE_LARGE("BLACK_CONCRETE_POWDER", "Smoke Large"),
	SMOKE_NORMAL("BLACK_CONCRETE_POWDER", "Smoke Normal"),
	SNOWBALL("SNOWBALL", "Snowball"),
	SNOW_SHOVEL("SNOWBALL", "Snow Shovel"),
	SPELL("LINGERING_POTION", "Spell"),
	SPELL_INSTANT("SPLASH_POTION", "Spell Instant"),
	SPELL_MOB("POTION", "Spell Mob"),
	SPELL_MOB_AMBIENT("POTION", "Spell Mob Ambient"),
	SPELL_WITCH("WITCH_SPAWN_EGG", "Spell Witch"),
	SPIT("LLAMA_SPAWN_EGG", "Spit"),
	SQUID_INK("SQUID_SPAWN_EGG", "Squid Ink"),
	SUSPENDED("WATER_BUCKET", "Suspended"),
	SUSPENDED_DEPTH("WATER_BUCKET", "Suspended Depth"),
	SWEEP_ATTACK("STONE_SWORD", "Sweep Attack"),
	TOTEM("TOTEM_OF_UNDYING", "Totem"),
	TOWN_AURA("GRAY_WOOL", "Town Aura"),
	VILLAGER_ANGRY("BLACK_GLAZED_TERRACOTTA", "Villager Angry"),
	VILLAGER_HAPPY("EMERALD", "Villager Happy"),
	WATER_BUBBLE("WATER_BUCKET", "Water Bubble"),
	WATER_DROP("WATER_BUCKET", "Water Drop"),
	WATER_SPLASH("WATER_BUCKET", "Water Splash"),
	WATER_WAKE("WATER_BUCKET", "Water Wake"),

	// 1.14
	COMPOSTER("EMERALD", "Composter"),
	CAMPFIRE_SIGNAL_SMOKE("CAMPFIRE", "Campfire Signal Smoke"),
	CAMPFIRE_COSY_SMOKE("CAMPFIRE", "Campfire Cosy Smoke"),
	SNEEZE("GREEN_DYE", "Sneeze"),

	// 1.15
	DRIPPING_HONEY("HONEYCOMB", "Dripping Honey"),
	FALLING_HONEY("HONEYCOMB", "Falling Honey"),
	FALLING_NECTAR("HONEYCOMB", "Falling Nectar"),
	LANDING_HONEY("HONEYCOMB", "Landing Honey"),

	// 1.16
	ASH("SOUL_SAND", "Ash"),
	CRIMSON_SPORE("CRIMSON_FUNGUS", "Crimson Spore"),
	DRIPPING_OBSIDIAN_TEAR("CRYING_OBSIDIAN", "Dripping Crying Obsidian"),
	FALLING_OBSIDIAN_TEAR("CRYING_OBSIDIAN", "Falling Crying Obsidian"),
	FLASH("CRYING_OBSIDIAN", "Flash"),
	LANDING_OBSIDIAN_TEAR("CRYING_OBSIDIAN", "Landing Crying Obsidian"),
	REVERSE_PORTAL("CRYING_OBSIDIAN", "Reverse Portal"),
	SOUL("SOUL_SOIL", "Soul"),
	SOUL_FIRE_FLAME("SOUL_SOIL", "SoulFire Flame"),
	WARPED_SPORE("WARPED_FUNGUS", "Warped Spore"),
	WHITE_ASH("SOUL_SOIL", "White Ash"),

	// 1.17
	LIGHT("YELLOW_CONCRETE_POWDER", "Light"),
	FALLING_SPORE_BLOSSOM("SPORE_BLOSSOM", "Falling Spore Blossom"),
	SPORE_BLOSSOM_AIR("SPORE_BLOSSOM", "Spoer Blossom Air"),
	SMALL_FLAME("BLAZE_POWDER", "Small Flame"),
	SNOWFLAKE("SNOWBALL", "Snowflake"),
	DRIPPING_DRIPSTONE_LAVA("LAVA_BUCKET", "Dripping Dripstone Lava"),
	FALLING_DRIPSTONE_LAVA("LAVA_BUCKET", "Falling Dripstone Lava"),
	DRIPPING_DRIPSTONE_WATER("WATER_BUCKET", "Dripping Dripstone Water"),
	FALLING_DRIPSTONE_WATER("WATER_BUCKET", "Falling Dripstone Water"),
	GLOW_SQUID_INK("GLOW_INK_SAC", "Glow Squid Ink"),
	GLOW("GLOWSTONE_DUST", "Glow"),
	WAX_ON("COPPER_INGOT", "Wax On"),
	WAX_OFF("IRON_INGOT", "Wax off"),
	ELECTRIC_SPARK("LIGHT_GRAY_DYE", "Electric Spark"),
	SCRAPE("CYAN_DYE", "Scrape");

	private final String material;
	private final String name;

	ParticleItem(final String material, final String name) {
		this.material = material;
		this.name = name;
	}

	public Material getMaterial() { return Material.valueOf(material); }

	public String getName() { return name; }

}
