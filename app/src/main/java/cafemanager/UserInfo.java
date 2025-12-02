package cafemanager;

public class UserInfo {
    private boolean continuePlaying;
    private int coins;

    public UserInfo(boolean continuePlaying, int coins){
        this.continuePlaying = continuePlaying;
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }
    
    public boolean getContinuePlaying() {
        return continuePlaying;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setContinuePlaying(boolean continuePlaying) {
        this.continuePlaying = continuePlaying;
    }

}
