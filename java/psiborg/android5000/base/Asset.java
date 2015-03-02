package psiborg.android5000.base;

public abstract class Asset {
    public String name, tag;
    private boolean assetLoaded;
    public boolean isLoaded() {
        return assetLoaded;
    }
    public void load()   {}
    public void unload() {}
}
