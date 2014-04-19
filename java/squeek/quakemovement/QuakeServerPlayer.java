package squeek.mods.quakemovement;

import api.player.server.ServerPlayerAPI;
import api.player.server.ServerPlayerBase;

public class QuakeServerPlayer extends ServerPlayerBase {

    private boolean wasVelocityChangedBeforeFall = false;
    public boolean isFallDistanceLeniencyEnabled = false;
    public float increasedFallDistanceLeniency = 3.0F;
    
    public QuakeServerPlayer(ServerPlayerAPI playerapi)
    {
    	super(playerapi);
    }
    
    public void fall( float fallDistance )
    {
    	wasVelocityChangedBeforeFall = this.playerAPI.getVelocityChangedField() || this.player.velocityChanged;

    	if (isFallDistanceLeniencyEnabled)
    	{
    		fallDistance -= increasedFallDistanceLeniency;
    	}
    	super.fall( fallDistance );
    	
    	this.playerAPI.setVelocityChangedField(wasVelocityChangedBeforeFall);
    	this.player.velocityChanged = wasVelocityChangedBeforeFall;
    }
}
