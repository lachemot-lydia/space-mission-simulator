package engine.data;

import java.util.ArrayList;

/**
 * Represents a trajectory made up of an ordered sequence of positions.
 * <p>
 * A trajectory is defined by a list of {@link Position} objects describing
 * a path traveled or to be traveled through space.
 * </p>
 */
public class Trajectory {
	
	private ArrayList<Position> positions=new ArrayList<Position>();
	
	public Trajectory(ArrayList<Position> positions) {
		this.positions=positions;
	}
	
	
	//GETTERS / SETTERS
	public ArrayList<Position> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}

	
	
	
	

}
