package com.inspirion.stockmarket.services;

import com.inspirion.stockmarket.dao.AppSettingsDAO;
import com.inspirion.stockmarket.entity.AppSettings;
import com.inspirion.stockmarket.model.Setting;

public class SettingService {
    AppSettingsDAO settingsDAO;

    public SettingService(AppSettingsDAO dao) {
        settingsDAO = dao;
    }

    public Setting get() {
        AppSettings s = settingsDAO.get();
        return new Setting(s);
    }

    public void update(AppSettings settings) {
        settingsDAO.update(settings);
    }
}
