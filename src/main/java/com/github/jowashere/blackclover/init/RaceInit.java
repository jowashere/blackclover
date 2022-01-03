package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.api.internal.BCMRace;

public class RaceInit {

    public static final BCMRace NULL = new BCMRace("null", 0, false);

    public static final BCMRace HUMAN = new BCMRace("Human", 6, false).setRaceMessage("You've been born as a human").setStartingMana(150);
    public static final BCMRace DWARF = new BCMRace("Elf", 3, false).setRaceMessage("You've been born as an dwarf").setStartingMana(200).setManaMultiplier(1.3F);
    public static final BCMRace ELF = new BCMRace("Elf", 1, false).setRaceMessage("You've been born as an elf").setStartingMana(250).setManaMultiplier(1.3F);

}
