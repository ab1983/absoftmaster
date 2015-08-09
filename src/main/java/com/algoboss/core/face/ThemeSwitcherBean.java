/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.face;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class ThemeSwitcherBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<String, String> themes;
    private List<Theme> themeList;
    //private List<Theme> advancedThemes;  
    private String theme = "cupertino";

    //private GuestPreferences gp;  
    //public void setGp(GuestPreferences gp) {  
    //  this.gp = gp;  
    //}  
    public Map<String, String> getThemes() {
        return themes;
    }

    public List<Theme> getThemeList() {
        return themeList;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        if (theme != null && !theme.isEmpty()) {
            this.theme = theme;
        }
    }

    public void generateThemes() {
        themeList = new ArrayList();
        themeList.add(new Theme("afterdark", "afterdark"));
        themeList.add(new Theme("afternoon", "afternoon"));
        themeList.add(new Theme("afterwork", "afterwork"));
        themeList.add(new Theme("aristo", "aristo"));
        themeList.add(new Theme("black-tie", "black-tie"));
        themeList.add(new Theme("blitzer", "blitzer"));
        themeList.add(new Theme("bluesky", "bluesky"));
        themeList.add(new Theme("bootstrap", "bootstrap"));
        themeList.add(new Theme("casablanca", "casablanca"));
        themeList.add(new Theme("cruze", "cruze"));
        themeList.add(new Theme("cupertino", "cupertino"));
        themeList.add(new Theme("dark-hive", "dark-hive"));
        themeList.add(new Theme("delta", "delta"));
        themeList.add(new Theme("dot-luv", "dot-luv"));
        themeList.add(new Theme("eggplant", "eggplant"));
        themeList.add(new Theme("excite-bike", "excite-bike"));
        themeList.add(new Theme("flick", "flick"));
        themeList.add(new Theme("glass-x", "glass-x"));
        themeList.add(new Theme("home", "home"));
        themeList.add(new Theme("hot-sneaks", "hot-sneaks"));
        themeList.add(new Theme("humanity", "humanity"));
        themeList.add(new Theme("le-frog", "le-frog"));
        themeList.add(new Theme("midnight", "midnight"));
        themeList.add(new Theme("mint-choc", "mint-choc"));
        themeList.add(new Theme("overcast", "overcast"));
        themeList.add(new Theme("pepper-grinder", "pepper-grinder"));
        themeList.add(new Theme("redmond", "redmond"));
        themeList.add(new Theme("rocket", "rocket"));
        themeList.add(new Theme("sam", "sam"));
        themeList.add(new Theme("smoothness", "smoothness"));
        themeList.add(new Theme("south-street", "south-street"));
        themeList.add(new Theme("start", "start"));
        themeList.add(new Theme("sunny", "sunny"));
        themeList.add(new Theme("swanky-purse", "swanky-purse"));
        themeList.add(new Theme("trontastic", "trontastic"));
        themeList.add(new Theme("ui-darkness", "ui-darkness"));
        themeList.add(new Theme("ui-lightness", "ui-lightness"));
        themeList.add(new Theme("vader", "vader"));

        //advancedThemes = new ArrayList<Theme>();  
        //advancedThemes.add(new Theme("aristo", "aristo.png"));  
        //advancedThemes.add(new Theme("cupertino", "cupertino.png"));  
        //advancedThemes.add(new Theme("trontastic", "trontastic.png"));  
        themes = new LinkedHashMap<String, String>();
        themes.put("afterdark", "afterdark");
        themes.put("afternoon", "afternoon");
        themes.put("afterwork", "afterwork");
        themes.put("Aristo", "aristo");
        themes.put("black-tie", "black-tie");
        themes.put("blitzer", "blitzer");
        themes.put("bluesky", "bluesky");
        themes.put("bootstrap", "bootstrap");
        themes.put("casablanca", "casablanca");
        themes.put("cruze", "cruze");
        themes.put("cupertino", "cupertino");
        themes.put("dark-hive", "dark-hive");
        themes.put("delta", "delta");
        themes.put("dot-luv", "dot-luv");
        themes.put("eggplant", "eggplant");
        themes.put("excite-bike", "excite-bike");
        themes.put("flick", "flick");
        themes.put("glass-x", "glass-x");
        themes.put("home", "home");
        themes.put("hot-sneaks", "hot-sneaks");
        themes.put("humanity", "humanity");
        themes.put("le-frog", "le-frog");
        themes.put("midnight", "midnight");
        themes.put("mint-choc", "mint-choc");
        themes.put("overcast", "overcast");
        themes.put("pepper-grinder", "pepper-grinder");
        themes.put("redmond", "redmond");
        themes.put("rocket", "rocket");
        themes.put("sam", "sam");
        themes.put("smoothness", "smoothness");
        themes.put("south-street", "south-street");
        themes.put("start", "start");
        themes.put("sunny", "sunny");
        themes.put("swanky-purse", "swanky-purse");
        themes.put("trontastic", "trontastic");
        themes.put("ui-darkness", "ui-darkness");
        themes.put("ui-lightness", "ui-lightness");
        themes.put("vader", "vader");
    }

    @PostConstruct
    public void init() {
        //theme = gp.getTheme();  
        themeList = new ArrayList();
        generateThemes();



    }
    //public void saveTheme() {  
    //gp.setTheme(theme);  
    //}  

    //public List<Theme> getAdvancedThemes() {  
    //return advancedThemes;  
    //}  
    public class Theme implements Serializable {

        private static final long serialVersionUID = 1L;
        private String name;
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public Theme(String name, String image) {
            this.name = name;
            this.image = image;
        }
    }
}
