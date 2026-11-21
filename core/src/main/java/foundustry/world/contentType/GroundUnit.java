package foundustry.world.contentType;

import foundustry.types.UnitType;

public class GroundUnit extends UnitType {
    public GroundUnit(String name) {
        super(name);
        flying = false;
    }
}
