package battletank;

import java.awt.Color;

public class Player {
    protected String name;
    
    protected Color primaryColor = Color.BLUE;
    protected Color secondaryColor = Color.CYAN;
    
    public Player(String name) {
        this.name = name;
    }
    
    public Player(String name, Color primaryColor, Color secondaryColor) {
        this.name = name;
        this.setColors(primaryColor, secondaryColor);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Color getPrimaryColor() {
        return this.primaryColor;
    }
    
    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }
    
    public Color getSecondaryColor() {
        return this.secondaryColor;
    }
    
    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }
    
    public void setColors(Color primaryColor, Color secondaryColor) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }
}
