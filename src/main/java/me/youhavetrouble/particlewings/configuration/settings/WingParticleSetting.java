package me.youhavetrouble.particlewings.configuration.settings;

import org.bukkit.Material;

import me.youhavetrouble.particlewings.ParticleWings;
import me.youhavetrouble.particlewings.wing.WingParticle;

public enum WingParticleSetting implements SettingInterface {

    PARTICLE("particle", SettingType.PARTICLE),
    DISTANCE("distance", SettingType.DOUBLE),
    HEIGHT("height", SettingType.DOUBLE),
    ANGLE("angle", SettingType.INT),
    SPEED("speed", SettingType.DOUBLE),
    COLOR("color", SettingType.COLOR),
    NOTE_COLOR("noteColor", SettingType.INT),
    BLOCK_TYPE("blockType", SettingType.MATERIAL);

    public String path;
    private SettingType settingType;

    WingParticleSetting(final String path, final SettingType settingType) {
        this.path = path;
        this.settingType = settingType;
    }

    @Override
    public SettingType getSettingType() {
        return settingType;
    }

    public void setValue(Object value, WingParticle wingParticle) {

        if (value instanceof Material) {
            value = value.toString();
        }

        wingParticle.getParticleConfig().set(this.path, value);
        wingParticle.getWingConfig().save();
        ParticleWings.getInstance().reload();
    }

    public Object getCurrentValue(WingParticle wingParticle) {

        return switch (this) {
            case PARTICLE -> wingParticle.getParticle();
            case DISTANCE -> wingParticle.getDistance();
            case HEIGHT -> wingParticle.getHeight();
            case ANGLE -> wingParticle.getAngle();
            case SPEED -> wingParticle.getSpeed();
            case COLOR -> wingParticle.getColor().asRGB();
            case NOTE_COLOR -> wingParticle.getNoteColor();
            case BLOCK_TYPE -> wingParticle.getMaterialData();
        };

    }

}
