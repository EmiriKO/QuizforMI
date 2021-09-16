package events.tgh2020.androidlab;

import java.util.Random;

public class FortuneTeller {
    private String fortunes[] = {"大吉", "吉", "凶"};

    public String tell(String birthday){
        Random r = new Random();
        int seed = Math.abs(birthday.hashCode());
        int magicNumber = r.nextInt(seed);

        return fortunes[magicNumber%fortunes.length];
    }

}
