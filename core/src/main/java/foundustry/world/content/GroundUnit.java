package foundustry.world.content;

import foundustry.types.UnitType;

public class GroundUnit extends UnitType {
    public GroundUnit(String name) {
        super(name);
        flying = false;
    }
}
