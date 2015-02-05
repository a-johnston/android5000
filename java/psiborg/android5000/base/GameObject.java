package psiborg.android5000.base;

public abstract class GameObject {
    public String name, tag;
    public Transform transform = new Transform();
	public Shader shader;
    public void load()   {}
    public void unload() {}
    public void step()   {}
}
