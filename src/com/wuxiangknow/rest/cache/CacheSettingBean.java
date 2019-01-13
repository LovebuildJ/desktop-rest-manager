package com.wuxiangknow.rest.cache;

import com.wuxiangknow.rest.gui.SettingGui;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Desciption 设置的缓存对象
 * @Author WuXiang
 * @Date 2019/1/13 11:04
 */
public class CacheSettingBean implements Serializable{

    private static final long serialVersionUID = -563855586972695173L;

    private long maxWorkTime;

    private long restTime;

    private Map<Date,Date> workTimes;

    private  String sleepImagePath;

    private boolean status;
    private boolean autoBoot;
    private boolean weekendDisable;

    public CacheSettingBean(SettingGui settingGui) {
        this.maxWorkTime = settingGui.getMaxWorkTime();
        this.restTime = settingGui.getRestTime();
        this.workTimes = settingGui.getWorkTimes();
        this.sleepImagePath = settingGui.getSleepImagePath();
        this.status = settingGui.isStatus();
        this.autoBoot = settingGui.isAutoBoot();
        this.weekendDisable = settingGui.isWeekendDisable();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getMaxWorkTime() {
        return maxWorkTime;
    }

    public void setMaxWorkTime(long maxWorkTime) {
        this.maxWorkTime = maxWorkTime;
    }

    public long getRestTime() {
        return restTime;
    }

    public void setRestTime(long restTime) {
        this.restTime = restTime;
    }

    public Map<Date, Date> getWorkTimes() {
        return workTimes;
    }

    public void setWorkTimes(Map<Date, Date> workTimes) {
        this.workTimes = workTimes;
    }

    public String getSleepImagePath() {
        return sleepImagePath;
    }

    public void setSleepImagePath(String sleepImagePath) {
        this.sleepImagePath = sleepImagePath;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isAutoBoot() {
        return autoBoot;
    }

    public void setAutoBoot(boolean autoBoot) {
        this.autoBoot = autoBoot;
    }

    public boolean isWeekendDisable() {
        return weekendDisable;
    }

    public void setWeekendDisable(boolean weekendDisable) {
        this.weekendDisable = weekendDisable;
    }
}
