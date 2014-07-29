package org.dspace.authority.orcid.model;

import java.util.Map;

/**
 * Created by: Antoine Snyers (antoine at atmire dot com)
 * Date: 16 Dec 2013
 */
public class WorkTitle {

    private String title;
    private String subtitle;
    private Map<String, String> translatedTitles;

    public WorkTitle(String title, String subtitle, Map<String, String> translatedTitles) {
        this.title = title;
        this.subtitle = subtitle;
        this.translatedTitles = translatedTitles;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTranslatedTitles(String languageCode) {
        return translatedTitles.get(languageCode);
    }

    public void setTranslatedTitle(String languageCode, String translatedTitle) {
        translatedTitles.put(languageCode, translatedTitle);
    }

    @Override
    public String toString() {
        return "WorkTitle{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", translatedTitles=" + translatedTitles +
                '}';
    }
}
