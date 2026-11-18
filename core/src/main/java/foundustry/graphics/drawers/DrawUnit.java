package foundustry.graphics.drawers;

import foundustry.types.UnitType;

import static foundustry.core.Vars.units;

public class DrawUnit extends Drawer {

    public void render() {
        begin();

        try {
            for (UnitType unit : units) {
                unit.render(batch);
            }
        } finally {
            end();
        }
    }
}
