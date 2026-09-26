package foundustry.types.UnitTypes;

import foundustry.world.content.UnitType;

public class GroundUnit extends UnitType {
    public GroundUnit(String name) {
        super(name);
        flying = false;
    }
}
