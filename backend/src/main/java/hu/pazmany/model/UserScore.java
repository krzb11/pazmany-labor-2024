package hu.pazmany.model;

public class UserScore {
    private String username;
    private int score;
    private int rank; // Optional, depending on whether you want to compute rank in SQL or in the application

    public UserScore(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public UserScore(String username, int score, int rank) {
        this.username = username;
        this.score = score;
        this.rank = rank;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
