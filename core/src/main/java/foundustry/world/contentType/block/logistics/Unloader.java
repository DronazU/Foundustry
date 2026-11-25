package foundustry.world.contentType.block.logistics;

public class Unloader extends Conveyor {

    public Unloader(String name) {
        super(name);
        canFlip = true;
        speed = 1f;
    }
}
