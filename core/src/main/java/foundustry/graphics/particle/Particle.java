package foundustry.graphics.particle;

import com.badlogic.gdx.graphics.Color;
import foundustry.core.Vars;

public class Particle {

    public float x;
    public float y;

    public float velocityX;
    public float velocityY;

    public float lifetime;
    public float time;

    public float size;
    public float sizeVelocity;

    public Color color;
    public float alpha;
    public float alphaVelocity;

    public ParticleShape shape;

    public boolean removed;

    public Particle(
            float x,
            float y,
            float velocityX,
            float velocityY,
            float lifetime,
            float size,
            float sizeVelocity,
            Color color,
            float alpha,
            float alphaVelocity,
            ParticleShape shape
    ) {
        this.x = x;
        this.y = y;

        this.velocityX = velocityX;
        this.velocityY = velocityY;

        this.lifetime = lifetime;
        this.size = size;
        this.sizeVelocity = sizeVelocity;

        this.color = new Color(color);
        this.alpha = alpha;
        this.alphaVelocity = alphaVelocity;

        this.shape = shape;

        Vars.particles.add(this);
    }

    public void update(float delta) {
        time += delta;

        x += velocityX * delta;
        y += velocityY * delta;

        size += sizeVelocity * delta;
        alpha += alphaVelocity * delta;

        alpha = Math.max(0f, Math.min(1f, alpha));

        if (time >= lifetime || alpha <= 0f || size <= 0f) {
            removed = true;
        }
    }

    public float getProgress() {
        if (lifetime <= 0f) return 1f;

        return Math.min(time / lifetime, 1f);
    }

    public boolean isAlive() {
        return !removed;
    }
}