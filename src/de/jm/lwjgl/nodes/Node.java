package de.jm.lwjgl.nodes;

import de.jm.lwjgl.common.RenderContext;

public interface Node {

	void update(int delta);

	void render(RenderContext context);
}
