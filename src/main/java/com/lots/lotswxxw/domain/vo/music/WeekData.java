
package com.lots.lotswxxw.domain.vo.music;


public class WeekData {

    private int playCount;
    private int score;
    private Song song;

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return song;
    }

}