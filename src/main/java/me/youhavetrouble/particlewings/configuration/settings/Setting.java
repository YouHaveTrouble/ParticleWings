package me.youhavetrouble.particlewings.configuration.settings;

import me.youhavetrouble.particlewings.wing.Wing;
import me.youhavetrouble.particlewings.wing.WingParticle;


/** Wrapper class for the {@link ConfigSetting}, {@link wingSetting} and {@link wingParticleSetting} Enums */
public class Setting implements SettingInterface {

    SettingInterface setting;
    Wing wing;
    WingParticle wingParticle;
    
    public Setting(ConfigSetting configSetting) {
        this.setting = configSetting;
    }

    public Setting(WingSetting wingSetting, Wing wing) {
        this.setting = wingSetting;
        this.wing = wing;
    }

    public Setting(WingParticleSetting wingParticleSetting, WingParticle wingParticle) {
        this.setting = wingParticleSetting;
        this.wingParticle = wingParticle;
    }

    public SettingType getSettingType() {
        return setting.getSettingType();
    }

    public Object getCurrentValue() {
        
        if (setting instanceof ConfigSetting configSetting) {
            return configSetting.getCurrentValue();
        }

        if (setting instanceof WingSetting wingSetting) {
            return wingSetting.getCurrentValue(wing);
        }

        if (setting instanceof WingParticleSetting wingParticleSetting) {
            return wingParticleSetting.getCurrentValue(wingParticle);
        }

        return null;

    }

    public void setValue(Object value) {
        
        if (setting instanceof ConfigSetting configSetting) {
            configSetting.setValue(value);
        }

        if (setting instanceof WingSetting wingSetting) {
            wingSetting.setValue(value, wing);
        }

        if (setting instanceof WingParticleSetting wingParticleSetting) {
            wingParticleSetting.setValue(value, wingParticle);
        }
        
    }

}
