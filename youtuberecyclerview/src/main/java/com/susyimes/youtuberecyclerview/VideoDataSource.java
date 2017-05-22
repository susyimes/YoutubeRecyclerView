package com.susyimes.youtuberecyclerview;

import java.util.Arrays;
import java.util.List;

/**
 * @author msahakyan
 */
public class VideoDataSource {

    /**
     * Returns some hard-coded youtube video list
     *
     * @return
     */
    public static List<YoutubeVideo> getVideoList() {

        YoutubeVideo video1 = new YoutubeVideo("hDrSK3yrGM4", "Planet Mercury - from Messenger Space Probe");
        YoutubeVideo video2 = new YoutubeVideo("TR-IWhZZDJY", "Venus planet documentary");
        YoutubeVideo video3 = new YoutubeVideo("P5_GlAOCHyE", "Planet Earth seen from space (Full HD 1080p) ORIGINAL");
        YoutubeVideo video4 = new YoutubeVideo("NHcpKl8loTE", "Mars - The Red Planet");
        YoutubeVideo video5 = new YoutubeVideo("WM9jAO1LPSI", "Giant Planet : Jupiter (New space documentary)");
        YoutubeVideo video6 = new YoutubeVideo("AyFMPdHU1n0", "Our Solar System's Planets: Saturn | in 4K Resolution");
        YoutubeVideo video7 = new YoutubeVideo("1EWTku7Fr0Y", "Planet Uranus");
        YoutubeVideo video8 = new YoutubeVideo("wW49Cc9cGis", "Ten facts about Neptune");
        YoutubeVideo video9 = new YoutubeVideo("LgzM-uV81YE", "Pluto the \"Dwarf Planet\" HD");

        return Arrays.asList(video1, video2, video3, video4, video5, video6, video7, video8, video9);
    }
}
