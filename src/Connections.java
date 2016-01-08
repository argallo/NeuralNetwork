
public class Connections {
	
	private double weight;
	private double deltaWeight;
	
	public Connections(double weight){
		this.weight = weight;
		deltaWeight = 0; // change later
	}
	
	public double getDeltaWeight() {
		return deltaWeight;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setDeltaWeight(double deltaWeight) {
		this.deltaWeight = deltaWeight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

}
