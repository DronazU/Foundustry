
package foundustry.graphics.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import static foundustry.core.Vars.particles;

public class ParticleSystem {

    private final ShapeRenderer renderer = new ShapeRenderer();
    private final Color drawColor = new Color();

    public void update(float delta) {
        for (int i = particles.size - 1; i >= 0; i--) {
            Particle particle = particles.get(i);

            particle.update(delta);

            if (!particle.isAlive()) {
                particles.removeIndex(i);
            }
        }
    }

    public void render(OrthographicCamera camera) {
        if (particles.isEmpty()) {
            return;
        }

        renderer.setProjectionMatrix(camera.combined);

        com.badlogic.gdx.Gdx.gl.glEnable(GL20.GL_BLEND);
        com.badlogic.gdx.Gdx.gl.glBlendFunc(
                GL20.GL_SRC_ALPHA,
                GL20.GL_ONE_MINUS_SRC_ALPHA
        );

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Particle particle : particles) {
            drawColor.set(particle.color);
            drawColor.a *= particle.alpha;

            renderer.setColor(drawColor);

            float halfSize = particle.size / 2f;

            if (particle.shape == ParticleShape.CIRCLE) {
                renderer.circle(
                        particle.x,
                        particle.y,
                        halfSize,
                        16
                );
            } else if (particle.shape == ParticleShape.SQUARE) {
                renderer.rect(
                        particle.x - halfSize,
                        particle.y - halfSize,
                        particle.size,
                        particle.size
                );
            }
        }

        renderer.end();

        com.badlogic.gdx.Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void clear() {
        particles.clear();
    }

    public int getParticleCount() {
        return particles.size;
    }

    public void dispose() {
        renderer.dispose();
        particles.clear();
    }

    public void add(Particle particle) {
        if (particle != null) {
            particles.add(particle);
        }
    }
}